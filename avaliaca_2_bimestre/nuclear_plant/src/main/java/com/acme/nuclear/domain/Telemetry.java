package avaliaca_2_bimestre.nuclear_plant.src.main.java.com.acme.nuclear.domain;

import java.time.Instant;
import java.util.Objects;

/**
 * Snapshot de telemetria usado pelas guardas de transição.
 * SRP (SOLID): somente dados de sensores/estado de suporte à decisão.
 */
public final class Telemetry {
    public final Instant timestamp;
    public final double temperaturaC;   // °C
    public final double pressaoBar;     // bar
    public final double radiacaoMsVh;   // mSv/h
    public final boolean resfriamentoFalhou;

    public Telemetry(Instant timestamp, double temperaturaC, double pressaoBar, double radiacaoMsVh, boolean resfriamentoFalhou) {
        this.timestamp = Objects.requireNonNull(timestamp);
        this.temperaturaC = temperaturaC;
        this.pressaoBar = pressaoBar;
        this.radiacaoMsVh = radiacaoMsVh;
        this.resfriamentoFalhou = resfriamentoFalhou;
    }

    @Override
    public String toString() {
        return "Telemetry{" +
                "t=" + timestamp +
                ", temp=" + temperaturaC +
                ", pressao=" + pressaoBar +
                ", radiacao=" + radiacaoMsVh +
                ", coolFail=" + resfriamentoFalhou +
                '}';
    }
}
