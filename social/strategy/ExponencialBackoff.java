package social.strategy;

public class ExponencialBackoff implements BackoffStrategy {
    private final long base;
    private final long max;
    public ExponencialBackoff(long base, long max) { this.base = base; this.max = max; }
    public long nextDelayMillis(int tentativa) {
        long d = (long)(base * Math.pow(2, Math.max(0, tentativa-1)));
        return Math.min(d, max);
    }
}
