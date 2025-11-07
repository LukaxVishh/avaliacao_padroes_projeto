package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.model.NFe;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline.ValidationContext;

import java.time.Instant;

/**
 * (2) Certificado Digital: checa expiração e revogação (dummy via FakeCA).
 */
public class CertificateValidator extends AbstractTimedValidator {
    @Override public long timeoutMillis() { return 700; }

    @Override
    public boolean validate(NFe nfe, ValidationContext ctx) {
        Instant now = ctx.clock.instant();
        return ctx.ca.isValid(nfe.getCertificadoSerial(), now);
    }
}
