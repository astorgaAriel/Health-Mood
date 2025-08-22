package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.model.ChatbotLog;
import java.util.List;
import java.util.Optional;

public interface ChatbotLogService {

    List<ChatbotLog> getAllChatbotLogs();

    Optional<ChatbotLog> getChatbotLogById(Integer id);

    ChatbotLog saveChatbotLog(ChatbotLog chatbotLog);

    void deleteChatbotLog(Integer id);

}