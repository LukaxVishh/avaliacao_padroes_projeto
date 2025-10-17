/**
 * Exemplo de uso da solução.
 * Justificativa do padrão:
 * - O Factory Method permite que o cliente obtenha a calculadora correta
 *   sem conhecer classes concretas, mantendo baixo acoplamento e alta extensibilidade.
 * - Para adicionar uma nova modalidade, basta criar uma nova calculadora (Product)
 *   e sua respectiva fábrica (Concrete Creator), sem alterar o código cliente.
 */
public class Main {
    public static void main(String[] args) {
        // Exemplos de entregas
        Entrega e1 = new Entrega(Modalidade.TERRESTRE, 120.0,  8.0,  0.2);
        Entrega e2 = new Entrega(Modalidade.AEREO,        0.0, 25.0, 0.0);
        Entrega e3 = new Entrega(Modalidade.MARITIMO,   900.0, 60.0, 12.5);

        // Fábricas concretas
        TransporteFactory fTerrestre = new TerrestreFactory();
        TransporteFactory fAereo     = new AereoFactory();
        TransporteFactory fMaritimo  = new MaritimoFactory();

        // Cálculos genéricos (o cliente não conhece as classes de Product)
        double v1 = fTerrestre.criarCalculadora().calcular(e1);
        double v2 = fAereo.criarCalculadora().calcular(e2);
        double v3 = fMaritimo.criarCalculadora().calcular(e3);

        System.out.printf("Tarifa Terrestre: R$ %.2f%n", v1);
        System.out.printf("Tarifa Aéreo:     R$ %.2f%n", v2);
        System.out.printf("Tarifa Marítimo:  R$ %.2f%n", v3);
    }
}
