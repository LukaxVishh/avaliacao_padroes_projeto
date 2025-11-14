package avaliacao_semestral.exercicio_2.src.com.empresa.investimentos.risco;


public class ModeloAgressivo implements EstrategiaCalculoRisco {

    @Override
    public double calcularRisco(Cliente cliente) {
        // Exemplo bem simples: dá mais peso à renda e tolerância ao risco
        double risco = cliente.getToleranciaRisco() * 0.6
                     + cliente.getRendaMensal() / 10000.0 * 0.3
                     + cliente.getIdade() * 0.1;
        System.out.println("[Modelo Agressivo] Risco calculado: " + risco);
        return risco;
    }
}
