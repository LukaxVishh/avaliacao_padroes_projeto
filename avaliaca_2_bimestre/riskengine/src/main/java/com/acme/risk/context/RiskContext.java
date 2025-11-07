package com.acme.risk.context;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Contexto imutável compartilhado entre algoritmos (Requirement: "compartilhar um contexto complexo").
 * Usamos Builder para facilitar a construção incremental e garantir imutabilidade (thread-safety).
 *
 * SOLID:
 * - SRP: esta classe só modela dados do contexto de risco.
 */
public final class RiskContext {

    private final List<BigDecimal> pnlSeries;   // série de PnL
    private final BigDecimal confidenceLevel;   // ex.: 0.95, 0.99
    private final int lookbackDays;             // janela histórica
    private final LocalDate asOfDate;           // data de referência
    private final BigDecimal portfolioNotional; // tamanho do portfólio
    private final String portfolioId;           // id lógico

    private RiskContext(Builder b) {
        this.pnlSeries = Collections.unmodifiableList(b.pnlSeries);
        this.confidenceLevel = b.confidenceLevel;
        this.lookbackDays = b.lookbackDays;
        this.asOfDate = b.asOfDate;
        this.portfolioNotional = b.portfolioNotional;
        this.portfolioId = b.portfolioId;
    }

    public List<BigDecimal> getPnlSeries() { return pnlSeries; }
    public BigDecimal getConfidenceLevel() { return confidenceLevel; }
    public int getLookbackDays() { return lookbackDays; }
    public LocalDate getAsOfDate() { return asOfDate; }
    public BigDecimal getPortfolioNotional() { return portfolioNotional; }
    public String getPortfolioId() { return portfolioId; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private List<BigDecimal> pnlSeries = List.of();
        private BigDecimal confidenceLevel = BigDecimal.valueOf(0.99);
        private int lookbackDays = 250;
        private LocalDate asOfDate = LocalDate.now();
        private BigDecimal portfolioNotional = BigDecimal.ONE;
        private String portfolioId = "DEFAULT";

        public Builder pnlSeries(List<BigDecimal> pnlSeries) {
            this.pnlSeries = Objects.requireNonNull(pnlSeries);
            return this;
        }
        public Builder confidenceLevel(BigDecimal level) {
            this.confidenceLevel = Objects.requireNonNull(level);
            return this;
        }
        public Builder lookbackDays(int days) {
            this.lookbackDays = days;
            return this;
        }
        public Builder asOfDate(LocalDate date) {
            this.asOfDate = Objects.requireNonNull(date);
            return this;
        }
        public Builder portfolioNotional(BigDecimal notional) {
            this.portfolioNotional = Objects.requireNonNull(notional);
            return this;
        }
        public Builder portfolioId(String id) {
            this.portfolioId = Objects.requireNonNull(id);
            return this;
        }
        public RiskContext build() { return new RiskContext(this); }
    }
}
