package com.thunderstormers.healthfuel.models;

import java.util.List;

public record DietResponse(
    MealSuggestions suggestedMeals,
    NutritionalInfo estimatedDailyNutritionalValue,
    List<String> generalDietaryAdvice) {

}
