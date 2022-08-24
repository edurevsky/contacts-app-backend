package dev.edurevsky.contacts.dtos;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record NewContactRequest(
    @NotBlank(message = "O Nome não pode estar em branco.")
    String name,
    @NotBlank(message = "O Email não pode estar em branco.")
    @Email(message = "É necessário inserir um email válido")
    String email,
    @NotBlank(message = "O Número não pode estar em branco.")
    String number,
    @NotBlank(message = "A url da imagem é obrigatória.")
    @URL(message = "É necessário inserir uma url válida.")
    String pictureUrl,
    @NotNull(message = "O id do usuário não pode ser nula.")
    Long userId
) { }
