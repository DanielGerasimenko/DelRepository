package com.daniel.delivery.service;

import com.daniel.delivery.dto.PersonCreateEditDto;
import com.daniel.delivery.dto.PersonReadDto;
import com.daniel.delivery.exception.PersonNotFoundException;
import com.daniel.delivery.map.PersonCreateEditMapper;
import com.daniel.delivery.map.PersonReadMapper;
import com.daniel.delivery.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService implements UserDetailsService {

    private final PersonRepository personRepository;
    private final PersonCreateEditMapper personCreateEditMapper;
    private final PersonReadMapper personReadMapper;

    public PersonService(PersonRepository personRepository, PersonCreateEditMapper personCreateEditMapper, PersonReadMapper personReadMapper) {
        this.personRepository = personRepository;
        this.personCreateEditMapper = personCreateEditMapper;
        this.personReadMapper = personReadMapper;
    }

    public List<PersonReadDto> getAllPerson() {
        return personRepository.findAll().stream()
                .map(personReadMapper::map)
                .toList();
    }

    public Optional<PersonReadDto> getPersonById(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .map(personReadMapper::map);
    }

    @Transactional
    public PersonReadDto createPerson(PersonCreateEditDto personDto) {
        return Optional.of(personDto)
                .map(personCreateEditMapper::map)
                .map(personRepository::save)
                .map(personReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<PersonReadDto> updatePersonById(Long id, PersonCreateEditDto personDto) throws PersonNotFoundException {
        return personRepository.findById(id)
                .map(entity -> {
                    return personCreateEditMapper.map(personDto, entity);
                })
                .map(personRepository::saveAndFlush)
                .map(personReadMapper::map);
    }

    @Transactional
    public boolean deletePersonById(Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.delete(person);
                    personRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByUsername(username)
                .map(person -> new org.springframework.security.core.userdetails.User(
                        person.getUsername(),
                        person.getPassword(),
                        Collections.singleton(person.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}