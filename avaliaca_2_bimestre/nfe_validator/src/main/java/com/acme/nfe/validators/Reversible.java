package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.model.NFe;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline.ValidationContext;

/**
 * Command reversível para rollback.
 * Usado quando o validador produz efeitos colaterais persistentes.
 */
public interface Reversible {
    /** Executa o rollback do efeito aplicado durante validate(). */
    void rollback(NFe nfe, ValidationContext ctx);
}
