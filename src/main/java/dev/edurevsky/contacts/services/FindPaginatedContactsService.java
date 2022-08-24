package dev.edurevsky.contacts.services;

import dev.edurevsky.contacts.models.Contact;
import dev.edurevsky.contacts.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindPaginatedContactsService {

    Page<Contact> execute(Pageable pageable, User user);
}
