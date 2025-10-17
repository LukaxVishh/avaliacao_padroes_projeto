/**
 * Creator do padrão Factory Method.
 * Define a operação "criarCalculadora" que retorna um Product (TarifaTransporte).
 * O cliente depende apenas desta abstração para obter a calculadora específica.
 */
public abstract class TransporteFactory {
    /**
     * Método de fábrica (Factory Method) que cria o Product apropriado.
     */
    public abstract TarifaTransporte criarCalculadora();
}
