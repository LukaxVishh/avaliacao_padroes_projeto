package exercicio_3;

import java.util.EnumMap;
import java.util.Map;

/**
 * Fachada simples do portal.
 * Mantém um Subject por tópico e oferece métodos de uso de alto nível.
 * Repare: o portal NÃO controla listas de leitores manualmente;
 * quem cuida disso é o próprio TopicoNoticias (Subject).
 */
public class PortalNoticias {
    private final Map<Topico, TopicoNoticias> topicos = new EnumMap<>(Topico.class);

    public PortalNoticias() {
        // Cria um Subject para cada tópico suportado
        for (Topico t : Topico.values()) {
            topicos.put(t, new TopicoNoticias(t));
        }
    }

    /** Recupera o Subject (tópico) para permitir inscrição */
    public TopicoNoticias getTopico(Topico t) {
        return topicos.get(t);
    }

    /** Publica uma notícia no tópico correspondente e dispara as notificações */
    public void publicar(Noticia noticia) {
        TopicoNoticias subject = topicos.get(noticia.getTopico());
        if (subject != null) {
            subject.notificarTodos(noticia);
        }
    }

    /** Atalho para inscrever um assinante em um tópico específico */
    public void inscrever(Topico topico, Assinante assinante) {
        getTopico(topico).inscrever(assinante);
    }

    /** Atalho para desinscrever */
    public void desinscrever(Topico topico, Assinante assinante) {
        getTopico(topico).desinscrever(assinante);
    }
}
