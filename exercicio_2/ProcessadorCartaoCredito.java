package exercicio_2;


/**
 * Product concreto para pagamento com Cartão de Crédito.
 * Regra ilustrativa: valida cartão e aplica taxa de 2,5%.
 */
public class ProcessadorCartaoCredito implements ProcessadorPagamento {
    @Override
    public String processar(Pagamento pagamento) {
        if (pagamento.getNumeroCartao() == null || pagamento.getNumeroCartao().isEmpty()) {
            throw new IllegalArgumentException("Número do cartão é obrigatório.");
        }
        double total = pagamento.getValor() * 1.025; // taxa 2,5%
        return String.format(
            "CARTÃO aprovado. Valor: R$ %.2f | Total c/ taxa: R$ %.2f | Desc: %s | Cartão: ****%s",
            pagamento.getValor(),
            total,
            pagamento.getDescricao(),
            pagamento.getNumeroCartao().substring(Math.max(0, pagamento.getNumeroCartao().length() - 4))
        );
    }
}
