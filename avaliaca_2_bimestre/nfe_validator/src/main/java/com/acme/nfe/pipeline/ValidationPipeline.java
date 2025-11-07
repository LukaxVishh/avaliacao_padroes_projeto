package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.model.NFe;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators.*;

import java.util.*;

/**
 * Chain of Responsibility com:
 * - Circuit Breaker (interrompe após 3 falhas)
 * - Regras condicionais (se X falhar, pule Y)
 * - Execução condicional (v3 e v5 só se anteriores PASSARAM)
 * - Rollback reverso em caso de falha final
 *
 * SOLID:
 *  - SRP: orquestrar a cadeia e políticas (uma única razão de mudança).
 *  - OCP: novos validadores/políticas podem ser adicionados sem alterar clientes.
 *  - DIP: depende de interfaces Validator/Reversible.
 */
public final class ValidationPipeline {

    private final List<AbstractTimedValidator> chain;
    private final ConditionalSkipPolicy skipPolicy;
    private final int breakerThreshold;
    private final Deque<Reversible> executedReversible = new ArrayDeque<>();

    public ValidationPipeline(List<AbstractTimedValidator> chain,
                              ConditionalSkipPolicy skipPolicy,
                              int breakerThreshold) {
        this.chain = List.copyOf(chain);
        this.skipPolicy = skipPolicy;
        this.breakerThreshold = breakerThreshold;
    }

    public List<ValidationResult> execute(NFe nfe, ValidationContext ctx) {
        List<ValidationResult> results = new ArrayList<>();
        int failures = 0;
        Set<Class<? extends Validator>> failedSoFar = new HashSet<>();
        boolean allPreviousPassed = true;

        for (int i = 0; i < chain.size(); i++) {
            AbstractTimedValidator v = chain.get(i);

            // Política: pular se alguma falha anterior indica que este deve ser ignorado
            if (skipPolicy.shouldSkip(failedSoFar, v.getClass())) {
                results.add(new ValidationResult(v.getClass(), ValidationStatus.SKIP, "Pulado por política"));
                continue;
            }

            // Regra: validadores 3 e 5 só executam se anteriores passaram
            if (v.requiresAllPreviousPass() && !allPreviousPassed) {
                results.add(new ValidationResult(v.getClass(), ValidationStatus.SKIP, "Anterior não passou"));
                continue;
            }

            // Circuit breaker: se já estourou, interrompe
            if (failures >= breakerThreshold) {
                results.add(new ValidationResult(v.getClass(), ValidationStatus.SKIP, "Circuit breaker ativo"));
                continue;
            }

            // Executa com timeout
            ValidationResult r = v.runWithTimeout(nfe, ctx);
            results.add(r);

            if (r.status == ValidationStatus.PASS) {
                allPreviousPassed = allPreviousPassed && true;
                if (v instanceof Reversible) {
                    // Empilha para possível rollback se algo falhar depois
                    executedReversible.push((Reversible) v);
                }
            } else if (r.status == ValidationStatus.SKIP) {
                // pulos não contam como falha
                allPreviousPassed = false; // impede "requiresAllPreviousPass" lá na frente
            } else {
                // FAIL/ TIMEOUT contam como falha
                failures++;
                allPreviousPassed = false;
                failedSoFar.add(v.getClass());
            }
        }

        // Se houve falha em qualquer ponto (inclui timeouts), faz rollback dos reversíveis executados
        boolean anyHardFailure = results.stream().anyMatch(x -> x.status == ValidationStatus.FAIL || x.status == ValidationStatus.TIMEOUT);
        if (anyHardFailure) {
            while (!executedReversible.isEmpty()) {
                try { executedReversible.pop().rollback(nfe, ctx); } catch (Exception ignore) {}
            }
        } else {
            // Nenhuma falha dura: podemos "commit" do DB (exemplo)
            ctx.database.commit(nfe.getNumero());
        }
        return results;
    }
}
