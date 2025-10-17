package social.core;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Interface unificada (comece por aqui).
 * Métodos retornam Resultado<T> (Sistema de Resposta Unificado) async e thread-safe.
 */
public interface GerenciadorMidiaSocial {
    CompletableFuture<Resultado<Publicacao>> publicar(Plataforma plataforma, Conteudo conteudo);

    CompletableFuture<Resultado<Map<Plataforma, Publicacao>>> publicarEmLote(Map<Plataforma, Conteudo> posts);

    CompletableFuture<Resultado<Estatisticas>> estatisticas(Plataforma plataforma, String postId);

    void agendar(Plataforma plataforma, Conteudo conteudo); // usa Strategy de agendamento/backoff
}
