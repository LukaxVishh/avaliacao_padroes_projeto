package social.vendors.tiktok;

import java.util.Random;

public class TikTokApiClient {
    private final String token;
    private final Random rng = new Random();
    public TikTokApiClient(String token) { this.token = token; }

    public record UploadResp(String videoId, String user) {}
    public record Stats(long likes, long comments, long shares, long views) {}

    public UploadResp uploadVideo(String pathOrUrl, String description) throws Exception {
        check();
        if (pathOrUrl == null) throw new Exception("invalid: missing video");
        if (rng.nextDouble() < 0.05) throw new Exception("timeout");
        return new UploadResp("tk_" + Math.abs(rng.nextInt()), "brand_account");
    }

    public Stats stats(String videoId) throws Exception {
        check();
        return new Stats(800, 90, 40, 15000);
    }

    private void check() throws Exception {
        if (token == null || token.isBlank()) throw new Exception("auth error");
    }
}
