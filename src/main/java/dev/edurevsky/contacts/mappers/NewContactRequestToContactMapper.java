package dev.edurevsky.contacts.mappers;

import dev.edurevsky.contacts.dtos.NewContactRequest;
import dev.edurevsky.contacts.models.Contact;
import org.springframework.stereotype.Component;

@Component
public class NewContactRequestToContactMapper implements Mapper<NewContactRequest, Contact> {

    @Override
    public Contact map(NewContactRequest data) {
        Contact contact = new Contact();
        contact.setName(data.name());
        contact.setNumber(data.number());
        contact.setEmail(data.email());
        contact.setPictureUrl(data.pictureUrl());
        return contact;
    }
}
