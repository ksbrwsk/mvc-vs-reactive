package de.ksbrwsk.people;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired PersonRepository personRepository;

    @Test
    void persist() {
        Flux<Person> personFlux = this.personRepository
                .deleteAll()
                .then(this.personRepository.save(new Person("Name1")))
                .then(this.personRepository.save(new Person("Name2")))
                .thenMany(this.personRepository.findAll());
        StepVerifier
                .create(personFlux)
                .expectNextCount(2L)
                .verifyComplete();
    }

    @Test
    void firstByName() {
        Mono<Person> personMono = this.personRepository
                .deleteAll()
                .then(this.personRepository.save(new Person("First")))
                .then(this.personRepository.save(new Person("First")))
                .then(this.personRepository.findFirstByName("First"));
        StepVerifier
                .create(personMono)
                .expectNextMatches(person -> person.getName().equals("First"))
                .verifyComplete();
    }

    @Test
    void testdata() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            people.add(new Person("Person@"+i));
        }
        Flux<Person> personFlux = this.personRepository.deleteAll()
                .thenMany(this.personRepository.saveAll(people));
        StepVerifier
                .create(personFlux)
                .expectNextCount(100)
                .verifyComplete();
    }
}
