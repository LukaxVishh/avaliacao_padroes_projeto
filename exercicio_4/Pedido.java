package exercicio_4;

import java.time.LocalDate;

/**
 * Entidade simples para o relatório.
 */
public class Pedido {
    private final String id;
    private final LocalDate data;
    private final double valor;

    public Pedido(String id, LocalDate data, double valor) {
        this.id = id;
        this.data = data;
        this.valor = valor;
    }

    public String getId() { return id; }
    public LocalDate getData() { return data; }
    public double getValor() { return valor; }
}
