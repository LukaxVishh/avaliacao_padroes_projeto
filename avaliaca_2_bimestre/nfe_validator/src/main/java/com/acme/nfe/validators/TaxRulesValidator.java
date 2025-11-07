package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.model.NFe;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline.ValidationContext;

/**
 * (3) Regras Fiscais: cálculo de impostos (dummy).
 * Requisito: só roda se anteriores PASSARAM.
 */
public class TaxRulesValidator extends AbstractTimedValidator {
    @Override public long timeoutMillis() { return 800; }
    @Override public boolean requiresAllPreviousPass() { return true; }

    @Override
    public boolean validate(NFe nfe, ValidationContext ctx) {
        // Dummy: imposto deve ser proporcional ao valorTotal e não-negativo
        double v = nfe.getValorTotal();
        double impostoEsperado = Math.max(0, v * 0.18);
        // aqui poderíamos escrever no documento campos calculados; simulamos sucesso simples
        return impostoEsperado >= 0;
    }
}
