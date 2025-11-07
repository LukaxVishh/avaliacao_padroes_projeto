package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.infra;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/** CA fake: mantém revogados e expirados. */
public final class FakeCertificateAuthority {
    private final Set<String> revoked = new HashSet<>();
    private final Set<String> expired = new HashSet<>();

    public void revoke(String serial) { revoked.add(serial); }
    public void expire(String serial) { expired.add(serial); }

    public boolean isValid(String serial, Instant now) {
        return !revoked.contains(serial) && !expired.contains(serial);
    }
}
