package social.core;

import java.time.Instant;
import java.util.List;

/** Imutável e thread-safe. */
public final class Conteudo {
    private final String texto;
    private final List<String> midias; // urls ou paths
    private final Instant publicarEm;  // null => agora

    public Conteudo(String texto, List<String> midias, Instant publicarEm) {
        this.texto = texto;
        this.midias = midias == null ? List.of() : List.copyOf(midias);
        this.publicarEm = publicarEm;
    }
    public String texto() { return texto; }
    public List<String> midias() { return midias; }
    public Instant publicarEm() { return publicarEm; }
}
