package com.thunderstormers.healthfuel.models;

import java.util.List;

public record DietMealsDTO(
        String dietType,
        MealSuggestions suggestedMeals,
        NutritionalInfo estimatedDailyNutritionalValue,
        List<String> generalDietaryAdvice) {

    public record MealSuggestions(
            Meal breakfast,
            Meal lunch,
            Meal dinner,
            Meal snacks) {

        public record Meal(
                String title,
                String description,
                Nutrients nutrients) {
        }

        public record Nutrients(
                String calories,
                String carbs,
                String proteins,
                String fats) {
        }
    }

    public record NutritionalInfo(
            double calories,
            double protein,
            double carbs,
            double fat) {

    }

}
