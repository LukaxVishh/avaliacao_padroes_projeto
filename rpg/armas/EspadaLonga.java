package rpg.armas;

import rpg.batalha.BattleLog;
import rpg.efeitos.Sangramento;
import rpg.personagens.Personagem;

import java.util.List;
import java.util.Random;

public class EspadaLonga implements Arma {
    @Override public String getNome() { return "Espada Longa"; }
    @Override public WeaponType getTipo() { return WeaponType.SWORD; }
    @Override public int getCustoMana() { return 0; }
    @Override public int getDanoBase() { return 15; }

    @Override
    public boolean atendeRequisitos(Personagem p) {
        return p.getForca() >= 10;
    }

    @Override
    public void atacar(Personagem atacante, Personagem alvo, List<Personagem> inimigos, Random rng, BattleLog log, boolean alvoDesprevenido) {
        int dano = aplicarCritico(getDanoBase(), rng, log);
        log.add("🗡️ " + atacante.getNome() + " golpeia com " + getNome() + " causando " + dano + ".");
        alvo.receberDano(dano, DamageType.PHYSICAL, log, getNome());

        if (rng.nextDouble() < 0.30 && alvo.estaVivo()) {
            alvo.adicionarEfeito(new Sangramento(5, 3), log);
            log.add("✂️ Corte Profundo: sangramento aplicado (30%).");
        }
    }
}
