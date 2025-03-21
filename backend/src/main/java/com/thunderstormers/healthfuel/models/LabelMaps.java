package com.thunderstormers.healthfuel.models;
import java.util.Map;

public class LabelMaps {
    public static final Map<String, Integer> gender = Map.of(
        "Female", 0,
        "Male", 1
    );

    public static final Map<String, Integer> diseaseType = Map.of(
        "Diabetes", 0,
        "Hypertension", 1,
        "Obesity", 2
    );

    public static final Map<String, Integer> severity = Map.of(
        "Mild", 0,
        "Moderate", 1,
        "Severe", 2
    );

    public static final Map<String, Integer> physicalActivityLevel = Map.of(
        "Active", 0,
        "Moderate", 1,
        "Sedentary", 2
    );

    public static final Map<String, Integer> dietaryRestrictions = Map.of(
        "Low_Sodium", 0,
        "Low_Sugar", 1
    );

    public static final Map<String, Integer> allergies = Map.of(
        "Gluten", 0,
        "Peanuts", 1
    );

    public static final Map<String, Integer> preferredCuisine = Map.of(
        "Chinese", 0,
        "Indian", 1,
        "Italian", 2,
        "Mexican", 3
    );

    public static final Map<Integer, String> dietRecommendation = Map.of(
        0, "Balanced",
        1, "Low_Carb",
        2, "Low_Sodium"
    );

}
