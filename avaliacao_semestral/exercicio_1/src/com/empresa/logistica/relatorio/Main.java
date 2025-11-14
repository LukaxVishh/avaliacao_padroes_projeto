package avaliacao_semestral.exercicio_1.src.com.empresa.logistica.relatorio;


public class Main {

    public static void main(String[] args) {

        // Exemplo 1: usar o relatório diário
        RelatorioFactory diariaFactory = new RelatorioDiarioFactory();
        GeradorRelatorios gerador = new GeradorRelatorios(diariaFactory);

        System.out.println("Gerando relatório diário:");
        gerador.gerarRelatorio();

        // Exemplo 2: trocar dinamicamente para relatório semanal
        RelatorioFactory semanalFactory = new RelatorioSemanalFactory();
        gerador.setFactory(semanalFactory);

        System.out.println("Gerando relatório semanal:");
        gerador.gerarRelatorio();

        // Se amanhã você criar RelatorioEmergencial + RelatorioEmergencialFactory,
        // bastaria fazer:
        //
        // RelatorioFactory emergencialFactory = new RelatorioEmergencialFactory();
        // gerador.setFactory(emergencialFactory);
        // gerador.gerarRelatorio();
        //
        // ...sem mudar nenhuma das classes acima.
    }
}
