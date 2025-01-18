package kimheonningg.chatgpt_backend.data;

import com.google.gson.Gson;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class ChatGPTResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private ChatGPTChoice[] choices;
    private ChatGPTUsage usage;
    private String service_tier;
    @Nullable
    private String system_fingerprint;

    // serializer
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // deserializer
    public ChatGPTResponse deserialize(String serialized) {
        if(serialized == null || serialized.isEmpty())
            return null;

        Gson gson = new Gson();
        ChatGPTResponse deserialized = gson.fromJson(serialized, ChatGPTResponse.class);
        return deserialized;
    }
}

class ChatGPTUsage {
    private int prompt_tokens;
    @Nullable
    private int completion_tokens;
    private int total_tokens;
    private ChatGPTPromptTokensDetails prompt_token_details;
    private ChatGPTCompletionTokenDetails completion_token_details;
}

class ChatGPTPromptTokensDetails {
    private int cached_tokens;
    private int audio_tokens;
}

class ChatGPTCompletionTokenDetails {
    private int reasoning_tokens;
    private int audio_tokens;
    private int accepted_prediction_tokens;
    private int rejected_prediction_tokens;
}
