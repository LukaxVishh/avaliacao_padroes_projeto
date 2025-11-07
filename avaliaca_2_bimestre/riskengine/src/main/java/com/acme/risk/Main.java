package com.acme.risk;

import com.acme.risk.context.RiskContext;
import com.acme.risk.facade.RiskService;
import com.acme.risk.factory.RiskAlgorithmType;
import com.acme.risk.factory.RiskStrategyFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Exemplo de uso.
 * Demonstra troca dinâmica de algoritmos e compartilhamento do mesmo contexto.
 */
public class Main {
    public static void main(String[] args) {
        // 1) Monta contexto compartilhado
        RiskContext context = RiskContext.builder()
                .portfolioId("FUND-ALPHA")
                .portfolioNotional(new BigDecimal("10000000")) // 10MM
                .confidenceLevel(new BigDecimal("0.99"))
                .lookbackDays(250)
                .asOfDate(LocalDate.now())
                .pnlSeries(List.of(new BigDecimal("-1000"), new BigDecimal("500"), new BigDecimal("-200")))
                .build();

        // 2) Cria Service com Factory e algoritmo padrão (VaR)
        RiskStrategyFactory factory = new RiskStrategyFactory();
        RiskService service = new RiskService(factory, RiskAlgorithmType.VAR);

        // 3) Executa VaR
        System.out.println(service.calculate(context));

        // 4) Troca para Expected Shortfall em runtime
        service.switchAlgorithm(RiskAlgorithmType.EXPECTED_SHORTFALL);
        System.out.println(service.calculate(context));

        // 5) Troca para Stress Testing em runtime
        service.switchAlgorithm(RiskAlgorithmType.STRESS_TESTING);
        System.out.println(service.calculate(context));
    }
}
