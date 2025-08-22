package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.model.ChatbotLog;
import cl.healthmood.Health.Mood.repository.ChatbotLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatbotLogServiceImpl implements ChatbotLogService {

    private final ChatbotLogRepository chatbotLogRepository;

    public ChatbotLogServiceImpl(ChatbotLogRepository chatbotLogRepository) {
        this.chatbotLogRepository = chatbotLogRepository;
    }

    @Override
    public List<ChatbotLog> getAllChatbotLogs() {
        return chatbotLogRepository.findAll();
    }

    @Override
    public Optional<ChatbotLog> getChatbotLogById(Integer id) {
        return chatbotLogRepository.findById(id);
    }

    @Override
    public ChatbotLog saveChatbotLog(ChatbotLog chatbotLog) {
        if (chatbotLog.getTimestamp() == null) {
            chatbotLog.setTimestamp(LocalDateTime.now());
        }
        return chatbotLogRepository.save(chatbotLog);
    }

    @Override
    public void deleteChatbotLog(Integer id) {
        chatbotLogRepository.deleteById(id);
    }
}