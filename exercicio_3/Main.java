package exercicio_3;

/**
 * Exemplo de uso do padrão Observer para múltiplos tópicos e leitores.
 *
 * Justificativa do padrão:
 * - O "quem recebe notificação" é resolvido por INSCRIÇÃO (Observer),
 *   evitando controle manual pelo site.
 * - Cada tópico é um Subject independente: múltiplos tópicos e múltiplos leitores
 *   funcionam naturalmente (um para muitos).
 * - Para adicionar novos tópicos, basta incluir no enum Topico (ou criar dinamicamente
 *   um novo TopicoNoticias) — sem alterar a lógica de notificação.
 */
public class Main {
    public static void main(String[] args) {
        PortalNoticias portal = new PortalNoticias();

        // Leitores (Observers)
        Leitor ana   = new Leitor("Ana",   "ana@exemplo.com");
        Leitor bruno = new Leitor("Bruno", "bruno@exemplo.com");
        Leitor caio  = new Leitor("Caio",  "caio@exemplo.com");

        // Inscrições
        portal.inscrever(Topico.ESPORTES, ana);
        portal.inscrever(Topico.TECNOLOGIA, ana);
        portal.inscrever(Topico.POLITICA, bruno);
        portal.inscrever(Topico.TECNOLOGIA, bruno);
        portal.inscrever(Topico.ESPORTES, caio);

        // Publicações (cada publicação notifica apenas seus assinantes)
        portal.publicar(new Noticia(
            "Time X conquista o título",
            "Final emocionante termina nos pênaltis.",
            Topico.ESPORTES
        ));

        portal.publicar(new Noticia(
            "Governo anuncia reforma",
            "Novas medidas fiscais são apresentadas.",
            Topico.POLITICA
        ));

        portal.publicar(new Noticia(
            "Novo smartphone é lançado",
            "Aparelho traz câmera periscópica e IA embarcada.",
            Topico.TECNOLOGIA
        ));

        // Cancelamento de inscrição e nova publicação
        portal.desinscrever(Topico.ESPORTES, caio);
        portal.publicar(new Noticia(
            "Clássico termina empatado",
            "Jogo truncado, poucas chances.",
            Topico.ESPORTES
        ));
    }
}
