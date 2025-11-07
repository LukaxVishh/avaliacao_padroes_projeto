package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.model.NFe;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline.ValidationContext;

/**
 * (1) Validador de Schema XML contra XSD (dummy).
 * Aqui apenas simulamos: XML deve começar com "<NFe".
 */
public class XmlSchemaValidator extends AbstractTimedValidator {
    @Override public long timeoutMillis() { return 500; }

    @Override
    public boolean validate(NFe nfe, ValidationContext ctx) {
        String xml = nfe.getXml().trim();
        return xml.startsWith("<NFe") && xml.endsWith("</NFe>");
    }
}
