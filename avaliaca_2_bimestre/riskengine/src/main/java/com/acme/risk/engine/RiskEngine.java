package com.acme.risk.engine;

import com.acme.risk.context.RiskContext;
import com.acme.risk.strategy.RiskMetricStrategy;

import java.util.Objects;

/**
 * Contexto do padrão Strategy em tempo de execução: carrega uma instância de Strategy e delega.
 *
 * SOLID:
 * - SRP: apenas orquestra a execução da estratégia escolhida.
 * - DIP: depende de RiskMetricStrategy (abstração).
 *
 * Requisito atendido:
 * - "Cada algoritmo deve ser intercambiável em tempo de execução" -> método setStrategy.
 */
public final class RiskEngine {

    private RiskMetricStrategy strategy;

    public RiskEngine(RiskMetricStrategy initialStrategy) {
        this.strategy = Objects.requireNonNull(initialStrategy);
    }

    public void setStrategy(RiskMetricStrategy newStrategy) {
        this.strategy = Objects.requireNonNull(newStrategy);
    }

    public String run(RiskContext context) {
        return strategy.calculate(context);
    }

    public String currentStrategyName() {
        return strategy.name();
    }
}
