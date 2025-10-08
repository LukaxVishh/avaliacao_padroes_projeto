/**
 * Representa os dados de uma entrega, necessários para o cálculo da tarifa.
 */
public class Entrega {
    private Modalidade modalidade;
    private double distanciaKm;
    private double pesoKg;
    private double volumeM3;

    public Entrega(Modalidade modalidade, double distanciaKm, double pesoKg, double volumeM3) {
        this.modalidade = modalidade;
        this.distanciaKm = distanciaKm;
        this.pesoKg = pesoKg;
        this.volumeM3 = volumeM3;
    }

    public Modalidade getModalidade() { return modalidade; }
    public double getDistanciaKm() { return distanciaKm; }
    public double getPesoKg() { return pesoKg; }
    public double getVolumeM3() { return volumeM3; }
}
