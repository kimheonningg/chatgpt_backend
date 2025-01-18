package kimheonningg.chatgpt_backend.data;

import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
