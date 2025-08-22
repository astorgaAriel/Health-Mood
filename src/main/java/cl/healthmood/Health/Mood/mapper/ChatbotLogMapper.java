package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.ChatbotLogRequest;
import cl.healthmood.Health.Mood.dto.ChatbotLogResponse;
import cl.healthmood.Health.Mood.model.ChatbotLog;
import cl.healthmood.Health.Mood.model.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatbotLogMapper {

    public ChatbotLog toEntity(ChatbotLogRequest chatbotLogRequest) {
        if (chatbotLogRequest == null) {
            return null;
        }

        return ChatbotLog.builder()
                .customer(Customer.builder().customerId(chatbotLogRequest.getCustomerId()).build())
                .message(chatbotLogRequest.getMessage())
                .response(chatbotLogRequest.getResponse())
                .timestamp(chatbotLogRequest.getTimestamp())
                .build();
    }

    public ChatbotLogResponse toResponse(ChatbotLog chatbotLog) {
        if (chatbotLog == null) {
            return null;
        }

        return ChatbotLogResponse.builder()
                .logId(chatbotLog.getLogId())
                .customerId(chatbotLog.getCustomer() != null ? chatbotLog.getCustomer().getCustomerId() : null)
                .customerName(chatbotLog.getCustomer() != null ? 
                        chatbotLog.getCustomer().getFirstName() + " " + chatbotLog.getCustomer().getLastName() : null)
                .message(chatbotLog.getMessage())
                .response(chatbotLog.getResponse())
                .timestamp(chatbotLog.getTimestamp())
                .build();
    }

    public List<ChatbotLogResponse> toResponseList(List<ChatbotLog> chatbotLogs) {
        if (chatbotLogs == null) {
            return null;
        }
        return chatbotLogs.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(ChatbotLogRequest chatbotLogRequest, ChatbotLog chatbotLog) {
        if (chatbotLogRequest != null && chatbotLog != null) {
            chatbotLog.setMessage(chatbotLogRequest.getMessage());
            chatbotLog.setResponse(chatbotLogRequest.getResponse());
            chatbotLog.setTimestamp(chatbotLogRequest.getTimestamp());
        }
    }
}
