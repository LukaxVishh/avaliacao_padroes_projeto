package exercicio_4;

import java.util.List;

/**
 * Decorator concreto: acrescenta um "gráfico" simples em ASCII
 * (apenas para demonstrar a extensão sem bibliotecas externas).
 */
public class RelatorioComGraficoASCII extends RelatorioDecorator {
    public RelatorioComGraficoASCII(Relatorio wrappee) {
        super(wrappee);
    }

    @Override
    public String gerar(List<Pedido> pedidos) {
        String base = super.gerar(pedidos);

        StringBuilder graf = new StringBuilder("\n--- GRÁFICO ASCII (valores) ---\n");
        for (Pedido p : pedidos) {
            int barras = (int) Math.max(1, Math.round(p.getValor() / 10.0));
            graf.append(String.format("%s | %s R$ %.2f%n",
                    p.getId(),
                    "#".repeat(barras),
                    p.getValor()));
        }
        if (pedidos.isEmpty()) {
            graf.append("(sem dados)\n");
        }

        return base + graf;
    }
}

