package com.acme.risk.factory;

/**
 * Catálogo de algoritmos disponíveis.
 * Usado para seleção dinâmica sem expor detalhes de implementação ao cliente.
 */
public enum RiskAlgorithmType {
    VAR,
    EXPECTED_SHORTFALL,
    STRESS_TESTING
}
