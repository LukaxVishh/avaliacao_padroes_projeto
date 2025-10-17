package exercicio_2;


/**
 * Concrete Creator para Cartão de Crédito.
 */
public class CartaoCreditoFactory extends PagamentoFactory {
    @Override
    public ProcessadorPagamento criarProcessador() {
        return new ProcessadorCartaoCredito();
    }
}
