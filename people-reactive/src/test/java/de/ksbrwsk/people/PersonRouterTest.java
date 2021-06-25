package de.ksbrwsk.people;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@Import({PersonHandler.class, PersonRouter.class})
class PersonRouterTest {

    private final static String BASE = "/api/people";

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    PersonRepository personRepository;

    @Test
    void notFound() {
        this.webTestClient
                .get()
                .uri("/api/polple")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void handleFindAll() {
        when(this.personRepository.findAll())
                .thenReturn(Flux.just(
                        new Person(1L, "Name1"),
                        new Person(2L, "Name2")
                ));
        this.webTestClient
                .get()
                .uri(BASE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Person.class)
                .hasSize(2)
                .value(people -> {
                    assertEquals(people.get(0).getName(), "Name1");
                    assertEquals(people.get(1).getName(), "Name2");
                });
    }

    @Test
    void handleFindById() {
        when(this.personRepository.findById(any(Long.class)))
                .thenReturn(Mono.just(new Person(1L, "Name")));
        this.webTestClient
                .get()
                .uri(BASE + "/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Person.class)
                .value(person -> assertEquals(person.getId(), 1L));
    }

    @Test
    void handleFindByIdNotFound() {
        when(this.personRepository.findById(any(Long.class)))
                .thenReturn(Mono.empty());
        this.webTestClient
                .get()
                .uri(BASE + "/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void handleSave() {
        when(this.personRepository.save(any(Person.class)))
                .thenReturn(Mono.just(new Person(1L, "Name")));
        this.webTestClient
                .post()
                .uri(BASE)
                .bodyValue(new Person("Name"))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Person.class)
                .value(person -> assertEquals(person, new Person(1L, "Name")));
    }

    @Test
    void handleDeleteById() {
        when(this.personRepository.findById(any(Long.class)))
                .thenReturn(Mono.just(new Person(1L, "Name")));
        when(this.personRepository.delete(any(Person.class)))
                .thenReturn(Mono.empty());
        this.webTestClient
                .delete()
                .uri(BASE + "/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class)
                .value(msg -> assertEquals(msg, "successfully deleted!"));
    }

    @Test
    void handleFirstByName() {
        when(this.personRepository.findFirstByName(any(String.class)))
                .thenReturn(Mono.just(new Person(1L, "First")));
        this.webTestClient
                .get()
                .uri(BASE+"/firstByName/First")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Person.class)
                .value(person -> assertEquals(person.getName(),"First"));
    }

    @Test
    void handleFirstByNameNotFound() {
        when(this.personRepository.findFirstByName(any(String.class)))
                .thenReturn(Mono.empty());
        this.webTestClient
                .get()
                .uri(BASE+"/firstByName/First")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}