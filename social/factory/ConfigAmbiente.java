package social.factory;

import java.util.Map;

/** Lê chaves por ambiente (ex.: variáveis de ambiente). */
public class ConfigAmbiente {
    private final Map<String, String> env = System.getenv();

    public String get(String key) { return env.get(key); }

    public String require(String key) {
        var v = env.get(key);
        if (v == null || v.isBlank())
            throw new IllegalStateException("Config ausente: " + key);
        return v;
    }
}
