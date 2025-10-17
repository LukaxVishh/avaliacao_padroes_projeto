package rpg.armas;

import rpg.batalha.BattleLog;
import rpg.personagens.Personagem;

import java.util.List;
import java.util.Random;

public class ArcoElfico implements Arma {
    @Override public String getNome() { return "Arco Élfico"; }
    @Override public WeaponType getTipo() { return WeaponType.BOW; }
    @Override public int getCustoMana() { return 15; }
    @Override public int getDanoBase() { return 12; }

    @Override
    public boolean atendeRequisitos(Personagem p) {
        return p.getDestreza() >= 8;
    }

    @Override
    public void atacar(Personagem atacante, Personagem alvo, List<Personagem> inimigos, Random rng, BattleLog log, boolean alvoDesprevenido) {
        log.add("🏹 " + atacante.getNome() + " usa Chuva de Flechas (área).");
        for (Personagem e : inimigos) {
            if (!e.estaVivo()) continue;
            int dano = aplicarCritico(getDanoBase(), rng, log);
            e.receberDano(dano, DamageType.PHYSICAL, log, getNome());
        }
    }
}
