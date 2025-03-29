package com.thunderstormers.healthfuel.controller;

import com.thunderstormers.healthfuel.error.ApiResponse;
import com.thunderstormers.healthfuel.models.DietPlanReportContentsRequest;
import com.thunderstormers.healthfuel.models.UserDietDetailsRequest;
import com.thunderstormers.healthfuel.service.DietPlanReportService;
import com.thunderstormers.healthfuel.service.DietMealService;
import com.thunderstormers.healthfuel.service.DietRecommenderService;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/api/diet-plan")
public class DietController {

	private DietRecommenderService dietService;
	private DietMealService mealService;
	private DietPlanReportService reportService;

	public DietController(DietRecommenderService dietService, DietMealService mealService,
			DietPlanReportService reportService) {
		this.dietService = dietService;
		this.mealService = mealService;
		this.reportService = reportService;
	}

	@PostMapping("/generate-plan")
	public ResponseEntity<?> generatePlan(@RequestBody UserDietDetailsRequest request) {
		var dietType = dietService.predict(request);
		var dietMeals = mealService.promptLLMAPI(request, dietType);
		return ApiResponse.success(dietMeals, "successfully created personalized diet meal plan").toResponseEntity();
	}

	@PostMapping(path = "/create-pdf")
	public ResponseEntity<?> createPdf(@RequestBody DietPlanReportContentsRequest content) {
		var id = reportService.exportDietMealsAsPdf(content.userInputs(), content.meals());
		return ApiResponse.success(Map.of("pdf-at", "/api/diet-plan/generated-reports/" + id),
				"pdf generated successfully at url").toResponseEntity();
	}

	@GetMapping(path = "/generated-reports/{id}/" + DietPlanReportService.GENERATED_REPORT_FILENAME, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> getGeneratedReport(@PathVariable String id) throws FileNotFoundException, IOException {
		var pdf = Path.of(DietPlanReportService.GENERATED_REPORT_DIRECTORY, id,
				DietPlanReportService.GENERATED_REPORT_FILENAME).toFile();

		if (!pdf.exists() || !pdf.canRead()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + DietPlanReportService.GENERATED_REPORT_FILENAME + "\"")
				.body(new FileInputStream(pdf).readAllBytes());
	}

}
