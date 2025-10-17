package rpg.efeitos;

import rpg.batalha.BattleLog;
import rpg.personagens.Personagem;
import rpg.armas.DamageType;

public class Queimadura implements StatusEffect {
    private int turnosRestantes;

    public Queimadura(int turnos) {
        this.turnosRestantes = turnos;
    }

    @Override public String getNome() { return "Queimadura"; }

    @Override
    public void onTurnStart(Personagem alvo, BattleLog log) {
        if (turnosRestantes <= 0) return;
        int dano = 10;
        alvo.receberDano(dano, DamageType.FIRE, log, "Queimadura");
        log.add("🔥 " + alvo.getNome() + " sofre " + dano + " de queimadura (" + turnosRestantes + " turno(s) restante(s)).");
        turnosRestantes--;
    }

    @Override
    public boolean expirou() { return turnosRestantes <= 0; }
}
