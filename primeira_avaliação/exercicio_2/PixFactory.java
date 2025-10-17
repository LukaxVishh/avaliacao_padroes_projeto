package exercicio_2;

/**
 * Concrete Creator para PIX.
 */
public class PixFactory extends PagamentoFactory {
    @Override
    public ProcessadorPagamento criarProcessador() {
        return new ProcessadorPix();
    }
}
