package exercicio_2;

/**
 * Product concreto para pagamento via PIX.
 * Regra ilustrativa: sem taxa, confirma imediatamente com um hash/txId.
 */
public class ProcessadorPix implements ProcessadorPagamento {
    @Override
    public String processar(Pagamento pagamento) {
        if (pagamento.getChavePix() == null || pagamento.getChavePix().isEmpty()) {
            throw new IllegalArgumentException("Chave PIX é obrigatória.");
        }
        String txId = ("TX-" + pagamento.getChavePix() + "-" + System.currentTimeMillis())
                        .replaceAll("[^A-Za-z0-9-]", "");
        return String.format(
            "PIX confirmado. Valor: R$ %.2f | Chave: %s | TxID: %s | Desc: %s",
            pagamento.getValor(),
            pagamento.getChavePix(),
            txId,
            pagamento.getDescricao()
        );
    }
}
