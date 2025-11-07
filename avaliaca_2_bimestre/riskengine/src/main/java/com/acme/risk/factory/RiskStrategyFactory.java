package com.acme.risk.factory;

import com.acme.risk.algorithms.ExpectedShortfallStrategy;
import com.acme.risk.algorithms.StressTestingStrategy;
import com.acme.risk.algorithms.ValueAtRiskStrategy;
import com.acme.risk.strategy.RiskMetricStrategy;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Factory (GoF) + Registry: resolve uma estratégia a partir de um tipo.
 *
 * Justificativa:
 * - O cliente pode trocar de algoritmo sem conhecer detalhes de implementação (Requirement).
 * - Mantém OCP: adicionar novo algoritmo = registrar novo Supplier sem alterar o cliente.
 * - Facilita injeção de dependências e testes (DIP).
 */
public final class RiskStrategyFactory {

    private final Map<RiskAlgorithmType, Supplier<RiskMetricStrategy>> registry = new EnumMap<>(RiskAlgorithmType.class);

    public RiskStrategyFactory() {
        // Registro padrão das 3 estratégias exigidas
        registry.put(RiskAlgorithmType.VAR, ValueAtRiskStrategy::new);
        registry.put(RiskAlgorithmType.EXPECTED_SHORTFALL, ExpectedShortfallStrategy::new);
        registry.put(RiskAlgorithmType.STRESS_TESTING, StressTestingStrategy::new);
    }

    public RiskStrategyFactory register(RiskAlgorithmType type, Supplier<RiskMetricStrategy> supplier) {
        registry.put(Objects.requireNonNull(type), Objects.requireNonNull(supplier));
        return this;
    }

    public RiskMetricStrategy create(RiskAlgorithmType type) {
        Supplier<RiskMetricStrategy> supplier = registry.get(type);
        if (supplier == null) {
            throw new IllegalArgumentException("Algoritmo não registrado: " + type);
        }
        return supplier.get();
    }
}
