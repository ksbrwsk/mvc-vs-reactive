package de.ksbrwsk.people;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PeopleRestControllerTest {

    private final static String BASE = "/api/people";

    @Autowired
    PeopleRestController peopleRestController;

    @MockBean
    PersonRepository personRepository;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        this.webTestClient = WebTestClient.bindToController(peopleRestController).build();
    }

    @Test
    void notFound() {
        this.webTestClient
                .get()
                .uri("/api/peple")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void handleFindAll() {
        when(this.personRepository.findAll())
                .thenReturn(List.of(new Person(1L, "Name"), new Person(2L, "Name2")));
        this.webTestClient
                .get()
                .uri(BASE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Person.class)
                .hasSize(2);
    }

    @Test
    void handleFindById() {
        when(this.personRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(new Person(1L, "Name")));
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
                .thenReturn(Optional.empty());
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
                .thenReturn(new Person(1L, "Name"));
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
                .thenReturn(Optional.of(new Person(1L, "Name")));
        this.webTestClient
                .delete()
                .uri(BASE + "/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .value(msg -> assertEquals(msg, "successfully deleted!"));
    }

    @Test
    void handleFirstByName() {
        when(this.personRepository.findFirstByName(any(String.class)))
                .thenReturn(Optional.of(new Person(1L, "First")));
        this.webTestClient
                .get()
                .uri(BASE + "/firstByName/First")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Person.class)
                .value(person -> assertEquals(person.getName(), "First"));
    }

    @Test
    void handleFirstByNameNotFound() {
        when(this.personRepository.findFirstByName(any(String.class)))
                .thenReturn(Optional.empty());
        this.webTestClient
                .get()
                .uri(BASE + "/firstByName/First")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}