package kimheonningg.chatgpt_backend.data.format;

import java.util.Map;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class ChatGPTChoice {
    private int index;
    private ChatGPTMessage message;
    @Nullable
    private Map<String, double[]> logprobs;
    private String finish_reason;
}