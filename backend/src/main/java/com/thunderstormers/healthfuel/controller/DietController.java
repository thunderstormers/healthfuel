package com.thunderstormers.healthfuel.controller;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thunderstormers.healthfuel.models.DietResponse;
import com.thunderstormers.healthfuel.models.ModelInputs;
// import com.thunderstormers.healthfuel.service.DietRecommenderService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/personalized-diet")
public class DietController {

	private ChatClient chat;

	@Value("classpath:/prompts/specialized-diet.st")
	private Resource dietPromptResource;

	// private DietRecommenderService dietRecommender;

	public DietController(ChatClient.Builder builder
	// , DietRecommenderService service
	) {
		chat = builder.build();
		// dietRecommender = service;
	}

	@Autowired
	HttpMessageConverters converters;

	@PostMapping
	public ResponseEntity<?> diet(@RequestBody @Valid String json) {

		ObjectMapper objectMapper = new ObjectMapper();

		// Deserialize JSON into Java record
		ModelInputs entity = null;
		try {
			entity = objectMapper.readValue(json, ModelInputs.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		// var dietType = dietRecommender.predict(entity);

		var dietPrompt = new PromptTemplate(dietPromptResource);


		System.out.println(dietPrompt.getTemplate());
		System.out.println(entity);
		var out = new BeanOutputConverter<>(DietResponse.class);
System.out.println(entity.age());
System.out.println(entity.weightKg());
System.out.println(entity.heightCm());
System.out.println(entity.gender());
System.out.println(entity.dietaryRestrictions());
System.out.println(entity.diseaseType());
System.out.println(entity.physicalActivityLevel());
// System.out.println(dietType);
System.out.println(out.getFormat());

		var prompt = dietPrompt.create(Map.of(
				"age", entity.age(),
				"weight", entity.weightKg(),
				"height", entity.heightCm(),
				"gender", entity.gender(),
				"foodAllergies", entity.dietaryRestrictions(),
				"medicalConditions", entity.diseaseType(),
				"activity", entity.physicalActivityLevel(),
				// "dietType", dietType,
				"format", out.getFormat()));
		System.out.println(prompt);
		var response = chat
				.prompt(prompt)
				.system(out.getFormat())
				.call()
				.content();

		response = response.strip().replaceAll("(?s)^```json\n?|\n?```$", "");

		try {
			var jsonNode = objectMapper.readValue(response, DietResponse.class);
			return ResponseEntity.ok(jsonNode);
		} catch (JsonProcessingException e) {
			System.out.println(response);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON response: " + e.getMessage());
		}
	}

}
