package kimheonningg.chatgpt_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kimheonningg.chatgpt_backend.entity.ChatGPTHistoryEntity;

@Repository
public interface ChatGPTHistoryRepository extends JpaRepository<ChatGPTHistoryEntity, Integer> {
    
}