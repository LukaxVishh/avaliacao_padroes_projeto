package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.model.NFe;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline.ValidationContext;

/**
 * (4) Banco de dados: duplicidade de número. Também simula uma "reserva"
 * (inserção provisória) que DEVE ser revertida se validadores subsequentes falharem.
 *
 * Implementa Reversible para permitir rollback pelo pipeline.
 */
public class DatabaseValidator extends AbstractTimedValidator implements Reversible {
    @Override public long timeoutMillis() { return 600; }
    private boolean reserved = false;

    @Override
    public boolean validate(NFe nfe, ValidationContext ctx) {
        // Checa duplicidade
        if (ctx.database.exists(nfe.getNumero())) {
            return false;
        }
        // Reserva (inserção provisória) — pode precisar rollback
        ctx.database.reserve(nfe.getNumero());
        reserved = true;
        return true;
    }

    @Override
    public void rollback(NFe nfe, ValidationContext ctx) {
        if (reserved) {
            ctx.database.unreserve(nfe.getNumero());
            reserved = false;
        }
    }
}
