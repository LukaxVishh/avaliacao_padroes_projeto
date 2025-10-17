package social.vendors.linkedin;

import java.util.List;
import java.util.Random;

public class LinkedInApiClient {
    private final String token;
    private final Random rng = new Random();
    public LinkedInApiClient(String token) { this.token = token; }

    public record ShareResp(String urn) {}
    public record Analytics(long likes, long comments, long reposts, long impressions) {}

    public ShareResp share(String text, List<String> media) throws Exception {
        check();
        if (text.length() > 3000) throw new Exception("invalid: too long");
        return new ShareResp("li_" + Math.abs(rng.nextInt()));
    }

    public Analytics analytics(String urn) throws Exception {
        check();
        return new Analytics(50, 7, 3, 2000);
    }

    private void check() throws Exception {
        if (token == null || token.isBlank()) throw new Exception("auth invalid");
    }
}
