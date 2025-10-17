package social.adapter;

import social.core.*;
import social.vendors.twitter.TwitterApiClient;

import java.time.Instant;

/** Adapter por composição: não modifica o client. */
public class TwitterAdapter implements SocialAdapter {
    private final TwitterApiClient client;

    public TwitterAdapter(TwitterApiClient client) { this.client = client; }

    public Plataforma plataforma() { return Plataforma.TWITTER; }

    public Publicacao publicar(Conteudo c) throws Exception {
        var resp = client.tweet(c.texto(), c.midias()); // API original
        return new Publicacao(Plataforma.TWITTER, resp.id(), Instant.now(), "https://twitter.com/i/status/" + resp.id());
    }

    public Estatisticas estatisticas(String postId) throws Exception {
        var s = client.metrics(postId);
        return new Estatisticas(s.likes(), s.replies(), s.retweets(), s.views());
    }
}
