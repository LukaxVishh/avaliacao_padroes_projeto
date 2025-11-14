package avaliacao_semestral.exercicio_2.src.com.empresa.investimentos.risco;


/**
 * Classe de "contexto" que usa uma estratégia de cálculo de risco.
 * O fluxo principal de análise fica aqui e NÃO muda quando trocamos o modelo.
 */
public class CalculadoraPerfilRisco {

    private EstrategiaCalculoRisco estrategia;

    public CalculadoraPerfilRisco(EstrategiaCalculoRisco estrategia) {
        this.estrategia = estrategia;
    }

    public void setEstrategia(EstrategiaCalculoRisco estrategia) {
        this.estrategia = estrategia;
    }

    public void analisarCliente(Cliente cliente) {
        System.out.println("Analisando cliente: " + cliente.getNome());
        double risco = estrategia.calcularRisco(cliente);

        // Aqui você poderia classificar o risco em faixas
        if (risco < 30) {
            System.out.println("Perfil de risco: BAIXO");
        } else if (risco < 60) {
            System.out.println("Perfil de risco: MÉDIO");
        } else {
            System.out.println("Perfil de risco: ALTO");
        }

        System.out.println("--------------------------------------------------");
    }
}
