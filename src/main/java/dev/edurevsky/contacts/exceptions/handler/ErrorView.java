package dev.edurevsky.contacts.exceptions.handler;

public record ErrorView(
    Integer status,
    String error,
    String message,
    String path
) { }
