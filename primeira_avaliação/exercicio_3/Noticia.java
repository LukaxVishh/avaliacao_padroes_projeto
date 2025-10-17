package exercicio_3;

import java.time.LocalDateTime;

/**
 * Entidade imutável que representa uma notícia publicada em um tópico.
 */
public class Noticia {
    private final String titulo;
    private final String conteudo;
    private final Topico topico;
    private final LocalDateTime dataHora;

    public Noticia(String titulo, String conteudo, Topico topico) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.topico = topico;
        this.dataHora = LocalDateTime.now();
    }

    public String getTitulo()   { return titulo; }
    public String getConteudo() { return conteudo; }
    public Topico getTopico()   { return topico; }
    public LocalDateTime getDataHora() { return dataHora; }
}
