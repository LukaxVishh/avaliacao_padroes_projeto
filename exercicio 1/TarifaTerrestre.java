/**
 * Product concreto: calcula tarifa terrestre.
 * Regra (exemplo didático): valor = 10.0 + (distanciaKm * 0.5)
 */
public class TarifaTerrestre implements TarifaTransporte {
    @Override
    public double calcular(Entrega entrega) {
        return 10.0 + entrega.getDistanciaKm() * 0.5;
    }
}
