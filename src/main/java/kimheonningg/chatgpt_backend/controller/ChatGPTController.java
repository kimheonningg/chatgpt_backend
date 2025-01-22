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
import kimheonningg.chatgpt_backend.data.code_review.CodeInfo;
import kimheonningg.chatgpt_backend.data.code_review.CodeReviewResponse;
import kimheonningg.chatgpt_backend.data.language_detect.LanguageInfo;
import kimheonningg.chatgpt_backend.data.summarize.TextSequence;
import kimheonningg.chatgpt_backend.data.summarize.TextSequence.Language;
import kimheonningg.chatgpt_backend.service.ChatGPTService;
import kimheonningg.chatgpt_backend.service.specific.CodeReviewService;
import kimheonningg.chatgpt_backend.service.specific.LanguageDetectService;
import kimheonningg.chatgpt_backend.service.specific.SummarizeService;


@RestController
@RequestMapping("/ask")
@CrossOrigin()
@Validated
public class ChatGPTController {
    @Autowired
    private ChatGPTService chatGPTService;
    @Autowired
    private SummarizeService summarizeService;
    @Autowired
    private CodeReviewService codeReviewService;
    @Autowired
    private LanguageDetectService languageDetectService;

    @PostMapping("/qna")
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
        return summarizeService.extractAnswer(summarized);
    }

    @PostMapping("/code-review")
    public CodeReviewResponse askCodeReview(@RequestBody CodeInfo codeInfo) {
        Question question = codeReviewService.makeChatGPTQuestion(codeInfo);
        String reviewed = chatGPTService.askChatGPT(question.getPrompt(), question.getModelType(), question.getSystemMessage());
        chatGPTService.saveHistory(question, new Answer(reviewed));
        return codeReviewService.extractAnswer(reviewed);
    }
    
    @PostMapping("/detect-language")
    public LanguageInfo detectLanguage(@RequestBody String text) {
        Question question = languageDetectService.makeChatGPTQuestion(text);
        String language = chatGPTService.askChatGPT(question.getPrompt(), question.getModelType(), question.getSystemMessage());
        chatGPTService.saveHistory(question, new Answer(language));
        return languageDetectService.extractAnswer(language);
    }
    
}
