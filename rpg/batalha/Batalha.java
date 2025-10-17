package rpg.batalha;

import rpg.personagens.Personagem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Orquestra turnos, verifica vitória/derrota e permite troca de arma via Personagem.equipar(...).
 * Simples: Team A x Team B; em cada rodada, todos vivos de A agem, depois os de B.
 */
public class Batalha {
    private final List<Personagem> timeA = new ArrayList<>();
    private final List<Personagem> timeB = new ArrayList<>();
    private final BattleLog log = new BattleLog();

    public Batalha(List<Personagem> a, List<Personagem> b) {
        timeA.addAll(a);
        timeB.addAll(b);
    }

    public BattleLog getLog() { return log; }

    private List<Personagem> vivos(List<Personagem> lista) {
        return lista.stream().filter(Personagem::estaVivo).collect(Collectors.toList());
    }

    private Personagem primeiroAlvoVivo(List<Personagem> inimigos) {
        return inimigos.stream().filter(Personagem::estaVivo).findFirst().orElse(null);
    }

    private void rodada(List<Personagem> atacantes, List<Personagem> defensores) {
        for (Personagem p : vivos(atacantes)) {
            // início do turno: passivas e efeitos
            p.inicioDoTurno(log);
            if (!p.estaVivo()) continue;

            List<Personagem> inimigosVivos = vivos(defensores);
            if (inimigosVivos.isEmpty()) return;

            Personagem alvo = primeiroAlvoVivo(inimigosVivos);
            p.agir(alvo, inimigosVivos, log);
        }
    }

    public String executar() {
        log.add("=== Início da Batalha ===");
        // loop simples até que um time caia
        int maxTurnos = 50; // segurança anti-loop
        int turno = 1;
        while (maxTurnos-- > 0) {
            log.add("\n--- Turno " + (turno++) + " ---");

            rodada(timeA, timeB);
            if (vivos(timeB).isEmpty()) {
                log.add("\n✅ Vitória do Time A!");
                return "A";
            }
            rodada(timeB, timeA);
            if (vivos(timeA).isEmpty()) {
                log.add("\n❌ Vitória do Time B!");
                return "B";
            }
        }
        log.add("\n⚠️ Batalha empatada por limite de turnos.");
        return "EMPATE";
    }
}
