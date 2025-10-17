package rpg.armas;

import rpg.batalha.BattleLog;
import rpg.personagens.Personagem;

import java.util.List;
import java.util.Random;

public class AdagaSombria implements Arma {
    @Override public String getNome() { return "Adaga Sombria"; }
    @Override public WeaponType getTipo() { return WeaponType.DAGGER; }
    @Override public int getCustoMana() { return 10; }
    @Override public int getDanoBase() { return 10; }

    @Override
    public boolean atendeRequisitos(Personagem p) {
        return p.getDestreza() >= 12;
    }

    @Override
    public void atacar(Personagem atacante, Personagem alvo, List<Personagem> inimigos, Random rng, BattleLog log, boolean alvoDesprevenido) {
        int dano = getDanoBase();
        if (alvoDesprevenido) {
            dano *= 3;
            log.add("🗡️ Ataque Furtivo! Dano triplo em alvo desprevenido.");
        }
        dano = aplicarCritico(dano, rng, log);
        log.add(atacante.getNome() + " acerta " + dano + " com " + getNome() + ".");
        alvo.receberDano(dano, DamageType.PHYSICAL, log, getNome());
    }
}
