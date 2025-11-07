package avaliaca_2_bimestre.nfe_validator.src.main.java.com.acme.nfe.infra;

import java.util.HashSet;
import java.util.Set;

/** DB fake com reservas (locks) e existência. */
public final class FakeDatabase {
    private final Set<String> committed = new HashSet<>();
    private final Set<String> reserved = new HashSet<>();

    public boolean exists(String numero) {
        return committed.contains(numero) || reserved.contains(numero);
    }

    public void reserve(String numero) { reserved.add(numero); }
    public void unreserve(String numero) { reserved.remove(numero); }
    public void commit(String numero) { committed.add(numero); reserved.remove(numero); }
}
