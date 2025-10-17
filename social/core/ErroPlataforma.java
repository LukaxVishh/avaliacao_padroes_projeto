package social.core;

public final class ErroPlataforma {
    private final Plataforma plataforma;
    private final CodigoErro codigo;
    private final String detalhe;

    public ErroPlataforma(Plataforma plataforma, CodigoErro codigo, String detalhe) {
        this.plataforma = plataforma; this.codigo = codigo; this.detalhe = detalhe;
    }
    public Plataforma plataforma() { return plataforma; }
    public CodigoErro codigo() { return codigo; }
    public String detalhe() { return detalhe; }
}
