package de.ksbrwsk.people;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class PeopleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeopleApplication.class, args);
	}

}
