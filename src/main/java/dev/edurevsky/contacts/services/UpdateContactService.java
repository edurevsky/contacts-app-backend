package dev.edurevsky.contacts.services;

import dev.edurevsky.contacts.dtos.UpdateContactRequest;
import dev.edurevsky.contacts.models.Contact;

public interface UpdateContactService {

    Contact execute(UpdateContactRequest request);
}
