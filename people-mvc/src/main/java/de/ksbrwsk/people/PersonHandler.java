package de.ksbrwsk.people;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonHandler {

    private final PersonRepository personRepository;

    List<Person> handleFindAll() {
        return (List<Person>) this.personRepository.findAll();
    }

    Optional<Person> handleFindById(Long id) {
        return this.personRepository.findById(id);
    }

    Optional<Person> handleSave(Person person) {
        return Optional.of(this.personRepository.save(person));
    }

    Optional<String> handleDeleteById(Long id) {
        Optional<Person> person = this.personRepository.findById(id);
        this.personRepository.delete(person.orElseThrow());
        return Optional.of("successfully deleted!");
    }

    Optional<Person> handleFindFirstByName(@NotNull String name) {
        return this.personRepository.findFirstByName(name);
    }
}
