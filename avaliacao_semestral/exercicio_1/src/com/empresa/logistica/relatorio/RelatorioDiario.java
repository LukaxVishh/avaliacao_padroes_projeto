package avaliacao_semestral.exercicio_1.src.com.empresa.logistica.relatorio;

public class RelatorioDiario implements Relatorio {

    @Override
    public void prepararDados() {
        System.out.println("[Relatório Diário] Coletando dados das últimas 24 horas...");
        // Aqui viriam as consultas específicas do relatório diário
    }

    @Override
    public void formatar() {
        System.out.println("[Relatório Diário] Formatando em layout compacto (PDF)...");
    }

    @Override
    public void gerarSaida() {
        System.out.println("[Relatório Diário] Enviando relatório diário para o e-mail do gerente.");
    }
}
