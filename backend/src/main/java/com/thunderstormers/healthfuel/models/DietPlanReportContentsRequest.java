package com.thunderstormers.healthfuel.models;

public record DietPlanReportContentsRequest(
	UserDietDetailsRequest userInputs,
	DietMealsDTO meals) {

}
