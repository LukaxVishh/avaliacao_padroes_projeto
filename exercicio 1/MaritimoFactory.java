/**
 * Concrete Creator: sabe instanciar o Product adequado para transporte marítimo.
 */
public class MaritimoFactory extends TransporteFactory {
    @Override
    public TarifaTransporte criarCalculadora() {
        return new TarifaMaritimo();
    }
}
