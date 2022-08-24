package dev.edurevsky.contacts.repositories;

import dev.edurevsky.contacts.models.Contact;
import dev.edurevsky.contacts.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Page<Contact> findAllByUser(Pageable pageable, User user);
}
