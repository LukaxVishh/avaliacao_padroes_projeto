package avaliacao_semestral.exercicio_1.src.com.empresa.logistica.relatorio;


// "Núcleo" que depende apenas da interface RelatorioFactory
public class GeradorRelatorios {

    private RelatorioFactory factory;

    // A fábrica é injetada (pode ser trocada em tempo de execução)
    public GeradorRelatorios(RelatorioFactory factory) {
        this.factory = factory;
    }

    public void setFactory(RelatorioFactory factory) {
        this.factory = factory;
    }

    public void gerarRelatorio() {
        // Núcleo não sabe qual é o tipo concreto de relatório
        Relatorio relatorio = factory.criarRelatorio();

        // Fluxo padrão de geração
        relatorio.prepararDados();
        relatorio.formatar();
        relatorio.gerarSaida();

        System.out.println("--------------------------------------------------");
    }
}
