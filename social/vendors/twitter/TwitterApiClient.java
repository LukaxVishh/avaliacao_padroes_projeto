package social.vendors.twitter;

import java.util.List;
import java.util.Random;

/** Simula a API original (não modificar). */
public class TwitterApiClient {
    private final String token;
    private final Random rng = new Random();
    public TwitterApiClient(String token) { this.token = token; }

    public record TweetResp(String id) {}
    public record Metrics(long likes, long replies, long retweets, long views) {}

    public TweetResp tweet(String text, List<String> mediaUrls) throws Exception {
        check();
        if (text == null || text.isBlank()) throw new Exception("invalid: texto vazio");
        if (rng.nextDouble() < 0.1) throw new Exception("rate limit");
        return new TweetResp("tw_" + Math.abs(rng.nextInt()));
    }

    public Metrics metrics(String id) throws Exception {
        check();
        return new Metrics(100, 10, 5, 1000);
    }

    private void check() throws Exception {
        if (token == null || token.isBlank()) throw new Exception("auth error");
    }
}
