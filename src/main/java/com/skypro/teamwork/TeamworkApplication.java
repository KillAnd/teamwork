package com.skypro.teamwork;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.service.impl.RecommendationsServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@OpenAPIDefinition
public class TeamworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamworkApplication.class, args);
	}

}
