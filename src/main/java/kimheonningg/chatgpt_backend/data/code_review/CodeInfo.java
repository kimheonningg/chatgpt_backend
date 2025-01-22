package kimheonningg.chatgpt_backend.data.code_review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeInfo {
    private String code;
    private String language;
}
