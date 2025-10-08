/**
 * Product do padrão Factory Method.
 * Define o contrato comum para qualquer calculadora de tarifa.
 */
public interface TarifaTransporte {
    /**
     * Calcula o valor da entrega de acordo com as regras da modalidade.
     */
    double calcular(Entrega entrega);
}
