package de.ksbrwsk.people;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/people")
@RequiredArgsConstructor
public class PeopleRestController {

    private final PersonHandler personHandler;

    @GetMapping
    public ResponseEntity<List<Person>> handleFindAll() {
        List<Person> people = this.personHandler.handleFindAll();
        return ResponseEntity.ok(people);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> handleFindById(@PathVariable("id") @NotNull Long id) {
        return ResponseEntity.of(this.personHandler.handleFindById(id));
    }

    @GetMapping(value = "/firstByName/{name}")
    public ResponseEntity<Person> handleFindFirstByName(@PathVariable("name") @NotNull String name) {
        return ResponseEntity.of(this.personHandler.handleFindFirstByName(name));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> handleDeleteById(@PathVariable("id") @NotNull Long id) {
        return ResponseEntity.of(this.personHandler.handleDeleteById(id));
    }

    @PostMapping
    ResponseEntity<Person> handleSave(@RequestBody @Valid Person person) {
        return ResponseEntity.of(this.personHandler.handleSave(person));
    }
}