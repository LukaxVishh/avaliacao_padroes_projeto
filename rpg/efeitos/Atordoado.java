package rpg.efeitos;

import rpg.batalha.BattleLog;
import rpg.personagens.Personagem;

public class Atordoado implements StatusEffect {
    private int turnosRestantes;

    public Atordoado(int turnos) {
        this.turnosRestantes = turnos;
    }

    @Override public String getNome() { return "Atordoado"; }

    @Override
    public void onTurnStart(Personagem alvo, BattleLog log) {
        if (turnosRestantes <= 0) return;
        alvo.setAtordoadoEsteTurno(true);
        log.add("💫 " + alvo.getNome() + " está atordoado e perderá o turno.");
        turnosRestantes--;
    }

    @Override
    public boolean expirou() { return turnosRestantes <= 0; }
}
