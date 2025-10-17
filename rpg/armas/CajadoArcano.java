package rpg.armas;

import rpg.batalha.BattleLog;
import rpg.efeitos.Queimadura;
import rpg.personagens.Personagem;

import java.util.List;
import java.util.Random;

public class CajadoArcano implements Arma {
    @Override public String getNome() { return "Cajado Arcano"; }
    @Override public WeaponType getTipo() { return WeaponType.STAFF; }
    @Override public int getCustoMana() { return 25; }
    @Override public int getDanoBase() { return 8; }

    @Override
    public boolean atendeRequisitos(Personagem p) {
        return p.getInteligencia() >= 12;
    }

    @Override
    public void atacar(Personagem atacante, Personagem alvo, List<Personagem> inimigos, Random rng, BattleLog log, boolean alvoDesprevenido) {
        int dano = aplicarCritico(getDanoBase(), rng, log);
        log.add("🔮 " + atacante.getNome() + " conjura Bola de Fogo causando " + dano + ".");
        alvo.receberDano(dano, DamageType.FIRE, log, getNome());
        if (alvo.estaVivo()) {
            alvo.adicionarEfeito(new Queimadura(2), log);
            log.add("🔥 Queimadura aplicada por 2 turnos.");
        }
    }
}
