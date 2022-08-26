package dev.edurevsky.contacts.services.impl;

import dev.edurevsky.contacts.models.Contact;
import dev.edurevsky.contacts.repositories.ContactRepository;
import dev.edurevsky.contacts.services.SwitchFavoriteContactService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class SwitchFavoriteContactServiceImpl implements SwitchFavoriteContactService {

    private final ContactRepository contactRepository;

    public SwitchFavoriteContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact execute(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        contact.switchFavorite();
        return contact;
    }
}
