package kimheonningg.chatgpt_backend.data;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String prompt;
    @Nullable
    private String modelType;
    @Nullable
    private String systemMessage;

    public Question(String prompt) {
        this.prompt = prompt;
    }

    public Question(String prompt, String modelType) {
        this.prompt = prompt;
        this.modelType = modelType;
    }
}
