package rpg.personagens;

import rpg.armas.*;
import rpg.batalha.BattleLog;
import rpg.efeitos.StatusEffect;

import java.util.*;

public abstract class Personagem {
    protected final String nome;
    protected int forca, destreza, inteligencia;
    protected int vida, mana;
    protected final int vidaMax, manaMax;

    protected Arma arma; // Strategy plugável
    protected final List<StatusEffect> efeitos = new ArrayList<>();
    protected boolean atordoadoEsteTurno = false;
    protected boolean jaAguiuNoCombate = false; // para "desprevenido"

    protected final Random rng = new Random();

    protected Personagem(String nome, int forca, int destreza, int inteligencia, int vida, int mana) {
        this.nome = nome;
        this.forca = forca;
        this.destreza = destreza;
        this.inteligencia = inteligencia;
        this.vida = vida;
        this.mana = mana;
        this.vidaMax = vida;
        this.manaMax = mana;
    }

    public String getNome() { return nome; }
    public boolean estaVivo() { return vida > 0; }
    public boolean isAtordoadoEsteTurno() { return atordoadoEsteTurno; }
    public void setAtordoadoEsteTurno(boolean b) { atordoadoEsteTurno = b; }
    public boolean jaAgiu() { return jaAguiuNoCombate; }
    public void marcarAgiu() { jaAguiuNoCombate = true; }

    public int getForca() { return forca; }
    public int getDestreza() { return destreza; }
    public int getInteligencia() { return inteligencia; }
    public int getVida() { return vida; }
    public int getMana() { return mana; }

    public void curar(int qtd, BattleLog log) {
        int antes = vida;
        vida = Math.min(vida + qtd, vidaMax);
        log.add("✨ " + nome + " cura " + (vida - antes) + " de vida (agora " + vida + ").");
    }

    /** Passivas por classe: implementadas por subclasses (chamadas no início do próprio turno). */
    public abstract void aplicarPassivaInicioTurno(BattleLog log);

    /** Quais tipos de armas a classe aceita. */
    public abstract Set<WeaponType> tiposPermitidos();

    /** Armas com requisitos: valida tipo e atributos. */
    public void equipar(Arma arma, BattleLog log) {
        if (!tiposPermitidos().contains(arma.getTipo())) {
            log.add("❌ " + nome + " não pode usar " + arma.getNome() + ".");
            return;
        }
        if (!arma.atendeRequisitos(this)) {
            log.add("❌ " + nome + " não atende requisitos para " + arma.getNome() + ".");
            return;
        }
        this.arma = arma;
        log.add("🔧 " + nome + " equipou " + arma.getNome() + ".");
    }

    /** Entrada do turno do personagem. */
    public void inicioDoTurno(BattleLog log) {
        atordoadoEsteTurno = false; // reset, efeitos podem reativar
        aplicarPassivaInicioTurno(log);
        // "tick" dos efeitos
        Iterator<StatusEffect> it = efeitos.iterator();
        while (it.hasNext()) {
            StatusEffect se = it.next();
            se.onTurnStart(this, log);
            if (se.expirou()) it.remove();
        }
    }

    /** Dano básico: implementação simples e previsível para fallback. */
    public int danoBasico() {
        // Heurística: favorece atributo "natural" do usuário, mas simples:
        int maior = Math.max(forca, Math.max(destreza, inteligencia));
        return 5 + (int) Math.floor(maior * 0.5); // justificativa: dano básico moderado
    }

    /** Evasão (Arqueiro tem 25%). Sobrescrito em Arqueiro. */
    public boolean tentaEsquivar() { return false; }

    /** Redução flat por passiva (ex: Guerreiro -20% em todo dano). Sobrescrito. */
    public int mitigarDano(int bruto) { return bruto; }

    public void receberDano(int qtd, DamageType tipo, BattleLog log, String fonte) {
        if (!estaVivo()) return;
        // checa esquiva
        if (tentaEsquivar()) {
            log.add("🕸️ " + nome + " esquivou do ataque (" + (fonte == null ? "ataque" : fonte) + ").");
            return;
        }
        int mitigado = mitigarDano(qtd);
        mitigado = Math.max(mitigado, 0);
        vida -= mitigado;
        log.add("💥 " + nome + " recebe " + mitigado + " de dano" +
                (fonte != null ? " (" + fonte + ")" : "") +
                " [HP " + Math.max(vida,0) + "/" + vidaMax + "]");
        if (vida <= 0) {
            log.add("☠️ " + nome + " caiu!");
        }
    }

    public void adicionarEfeito(StatusEffect effect, BattleLog log) {
        efeitos.add(effect);
        log.add("🔗 " + nome + " agora está com efeito: " + effect.getNome() + ".");
    }

    /** Atacar via Strategy; se mana insuficiente, usa básico. */
    public void agir(Personagem alvo, List<Personagem> inimigos, BattleLog log) {
        if (!estaVivo()) return;
        if (isAtordoadoEsteTurno()) {
            log.add("⏳ " + nome + " perde o turno (atordoado).");
            marcarAgiu();
            return;
        }
        if (arma == null) {
            // sem arma -> ataque básico
            int dano = danoBasico();
            log.add("👊 " + nome + " usa ataque básico causando " + dano + ".");
            alvo.receberDano(dano, DamageType.PHYSICAL, log, "Ataque básico");
            marcarAgiu();
            return;
        }

        // Consumo de mana: se faltar, usa básico.
        int custo = arma.getCustoMana();
        if (mana < custo) {
            int dano = danoBasico();
            log.add("🪫 Mana insuficiente ("+mana+"/"+custo+"). " + nome + " usa ataque básico por " + dano + ".");
            alvo.receberDano(dano, DamageType.PHYSICAL, log, "Ataque básico");
            marcarAgiu();
            return;
        }

        mana -= custo;
        boolean alvoDesprevenido = !alvo.jaAgiu(); // justificativa: "desprevenido" até agir 1ª vez
        arma.atacar(this, alvo, inimigos, new Random(), log, alvoDesprevenido);
        marcarAgiu();
    }
}
