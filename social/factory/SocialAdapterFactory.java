package social.factory;

import social.adapter.*;
import social.core.Plataforma;
import social.vendors.instagram.InstagramApiClient;
import social.vendors.linkedin.LinkedInApiClient;
import social.vendors.tiktok.TikTokApiClient;
import social.vendors.twitter.TwitterApiClient;

import java.util.EnumMap;
import java.util.Map;

/** Factory Method + configuração dinâmica por ambiente. */
public class SocialAdapterFactory {
    private final ConfigAmbiente cfg;

    public SocialAdapterFactory(ConfigAmbiente cfg) { this.cfg = cfg; }

    public Map<Plataforma, SocialAdapter> criarTodos() {
        var map = new EnumMap<Plataforma, SocialAdapter>(Plataforma.class);

        // Exemplo: pega tokens do ambiente (THREAD-SAFE: clientes imutáveis)
        var tw = new TwitterApiClient(cfg.require("TW_TOKEN"));
        var ig = new InstagramApiClient(cfg.require("IG_APP_ID"), cfg.require("IG_SECRET"));
        var li = new LinkedInApiClient(cfg.require("LI_TOKEN"));
        var tk = new TikTokApiClient(cfg.require("TK_TOKEN"));

        map.put(Plataforma.TWITTER,  new TwitterAdapter(tw));
        map.put(Plataforma.INSTAGRAM,new InstagramAdapter(ig));
        map.put(Plataforma.LINKEDIN, new LinkedInAdapter(li));
        map.put(Plataforma.TIKTOK,   new TikTokAdapter(tk));
        return map;
    }
}
