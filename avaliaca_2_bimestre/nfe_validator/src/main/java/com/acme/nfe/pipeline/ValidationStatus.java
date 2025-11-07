package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline;

/** Resultado unitário de um validador. */
public enum ValidationStatus {
    PASS,      // validou OK
    FAIL,      // falhou (conta para o circuit breaker)
    SKIP,      // pulado por política condicional
    TIMEOUT    // excedeu o tempo
}
