package com.thunderstormers.healthfuel.models;

public record MealSuggestions(
    Meal breakfast,
    Meal lunch,
    Meal dinner,
    Meal snacks
) {
    
    public record Meal(
        String title,
        String description,
        Nutrients nutrients
    ) {}
    
    public record Nutrients(
        String calories,
        String carbs,
        String proteins,
        String fats
    ) {}
}
