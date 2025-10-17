package social.vendors.instagram;

import java.util.List;
import java.util.Random;

public class InstagramApiClient {
    private final String appId, secret;
    private final Random rng = new Random();
    public InstagramApiClient(String appId, String secret) { this.appId = appId; this.secret = secret; }

    public String createPost(String caption, List<String> media) throws Exception {
        check();
        if (rng.nextDouble() < 0.05) throw new Exception("network failure");
        return "ig_" + Math.abs(rng.nextInt());
    }

    public record Insights(long hearts, long comments, long shares, long reach) {}

    public Insights insights(String id) throws Exception {
        check();
        return new Insights(300, 40, 20, 5000);
    }

    private void check() throws Exception {
        if (appId == null || secret == null) throw new Exception("auth missing");
    }
}
