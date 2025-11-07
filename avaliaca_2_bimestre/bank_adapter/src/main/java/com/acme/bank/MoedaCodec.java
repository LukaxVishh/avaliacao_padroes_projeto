package avaliaca_2_bimestre.bank_adapter.src.main.java.com.acme.bank;
import java.util.Map;

/**
 * Codec simples para mapear ISO alfa-3 -> código numérico exigido pelo legado.
 * Restrição do enunciado: USD=1, EUR=2, BRL=3
 *
 * SRP: classe focada em conversão de moeda (SOLID).
 * OCP: adicionar novas moedas sem alterar clientes (extensível).
 */
public final class MoedaCodec {
    private static final Map<String, Integer> ISO_TO_CODE = Map.of(
            "USD", 1,
            "EUR", 2,
            "BRL", 3
    );

    private static final Map<Integer, String> CODE_TO_ISO = Map.of(
            1, "USD",
            2, "EUR",
            3, "BRL"
    );

    private MoedaCodec() {}

    public static int toLegacyCode(String iso) {
        if (iso == null) return 0;
        return ISO_TO_CODE.getOrDefault(iso.toUpperCase(), 0);
    }

    public static String toIso(int legacyCode) {
        return CODE_TO_ISO.getOrDefault(legacyCode, "UNK");
    }
}
