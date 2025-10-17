package social.manager;

import social.core.Conteudo;
import social.strategy.AgendamentoStrategy;

import java.util.Objects;
import java.util.concurrent.*;

/** Scheduler compartilhado, thread-safe. */
public class Scheduler implements AutoCloseable {
    private final ScheduledExecutorService ses =
            Executors.newScheduledThreadPool(Math.max(2, Runtime.getRuntime().availableProcessors()/2));

    public ScheduledFuture<?> schedule(Conteudo c, AgendamentoStrategy strategy, Runnable task) {
        Objects.requireNonNull(task);
        long delay = strategy.delayMillis(c);
        return ses.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public <T> CompletableFuture<T> supplyAsync(Callable<T> call) {
        return CompletableFuture.supplyAsync(() -> {
            try { return call.call(); }
            catch (Exception e) { throw new CompletionException(e); }
        }, ses);
    }

    @Override public void close() { ses.shutdown(); }
}
