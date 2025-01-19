package kimheonningg.chatgpt_backend.data;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextSequence {
    public enum Language {
        KOREAN, ENGLISH
    }
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Language language; 
    private int sentenceNum;
}
