package avaliaca_2_bimestre.bank_adapter.src.main.java.com.acme.bank;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Simulação do sistema legado com assinatura incompatível.
 * Requer HashMap<String,Object> com chaves específicas.
 *
 * Campos obrigatórios: "numeroCartao", "valor", "moedaCodigo", "canal".
 *  - "canal" é um campo OBRIGATÓRIO inexistente na interface moderna.
 */
public class SistemaBancarioLegado {

    /**
     * Assinatura legada exigida.
     */
    public Map<String, Object> processarTransacao(HashMap<String, Object> parametros) {
        Objects.requireNonNull(parametros, "parametros");

        // Validação mínima dos obrigatórios do legado
        for (String obrig : new String[]{"numeroCartao","valor","moedaCodigo","canal"}) {
            if (!parametros.containsKey(obrig)) {
                return falha("Faltando campo obrigatório do legado: " + obrig);
            }
        }

        // Regras dummy: aprova se valor <= 1000, caso contrário nega
        double valor = ((Number)parametros.get("valor")).doubleValue();
        int moedaCodigo = (int) parametros.get("moedaCodigo");

        Map<String, Object> resp = new HashMap<>();
        boolean aprovado = valor <= 1000.0;

        resp.put("status", aprovado ? "APROVADO" : "NEGADO");
        resp.put("autorizacao", aprovado ? "AUTH" + System.currentTimeMillis() % 100000 : null);
        resp.put("valorAutorizado", aprovado ? valor : 0.0);
        resp.put("moedaCodigo", moedaCodigo);
        resp.put("mensagem", aprovado ? "Transação aprovada" : "Limite excedido");
        return resp;
    }

    private Map<String,Object> falha(String msg) {
        Map<String,Object> r = new HashMap<>();
        r.put("status", "ERRO");
        r.put("mensagem", msg);
        r.put("autorizacao", null);
        r.put("valorAutorizado", 0.0);
        r.put("moedaCodigo", 0);
        return r;
    }
}
