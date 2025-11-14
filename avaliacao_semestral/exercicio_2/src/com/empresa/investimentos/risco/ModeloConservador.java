package avaliacao_semestral.exercicio_2.src.com.empresa.investimentos.risco;


public class ModeloConservador implements EstrategiaCalculoRisco {

    @Override
    public double calcularRisco(Cliente cliente) {
        // Exemplo: dá mais peso à idade, menos à tolerância
        double risco = cliente.getToleranciaRisco() * 0.2
                     + cliente.getRendaMensal() / 10000.0 * 0.2
                     + cliente.getIdade() * 0.6;
        System.out.println("[Modelo Conservador] Risco calculado: " + risco);
        return risco;
    }
}
