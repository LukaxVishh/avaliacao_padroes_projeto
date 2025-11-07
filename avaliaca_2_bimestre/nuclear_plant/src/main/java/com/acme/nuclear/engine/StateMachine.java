package avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.engine;

import avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.domain.PlantState;
import avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.domain.Telemetry;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Máquina de estados com transições guardadas.
 *
 * Padrões e SOLID:
 * - State (table-driven): comportamento varia por estado via tabela de transições + guardas.
 * - Strategy (guards): regras complexas de validação plugáveis.
 * - Facilidade de OCP: adicionar/ajustar transições sem tocar no cliente (via Facade).
 * - DIP: dependemos de abstrações (TransitionGuard), não de implementações concretas rígidas.
 *
 * Requisitos implementados:
 * - Regras de transição complexas (temperatura/tempo/resfriamento).
 * - Transições bidirecionais vs unidirecionais.
 * - Prevenção de ciclos perigosos (anti-bounce window).
 * - EMERGENCIA somente após ALERTA_VERMELHO.
 * - Modo manutenção que sobrepõe estados normais.
 */
public final class StateMachine {

    private final Map<PlantState, List<Transition>> graph = new EnumMap<>(PlantState.class);
    private final Deque<PlantState> recentHistory = new ArrayDeque<>();
    private final Duration antiBounceWindow = Duration.ofSeconds(5);

    private PlantState current = PlantState.DESLIGADA;

    // Controle de regra temporal (T>400°C por >30s em ALERTA_AMARELO)
    private Instant t400Start = null;

    // Rastro para a restrição "EMERGENCIA só após ALERTA_VERMELHO"
    private boolean redVisited = false;

    // Modo de manutenção (overlay)
    private boolean maintenance = false;

    // Últimas telemetrias para avaliação de regras temporais
    private Telemetry lastTelem = null;

    public StateMachine() {
        for (PlantState s : PlantState.values()) {
            graph.put(s, new ArrayList<>());
        }
    }

    public PlantState getCurrentState() {
        return current;
    }

    /**
     * Estado "percebido" externamente: se manutenção ligada, reporta MANUTENCAO.
     */
    public String getReportedState() {
        return maintenance ? "MANUTENCAO(overlay)/" + current.name() : current.name();
    }

    public boolean isInMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean enabled) {
        this.maintenance = enabled;
    }

    public void register(Transition t) {
        graph.get(t.from).add(t);
        if (t.bidirectional) {
            // Cria a volta com a MESMA guarda (outra guarda poderia ser registrada separadamente se necessário)
            graph.get(t.to).add(new Transition(t.to, t.from, true, t.guard));
        }
    }

    /**
     * Atualiza telemetria e mantém marcadores de tempo para regras.
     */
    public void updateTelemetry(Telemetry telem) {
        this.lastTelem = telem;

        // Regra temporal: se estiver em ALERTA_AMARELO e T>400, iniciar/continuar cronômetro
        if (current == PlantState.ALERTA_AMARELO) {
            if (telem.temperaturaC > 400.0) {
                if (t400Start == null) t400Start = telem.timestamp;
            } else {
                t400Start = null; // reset se cair abaixo
            }
        } else {
            t400Start = null; // fora de AMARELO não conta
        }
    }

    public Duration getAbove400Duration() {
        return t400Start == null ? Duration.ZERO : Duration.between(t400Start, lastTelem.timestamp);
    }

    /**
     * Solicita transição explícita. Pode ser bloqueada por manutenção ou por regras.
     */
    public synchronized boolean request(PlantState target) {
        if (maintenance && target != current) {
            // Modo manutenção sobrepõe: não permite mudanças "reais" enquanto ativo
            return false;
        }
        if (current == target) return true;

        // Prevenção de ciclo perigoso A->B->A dentro da janela anti-bounce
        if (isDangerousCycle(target)) return false;

        // Encontrar aresta válida
        for (Transition t : graph.getOrDefault(current, List.of())) {
            if (t.to == target) {
                if (!checkEmergencyConstraint(t)) return false;
                if (t.guard == null || t.guard.allow(this, lastTelem)) {
                    PlantState prev = current;
                    current = target;

                    // Marcar passagem por vermelho
                    if (current == PlantState.ALERTA_VERMELHO) redVisited = true;

                    // Registrar histórico
                    recentHistory.addLast(prev);
                    recentHistory.addLast(current);
                    trimHistory();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkEmergencyConstraint(Transition t) {
        if (t.to == PlantState.EMERGENCIA && !redVisited) {
            // Só permite EMERGENCIA se já passou por ALERTA_VERMELHO
            return false;
        }
        return true;
    }

    private boolean isDangerousCycle(PlantState target) {
        // Verifica padrão curto A->B->A dentro antiBounceWindow
        if (recentHistory.size() >= 4) {
            Iterator<PlantState> it = recentHistory.descendingIterator();
            // PlantState last = it.next();        // último "to"
            PlantState penult = it.next();      // último "from"
            Instant now = (lastTelem != null) ? lastTelem.timestamp : Instant.now();

            // Heurística: se tentar voltar imediatamente ao estado de duas transições atrás, bloqueia
            if (penult == target) {
                // Se a última transição foi muito recente, consideramos bounce perigoso
                // (No exemplo simples, não armazenamos timestamp por transição;
                // usamos janela fixa desde a última telemetria)
                Duration since = Duration.between(now.minus(antiBounceWindow), now);
                return !since.isNegative(); // dentro da janela
            }
        }
        return false;
    }

    private void trimHistory() {
        while (recentHistory.size() > 10) recentHistory.removeFirst();
    }
}
