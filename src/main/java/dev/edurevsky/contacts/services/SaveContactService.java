package dev.edurevsky.contacts.services;

import dev.edurevsky.contacts.dtos.NewContactRequest;
import dev.edurevsky.contacts.models.Contact;

public interface SaveContactService {

    Contact execute(NewContactRequest request);
}
