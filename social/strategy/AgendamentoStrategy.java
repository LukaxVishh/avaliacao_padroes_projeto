package social.strategy;

import social.core.Conteudo;

import java.time.Duration;
import java.time.Instant;

/** Define quando executar (para Conteudo com publicarEm futuro). */
public interface AgendamentoStrategy {
    long delayMillis(Conteudo c);
    static AgendamentoStrategy defaultStrategy() {
        return c -> {
            if (c.publicarEm() == null) return 0L;
            long d = Duration.between(Instant.now(), c.publicarEm()).toMillis();
            return Math.max(0, d);
        };
    }
}
