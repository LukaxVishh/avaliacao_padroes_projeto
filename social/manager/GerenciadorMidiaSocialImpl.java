package social.manager;

import social.adapter.SocialAdapter;
import social.core.*;
import social.strategy.AgendamentoStrategy;
import social.strategy.BackoffStrategy;

import java.util.*;
import java.util.concurrent.*;

/** Implementação unificada, segura para uso concorrente. */
public class GerenciadorMidiaSocialImpl implements GerenciadorMidiaSocial, AutoCloseable {
    private final Map<Plataforma, SocialAdapter> adapters; // imutável após construção
    private final Scheduler scheduler;
    private final BackoffStrategy backoff;
    private final AgendamentoStrategy agendamento;

    public GerenciadorMidiaSocialImpl(Map<Plataforma, SocialAdapter> adapters,
                                      Scheduler scheduler,
                                      BackoffStrategy backoff,
                                      AgendamentoStrategy agendamento) {
        this.adapters = Map.copyOf(adapters);
        this.scheduler = scheduler;
        this.backoff = backoff;
        this.agendamento = agendamento;
    }

    @Override
    public CompletableFuture<Resultado<Publicacao>> publicar(Plataforma p, Conteudo c) {
        SocialAdapter adapter = requireAdapter(p);
        RunnableCommand<Publicacao> cmd = tentativa -> adapter.publicar(c);
        return executarComRetry(p, cmd);
    }

    @Override
    public CompletableFuture<Resultado<Map<Plataforma, Publicacao>>> publicarEmLote(Map<Plataforma, Conteudo> posts) {
        Map<Plataforma, CompletableFuture<Resultado<Publicacao>>> futures = new EnumMap<>(Plataforma.class);
        posts.forEach((p, c) -> futures.put(p, publicar(p, c)));

        return CompletableFuture.allOf(futures.values().toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    List<ErroPlataforma> erros = new ArrayList<>();
                    Map<Plataforma, Publicacao> ok = new EnumMap<>(Plataforma.class);

                    futures.forEach((plat, fut) -> {
                        var r = fut.join();
                        if (r.sucesso()) ok.put(plat, r.dado().orElseThrow());
                        else erros.addAll(r.erros());
                    });

                    if (erros.isEmpty())
                        return Resultado.ok(ok, List.of());
                    else
                        return Resultado.erro(erros);
                });
    }

    @Override
    public CompletableFuture<Resultado<Estatisticas>> estatisticas(Plataforma p, String postId) {
        SocialAdapter adapter = requireAdapter(p);
        RunnableCommand<Estatisticas> cmd = tentativa -> adapter.estatisticas(postId);
        return executarComRetry(p, cmd);
    }

    @Override
    public void agendar(Plataforma p, Conteudo c) {
        scheduler.schedule(c, agendamento, () -> publicar(p, c));
    }

    private SocialAdapter requireAdapter(Plataforma p) {
        SocialAdapter a = adapters.get(p);
        if (a == null) throw new IllegalArgumentException("Adapter não configurado: " + p);
        return a;
    }

    /** Função de ordem superior que aplica estratégia de backoff e captura erros granulares. */
    private <T> CompletableFuture<Resultado<T>> executarComRetry(Plataforma p, RunnableCommand<T> cmd) {
        final int maxTentativas = 3;

        // Dica-chave: informar o parâmetro de tipo explicitamente para o Scheduler
        return scheduler.<Resultado<T>>supplyAsync(() -> {
            int tentativa = 1;
            List<ErroPlataforma> erros = new ArrayList<>();
            while (tentativa <= maxTentativas) {
                try {
                    T out = cmd.run(tentativa);
                    return Resultado.ok(
                            out,
                            tentativa > 1 ? List.of("Publicação concluída após " + tentativa + " tentativas")
                                        : List.of()
                    );
                } catch (Exception e) {
                    erros.add(mapErro(p, e));
                    if (tentativa == maxTentativas) break;
                    try {
                        Thread.sleep(backoff.nextDelayMillis(tentativa));
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    tentativa++;
                }
            }
            return Resultado.erro(erros);
        });
    }


    private interface RunnableCommand<T> { T run(int tentativa) throws Exception; }

    private ErroPlataforma mapErro(Plataforma p, Exception e) {
        String msg = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
        CodigoErro code =
                msg.toLowerCase().contains("auth") ? CodigoErro.AUTH :
                msg.toLowerCase().contains("rate") ? CodigoErro.RATE_LIMIT :
                msg.toLowerCase().contains("timeout") ? CodigoErro.TIMEOUT :
                msg.toLowerCase().contains("network") ? CodigoErro.REDE :
                msg.toLowerCase().contains("invalid") ? CodigoErro.VALIDAÇÃO :
                CodigoErro.DESCONHECIDO;
        return new ErroPlataforma(p, code, msg);
    }

    @Override public void close() { scheduler.close(); }
}
