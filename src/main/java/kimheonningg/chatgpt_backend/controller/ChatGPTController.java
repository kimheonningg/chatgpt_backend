package kimheonningg.chatgpt_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kimheonningg.chatgpt_backend.data.Answer;
import kimheonningg.chatgpt_backend.data.Question;
import kimheonningg.chatgpt_backend.data.TextSequence;
import kimheonningg.chatgpt_backend.data.TextSequence.Language;
import kimheonningg.chatgpt_backend.service.ChatGPTService;
import kimheonningg.chatgpt_backend.service.SummarizeService;


@RestController
@RequestMapping("/ask")
@CrossOrigin()
@Validated
public class ChatGPTController {
    @Autowired
    private ChatGPTService chatGPTService;
    @Autowired
    private SummarizeService summarizeService;

    @PostMapping("/chatgpt")
    public Answer askChatGPT(@RequestBody Question question) {
        String answer = chatGPTService.askChatGPT(question.getPrompt(), question.getModelType(), question.getSystemMessage());
        chatGPTService.saveHistory(question, new Answer(answer));
        return new Answer(answer);
    }

    @PostMapping("/summarize")
    public Answer askSummarize(@RequestBody TextSequence textSequence) {
        // only Korean and English text are allowed
        if(textSequence.getLanguage() != Language.KOREAN && textSequence.getLanguage() != Language.ENGLISH) {
            throw new RuntimeException("Invalid Language");
        }
        Question question = summarizeService.makeChatGPTQuestion(textSequence);
        String summarized = chatGPTService.askChatGPT(question.getPrompt(), question.getModelType(), question.getSystemMessage());
        chatGPTService.saveHistory(question, new Answer(summarized));
        return summarizeService.extractSummarized(summarized);
    }
}
