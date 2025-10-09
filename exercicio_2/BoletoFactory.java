package exercicio_2;

/**
 * Concrete Creator para Boleto.
 */
public class BoletoFactory extends PagamentoFactory {
    @Override
    public ProcessadorPagamento criarProcessador() {
        return new ProcessadorBoleto();
    }
}
