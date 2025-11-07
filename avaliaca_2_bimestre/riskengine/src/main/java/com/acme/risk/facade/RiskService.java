package com.acme.risk.facade;

import com.acme.risk.context.RiskContext;
import com.acme.risk.engine.RiskEngine;
import com.acme.risk.factory.RiskAlgorithmType;
import com.acme.risk.factory.RiskStrategyFactory;
import com.acme.risk.strategy.RiskMetricStrategy;

import java.util.Objects;

/**
 * Facade (GoF) para esconder detalhes de Strategy/Factory do cliente.
 *
 * Justificativa (Restrições):
 * - O cliente pode mudar de algoritmo sem conhecer a implementação.
 * - Ponto único de entrada; facilita uso e manutenção.
 */
public final class RiskService {

    private final RiskStrategyFactory factory;
    private final RiskEngine engine;

    public RiskService(RiskStrategyFactory factory, RiskAlgorithmType defaultType) {
        this.factory = Objects.requireNonNull(factory);
        RiskMetricStrategy defaultStrategy = factory.create(defaultType);
        this.engine = new RiskEngine(defaultStrategy);
    }

    /**
     * Troca o algoritmo em tempo de execução por tipo.
     */
    public void switchAlgorithm(RiskAlgorithmType type) {
        engine.setStrategy(factory.create(type));
    }

    /**
     * Executa o cálculo contra o contexto informado.
     */
    public String calculate(RiskContext context) {
        return "[" + engine.currentStrategyName() + "] " + engine.run(context);
    }
}
