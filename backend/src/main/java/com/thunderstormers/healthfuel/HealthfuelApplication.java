package com.thunderstormers.healthfuel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.thunderstormers.healthfuel.models.UserDietDetailsRequest;
import com.thunderstormers.healthfuel.service.DietPlanReportService;
import com.thunderstormers.healthfuel.service.DietMealService;
import com.thunderstormers.healthfuel.service.DietRecommenderService;

@SpringBootApplication
public class HealthfuelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthfuelApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(DietRecommenderService diets, DietMealService meals, DietPlanReportService pdfs) {
		return (args) -> {
			UserDietDetailsRequest request = UserDietDetailsRequest.builder()
					.age(56)
					.gender("Male")
					.weightKg(58.4f)
					.heightCm(160)
					.BMI(22.8f)
					.diseaseType("Obesity")
					.severity("Moderate")
					.physicalActivityLevel("Moderate")
					.dailyCaloricIntake(3079)
					.cholesterolMgDL(173.3f)
					.bloodPressureMmHg(133)
					.glucoseMgDL(116.3f)
					.dietaryRestrictions("Low_Sugar")
					.allergy("Peanuts")
					.preferredCuisine("Mexican")
					.weeklyExerciseHours(3.1f)
					.adherenceToDietPlan(96.6f)
					.dietaryNutrientImbalanceScore(3.1f)
					.build();

			var dietType = diets.predict(request);
			var dietMeals = meals.promptLLMAPI(request, dietType);
			var pdf = pdfs.exportDietMealsAsPdf(request, dietMeals);

			System.out.println("Created file: " + pdf);
		};
	}
}
