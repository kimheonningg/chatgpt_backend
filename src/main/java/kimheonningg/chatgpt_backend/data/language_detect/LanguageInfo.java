package kimheonningg.chatgpt_backend.data.language_detect;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageInfo {
    private String language;

    // serializer
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    
    // deserializer
    public LanguageInfo deserialize(String serialized) {
        if(serialized == null || serialized.isEmpty())
            return null;
        
        Gson gson = new Gson();
        LanguageInfo deserialized = gson.fromJson(serialized, LanguageInfo.class);
        return deserialized;
    }
}
