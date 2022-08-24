package dev.edurevsky.contacts.services.impl;

import dev.edurevsky.contacts.models.Contact;
import dev.edurevsky.contacts.models.User;
import dev.edurevsky.contacts.repositories.ContactRepository;
import dev.edurevsky.contacts.services.FindPaginatedContactsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindPaginatedContactsServiceImpl implements FindPaginatedContactsService {

    private final ContactRepository contactRepository;

    public FindPaginatedContactsServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Page<Contact> execute(Pageable pageable, User user) {
        return contactRepository.findAllByUser(pageable, user);
    }
}
