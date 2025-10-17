package social.core;

import java.time.Instant;

/** Resultado padronizado de uma publicação. */
public final class Publicacao {
    private final Plataforma plataforma;
    private final String postId;
    private final Instant criadoEm;
    private final String link;

    public Publicacao(Plataforma plataforma, String postId, Instant criadoEm, String link) {
        this.plataforma = plataforma; this.postId = postId; this.criadoEm = criadoEm; this.link = link;
    }
    public Plataforma plataforma() { return plataforma; }
    public String postId() { return postId; }
    public Instant criadoEm() { return criadoEm; }
    public String link() { return link; }
}
