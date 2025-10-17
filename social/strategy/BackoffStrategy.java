package social.strategy;

public interface BackoffStrategy {
    long nextDelayMillis(int tentativa); // 1..n
}
