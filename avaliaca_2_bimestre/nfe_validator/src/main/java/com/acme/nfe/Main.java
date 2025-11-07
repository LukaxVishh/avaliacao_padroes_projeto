package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.infra.FakeCertificateAuthority;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.infra.FakeDatabase;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.infra.FakeSefazService;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.model.NFe;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline.*;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.validators.*;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

/**
 * Demonstração do pipeline:
 * - Cria NF-e, configura regras condicionais e executa cadeia.
 */
public class Main {
    public static void main(String[] args) {
        // ===== Infra =====
        FakeCertificateAuthority ca = new FakeCertificateAuthority();
        FakeDatabase db = new FakeDatabase();
        FakeSefazService sefaz = new FakeSefazService();
        ValidationContext ctx = new ValidationContext(ca, db, sefaz, Clock.systemUTC());

        // Ex.: revogar um certificado para provocar falha no 2º validador
        // ca.revoke("CERT-XYZ"); // comente/descomente para testar

        // ===== Documento =====
        NFe nfe = new NFe(
                "NFE1234560",                 // termina em 0 -> SEFAZ autoriza (dummy)
                "11222333000181",
                "99888777000166",
                1000.00,
                Instant.now(),
                "<NFe><total>1000</total></NFe>",
                "CERT-OK"
        );

        // ===== Cadeia de validadores (ordem exigida) =====
        List<AbstractTimedValidator> chain = List.of(
                new XmlSchemaValidator(),      // (1)
                new CertificateValidator(),    // (2)
                new TaxRulesValidator(),       // (3) only-if-previous-pass
                new DatabaseValidator(),       // (4) reversible
                new SefazServiceValidator()    // (5) only-if-previous-pass
        );

        // ===== Política de pulo condicional =====
        ConditionalSkipPolicy policy = new ConditionalSkipPolicy()
                // Se Schema falhar, pular TODOS os subsequentes (exemplo abrangente)
                .rule(XmlSchemaValidator.class,
                        CertificateValidator.class, TaxRulesValidator.class, DatabaseValidator.class, SefazServiceValidator.class)
                // Se Certificado falhar, pular SEFAZ (não faz sentido consultar)
                .rule(CertificateValidator.class, SefazServiceValidator.class);

        // ===== Circuit breaker: para após 3 falhas (FAIL/TIMEOUT) =====
        ValidationPipeline pipeline = new ValidationPipeline(chain, policy, 3);

        // ===== Executa =====
        List<ValidationResult> results = pipeline.execute(nfe, ctx);

        // ===== Relato =====
        System.out.println("NF-e: " + nfe);
        results.forEach(r -> System.out.println(" - " + r));

        // Observação:
        // - Se não houver falhas duras, o DatabaseValidator será "committed" pelo pipeline.
        // - Se houver falha posterior, o rollback reverterá a reserva do DB.
    }
}
