package exercicio_4;

import java.time.LocalDate;
import java.util.List;

/**
 * Exemplo de composição dinâmica com Decorator.
 *
 * Justificativa do padrão:
 *  - Precisamos adicionar recursos (estatísticas, gráfico, exportação) de forma
 *    opcional e progressiva, sem alterar o relatório básico existente.
 *  - Decorator permite empilhar funcionalidades em tempo de execução,
 *    mantendo a mesma interface (Relatorio) e baixo acoplamento.
 */
public class Main {
    public static void main(String[] args) {
        List<Pedido> pedidos = List.of(
            new Pedido("P-1001", LocalDate.of(2025, 10, 1), 120.00),
            new Pedido("P-1002", LocalDate.of(2025, 10, 2), 250.50),
            new Pedido("P-1003", LocalDate.of(2025, 10, 3), 80.90)
        );

        // 1) Apenas o relatório básico
        Relatorio basico = new RelatorioBasico();
        System.out.println("--- Relatório Básico ---");
        System.out.println(basico.gerar(pedidos));

        // 2) Básico + Estatísticas
        Relatorio comStats = new RelatorioComEstatisticas(basico);
        System.out.println("--- Básico + Estatísticas ---");
        System.out.println(comStats.gerar(pedidos));

        // 3) Básico + Estatísticas + Gráfico ASCII
        Relatorio comStatsGraf = new RelatorioComGraficoASCII(comStats);
        System.out.println("--- Básico + Estatísticas + Gráfico ---");
        System.out.println(comStatsGraf.gerar(pedidos));

        // 4) Qualquer combinação + Exportação PDF
        Relatorio comTudoEPdf = new RelatorioExportPDF(comStatsGraf, "relatorio_vendas.pdf");
        System.out.println("--- Com Tudo + Export PDF ---");
        System.out.println(comTudoEPdf.gerar(pedidos));

        // 5) Outra combinação possível: Básico + PDF (sem stats/gráfico)
        Relatorio basicoPdf = new RelatorioExportPDF(basico, "relatorio_basico.pdf");
        System.out.println("--- Básico + PDF ---");
        System.out.println(basicoPdf.gerar(pedidos));
    }
}
