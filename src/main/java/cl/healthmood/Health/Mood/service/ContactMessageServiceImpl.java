package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.model.ContactMessage;
import cl.healthmood.Health.Mood.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageServiceImpl(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @Override
    public List<ContactMessage> getAllContactMessages() {
        return contactMessageRepository.findAll();
    }

    @Override
    public Optional<ContactMessage> getContactMessageById(Integer id) {
        return contactMessageRepository.findById(id);
    }

    @Override
    public ContactMessage saveContactMessage(ContactMessage contactMessage) {
        if (contactMessage.getSentAt() == null) {
            contactMessage.setSentAt(LocalDateTime.now());
        }
        return contactMessageRepository.save(contactMessage);
    }

    @Override
    public void deleteContactMessage(Integer id) {
        contactMessageRepository.deleteById(id);
    }
}