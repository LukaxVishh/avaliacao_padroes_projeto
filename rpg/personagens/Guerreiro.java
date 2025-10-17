package rpg.personagens;

import rpg.armas.WeaponType;
import rpg.batalha.BattleLog;

import java.util.EnumSet;
import java.util.Set;

public class Guerreiro extends Personagem {
    public Guerreiro(String nome) {
        super(nome, 15, 8, 5, 120, 50);
    }

    @Override
    public void aplicarPassivaInicioTurno(BattleLog log) {
        // Pele Dura: efeito passivo aplicado na mitigação (abaixo)
        // não há ação por turno
    }

    @Override
    public Set<WeaponType> tiposPermitidos() {
        return EnumSet.of(WeaponType.SWORD, WeaponType.AXE);
    }

    @Override
    public int mitigarDano(int bruto) {
        return (int) Math.round(bruto * 0.8); // -20%
    }
}
