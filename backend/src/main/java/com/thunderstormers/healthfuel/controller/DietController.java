package com.thunderstormers.healthfuel.controller;

import com.thunderstormers.healthfuel.error.ApiResponse;
import com.thunderstormers.healthfuel.models.UserDietDetailsRequest;
import com.thunderstormers.healthfuel.service.DietMealService;
import com.thunderstormers.healthfuel.service.DietRecommenderService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("api/personalized-diet")
public class DietController {

	private DietRecommenderService dietRecommender;
	private DietMealService dietMealMaker;

	public DietController(DietMealService mealService, DietRecommenderService dietService) {
		dietMealMaker = mealService;
		dietRecommender = dietService;
	}

	@PostMapping
	public ResponseEntity<?> personalizedDiet(@RequestBody UserDietDetailsRequest request) {
		var dietType = dietRecommender.predict(request);
		var dietMeals = dietMealMaker.promptLLMAPI(request, dietType);
		return ApiResponse.success(dietMeals, "successfully created personalized diet meal plan").toResponseEntity();
	}

}
