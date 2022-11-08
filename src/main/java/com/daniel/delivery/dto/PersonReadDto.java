package com.daniel.delivery.dto;

import com.daniel.delivery.entity.Role;
import lombok.*;

import java.time.LocalDate;

@Value
public class PersonReadDto {

    private Long id;

    private String username;

    private LocalDate birthDate;

    private String firstname;

    private String lastname;

    private Role role;
}
