package exercicio_2;


/**
 * Exemplo de uso.
 * Justificativa do padrão:
 *  - O Factory Method permite configurar o meio de pagamento e obter o
 *    processador correto SEM que o cliente conheça classes concretas.
 *  - Para adicionar novos meios (ex.: carteira digital), basta criar
 *    um novo Processador e sua Factory, sem alterar o cliente.
 */
public class Main {
    public static void main(String[] args) {
        // Configuração pode vir de propriedades, BD, env, etc.
        // Aqui vamos simular escolhas diferentes:

        // 1) Cartão de crédito
        Pagamento pCartao = new Pagamento.Builder()
            .meio(MeioPagamento.CARTAO_CREDITO)
            .valor(250.00)
            .descricao("Assinatura Premium")
            .numeroCartao("4111111111111111")
            .build();

        PagamentoFactory fCartao = new CartaoCreditoFactory();
        String reciboCartao = fCartao.criarProcessador().processar(pCartao);
        System.out.println(reciboCartao);

        // 2) Boleto
        Pagamento pBoleto = new Pagamento.Builder()
            .meio(MeioPagamento.BOLETO)
            .valor(799.90)
            .descricao("Curso de Java")
            .cpfCnpj("123.456.789-09")
            .build();

        PagamentoFactory fBoleto = new BoletoFactory();
        String reciboBoleto = fBoleto.criarProcessador().processar(pBoleto);
        System.out.println(reciboBoleto);

        // 3) PIX
        Pagamento pPix = new Pagamento.Builder()
            .meio(MeioPagamento.PIX)
            .valor(59.99)
            .descricao("Renovação de domínio")
            .chavePix("email@empresa.com")
            .build();

        PagamentoFactory fPix = new PixFactory();
        String reciboPix = fPix.criarProcessador().processar(pPix);
        System.out.println(reciboPix);
    }
}
