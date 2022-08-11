package dev.edurevsky.contacts.controllers;

import dev.edurevsky.contacts.dtos.NewContactRequest;
import dev.edurevsky.contacts.models.Contact;
import dev.edurevsky.contacts.services.DeleteContactByIdService;
import dev.edurevsky.contacts.services.FindContactByIdService;
import dev.edurevsky.contacts.services.FindPaginatedContactsService;
import dev.edurevsky.contacts.services.SaveContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ContactController(
            SaveContactService saveContactService,
            FindContactByIdService findContactByIdService,
            FindPaginatedContactsService findPaginatedContactsService,
            DeleteContactByIdService deleteContactByIdService
    ) {
        this.saveContactService = saveContactService;
        this.findContactByIdService = findContactByIdService;
        this.findPaginatedContactsService = findPaginatedContactsService;
        this.deleteContactByIdService = deleteContactByIdService;
    }

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
    public ResponseEntity<Page<Contact>> findAllPaginated(@PageableDefault(size = 15, sort = {"name"}) Pageable pageable) {
        Page<Contact> contacts = findPaginatedContactsService.execute(pageable);
        return ResponseEntity.ok(contacts);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        deleteContactByIdService.execute(id);
    }
}
