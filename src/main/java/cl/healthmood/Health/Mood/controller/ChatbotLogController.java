package cl.healthmood.Health.Mood.controller;

import cl.healthmood.Health.Mood.model.ChatbotLog;
import cl.healthmood.Health.Mood.service.ChatbotLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chatbot-logs")
public class ChatbotLogController {

    private final ChatbotLogService chatbotLogService;

    public ChatbotLogController(ChatbotLogService chatbotLogService) {
        this.chatbotLogService = chatbotLogService;
    }

    @GetMapping
    public List<ChatbotLog> getAllChatbotLogs() {
        return chatbotLogService.getAllChatbotLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatbotLog> getChatbotLogById(@PathVariable Integer id) {
        Optional<ChatbotLog> chatbotLog = chatbotLogService.getChatbotLogById(id);
        if (chatbotLog.isPresent()) {
            return ResponseEntity.ok(chatbotLog.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ChatbotLog createChatbotLog(@RequestBody ChatbotLog chatbotLog) {
        return chatbotLogService.saveChatbotLog(chatbotLog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatbotLog> updateChatbotLog(@PathVariable Integer id, @RequestBody ChatbotLog chatbotLog) {
        Optional<ChatbotLog> existingLog = chatbotLogService.getChatbotLogById(id);
        if (existingLog.isPresent()) {
            chatbotLog.setLogId(id);
            ChatbotLog updatedLog = chatbotLogService.saveChatbotLog(chatbotLog);
            return ResponseEntity.ok(updatedLog);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatbotLog(@PathVariable Integer id) {
        Optional<ChatbotLog> chatbotLog = chatbotLogService.getChatbotLogById(id);
        if (chatbotLog.isPresent()) {
            chatbotLogService.deleteChatbotLog(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}