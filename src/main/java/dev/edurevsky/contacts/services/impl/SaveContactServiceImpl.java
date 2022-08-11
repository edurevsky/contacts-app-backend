package dev.edurevsky.contacts.services.impl;

import dev.edurevsky.contacts.dtos.NewContactRequest;
import dev.edurevsky.contacts.mappers.NewContactRequestToContactMapper;
import dev.edurevsky.contacts.models.Contact;
import dev.edurevsky.contacts.repositories.ContactRepository;
import dev.edurevsky.contacts.services.SaveContactService;
import org.springframework.stereotype.Service;

@Service
public class SaveContactServiceImpl implements SaveContactService {

    private final NewContactRequestToContactMapper mapper;
    private final ContactRepository contactRepository;

    public SaveContactServiceImpl(NewContactRequestToContactMapper mapper, ContactRepository contactRepository) {
        this.mapper = mapper;
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact execute(NewContactRequest request) {
        Contact contact = mapper.map(request);
        return contactRepository.save(contact);
    }
}
