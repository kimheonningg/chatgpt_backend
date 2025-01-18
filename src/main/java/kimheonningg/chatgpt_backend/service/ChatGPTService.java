package kimheonningg.chatgpt_backend.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.micrometer.common.lang.Nullable;
import kimheonningg.chatgpt_backend.data.Answer;
import kimheonningg.chatgpt_backend.data.ChatGPTChoice;
import kimheonningg.chatgpt_backend.data.ChatGPTMessage;
import kimheonningg.chatgpt_backend.data.ChatGPTRequest;
import kimheonningg.chatgpt_backend.data.ChatGPTResponse;
import kimheonningg.chatgpt_backend.data.Question;
import kimheonningg.chatgpt_backend.entity.ChatGPTHistoryEntity;
import kimheonningg.chatgpt_backend.repository.ChatGPTHistoryRepository;

@Service
public class ChatGPTService {

    @Autowired
    ChatGPTHistoryRepository historyRepository;

    @Value("${chatgpt_backend.chat-gpt-api-key}")
    private String CHAT_GPT_API_KEY;

    public String askChatGPT(String prompt, @Nullable String modelType, @Nullable String systemMessage) {
        String url = "https://api.openai.com/v1/chat/completions";
        if(CHAT_GPT_API_KEY == null || CHAT_GPT_API_KEY.isEmpty()) {
            throw new RuntimeException("Invalid API Key or API Key missing");
        }
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + CHAT_GPT_API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");

            ChatGPTRequest chatGPTRequest = new ChatGPTRequest();
            chatGPTRequest = makeChatGPTRequestInstance(prompt, modelType, systemMessage);
            String body = chatGPTRequest.serialize();
            
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed: HTTP error code : " + responseCode);
            }

            // response from Chat GPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();

            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // extract response
            return extractResponse(response.toString());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ChatGPTRequest makeChatGPTRequestInstance(String prompt, @Nullable String modelType, @Nullable String systemMessage) {
        ChatGPTRequest request = new ChatGPTRequest();

        // set Chat GPT model
        if(modelType == null || modelType.isEmpty())
            modelType = "gpt-4";
        request.setModel(modelType);

        List<ChatGPTMessage> messages = new ArrayList<>();
        
        // set system message, if it exists
        if(systemMessage != null && !systemMessage.isEmpty()) {
            ChatGPTMessage system = new ChatGPTMessage("system", systemMessage);
            messages.add(system);
        }

        // set prompt
        ChatGPTMessage user = new ChatGPTMessage("user", prompt);
        messages.add(user);

        request.setMessages(messages);

        return request;
    }

    public static String extractResponse(String response) {
        ChatGPTResponse chatGPTResponse = new ChatGPTResponse();
        chatGPTResponse = chatGPTResponse.deserialize(response.toString());
        ChatGPTChoice[] choices = chatGPTResponse.getChoices();
        String extracted = choices[choices.length-1].getMessage().getContent();
        return extracted;
    }

    public void saveHistory(Question question, Answer answer) {
        ChatGPTHistoryEntity historyEntity = new ChatGPTHistoryEntity();
        historyEntity.setQuestion(question.getPrompt());
        historyEntity.setAnswer(answer.getAnswer());
        historyEntity = historyRepository.saveAndFlush(historyEntity);
    }
}