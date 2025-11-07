package com.acme.risk.algorithms;

import com.acme.risk.context.RiskContext;
import com.acme.risk.strategy.RiskMetricStrategy;

import java.math.BigDecimal;

/**
 * Implementação dummy de Stress Testing.
 * Ideia: amplificar perdas hipotéticas em 5% do notional como cenário de estresse.
 */
public class StressTestingStrategy implements RiskMetricStrategy {
    @Override
    public String calculate(RiskContext context) {
        BigDecimal stressed = context.getPortfolioNotional().multiply(BigDecimal.valueOf(0.05));
        return "[StressTest] Portfólio=" + context.getPortfolioId()
                + " Cenário='Queda generalizada 5%'"
                + " PerdaHipotética=" + stressed.toPlainString();
    }

    @Override
    public String name() { return "StressTesting"; }
}
