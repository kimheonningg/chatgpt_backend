package kimheonningg.chatgpt_backend.service.specific;

import org.springframework.stereotype.Service;

import kimheonningg.chatgpt_backend.data.Question;

@Service
public interface SpecificService<T1, T2, T3> {
    public Question makeChatGPTQuestion(T1 input);
    public String generateJSON(T2 input);
    public T3 extractAnswer(String input);
}
