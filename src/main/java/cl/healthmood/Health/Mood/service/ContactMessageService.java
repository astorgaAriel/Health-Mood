package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.model.ContactMessage;
import java.util.List;
import java.util.Optional;

public interface ContactMessageService {

    List<ContactMessage> getAllContactMessages();

    Optional<ContactMessage> getContactMessageById(Integer id);

    ContactMessage saveContactMessage(ContactMessage contactMessage);

    void deleteContactMessage(Integer id);

}