package rpg.personagens;

import rpg.armas.WeaponType;
import rpg.batalha.BattleLog;

import java.util.EnumSet;
import java.util.Set;

/** Híbrido de Guerreiro/Mago para a extensão. */
public class Paladino extends Personagem {
    public Paladino(String nome) {
        super(nome, 12, 8, 12, 110, 90);
    }

    @Override
    public void aplicarPassivaInicioTurno(BattleLog log) {
        // Pequena proteção divina: reduz 10% do próximo dano via mitigarDano
    }

    @Override
    public Set<WeaponType> tiposPermitidos() {
        return EnumSet.of(WeaponType.SWORD, WeaponType.SPEAR);
    }

    @Override
    public int mitigarDano(int bruto) {
        return (int) Math.round(bruto * 0.9);
    }
}
