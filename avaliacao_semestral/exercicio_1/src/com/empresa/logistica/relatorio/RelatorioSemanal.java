package avaliacao_semestral.exercicio_1.src.com.empresa.logistica.relatorio;

public class RelatorioSemanal implements Relatorio {

    @Override
    public void prepararDados() {
        System.out.println("[Relatório Semanal] Coletando dados dos últimos 7 dias...");
        // Consultas e agregações específicas do relatório semanal
    }

    @Override
    public void formatar() {
        System.out.println("[Relatório Semanal] Formatando em layout detalhado (Excel)...");
    }

    @Override
    public void gerarSaida() {
        System.out.println("[Relatório Semanal] Salvando relatório semanal em pasta compartilhada.");
    }
}
