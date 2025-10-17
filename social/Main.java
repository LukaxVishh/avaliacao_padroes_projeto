package social;

import social.adapter.SocialAdapter;
import social.core.*;
import social.factory.ConfigAmbiente;
import social.factory.SocialAdapterFactory;
import social.manager.GerenciadorMidiaSocialImpl;
import social.manager.Scheduler;
import social.strategy.AgendamentoStrategy;
import social.strategy.ExponencialBackoff;

import java.time.Instant;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        // Simule variáveis de ambiente (no seu ambiente real, exporte-as).
        // System.setProperty... (ou set no sistema); aqui apenas exemplo:
        // (Se quiser, comente os requires na factory para testar erros de config)
        Map<String,String> fakeEnv = Map.of(
            "TW_TOKEN","tw_token",
            "IG_APP_ID","ig_app",
            "IG_SECRET","ig_secret",
            "LI_TOKEN","li_token",
            "TK_TOKEN","tk_token"
        );
        fakeEnv.forEach((k,v) -> System.getenv().put(k, v)); // alguns JDKs não permitem; se não permitir, set no SO.

        var factory = new SocialAdapterFactory(new ConfigAmbiente());
        Map<Plataforma, SocialAdapter> adapters = factory.criarTodos();

        try (var scheduler = new Scheduler();
             var manager = new GerenciadorMidiaSocialImpl(
                     adapters,
                     scheduler,
                     new ExponencialBackoff(300, 3_000),       // Strategy de retry/backoff
                     AgendamentoStrategy.defaultStrategy()))   // Strategy de agendamento
        {
            // Conteúdos
            Conteudo agora = new Conteudo("Olá Twitter + LinkedIn!", List.of(), null);
            Conteudo futuro = new Conteudo("Post Instagram daqui a 2s", List.of("video.mp4"), Instant.now().plusSeconds(2));
            Conteudo tiktok = new Conteudo("Vídeo TikTok!", List.of("video.mp4"), null);

            // Publicação individual (async)
            var fTw = manager.publicar(Plataforma.TWITTER, agora);
            var fLi = manager.publicar(Plataforma.LINKEDIN, agora);

            // Agendado (executa quando chegar a hora)
            manager.agendar(Plataforma.INSTAGRAM, futuro);

            // Lote
            Map<Plataforma, Conteudo> lote = new EnumMap<>(Plataforma.class);
            lote.put(Plataforma.TIKTOK, tiktok);
            var fLote = manager.publicarEmLote(lote);

            System.out.println("TW result: " + fTw.get().sucesso());
            System.out.println("LI result: " + fLi.get().sucesso());
            System.out.println("Lote result: " + fLote.get().sucesso());

            // Estatísticas
            var pubTikTok = fLote.get().dado().map(m -> m.get(Plataforma.TIKTOK)).orElse(null);
            if (pubTikTok != null) {
                var statsRes = manager.estatisticas(Plataforma.TIKTOK, pubTikTok.postId()).get();
                System.out.println("Stats TikTok sucesso: " + statsRes.sucesso());
            }

            // Espera breve para o agendamento de exemplo
            Thread.sleep(2500);
        }
    }
}
