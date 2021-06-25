package de.ksbrwsk.people;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    @Test
    void create() {
        Person person = new Person(1L, "Name");
        assertEquals(person.getId(), 1L);
        assertEquals(person.getName(), "Name");
    }
}