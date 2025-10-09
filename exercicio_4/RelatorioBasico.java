package exercicio_4;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Componente concreto: relatório básico (lista de pedidos).
 */
public class RelatorioBasico implements Relatorio {
    @Override
    public String gerar(List<Pedido> pedidos) {
        String cabecalho = "=== RELATÓRIO BÁSICO: PEDIDOS ===\n";
        String linhas = pedidos.stream()
            .map(p -> String.format("- %s | %s | R$ %.2f",
                    p.getId(), p.getData(), p.getValor()))
            .collect(Collectors.joining("\n"));
        return cabecalho + linhas + (linhas.isEmpty() ? "(sem pedidos)" : "") + "\n";
    }
}
