package avaliacao_semestral.exercicio_1.src.com.empresa.logistica.relatorio;


public interface Relatorio {

    // Passos básicos que qualquer relatório deve executar
    void prepararDados();

    void formatar();

    void gerarSaida();
}
