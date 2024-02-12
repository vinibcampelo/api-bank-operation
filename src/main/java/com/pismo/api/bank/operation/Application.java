package com.pismo.api.bank.operation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info =
	@Info(
			title = "API BANK OPERATION",
			version = "1.0",
			description = "The function of this project is to provide a Rest APIS for the financial management of our customers accounts.",
			contact = @Contact(url = "https://www.linkedin.com/in/vin%C3%ADciuscampelo/", name = "Vinicius Campelo", email = "vinibcampelo@gmail.com")
		)
)
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
