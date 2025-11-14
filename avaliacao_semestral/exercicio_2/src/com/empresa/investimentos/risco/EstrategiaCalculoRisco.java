package avaliacao_semestral.exercicio_2.src.com.empresa.investimentos.risco;

public interface EstrategiaCalculoRisco {

    /**
     * Calcula o perfil de risco de um cliente.
     * Pode retornar, por exemplo, um valor de 0 a 100.
     */
    double calcularRisco(Cliente cliente);
}
