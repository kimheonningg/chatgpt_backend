package kimheonningg.chatgpt_backend.service;

import org.springframework.stereotype.Service;

import kimheonningg.chatgpt_backend.data.Answer;
import kimheonningg.chatgpt_backend.data.Question;
import kimheonningg.chatgpt_backend.data.TextSequence;
import kimheonningg.chatgpt_backend.data.TextSummarized;
import kimheonningg.chatgpt_backend.data.TextSequence.Language;

@Service
public class SummarizeService implements SpecificService<TextSequence, Language, Answer> {
    
    @Override
    public Question makeChatGPTQuestion(TextSequence textSequence) {
        Question question = new Question();
        
        // make systemMessage
        String systemMessage = "";
        if(textSequence.getLanguage() == Language.KOREAN) {
            systemMessage += "한국어로 대답해줘.\n";
            systemMessage += "아래의 JSON 형식으로 대답해줘:\n";
        }
        else if(textSequence.getLanguage() == Language.ENGLISH) {
            systemMessage += "Respond in English.\n";
            systemMessage += "Respond in the following JSON format:\n";
        }
        else { // does not happen
            throw new RuntimeException("Invalid Language");
        }
        systemMessage += generateJSON(textSequence.getLanguage());
        question.setSystemMessage(systemMessage);

        // make prompt
        String prompt = "";
        if(textSequence.getLanguage() == Language.KOREAN) {
            prompt += "주어진 아래의 글을 " + textSequence.getSentenceNum() + "개의 문장으로 요약해줘:";
        }
        else if(textSequence.getLanguage() == Language.ENGLISH) {
            prompt += "Summarize the following text in " + textSequence.getSentenceNum() + "sentences:";
        }
        else { // does not happen
            throw new RuntimeException("Invalid Language");
        }
        prompt += "\n";
        prompt += textSequence.getText();
        question.setPrompt(prompt);

        return question;
    }

    @Override
    public String generateJSON(Language language) {
        if(language == Language.KOREAN) {
            TextSummarized summarized = new TextSummarized("요약된 글");
            return summarized.serialize();
        }
        else if(language == Language.ENGLISH) {
            TextSummarized summarized = new TextSummarized("summarized");
            return summarized.serialize();
        }
        else { // does not happen
            throw new RuntimeException("Invalid Language");
        }
    }

    @Override
    public Answer extractAnswer(String summarized) {
        TextSummarized deserialized = new TextSummarized();
        deserialized = deserialized.deserialize(summarized);
        return new Answer(deserialized.getSummarized());
    }
}
