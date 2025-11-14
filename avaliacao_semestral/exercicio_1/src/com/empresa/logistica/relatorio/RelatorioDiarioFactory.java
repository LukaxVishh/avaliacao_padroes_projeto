package avaliacao_semestral.exercicio_1.src.com.empresa.logistica.relatorio;


public class RelatorioDiarioFactory implements RelatorioFactory {

    @Override
    public Relatorio criarRelatorio() {
        return new RelatorioDiario();
    }
}
