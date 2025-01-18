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
import kimheonningg.chatgpt_backend.service.ChatGPTService;


@RestController
@RequestMapping("/ask")
@CrossOrigin()
@Validated
public class ChatGPTController {
    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping("/chatgpt")
    public Answer askChatGPT(@RequestBody Question question) {
        String answer = chatGPTService.askChatGPT(question.getPrompt(), question.getModelType(), question.getSystemMessage());
        chatGPTService.saveHistory(question, new Answer(answer));
        return new Answer(answer);
    }
}
