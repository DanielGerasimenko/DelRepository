package com.daniel.delivery.map;

import com.daniel.delivery.dto.PersonReadDto;
import com.daniel.delivery.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonReadMapper implements Mapper<Person, PersonReadDto> {

    @Override
    public PersonReadDto map(Person object) {
        return new PersonReadDto(
                object.getId(),
                object.getUsername(),
                object.getBirthDate(),
                object.getFirstname(),
                object.getLastname(),
                object.getRole()
                );
    }
}