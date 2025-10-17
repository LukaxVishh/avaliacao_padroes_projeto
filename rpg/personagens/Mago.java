package rpg.personagens;

import rpg.armas.WeaponType;
import rpg.batalha.BattleLog;

import java.util.EnumSet;
import java.util.Set;

public class Mago extends Personagem {
    public Mago(String nome) {
        super(nome, 5, 7, 18, 70, 150);
    }

    @Override
    public void aplicarPassivaInicioTurno(BattleLog log) {
        int antes = mana;
        mana = Math.min(mana + 10, manaMax); // +10 mana/turno
        int ganho = mana - antes;
        if (ganho > 0) {
            log.add("🔷 " + nome + " regenera " + ganho + " de mana (passiva).");
        }
    }

    @Override
    public Set<WeaponType> tiposPermitidos() {
        return EnumSet.of(WeaponType.STAFF, WeaponType.DAGGER);
    }
}
