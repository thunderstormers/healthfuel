package com.thunderstormers.healthfuel.models;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDietDetailsRequest(
	float age,
	float weightKg,
	float heightCm,
	@NotNull String gender,
	@NotNull String dietaryRestrictions,
	@NotNull String allergy,
	@NotNull String diseaseType,
	@NotNull String severity,
	@NotNull String physicalActivityLevel,
	@NotNull String preferredCuisine,
	float weeklyExerciseHours,
	float adherenceToDietPlan,
	// @NotBlank String goal,
	float dietaryNutrientImbalanceScore,
	float BMI,
	float dailyCaloricIntake,
	float cholesterolMgDL,
	float bloodPressureMmHg,
	float glucoseMgDL) {

}
