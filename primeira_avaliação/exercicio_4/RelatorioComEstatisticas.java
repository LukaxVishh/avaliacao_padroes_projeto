package exercicio_4;

import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Decorator concreto: adiciona estatísticas de faturamento.
 */
public class RelatorioComEstatisticas extends RelatorioDecorator {
    public RelatorioComEstatisticas(Relatorio wrappee) {
        super(wrappee);
    }

    @Override
    public String gerar(List<Pedido> pedidos) {
        String base = super.gerar(pedidos);

        DoubleSummaryStatistics st = pedidos.stream()
            .mapToDouble(Pedido::getValor)
            .summaryStatistics();

        String extra = String.format(
            "\n--- ESTATÍSTICAS ---\n" +
            "Qtd pedidos: %d\n" +
            "Faturamento total: R$ %.2f\n" +
            "Ticket médio: R$ %.2f\n" +
            "Maior pedido: R$ %.2f\n" +
            "Menor pedido: R$ %.2f\n",
            st.getCount(),
            st.getSum(),
            st.getCount() == 0 ? 0.0 : (st.getAverage()),
            st.getCount() == 0 ? 0.0 : st.getMax(),
            st.getCount() == 0 ? 0.0 : st.getMin()
        );

        return base + extra;
    }
}
