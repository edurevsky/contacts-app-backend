package dev.edurevsky.contacts.services;

import dev.edurevsky.contacts.models.Contact;

public interface SwitchFavoriteContactService {

    Contact execute(Long id);
}
