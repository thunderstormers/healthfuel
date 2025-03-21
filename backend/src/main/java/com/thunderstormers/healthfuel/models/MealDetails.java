package com.thunderstormers.healthfuel.models;

import java.util.List;

@Deprecated
public record MealDetails(
      String mealType,
	String timing,
      List<String> mealContents,
      NutritionalInfo nutritionalInfo,
      String dietaryAdvice) {
	
}
