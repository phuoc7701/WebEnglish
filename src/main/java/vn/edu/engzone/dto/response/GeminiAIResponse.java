package vn.edu.engzone.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class GeminiAIResponse {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;
    private String responseId;

    @Data
    public static class Candidate {
        private Content content;
        private String finishReason;
        private Double avgLogprobs;
    }

    @Data
    public static class Content {
        private List<Part> parts;
        private String role;
    }

    @Data
    public static class Part {
        private String text;
    }

    @Data
    public static class UsageMetadata {
        private int promptTokenCount;
        private int candidatesTokenCount;
        private int totalTokenCount;
        private List<TokensDetails> promptTokensDetails;
        private List<TokensDetails> candidatesTokensDetails;
    }

    @Data
    public static class TokensDetails {
        private String modality;
        private int tokenCount;
    }
}

