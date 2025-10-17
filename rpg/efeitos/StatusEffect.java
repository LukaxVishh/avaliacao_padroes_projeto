package rpg.efeitos;

import rpg.batalha.BattleLog;
import rpg.personagens.Personagem;

/**
 * Efeito de status genérico que "tica" no início do turno do portador.
 * Justificativa: isola regras de efeitos do núcleo de batalha/personagem.
 */
public interface StatusEffect {
    String getNome();
    void onTurnStart(Personagem alvo, BattleLog log);
    boolean expirou();
}
