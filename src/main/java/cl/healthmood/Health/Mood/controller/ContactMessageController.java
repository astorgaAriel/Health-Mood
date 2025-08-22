package cl.healthmood.Health.Mood.controller;

import cl.healthmood.Health.Mood.model.ContactMessage;
import cl.healthmood.Health.Mood.service.ContactMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contact-messages")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    public ContactMessageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @GetMapping
    public List<ContactMessage> getAllContactMessages() {
        return contactMessageService.getAllContactMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactMessage> getContactMessageById(@PathVariable Integer id) {
        Optional<ContactMessage> contactMessage = contactMessageService.getContactMessageById(id);
        if (contactMessage.isPresent()) {
            return ResponseEntity.ok(contactMessage.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ContactMessage createContactMessage(@RequestBody ContactMessage contactMessage) {
        return contactMessageService.saveContactMessage(contactMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactMessage> updateContactMessage(@PathVariable Integer id, @RequestBody ContactMessage contactMessage) {
        Optional<ContactMessage> existingMessage = contactMessageService.getContactMessageById(id);
        if (existingMessage.isPresent()) {
            contactMessage.setMessageId(id);
            ContactMessage updatedMessage = contactMessageService.saveContactMessage(contactMessage);
            return ResponseEntity.ok(updatedMessage);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactMessage(@PathVariable Integer id) {
        Optional<ContactMessage> contactMessage = contactMessageService.getContactMessageById(id);
        if (contactMessage.isPresent()) {
            contactMessageService.deleteContactMessage(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}