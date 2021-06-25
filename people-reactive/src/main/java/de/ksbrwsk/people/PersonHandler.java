package de.ksbrwsk.people;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
@Log4j2
public class PersonHandler {

    private final PersonRepository personRepository;

    public Mono<ServerResponse> handleFindAll(ServerRequest serverRequest) {
        log.info("handle request {} - {}", serverRequest.method(), serverRequest.path());
        return ok().body(this.personRepository.findAll(), Person.class);
    }

    public Mono<ServerResponse> handleFindById(ServerRequest serverRequest) {
        log.info("handle request {} - {}", serverRequest.method(), serverRequest.path());
        var id = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<Person> byId = this.personRepository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        return byId.flatMap(person -> ok()
                .body(fromValue(person)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> handleSave(ServerRequest serverRequest) {
        log.info("handle request {} - {}", serverRequest.method(), serverRequest.path());
        Mono<Person> personMono = serverRequest.bodyToMono(Person.class)
                .flatMap(this.personRepository::save);
        return personMono.flatMap(person -> ok()
                .body(fromValue(person)));
    }

    public Mono<ServerResponse> handleDeleteById(ServerRequest serverRequest) {
        log.info("handle request {} - {}", serverRequest.method(), serverRequest.path());
        var id = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<Mono<String>> msgMono = this.personRepository
                .findById(id)
                .flatMap(this.personRepository::delete)
                .thenReturn(Mono.just("successfully deleted!"));
        return msgMono.flatMap(msg -> ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(msg, String.class));
    }

    public Mono<ServerResponse> handleFindFirstByName(ServerRequest serverRequest) {
        log.info("handle request {} - {}", serverRequest.method(), serverRequest.path());
        var name = serverRequest.pathVariable("name");
        Mono<Person> firstByName = this.personRepository.findFirstByName(name);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        return firstByName.flatMap(person -> ok()
                .body(fromValue(person)))
                .switchIfEmpty(notFound);

    }
}
