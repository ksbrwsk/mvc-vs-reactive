package de.ksbrwsk.people;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    private Long id;
    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    public Person(String name) {
        this.name = name;
    }
}
