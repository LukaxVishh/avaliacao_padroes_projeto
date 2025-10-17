package rpg.armas;

import rpg.batalha.BattleLog;
import rpg.efeitos.Atordoado;
import rpg.personagens.Personagem;

import java.util.List;
import java.util.Random;

/** Extensão: nova arma com efeito único "Cura Radiante" e chance de atordoar. */
public class LancaSagrada implements Arma {
    @Override public String getNome() { return "Lança Sagrada"; }
    @Override public WeaponType getTipo() { return WeaponType.SPEAR; }
    @Override public int getCustoMana() { return 10; }
    @Override public int getDanoBase() { return 14; }

    @Override
    public boolean atendeRequisitos(Personagem p) {
        return p.getForca() >= 12 && p.getInteligencia() >= 10;
    }

    @Override
    public void atacar(Personagem atacante, Personagem alvo, List<Personagem> inimigos, Random rng, BattleLog log, boolean alvoDesprevenido) {
        int dano = aplicarCritico(getDanoBase(), rng, log);
        log.add("🪄 " + atacante.getNome() + " perfura com " + getNome() + " causando " + dano + ".");
        alvo.receberDano(dano, DamageType.PHYSICAL, log, getNome());

        // Efeito único: cura o usuário em 5
        atacante.curar(5, log);
        // 20% de aplicar atordoado (reutiliza efeito já implementado)
        if (rng.nextDouble() < 0.20 && alvo.estaVivo()) {
            alvo.adicionarEfeito(new Atordoado(1), log);
            log.add("🌟 Luz Purificadora: " + alvo.getNome() + " é atordoado (20%).");
        }
    }
}
