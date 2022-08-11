package dev.edurevsky.contacts.services.impl;

import dev.edurevsky.contacts.repositories.ContactRepository;
import dev.edurevsky.contacts.services.DeleteContactByIdService;
import org.springframework.stereotype.Service;

@Service
public class DeleteContactByIdServiceImpl implements DeleteContactByIdService {

    private final ContactRepository contactRepository;

    public DeleteContactByIdServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void execute(Long id) {
        contactRepository.deleteById(id);
    }
}
