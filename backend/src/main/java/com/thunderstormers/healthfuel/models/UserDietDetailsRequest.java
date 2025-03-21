package com.thunderstormers.healthfuel.models;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserDietDetailsRequest(
		String name,
		double age,
		double weight,
		double height,
		Gender gender,
		@NotNull
		List<String> foodAllergies,
		@NotNull
		List<String> medicalConditions,
		String activityLevel,
		String dietaryPreferences,
		String goal,
		ModelInputs inputs) {

}
