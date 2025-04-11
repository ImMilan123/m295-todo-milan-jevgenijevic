package ch.jevgenijevic.milan.todo.todo_project;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
		title = "Todo API",
		version = "1.0",
		description = "Simple backend for managing todo items, secured with Keycloak."
))
@SpringBootApplication
public class TodoProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoProjectApplication.class, args);
	}
}
