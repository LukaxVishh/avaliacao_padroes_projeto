package avaliacao_semestral.exercicio_2.src.com.empresa.investimentos.risco;


public class Cliente {

    private String nome;
    private int idade;
    private double rendaMensal;
    /**
     * Tolerância ao risco: de 0 a 100
     */
    private int toleranciaRisco;

    public Cliente(String nome, int idade, double rendaMensal, int toleranciaRisco) {
        this.nome = nome;
        this.idade = idade;
        this.rendaMensal = rendaMensal;
        this.toleranciaRisco = toleranciaRisco;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public double getRendaMensal() {
        return rendaMensal;
    }

    public int getToleranciaRisco() {
        return toleranciaRisco;
    }
}
