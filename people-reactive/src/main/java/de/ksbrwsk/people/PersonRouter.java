package de.ksbrwsk.people;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PersonRouter {

    private final static String BASE = "/api/people";

    @Bean
    RouterFunction<ServerResponse> http(PersonHandler personHandler) {
        return route()
                .GET(BASE, personHandler::handleFindAll)
                .GET(BASE+"/{id}", personHandler::handleFindById)
                .GET(BASE+"/firstByName/{name}", personHandler::handleFindFirstByName)
                .DELETE(BASE+"/{id}", personHandler::handleDeleteById)
                .POST(BASE, personHandler::handleSave)
                .build();
    }
}
