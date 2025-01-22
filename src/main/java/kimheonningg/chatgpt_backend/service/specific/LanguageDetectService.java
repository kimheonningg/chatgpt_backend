package kimheonningg.chatgpt_backend.service.specific;

import org.springframework.stereotype.Service;

import kimheonningg.chatgpt_backend.data.Question;
import kimheonningg.chatgpt_backend.data.language_detect.LanguageInfo;

@Service
public class LanguageDetectService implements SpecificService<String, LanguageInfo, LanguageInfo>{
    @Override
    public Question makeChatGPTQuestion(String text) {

        if(text == null || text.isEmpty()) {
            throw new RuntimeException("No Given Text");
        }

        Question question = new Question();

        // make systemMessage
        String systemMessage = "Respond in the following format:\n";
        systemMessage += generateJSON(new LanguageInfo("English"));
        question.setSystemMessage(systemMessage);

        // make prompt
        String prompt = "Detect the language used in the following text:\n";
        prompt += text;
        question.setPrompt(prompt);

        return question;
    }

    @Override
    public String generateJSON(LanguageInfo languageInfo) {
        return languageInfo.serialize();
    }

    @Override 
    public LanguageInfo extractAnswer(String languageInfo) {
        LanguageInfo deserialized = new LanguageInfo();
        deserialized = deserialized.deserialize(languageInfo);
        return deserialized;
    }
}
