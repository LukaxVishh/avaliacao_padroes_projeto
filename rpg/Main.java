package rpg;

import rpg.armas.*;
import rpg.batalha.Batalha;
import rpg.batalha.BattleLog;
import rpg.personagens.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Criação dos personagens (com atributos/vida/mana conforme enunciado)
        Guerreiro g = new Guerreiro("Joaquim, o Guerreiro");
        Arqueiro a = new Arqueiro("Tobias, a Arqueira");
        Mago m = new Mago("Aroldo, o Mago");
        Paladino p = new Paladino("Sir Carlinhos (Paladino)"); // extensão

        // Criação de armas
        Arma espada = new EspadaLonga();
        Arma machado = new MachadoGuerra();
        Arma arco = new ArcoElfico();
        Arma adaga = new AdagaSombria();
        Arma cajado = new CajadoArcano();
        Arma lanca = new LancaSagrada(); // extensão

        BattleLog log = new BattleLog();

        // Equipamentos (com validação de requisitos e tipos permitidos)
        g.equipar(machado, log); // Guerreiro pode usar Machado
        a.equipar(arco, log);    // Arqueiro pode usar Arco
        a.equipar(adaga, log);    // Arqueiro pode usar Adaga
        m.equipar(cajado, log);  // Mago pode usar Cajado
        p.equipar(lanca, log);   // Paladino pode usar Lança

        // Monte times (ex.: A = Guerreiro + Arqueiro, B = Mago + Paladino)
        Batalha batalha = new Batalha(
            Arrays.asList(g, a),
            Arrays.asList(m, p)
        );

        // Demonstração de troca de arma durante a batalha:
        log.add("Antes da batalha, uma troca de plano foi considerada...");
        g.equipar(espada, log); // ainda atende requisitos

        // Executa
        String vencedor = batalha.executar();

        // Dump
        batalha.getLog().dumpToStdout();
        System.out.println("\nVencedor: " + vencedor);
    }
}
