package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators.Validator;

/** DTO de saída por etapa. Mantém quem validou e qual mensagem. */
public final class ValidationResult {
    public final Class<? extends Validator> validator;
    public final ValidationStatus status;
    public final String message;

    public ValidationResult(Class<? extends Validator> v, ValidationStatus s, String m) {
        this.validator = v;
        this.status = s;
        this.message = m;
    }

    @Override public String toString() {
        return validator.getSimpleName() + " -> " + status + " (" + message + ")";
    }
}
