package kimheonningg.chatgpt_backend.data.format;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTMessage {
    private String role;
    private String content;
    @Nullable
    private String refusal;

    public ChatGPTMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
