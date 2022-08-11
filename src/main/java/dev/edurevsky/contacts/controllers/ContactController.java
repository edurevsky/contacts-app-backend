package dev.edurevsky.contacts.controllers;

import dev.edurevsky.contacts.dtos.NewContactRequest;
import dev.edurevsky.contacts.models.Contact;
import dev.edurevsky.contacts.services.FindContactByIdService;
import dev.edurevsky.contacts.services.SaveContactService;
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

    public ContactController(SaveContactService saveContactService, FindContactByIdService findContactByIdService) {
        this.saveContactService = saveContactService;
        this.findContactByIdService = findContactByIdService;
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
}
