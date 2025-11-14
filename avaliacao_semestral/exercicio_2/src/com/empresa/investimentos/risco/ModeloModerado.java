package avaliacao_semestral.exercicio_2.src.com.empresa.investimentos.risco;


public class ModeloModerado implements EstrategiaCalculoRisco {

    @Override
    public double calcularRisco(Cliente cliente) {
        // Exemplo: peso mais equilibrado entre idade, renda e tolerância
        double risco = cliente.getToleranciaRisco() * 0.4
                     + cliente.getRendaMensal() / 10000.0 * 0.3
                     + cliente.getIdade() * 0.3;
        System.out.println("[Modelo Moderado] Risco calculado: " + risco);
        return risco;
    }
}
