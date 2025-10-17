package social.strategy;

public class FixoBackoff implements BackoffStrategy {
    private final long intervalo;
    public FixoBackoff(long intervalo) { this.intervalo = intervalo; }
    public long nextDelayMillis(int tentativa) { return intervalo; }
}
