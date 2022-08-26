package dev.edurevsky.contacts.controllers;

import dev.edurevsky.contacts.dtos.NewContactRequest;
import dev.edurevsky.contacts.dtos.UpdateContactRequest;
import dev.edurevsky.contacts.models.ApplicationUser;
import dev.edurevsky.contacts.models.Contact;
import dev.edurevsky.contacts.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final SaveContactService saveContactService;
    private final FindContactByIdService findContactByIdService;
    private final FindPaginatedContactsService findPaginatedContactsService;
    private final DeleteContactByIdService deleteContactByIdService;
    private final UpdateContactService updateContactService;
    private final SwitchFavoriteContactService switchFavoriteContactService;

    public ContactController(
            SaveContactService saveContactService,
            FindContactByIdService findContactByIdService,
            FindPaginatedContactsService findPaginatedContactsService,
            DeleteContactByIdService deleteContactByIdService,
            UpdateContactService updateContactService,
            SwitchFavoriteContactService switchFavoriteContactService) {
        this.saveContactService = saveContactService;
        this.findContactByIdService = findContactByIdService;
        this.findPaginatedContactsService = findPaginatedContactsService;
        this.deleteContactByIdService = deleteContactByIdService;
        this.updateContactService = updateContactService;
        this.switchFavoriteContactService = switchFavoriteContactService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Contact> save(@RequestBody @Valid NewContactRequest request, UriComponentsBuilder uriBuilder) {
        Contact contact = saveContactService.execute(request);
        URI contactUri = uriBuilder.path("/contacts/" + contact.getId()).build().toUri();
        return ResponseEntity.created(contactUri).body(contact);
    }

    @GetMapping("{id}")
    public ResponseEntity<Contact> findById(@PathVariable("id") Long id) {
        Contact contact = findContactByIdService.execute(id);
        return ResponseEntity.ok(contact);
    }

    @GetMapping
    public ResponseEntity<Page<Contact>> findAllPaginated(
            @PageableDefault(size = 15, sort = {"name"}) Pageable pageable,
            @AuthenticationPrincipal ApplicationUser appUser
    ) {
        Page<Contact> contacts = findPaginatedContactsService.execute(pageable, appUser.getUser());
        return ResponseEntity.ok(contacts);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        deleteContactByIdService.execute(id);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Contact> update(@RequestBody UpdateContactRequest request) {
        Contact contact = updateContactService.execute(request);
        return ResponseEntity.ok(contact);
    }

    @Transactional
    @PutMapping("/favorite/{id}")
    public ResponseEntity<Contact> switchFavorite(@PathVariable("id") Long id) {
        Contact contact = switchFavoriteContactService.execute(id);
        return ResponseEntity.ok(contact);
    }
}
