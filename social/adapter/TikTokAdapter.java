package social.adapter;

import social.core.*;
import social.vendors.tiktok.TikTokApiClient;

import java.time.Instant;

public class TikTokAdapter implements SocialAdapter {
    private final TikTokApiClient client;
    public TikTokAdapter(TikTokApiClient client) { this.client = client; }
    public Plataforma plataforma() { return Plataforma.TIKTOK; }

    public Publicacao publicar(Conteudo c) throws Exception {
        var out = client.uploadVideo(c.midias().isEmpty()? null : c.midias().get(0), c.texto());
        return new Publicacao(Plataforma.TIKTOK, out.videoId(), Instant.now(), "https://www.tiktok.com/@" + out.user() + "/video/" + out.videoId());
    }

    public Estatisticas estatisticas(String postId) throws Exception {
        var s = client.stats(postId);
        return new Estatisticas(s.likes(), s.comments(), s.shares(), s.views());
    }
}
