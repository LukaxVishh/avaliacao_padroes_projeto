package avaliaca_2_bimestre.bank_adapter.src.main.java.com.acme.bank;

/**
 * Interface moderna que o domínio consome.
 *
 * Padrão aplicado:
 * - ISP (SOLID): interface pequena e coesa.
 * - DIP (SOLID): clientes dependem desta abstração.
 */
public interface ProcessadorTransacoes {
    /**
     * Autoriza uma transação.
     * @param cartao número do cartão (PAN mascarado ou tokenizado)
     * @param valor valor da transação
     * @param moeda código ISO alfa-3 (ex.: "USD","EUR","BRL")
     * @return objeto de resultado em formato moderno
     */
    ResultadoModerno autorizar(String cartao, double valor, String moeda);
}
