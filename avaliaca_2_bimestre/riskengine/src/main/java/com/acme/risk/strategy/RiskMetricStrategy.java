package com.acme.risk.strategy;

import com.acme.risk.context.RiskContext;

/**
 * Strategy (GoF) — define a família de algoritmos de risco como intercambiáveis.
 *
 * SOLID:
 * - ISP: a interface é mínima e específica para o cálculo de risco.
 * - OCP: novas estratégias são adicionadas sem mudar as existentes.
 * - DIP: consumidores dependem desta abstração, não de concretas.
 */
public interface RiskMetricStrategy {

    /**
     * Executa o cálculo de risco para um dado contexto financeiro.
     * O retorno é String para permitir "cálculos dummy" distintos sem amarrar a um tipo numérico.
     */
    String calculate(RiskContext context);

    /**
     * Nome legível da estratégia (útil para logs/telemetria).
     */
    String name();
}
