package exercicio_2;


/**
 * DTO simples com dados necessários ao processamento.
 * Repare que os campos são genéricos; cada processador usa o que for relevante.
 */
public class Pagamento {
    private final MeioPagamento meio;
    private final double valor;
    private final String descricao;

    // Campos opcionais que podem ser usados por alguns meios:
    private final String numeroCartao; // cartão
    private final String chavePix;     // pix
    private final String cpfCnpj;      // boleto

    private Pagamento(Builder b) {
        this.meio = b.meio;
        this.valor = b.valor;
        this.descricao = b.descricao;
        this.numeroCartao = b.numeroCartao;
        this.chavePix = b.chavePix;
        this.cpfCnpj = b.cpfCnpj;
    }

    public MeioPagamento getMeio() { return meio; }
    public double getValor() { return valor; }
    public String getDescricao() { return descricao; }
    public String getNumeroCartao() { return numeroCartao; }
    public String getChavePix() { return chavePix; }
    public String getCpfCnpj() { return cpfCnpj; }

    /**
     * Builder para facilitar a criação de pagamentos com campos opcionais.
     */
    public static class Builder {
        private MeioPagamento meio;
        private double valor;
        private String descricao;
        private String numeroCartao;
        private String chavePix;
        private String cpfCnpj;

        public Builder meio(MeioPagamento m) { this.meio = m; return this; }
        public Builder valor(double v) { this.valor = v; return this; }
        public Builder descricao(String d) { this.descricao = d; return this; }
        public Builder numeroCartao(String n) { this.numeroCartao = n; return this; }
        public Builder chavePix(String c) { this.chavePix = c; return this; }
        public Builder cpfCnpj(String c) { this.cpfCnpj = c; return this; }
        public Pagamento build() { return new Pagamento(this); }
    }
}
