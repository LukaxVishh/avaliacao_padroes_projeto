/**
 * Concrete Creator: sabe instanciar o Product adequado para transporte aéreo.
 */
public class AereoFactory extends TransporteFactory {
    @Override
    public TarifaTransporte criarCalculadora() {
        return new TarifaAereo();
    }
}
