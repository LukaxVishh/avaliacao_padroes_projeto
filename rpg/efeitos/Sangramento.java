package rpg.efeitos;

import rpg.batalha.BattleLog;
import rpg.personagens.Personagem;

public class Sangramento implements StatusEffect {
    private int turnosRestantes;
    private final int danoPorTurno;

    public Sangramento(int danoPorTurno, int turnos) {
        this.danoPorTurno = danoPorTurno;
        this.turnosRestantes = turnos;
    }

    @Override public String getNome() { return "Sangramento"; }

    @Override
    public void onTurnStart(Personagem alvo, BattleLog log) {
        if (turnosRestantes <= 0) return;
        alvo.receberDano(danoPorTurno, rpg.armas.DamageType.PHYSICAL, log, "Sangramento");
        log.add("🩸 " + alvo.getNome() + " sofre " + danoPorTurno + " de sangramento (" + turnosRestantes + " turno(s) restante(s)).");
        turnosRestantes--;
    }

    @Override
    public boolean expirou() { return turnosRestantes <= 0; }
}
