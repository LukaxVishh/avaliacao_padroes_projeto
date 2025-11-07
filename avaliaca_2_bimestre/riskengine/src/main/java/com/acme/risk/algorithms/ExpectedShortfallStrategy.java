package com.acme.risk.algorithms;

import com.acme.risk.context.RiskContext;
import com.acme.risk.strategy.RiskMetricStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implementação dummy de Expected Shortfall (ES/CVaR).
 */
public class ExpectedShortfallStrategy implements RiskMetricStrategy {
    @Override
    public String calculate(RiskContext context) {
        // Cálculo dummy: ES ~ VaR_dummy * 1.2 (apenas para distinguir dos demais)
        BigDecimal base = context.getPortfolioNotional()
                .multiply(BigDecimal.ONE.subtract(context.getConfidenceLevel()))
                .multiply(BigDecimal.valueOf(context.getLookbackDays()))
                .divide(BigDecimal.valueOf(1000), 6, RoundingMode.HALF_UP);
        BigDecimal es = base.multiply(BigDecimal.valueOf(1.2));
        return "[ES] Portfólio=" + context.getPortfolioId()
                + " CL=" + context.getConfidenceLevel()
                + " Resultado=" + es.toPlainString();
    }

    @Override
    public String name() { return "ExpectedShortfall"; }
}
