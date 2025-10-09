package exercicio_2;


/**
 * Product do padrão Factory Method.
 * Contrato comum a todos os processadores de pagamento.
 */
public interface ProcessadorPagamento {
    /**
     * Executa o processamento do pagamento.
     * Retorna um "recibo" (String) apenas para fins didáticos.
     */
    String processar(Pagamento pagamento);
}
