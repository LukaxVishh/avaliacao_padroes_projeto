package com.acme.risk.algorithms;

import com.acme.risk.context.RiskContext;
import com.acme.risk.strategy.RiskMetricStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implementação dummy de VaR.
 * Justificativa: VaR é uma das métricas pedidas; aqui retornamos um texto com base em parâmetros do contexto.
 */
public class ValueAtRiskStrategy implements RiskMetricStrategy {
    @Override
    public String calculate(RiskContext context) {
        // Cálculo dummy: VaR ~ notional * (1 - confidenceLevel) * (lookback/1000)
        BigDecimal dummy = context.getPortfolioNotional()
                .multiply(BigDecimal.ONE.subtract(context.getConfidenceLevel()))
                .multiply(BigDecimal.valueOf(context.getLookbackDays()))
                .divide(BigDecimal.valueOf(1000), 6, RoundingMode.HALF_UP);
        return "[VaR] Portfólio=" + context.getPortfolioId()
                + " CL=" + context.getConfidenceLevel()
                + " Lookback=" + context.getLookbackDays()
                + " Resultado=" + dummy.toPlainString();
    }

    @Override
    public String name() { return "ValueAtRisk"; }
}
