package kimheonningg.chatgpt_backend.service;

import org.springframework.stereotype.Service;

import kimheonningg.chatgpt_backend.data.CodeInfo;
import kimheonningg.chatgpt_backend.data.CodeReviewResponse;
import kimheonningg.chatgpt_backend.data.Question;

@Service
public class CodeReviewService implements SpecificService<CodeInfo, CodeReviewResponse, CodeReviewResponse>{

    @Override
    public Question makeChatGPTQuestion(CodeInfo codeInfo) {
        Question question = new Question();

        if(codeInfo.getCode().isEmpty()) {
            throw new RuntimeException("No Code Provided");
        }

        // make systemMessage
        String systemMessage = "Answer in the following format";
        CodeReviewResponse reviewed = new CodeReviewResponse("System.out.println();", 1);
        systemMessage += generateJSON(reviewed);
        question.setSystemMessage(systemMessage);

        // make prompt
        String prompt = "";
        if(!codeInfo.getLanguage().isEmpty()) {
            prompt += "Review the following " + codeInfo.getLanguage() + " code:\n";
        }
        else {
            prompt += "Review the following code:\n";
        }

        prompt += codeInfo.getCode();
        question.setPrompt(prompt);

        return question;
    }

    @Override
    public String generateJSON(CodeReviewResponse reviewed) {
        return reviewed.serialize();
    }

    @Override
    public CodeReviewResponse extractAnswer(String reviewed) {
        CodeReviewResponse deserialized = new CodeReviewResponse();
        deserialized = deserialized.deserialize(reviewed);
        return deserialized;
    }
}
