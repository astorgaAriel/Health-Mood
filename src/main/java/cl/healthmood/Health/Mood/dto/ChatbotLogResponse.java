package cl.healthmood.Health.Mood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatbotLogResponse {

    private Integer logId;
    private Integer customerId;
    private String customerName;
    private String message;
    private String response;
    private LocalDateTime timestamp;
}
