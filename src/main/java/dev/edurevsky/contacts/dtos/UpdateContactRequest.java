package dev.edurevsky.contacts.dtos;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UpdateContactRequest(
    Long id,
    @NotBlank(message = "O Nome não pode estar em branco.")
    String name,
    @NotBlank(message = "O Email não pode estar em branco.")
    @Email(message = "É necessário inserir um email válido")
    String email,
    @NotBlank(message = "O Número não pode estar em branco.")
    String number,
    @NotBlank(message = "A url da imagem é obrigatória.")
    @URL(message = "É necessário inserir uma url válida.")
    String pictureUrl
) { }
