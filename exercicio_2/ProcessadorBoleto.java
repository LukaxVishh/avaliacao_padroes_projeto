package exercicio_2;


/**
 * Product concreto para pagamento via Boleto.
 * Regra ilustrativa: sem taxa, gera linha digitável.
 */
public class ProcessadorBoleto implements ProcessadorPagamento {
    @Override
    public String processar(Pagamento pagamento) {
        if (pagamento.getCpfCnpj() == null || pagamento.getCpfCnpj().isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ do pagador é obrigatório para boleto.");
        }
        // Simula geração de linha digitável:
        String linha = "34191.79001 01043.510047 91020.150008 1 123400000" +
                       String.format("%02d", (int) Math.round(pagamento.getValor()));
        return String.format(
            "BOLETO gerado. Valor: R$ %.2f | Pagador: %s | Linha Digitável: %s | Desc: %s",
            pagamento.getValor(),
            pagamento.getCpfCnpj(),
            linha,
            pagamento.getDescricao()
        );
    }
}
