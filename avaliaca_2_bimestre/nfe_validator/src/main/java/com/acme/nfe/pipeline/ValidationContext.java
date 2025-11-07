package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.pipeline;

import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.infra.FakeCertificateAuthority;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.infra.FakeDatabase;
import avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.infra.FakeSefazService;

import java.time.Clock;

/**
 * Contexto compartilhado (DIP): injeta dependências e recursos.
 * SRP: somente infraestrutura e tempo.
 */
public final class ValidationContext {
    public final FakeCertificateAuthority ca;
    public final FakeDatabase database;
    public final FakeSefazService sefaz;
    public final Clock clock;

    public ValidationContext(FakeCertificateAuthority ca, FakeDatabase database, FakeSefazService sefaz, Clock clock) {
        this.ca = ca;
        this.database = database;
        this.sefaz = sefaz;
        this.clock = clock;
    }
}
