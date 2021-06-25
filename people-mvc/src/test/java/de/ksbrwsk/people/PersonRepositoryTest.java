package de.ksbrwsk.people;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    void findFirstByName() {
        this.personRepository.deleteAll();
        this.personRepository.save(new Person("First"));
        this.personRepository.save(new Person("First"));
        Optional<Person> firstByName = this.personRepository.findFirstByName("First");
        assertTrue(firstByName.isPresent());
        var person  = firstByName.get();
        assertEquals(person.getName(), "First");
    }

    @Test
    void persist() {
        this.personRepository.deleteAll();
        this.personRepository.save(new Person("Name1"));
        this.personRepository.save(new Person("Name2"));
        List<Person> all = (List<Person>) this.personRepository.findAll();
        assertNotNull(all);
        assertEquals(all.size(), 2);
    }

    @Test
    void testdata() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            people.add(new Person("person@" + i));
        }
        this.personRepository.deleteAll();
        List<Person> all = (List<Person>) this.personRepository.saveAll(people);
        assertNotNull(all);
        assertEquals(100, all.size());
    }
}