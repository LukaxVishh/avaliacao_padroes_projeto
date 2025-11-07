package avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.engine;

import avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.domain.PlantState;

/**
 * Aresta do grafo de estados. Guarda valida condições complexas.
 * Permite indicar se a transição é bidirecional (inverte automaticamente).
 */
public final class Transition {
    public final PlantState from;
    public final PlantState to;
    public final boolean bidirectional;
    public final TransitionGuard guard;

    public Transition(PlantState from, PlantState to, boolean bidirectional, TransitionGuard guard) {
        this.from = from;
        this.to = to;
        this.bidirectional = bidirectional;
        this.guard = guard;
    }
}
