package kimheonningg.chatgpt_backend.data;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeReviewResponse {
    private String fixedCode;
    private int errorNum;

    // serializer
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // deserializer
    public CodeReviewResponse deserialize(String serialized) {
        if(serialized == null || serialized.isEmpty())
            return null;
            
        Gson gson = new Gson();
        CodeReviewResponse deserialized = gson.fromJson(serialized, CodeReviewResponse.class);
        return deserialized;
    }
}