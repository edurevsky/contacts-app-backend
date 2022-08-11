package dev.edurevsky.contacts.services.impl;

import dev.edurevsky.contacts.models.Contact;
import dev.edurevsky.contacts.repositories.ContactRepository;
import dev.edurevsky.contacts.services.FindContactByIdService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class FindContactByIdServiceImpl implements FindContactByIdService {

    private final ContactRepository contactRepository;

    public FindContactByIdServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact execute(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact with id " + id + " not found."));
    }
}
