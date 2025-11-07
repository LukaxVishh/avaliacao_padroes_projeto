package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.model.NFe;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline.ValidationContext;

/**
 * ISP: interface mínima para cada validador.
 * LSP: qualquer validador pode substituir outro no pipeline.
 */
public interface Validator {
    /** Identificação amigável. */
    String name();

    /** Timeout em milissegundos para esta validação. */
    long timeoutMillis();

    /**
     * Executa a validação (pode modificar o documento). Deve ser idempotente.
     * Retorna mensagem de sucesso/falha; exceções são capturadas pelo template.
     */
    boolean validate(NFe nfe, ValidationContext ctx) throws Exception;

    /** True se o validador requer que todos anteriores tenham passado. */
    default boolean requiresAllPreviousPass() { return false; }
}
