package exercicio_4;

import java.util.List;

/**
 * Decorator base: mantém uma referência ao componente (Relatorio)
 * e delega a ele, permitindo acrescentar comportamento antes/depois.
 */
public abstract class RelatorioDecorator implements Relatorio {
    protected final Relatorio wrappee;

    protected RelatorioDecorator(Relatorio wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public String gerar(List<Pedido> pedidos) {
        return wrappee.gerar(pedidos);
    }
}
