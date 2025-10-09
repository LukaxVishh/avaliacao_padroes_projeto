package exercicio_2;


/**
 * Creator do padrão Factory Method.
 * Define a operação de fábrica que cria um ProcessadorPagamento (Product).
 */
public abstract class PagamentoFactory {
    /**
     * Factory Method: retorna o processador apropriado.
     */
    public abstract ProcessadorPagamento criarProcessador();
}
