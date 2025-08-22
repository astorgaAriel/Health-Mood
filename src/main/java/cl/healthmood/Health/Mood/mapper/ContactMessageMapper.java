package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.ContactMessageRequest;
import cl.healthmood.Health.Mood.dto.ContactMessageResponse;
import cl.healthmood.Health.Mood.model.ContactMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactMessageMapper {

    public ContactMessage toEntity(ContactMessageRequest contactMessageRequest) {
        if (contactMessageRequest == null) {
            return null;
        }

        return ContactMessage.builder()
                .name(contactMessageRequest.getName())
                .email(contactMessageRequest.getEmail())
                .subject(contactMessageRequest.getSubject())
                .message(contactMessageRequest.getMessage())
                .sentAt(contactMessageRequest.getSentAt())
                .build();
    }

    public ContactMessageResponse toResponse(ContactMessage contactMessage) {
        if (contactMessage == null) {
            return null;
        }

        return ContactMessageResponse.builder()
                .messageId(contactMessage.getMessageId())
                .name(contactMessage.getName())
                .email(contactMessage.getEmail())
                .subject(contactMessage.getSubject())
                .message(contactMessage.getMessage())
                .sentAt(contactMessage.getSentAt())
                .build();
    }

    public List<ContactMessageResponse> toResponseList(List<ContactMessage> contactMessages) {
        if (contactMessages == null) {
            return null;
        }
        return contactMessages.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(ContactMessageRequest contactMessageRequest, ContactMessage contactMessage) {
        if (contactMessageRequest != null && contactMessage != null) {
            contactMessage.setName(contactMessageRequest.getName());
            contactMessage.setEmail(contactMessageRequest.getEmail());
            contactMessage.setSubject(contactMessageRequest.getSubject());
            contactMessage.setMessage(contactMessageRequest.getMessage());
            contactMessage.setSentAt(contactMessageRequest.getSentAt());
        }
    }
}
