package rpg.personagens;

import rpg.armas.WeaponType;
import rpg.batalha.BattleLog;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

public class Arqueiro extends Personagem {
    private final Random localRng = new Random();

    public Arqueiro(String nome) {
        super(nome, 8, 15, 7, 90, 80);
    }

    @Override
    public void aplicarPassivaInicioTurno(BattleLog log) {
        // Esquiva é checada no momento de receber dano
    }

    @Override
    public Set<WeaponType> tiposPermitidos() {
        return EnumSet.of(WeaponType.BOW, WeaponType.DAGGER);
    }

    @Override
    public boolean tentaEsquivar() {
        return localRng.nextDouble() < 0.25; // 25%
    }
}
