package social.adapter;

import social.core.*;
import social.vendors.instagram.InstagramApiClient;

import java.time.Instant;

public class InstagramAdapter implements SocialAdapter {
    private final InstagramApiClient client;
    public InstagramAdapter(InstagramApiClient client) { this.client = client; }
    public Plataforma plataforma() { return Plataforma.INSTAGRAM; }

    public Publicacao publicar(Conteudo c) throws Exception {
        var id = client.createPost(c.texto(), c.midias()); // retorna id
        return new Publicacao(Plataforma.INSTAGRAM, id, Instant.now(), "https://instagram.com/p/" + id);
    }

    public Estatisticas estatisticas(String postId) throws Exception {
        var m = client.insights(postId);
        return new Estatisticas(m.hearts(), m.comments(), m.shares(), m.reach());
    }
}
