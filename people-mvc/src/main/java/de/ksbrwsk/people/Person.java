package de.ksbrwsk.people;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
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
