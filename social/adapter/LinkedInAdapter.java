package social.adapter;

import social.core.*;
import social.vendors.linkedin.LinkedInApiClient;

import java.time.Instant;

public class LinkedInAdapter implements SocialAdapter {
    private final LinkedInApiClient client;
    public LinkedInAdapter(LinkedInApiClient client) { this.client = client; }
    public Plataforma plataforma() { return Plataforma.LINKEDIN; }

    public Publicacao publicar(Conteudo c) throws Exception {
        var r = client.share(c.texto(), c.midias());
        return new Publicacao(Plataforma.LINKEDIN, r.urn(), Instant.now(), "https://linkedin.com/feed/update/" + r.urn());
    }

    public Estatisticas estatisticas(String postId) throws Exception {
        var s = client.analytics(postId);
        return new Estatisticas(s.likes(), s.comments(), s.reposts(), s.impressions());
    }
}
