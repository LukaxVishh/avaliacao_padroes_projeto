package exercicio_4;

import java.util.List;

/**
 * Componente do Decorator: contrato comum.
 * Cada relatório retorna uma String (poderia ser HTML, Markdown etc.).
 */
public interface Relatorio {
    String gerar(List<Pedido> pedidos);
}
