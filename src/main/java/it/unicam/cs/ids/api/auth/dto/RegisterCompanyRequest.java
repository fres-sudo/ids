package it.unicam.cs.ids.api.auth.dto;

public record RegisterCompanyRequest(
        String name,
        String email,
        String password,
        String vat
) {}