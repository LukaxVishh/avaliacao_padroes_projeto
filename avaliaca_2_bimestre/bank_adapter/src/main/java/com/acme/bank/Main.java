package avaliaca_2_bimestre.bank_adapter.src.main.java.com.acme.bank;

/**
 * Demonstração simples do uso do Adapter.
 */
public class Main {
    public static void main(String[] args) {
        SistemaBancarioLegado legado = new SistemaBancarioLegado();

        // "canalPadrao" preenche o campo obrigatório do legado que não existe no moderno
        ProcessadorTransacoes processador = new LegacyProcessadorAdapter(legado, "ECOM");

        // 1) Aprova (valor <= 1000)
        ResultadoModerno ok = processador.autorizar("411111******1111", 150.0, "BRL");
        System.out.println(ok);

        // 2) Nega (valor > 1000)
        ResultadoModerno neg = processador.autorizar("411111******1111", 2000.0, "USD");
        System.out.println(neg);
    }
}
