package com.thunderstormers.healthfuel.service;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thunderstormers.healthfuel.models.DietMealsDTO;
import com.thunderstormers.healthfuel.models.UserDietDetailsRequest;
import com.thunderstormers.healthfuel.service.error.InvalidPromptResponseException;

@Service
public class DietMealService {
	
	private final ChatClient chat;

	@Value("classpath:/prompts/personalized-diet.user.st")
	private Resource dietUserPromptResource;
	
	@Value("classpath:/prompts/personalized-diet.system.st")
	private Resource dietSystemPromptResource;
	
	@Autowired
	private ObjectMapper objectMapper; // Ensure it's initialized

	public DietMealService(ChatClient.Builder builder) {
		this.chat = builder.build();
	}

	public DietMealsDTO promptLLMAPI(UserDietDetailsRequest details, String dietType) {
		// System prompt ensuring diet type is strictly followed
		var systemPrompt = new PromptTemplate(dietSystemPromptResource, Map.of("dietType", dietType)).render();

		// Create user prompt template
		var dietUserPrompt = new PromptTemplate(dietUserPromptResource);
		var out = new BeanOutputConverter<>(DietMealsDTO.class);

		// Convert user details to map
		Map<String, Object> userPromptArgs = objectMapper.convertValue(details, Map.class);
		userPromptArgs.put("format", out.getFormat());
		var userPrompt = dietUserPrompt.render(userPromptArgs);

		// Call LLM with strict diet constraint
		var response = chat
				.prompt()
				.system(out.getFormat())
				.system(systemPrompt)
				.user(userPrompt)
				.call()
				.content();

		response = response.strip().replaceAll("(?s)^```json\n?|\n?```$", "");

		try {
			return objectMapper.readValue(response, DietMealsDTO.class);
		} catch (JsonProcessingException e) {
			throw new InvalidPromptResponseException("Couldn't process LLM output: ");
		}
	}

}
