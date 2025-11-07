package avaliaca_2_bimestre.bank_adapter.src.main.java.com.acme.bank;

/**
 * DTO moderno de resposta.
 *
 * LSP: qualquer adaptador que implemente ProcessadorTransacoes deve retornar este contrato.
 * Imutável para simplicidade e segurança.
 */
public final class ResultadoModerno {
    private final boolean aprovado;
    private final String codigoAutorizacao;
    private final double valor;
    private final String moeda;
    private final String mensagem;

    public ResultadoModerno(boolean aprovado, String codigoAutorizacao, double valor, String moeda, String mensagem) {
        this.aprovado = aprovado;
        this.codigoAutorizacao = codigoAutorizacao;
        this.valor = valor;
        this.moeda = moeda;
        this.mensagem = mensagem;
    }

    public boolean isAprovado() { return aprovado; }
    public String getCodigoAutorizacao() { return codigoAutorizacao; }
    public double getValor() { return valor; }
    public String getMoeda() { return moeda; }
    public String getMensagem() { return mensagem; }

    @Override
    public String toString() {
        return "ResultadoModerno{" +
                "aprovado=" + aprovado +
                ", codigoAutorizacao='" + codigoAutorizacao + '\'' +
                ", valor=" + valor +
                ", moeda='" + moeda + '\'' +
                ", mensagem='" + mensagem + '\'' +
                '}';
    }
}
