package dev.edurevsky.contacts.mappers;

import dev.edurevsky.contacts.dtos.NewContactRequest;
import dev.edurevsky.contacts.models.Contact;
import dev.edurevsky.contacts.models.User;
import dev.edurevsky.contacts.repositories.UserRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class NewContactRequestToContactMapper implements Mapper<NewContactRequest, Contact> {

    private final UserRepository userRepository;

    public NewContactRequestToContactMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Contact map(NewContactRequest data) {
        User user = userRepository.findById(data.userId())
                .orElseThrow(EntityNotFoundException::new);
        Contact contact = new Contact();
        contact.setName(data.name());
        contact.setNumber(data.number());
        contact.setEmail(data.email());
        contact.setPictureUrl(data.pictureUrl());
        contact.setUser(user);
        return contact;
    }
}
