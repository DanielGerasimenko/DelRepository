package com.daniel.delivery.map;

import com.daniel.delivery.dto.PersonCreateEditDto;
import com.daniel.delivery.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonCreateEditMapper implements Mapper<PersonCreateEditDto, Person>{
    private final PasswordEncoder passwordEncoder;

    @Override
    public Person map(PersonCreateEditDto object) {
        Person person = new Person();
        copy(object, person);

        return person;
    }

    @Override
    public Person map(PersonCreateEditDto fromObject, Person toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(PersonCreateEditDto object, Person person) {
        person.setUsername(object.getUsername());
        person.setFirstname(object.getFirstname());
        person.setLastname(object.getLastname());
        person.setBirthDate(object.getBirthDate());
        person.setRole(object.getRole());

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(person::setPassword);
    }
}
