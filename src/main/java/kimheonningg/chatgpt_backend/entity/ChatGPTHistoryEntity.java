package kimheonningg.chatgpt_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "CHAT_GPT_HISTORY", catalog = "chatgpt_backend")
public class ChatGPTHistoryEntity {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "QUESTION")
    private String question;

    @Column(name = "ANSWER")
    private String answer;
}
