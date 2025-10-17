package social.core;

/** Métrica simples; extensível. Imutável. */
public final class Estatisticas {
    private final long curtidas, comentarios, compartilhamentos, visualizacoes;

    public Estatisticas(long curtidas, long comentarios, long compartilhamentos, long visualizacoes) {
        this.curtidas = curtidas; this.comentarios = comentarios; this.compartilhamentos = compartilhamentos; this.visualizacoes = visualizacoes;
    }
    public long curtidas() { return curtidas; }
    public long comentarios() { return comentarios; }
    public long compartilhamentos() { return compartilhamentos; }
    public long visualizacoes() { return visualizacoes; }
}
