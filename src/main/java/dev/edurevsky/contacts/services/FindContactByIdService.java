package dev.edurevsky.contacts.services;

import dev.edurevsky.contacts.models.Contact;

public interface FindContactByIdService {

    Contact execute(Long id);
}
