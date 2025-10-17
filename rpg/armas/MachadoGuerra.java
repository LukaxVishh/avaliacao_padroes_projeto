package rpg.armas;

import rpg.batalha.BattleLog;
import rpg.efeitos.Atordoado;
import rpg.personagens.Personagem;

import java.util.List;
import java.util.Random;

public class MachadoGuerra implements Arma {
    @Override public String getNome() { return "Machado de Guerra"; }
    @Override public WeaponType getTipo() { return WeaponType.AXE; }
    @Override public int getCustoMana() { return 5; }
    @Override public int getDanoBase() { return 18; }

    @Override
    public boolean atendeRequisitos(Personagem p) {
        return p.getForca() >= 15;
    }

    @Override
    public void atacar(Personagem atacante, Personagem alvo, List<Personagem> inimigos, Random rng, BattleLog log, boolean alvoDesprevenido) {
        int dano = aplicarCritico(getDanoBase(), rng, log);
        log.add("🪓 " + atacante.getNome() + " desfere Golpe Esmagador causando " + dano + ".");
        alvo.receberDano(dano, DamageType.PHYSICAL, log, getNome());
        if (rng.nextDouble() < 0.25 && alvo.estaVivo()) {
            alvo.adicionarEfeito(new Atordoado(1), log);
            log.add("💥 Atordoamento (25%) aplicado: perderá 1 turno.");
        }
    }
}
