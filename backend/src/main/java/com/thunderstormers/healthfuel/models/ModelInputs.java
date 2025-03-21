package com.thunderstormers.healthfuel.models;

public record ModelInputs(
	float age,
	String gender,
	float weightKg,
	float heightCm,
	float bMI,
	String diseaseType,
	String severity,
	String physicalActivityLevel,
	float weeklyExerciseHours,
	float dailyCaloricIntake,
	float cholesterolMgDL,
	float bloodPressureMmHg,
	float glucoseMgDL,
	String dietaryRestrictions,
	String allergy,
	String preferredCuisine,
	float adherenceToDietPlan,
	float dietaryNutrientImbalance_Score) {

}
