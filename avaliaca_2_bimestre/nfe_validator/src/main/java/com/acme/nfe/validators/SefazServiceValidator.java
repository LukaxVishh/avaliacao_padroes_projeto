package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.model.NFe;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline.ValidationContext;

/**
 * (5) Serviço SEFAZ (consulta online).
 * Requisito: só roda se anteriores PASSARAM.
 */
public class SefazServiceValidator extends AbstractTimedValidator {
    @Override public long timeoutMillis() { return 1200; }
    @Override public boolean requiresAllPreviousPass() { return true; }

    @Override
    public boolean validate(NFe nfe, ValidationContext ctx) throws Exception {
        // Simula chamada remota
        return ctx.sefaz.authorize(nfe.getNumero(), nfe.getEmitenteCnpj(), nfe.getValorTotal());
    }
}
