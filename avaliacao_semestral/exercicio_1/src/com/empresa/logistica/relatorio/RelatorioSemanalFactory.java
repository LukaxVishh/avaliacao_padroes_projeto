package avaliacao_semestral.exercicio_1.src.com.empresa.logistica.relatorio;


public class RelatorioSemanalFactory implements RelatorioFactory {

    @Override
    public Relatorio criarRelatorio() {
        return new RelatorioSemanal();
    }
}
