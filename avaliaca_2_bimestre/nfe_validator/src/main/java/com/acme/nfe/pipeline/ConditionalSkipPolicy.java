package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators.Validator;

import java.util.*;

/**
 * Política de pulo condicional: se X falhar, pule Y.
 * OCP: novas regras podem ser adicionadas sem alterar o pipeline.
 */
public final class ConditionalSkipPolicy {
    private final Map<Class<? extends Validator>, Set<Class<? extends Validator>>> rules = new HashMap<>();

    @SafeVarargs
    public final ConditionalSkipPolicy rule(Class<? extends Validator> onFail,
                                            Class<? extends Validator>... skipThese) {
        Set<Class<? extends Validator>> set = rules.computeIfAbsent(onFail, k -> new HashSet<>());
        for (Class<? extends Validator> c : skipThese) {
            set.add(c);
        }
        return this;
    }

    public boolean shouldSkip(Set<Class<? extends Validator>> failed, Class<? extends Validator> candidate) {
        for (Class<? extends Validator> f : failed) {
            if (rules.getOrDefault(f, Set.of()).contains(candidate)) return true;
        }
        return false;
    }
}
