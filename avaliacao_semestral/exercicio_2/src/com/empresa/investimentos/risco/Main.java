package avaliacao_semestral.exercicio_2.src.com.empresa.investimentos.risco;


public class Main {

    public static void main(String[] args) {

        Cliente cliente1 = new Cliente("Ana", 28, 8000.0, 80);
        Cliente cliente2 = new Cliente("Carlos", 55, 12000.0, 40);

        // Começa com modelo agressivo
        EstrategiaCalculoRisco agressivo = new ModeloAgressivo();
        CalculadoraPerfilRisco calculadora = new CalculadoraPerfilRisco(agressivo);

        System.out.println("=== Usando modelo AGRESSIVO ===");
        calculadora.analisarCliente(cliente1);
        calculadora.analisarCliente(cliente2);

        // Troca dinamicamente para modelo moderado
        EstrategiaCalculoRisco moderado = new ModeloModerado();
        calculadora.setEstrategia(moderado);

        System.out.println("=== Usando modelo MODERADO ===");
        calculadora.analisarCliente(cliente1);
        calculadora.analisarCliente(cliente2);

        // Troca dinamicamente para modelo conservador
        EstrategiaCalculoRisco conservador = new ModeloConservador();
        calculadora.setEstrategia(conservador);

        System.out.println("=== Usando modelo CONSERVADOR ===");
        calculadora.analisarCliente(cliente1);
        calculadora.analisarCliente(cliente2);
    }
}
