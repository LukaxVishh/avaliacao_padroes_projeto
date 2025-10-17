package rpg.armas;

import rpg.batalha.BattleLog;
import rpg.personagens.Personagem;
import java.util.List;
import java.util.Random;

/**
 * Strategy: cada arma implementa esta interface e define seu "comportamento de ataque".
 * Justificativa: permite trocar/estender armas sem mudar Personagem/Batalha.
 */
public interface Arma {
    String getNome();
    WeaponType getTipo();
    int getCustoMana();
    int getDanoBase();

    /** Requisitos mínimos de atributos por arma. */
    boolean atendeRequisitos(Personagem p);

    /**
     * Executa o ataque. Pode ser single-target ou em área.
     * @param atacante quem ataca
     * @param alvo alvo primário (pode ser ignorado por armas em área)
     * @param inimigos lista de inimigos vivos do alvo (para efeitos em área)
     * @param rng gerador
     * @param log log de batalha
     * @param alvoDesprevenido dica para efeitos como "Ataque Furtivo" (adaga)
     */
    void atacar(Personagem atacante,
                Personagem alvo,
                List<Personagem> inimigos,
                Random rng,
                BattleLog log,
                boolean alvoDesprevenido);

    /** util: crítico (10% padrão). Mantido aqui para reuso/coerência entre armas. */
    default int aplicarCritico(int dano, Random rng, BattleLog log) {
        if (rng.nextDouble() < 0.10) {
            log.add("⚡ Crítico! Dano x2");
            return dano * 2;
        }
        return dano;
    }
}
