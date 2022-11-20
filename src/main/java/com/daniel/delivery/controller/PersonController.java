package com.daniel.delivery.controller;

import com.daniel.delivery.dto.PersonCreateEditDto;
import com.daniel.delivery.dto.PersonReadDto;
import com.daniel.delivery.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<PersonReadDto> getAllPerson() {
        return personService.getAllPerson();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public PersonReadDto createPerson(@RequestBody PersonCreateEditDto personCreateEditDto) {
        return personService.createPerson(personCreateEditDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Optional<PersonReadDto> getPerson(@PathVariable("id") Long id) {
        return personService.getPersonById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Optional<PersonReadDto> updatePerson(@PathVariable Long id, @RequestBody PersonCreateEditDto personDto) {
       return personService.updatePersonById(id, personDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deletePerson(@PathVariable("id") Long id) {
        personService.deletePersonById(id);
    }
}