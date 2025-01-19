package kimheonningg.chatgpt_backend.data.format;

import java.util.List;

import com.google.gson.Gson;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatGPTRequest {
    private String model; // system, user, assistant
    private List<ChatGPTMessage> messages;
    
    // Serializer
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // Deserializer
    public ChatGPTRequest deserialize(String serialized) {
        if(serialized == null || serialized.isEmpty())
            return null;
        if(!serialized.contains("model") || !serialized.contains("messages"))
            return null;

        Gson gson = new Gson();
        ChatGPTRequest deserialized = gson.fromJson(serialized, ChatGPTRequest.class);
        return deserialized;
    }
}
