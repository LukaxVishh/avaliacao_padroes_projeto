/**
 * Product concreto: calcula tarifa aérea.
 * Regra (exemplo didático): valor = 20.0 + (pesoKg * 2.0)
 */
public class TarifaAereo implements TarifaTransporte {
    @Override
    public double calcular(Entrega entrega) {
        return 20.0 + entrega.getPesoKg() * 2.0;
    }
}
