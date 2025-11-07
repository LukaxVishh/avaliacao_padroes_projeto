package avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.engine;

import avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.domain.Telemetry;

/**
 * Função de guarda (GoF Strategy leve) para validação de transições.
 * ISP: interface mínima; OCP: novas guardas sem alterar clientes.
 */
@FunctionalInterface
public interface TransitionGuard {
    boolean allow(StateMachine sm, Telemetry telem);
}
