package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.model.NFe;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline.ValidationContext;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline.ValidationResult;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline.ValidationStatus;

import java.util.concurrent.*;

/**
 * Template Method para aplicar timeout uniformemente.
 * SRP: encapsula a mecânica de execução com timeout.
 * DIP: pipeline depende desta abstração, não de executores concretos.
 */
public abstract class AbstractTimedValidator implements Validator {

    @Override
    public final String name() { return getClass().getSimpleName(); }

    public final ValidationResult runWithTimeout(NFe nfe, ValidationContext ctx) {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        try {
            Future<Boolean> f = ex.submit(() -> {
                try { return validate(nfe, ctx); }
                catch (Exception e) { throw new ExecutionException(e); }
            });
            Boolean ok = f.get(timeoutMillis(), TimeUnit.MILLISECONDS);
            return new ValidationResult(getClass(), ok ? ValidationStatus.PASS : ValidationStatus.FAIL,
                    ok ? "OK" : "Regra violada");
        } catch (TimeoutException te) {
            return new ValidationResult(getClass(), ValidationStatus.TIMEOUT, "Timeout " + timeoutMillis() + "ms");
        } catch (Exception e) {
            return new ValidationResult(getClass(), ValidationStatus.FAIL, "Erro: " + e.getMessage());
        } finally {
            ex.shutdownNow();
        }
    }
}
