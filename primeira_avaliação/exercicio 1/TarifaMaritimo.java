/**
 * Product concreto: calcula tarifa marítima.
 * Regra (exemplo didático): valor = 15.0 + (distanciaKm * 0.2) + (volumeM3 * 1.0)
 */
public class TarifaMaritimo implements TarifaTransporte {
    @Override
    public double calcular(Entrega entrega) {
        return 15.0 + entrega.getDistanciaKm() * 0.2 + entrega.getVolumeM3() * 1.0;
    }
}
