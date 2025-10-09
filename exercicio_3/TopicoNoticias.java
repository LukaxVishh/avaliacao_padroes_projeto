package exercicio_3;

import java.util.HashSet;
import java.util.Set;

/**
 * Subject concreto do padrão Observer.
 * Representa UM tópico (ex.: Esportes) e gerencia seus assinantes.
 */
public class TopicoNoticias {
    private final Topico topico;
    private final Set<Assinante> assinantes = new HashSet<>();

    public TopicoNoticias(Topico topico) {
        this.topico = topico;
    }

    public Topico getTopico() { return topico; }

    /** Observers se inscrevem aqui */
    public void inscrever(Assinante a) {
        if (a != null) assinantes.add(a);
    }

    /** Cancela a inscrição */
    public void desinscrever(Assinante a) {
        assinantes.remove(a);
    }

    /** Notifica todos os inscritos deste tópico */
    public void notificarTodos(Noticia noticia) {
        // Garantia: só notifica notícias do próprio tópico
        if (noticia.getTopico() != this.topico) return;
        for (Assinante a : assinantes) {
            a.notificar(noticia);
        }
    }

    /** Informação de apoio (quantos inscritos tem) */
    public int totalAssinantes() {
        return assinantes.size();
    }
}
