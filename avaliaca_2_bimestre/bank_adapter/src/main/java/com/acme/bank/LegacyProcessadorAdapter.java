package avaliaca_2_bimestre.bank_adapter.src.main.java.com.acme.bank;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ADAPTER (GoF): converte a interface moderna ProcessadorTransacoes
 * para a interface legada SistemaBancarioLegado e vice-versa.
 *
 * Justificativa:
 * - Assinaturas incompatíveis e tipos obsoletos (HashMap, códigos numéricos).
 * - Precisamos que o cliente moderno use o legado sem conhecer detalhes internos.
 *
 * SOLID:
 * - SRP: apenas adaptação entre modelos.
 * - DIP: consumidores dependem de ProcessadorTransacoes (abstração).
 * - OCP: novas regras de mapeamento podem ser adicionadas sem mudar o cliente.
 */
public final class LegacyProcessadorAdapter implements ProcessadorTransacoes {

    private final SistemaBancarioLegado legado;
    private final String canalPadrao; // Campo OBRIGATÓRIO no legado, inexistente no moderno

    /**
     * @param legado sistema legado
     * @param canalPadrao preenchido para atender o campo obrigatório do legado ("canal")
     *                    Ex.: "ECOM", "POS", "APP"
     */
    public LegacyProcessadorAdapter(SistemaBancarioLegado legado, String canalPadrao) {
        this.legado = Objects.requireNonNull(legado);
        this.canalPadrao = Objects.requireNonNull(canalPadrao);
    }

    /**
     * Direção moderna -> legado.
     * Converte parâmetros modernos para o HashMap exigido, chama o legado
     * e converte a resposta para ResultadoModerno.
     */
    @Override
    public ResultadoModerno autorizar(String cartao, double valor, String moeda) {
        HashMap<String, Object> params = toLegacyParams(cartao, valor, moeda);
        Map<String, Object> respLegado = legado.processarTransacao(params);
        return toModernResult(respLegado);
    }

    /**
     * Conversão de parâmetros modernos para formato legado.
     * Inclui o CAMPO OBRIGATÓRIO "canal" que não existe na interface moderna.
     */
    public HashMap<String, Object> toLegacyParams(String cartao, double valor, String moedaIso) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("numeroCartao", cartao);
        map.put("valor", valor);
        map.put("moedaCodigo", MoedaCodec.toLegacyCode(moedaIso));
        map.put("canal", canalPadrao); // tratamento do campo obrigatório ausente no moderno
        return map;
    }

    /**
     * Conversão de resposta do legado -> formato moderno (bidirecional).
     */
    public ResultadoModerno toModernResult(Map<String, Object> legadoResp) {
        String status = (String) legadoResp.getOrDefault("status", "ERRO");
        boolean aprovado = "APROVADO".equalsIgnoreCase(status);
        String codigo = (String) legadoResp.get("autorizacao");
        double valorAut = ((Number) legadoResp.getOrDefault("valorAutorizado", 0.0)).doubleValue();
        int moedaCod = ((Number) legadoResp.getOrDefault("moedaCodigo", 0)).intValue();
        String moeda = MoedaCodec.toIso(moedaCod);
        String msg = (String) legadoResp.getOrDefault("mensagem", "");

        return new ResultadoModerno(aprovado, codigo, valorAut, moeda, msg);
    }
}
