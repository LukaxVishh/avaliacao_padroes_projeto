package avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.facade;

import avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.domain.PlantState;
import avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.domain.Telemetry;
import avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.engine.StateMachine;
import avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.engine.Transition;

/**
 * Facade: ponto único para operar a usina sem expor detalhes da máquina.
 * - Cliente atualiza telemetria e solicita mudanças por alto nível.
 * - Mantém o sistema em DIP (cliente -> abstração simples).
 */
public final class NuclearController {

    private final StateMachine sm = new StateMachine();

    public NuclearController() {
        // ===== Registro de transições e guardas =====

        // OPERACAO_NORMAL -> ALERTA_AMARELO (unidirecional aqui; a volta registramos explicitamente)
        sm.register(new Transition(
                PlantState.OPERACAO_NORMAL,
                PlantState.ALERTA_AMARELO,
                false,
                (s, t) -> t != null && t.temperaturaC > 300.0
        ));

        // ALERTA_AMARELO -> OPERACAO_NORMAL (bidirecionalidade explícita com outra guarda)
        sm.register(new Transition(
                PlantState.ALERTA_AMARELO,
                PlantState.OPERACAO_NORMAL,
                false,
                (s, t) -> t != null && t.temperaturaC <= 300.0
        ));

        // ALERTA_AMARELO -> ALERTA_VERMELHO (tempo acima de 400°C por >30s)
        sm.register(new Transition(
                PlantState.ALERTA_AMARELO,
                PlantState.ALERTA_VERMELHO,
                false,
                (s, t) -> t != null && t.temperaturaC > 400.0 && s.getAbove400Duration().getSeconds() > 30
        ));

        // ALERTA_VERMELHO -> EMERGENCIA (unidirecional)
        sm.register(new Transition(
                PlantState.ALERTA_VERMELHO,
                PlantState.EMERGENCIA,
                false,
                (s, t) -> t != null && t.resfriamentoFalhou
        ));

        // DESLIGADA -> OPERACAO_NORMAL (start-up)
        sm.register(new Transition(
                PlantState.DESLIGADA,
                PlantState.OPERACAO_NORMAL,
                false,
                (s, t) -> t != null && !t.resfriamentoFalhou // exemplo simples
        ));
    }

    public void updateTelemetry(Telemetry telemetry) {
        sm.updateTelemetry(telemetry);
    }

    public boolean request(PlantState target) {
        return sm.request(target);
    }

    public String state() {
        return sm.getReportedState();
    }

    public void enterMaintenance() {
        sm.setMaintenance(true);
    }

    public void exitMaintenance() {
        sm.setMaintenance(false);
    }
}
