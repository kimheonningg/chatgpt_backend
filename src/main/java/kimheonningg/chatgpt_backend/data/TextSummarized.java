package kimheonningg.chatgpt_backend.data;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextSummarized {
    private String summarized;

    // serializer
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // deserializer
    public TextSummarized deserialize(String serialized) {
        if(serialized == null || serialized.isEmpty())
            return null;

        Gson gson = new Gson();
        TextSummarized deserialized = gson.fromJson(serialized, TextSummarized.class);
        return deserialized;
    }
}
