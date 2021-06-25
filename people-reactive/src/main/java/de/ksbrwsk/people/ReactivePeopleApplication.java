package de.ksbrwsk.people;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;
import reactor.core.scheduler.ReactorBlockHoundIntegration;

@SpringBootApplication
public class ReactivePeopleApplication {

	public static void main(String[] args) {
		//BlockHound.install();
		SpringApplication.run(ReactivePeopleApplication.class, args);
	}

}
