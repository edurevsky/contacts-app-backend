package dev.edurevsky.contacts.services.impl;

import dev.edurevsky.contacts.dtos.UpdateContactRequest;
import dev.edurevsky.contacts.models.Contact;
import dev.edurevsky.contacts.repositories.ContactRepository;
import dev.edurevsky.contacts.services.UpdateContactService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UpdateContactServiceImpl implements UpdateContactService {

    private final ContactRepository contactRepository;

    public UpdateContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact execute(UpdateContactRequest request) {
        Contact contact = contactRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Contact not found."));
        contact.setName(request.name());
        contact.setEmail(request.email());
        contact.setNumber(request.number());
        contact.setPictureUrl(request.pictureUrl());
        return contact;
    }
}
