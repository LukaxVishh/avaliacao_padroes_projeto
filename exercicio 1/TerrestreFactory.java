/**
 * Concrete Creator: sabe instanciar o Product adequado para transporte terrestre.
 */
public class TerrestreFactory extends TransporteFactory {
    @Override
    public TarifaTransporte criarCalculadora() {
        return new TarifaTerrestre();
    }
}
