package com.thunderstormers.healthfuel.ai;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.thunderstormers.healthfuel.ai.error.ModelLoadingException;
import com.thunderstormers.healthfuel.models.UserDietDetailsRequestBatch;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DietRecommendationModel {

	private static final String PREPROCESSING_MODEL_PATH = "models/diet_recommendation.preprocess.onnx";
	private static final String MAIN_MODEL_PATH = "models/diet_recommendation.model.onnx";
	private static final String POSTPROCESSING_MODEL_PATH = "models/diet_recommendation.postprocess.onnx";

	private final OrtEnvironment ortEnv;
	private final OrtSession preprocessor;
	private final OrtSession model;
	private final OrtSession postprocessor;

	public DietRecommendationModel() {
		ortEnv = OrtEnvironment.getEnvironment();
		var preprocessModel = getClass().getClassLoader().getResourceAsStream(PREPROCESSING_MODEL_PATH);
		var mainModel = getClass().getClassLoader().getResourceAsStream(MAIN_MODEL_PATH);
		var postprocessModel = getClass().getClassLoader().getResourceAsStream(POSTPROCESSING_MODEL_PATH);
		var ops = new OrtSession.SessionOptions();

		try {
			preprocessor = ortEnv.createSession(preprocessModel.readAllBytes(), ops);
			log.info("Loaded Diet Recommendation preprocessor from classpath: {}", PREPROCESSING_MODEL_PATH);
			model = ortEnv.createSession(mainModel.readAllBytes(), ops);
			log.info("Loaded Diet Recommendation model from classpath: {}", MAIN_MODEL_PATH);
			postprocessor = ortEnv.createSession(postprocessModel.readAllBytes(), ops);
			log.info("Loaded Diet Recommendation postprocessor from classpath: {}", POSTPROCESSING_MODEL_PATH);
		} catch (IOException | NullPointerException e) {
			throw new ModelLoadingException("Error opening model files: ", e);
		} catch (OrtException e) {
			throw new ModelLoadingException("Error running the model: ", e);
		}
	}

	public String[] predict(UserDietDetailsRequestBatch batch) throws OrtException {
		// === Step 1: Preprocessing Model ===
		Map<String, OnnxTensor> inputs = new HashMap<>();
		inputs.put("Age", OnnxTensor.createTensor(ortEnv, batch.age()));
		inputs.put("Gender", OnnxTensor.createTensor(ortEnv, batch.gender()));
		inputs.put("Weight_kg", OnnxTensor.createTensor(ortEnv, batch.weightKg()));
		inputs.put("Height_cm", OnnxTensor.createTensor(ortEnv, batch.heightCm()));
		inputs.put("BMI", OnnxTensor.createTensor(ortEnv, batch.BMI()));
		inputs.put("Disease_Type", OnnxTensor.createTensor(ortEnv, batch.diseaseType()));
		inputs.put("Severity", OnnxTensor.createTensor(ortEnv, batch.severity()));
		inputs.put("Physical_Activity_Level", OnnxTensor.createTensor(ortEnv, batch.physicalActivityLevel()));
		inputs.put("Daily_Caloric_Intake", OnnxTensor.createTensor(ortEnv, batch.dailyCaloricIntake()));
		inputs.put("Cholesterol_mg_dL", OnnxTensor.createTensor(ortEnv, batch.cholesterolMgDL()));
		inputs.put("Blood_Pressure_mmHg", OnnxTensor.createTensor(ortEnv, batch.bloodPressureMmHg()));
		inputs.put("Glucose_mg_dL", OnnxTensor.createTensor(ortEnv, batch.glucoseMgDL()));
		inputs.put("Dietary_Restrictions", OnnxTensor.createTensor(ortEnv, batch.dietaryRestrictions()));
		inputs.put("Allergies", OnnxTensor.createTensor(ortEnv, batch.allergies()));
		inputs.put("Preferred_Cuisine", OnnxTensor.createTensor(ortEnv, batch.preferredCuisine()));
		inputs.put("Weekly_Exercise_Hours", OnnxTensor.createTensor(ortEnv, batch.weeklyExerciseHours()));
		inputs.put("Adherence_to_Diet_Plan", OnnxTensor.createTensor(ortEnv, batch.adherenceToDietPlan()));
		inputs.put("Dietary_Nutrient_Imbalance_Score", OnnxTensor.createTensor(ortEnv, batch.dietaryNutrientImbalanceScore()));

		// Run the preprocessing model
		OrtSession.Result preprocessResult = preprocessor.run(inputs);
		OnnxTensor preprocessedData = (OnnxTensor) preprocessResult.get(0);

		// === Step 2: Main Model Inference ===
		Map<String, OnnxTensor> mainInputs = Collections.singletonMap("float_input", preprocessedData);
		OrtSession.Result mainResult = model.run(mainInputs);
		OnnxTensor predictionTensor = (OnnxTensor) mainResult.get(0);
		long[] predictedLabel = (long[]) predictionTensor.getValue();

		// === Step 3: Postprocessing Model ===
		Map<String, OnnxTensor> postprocessInputs = Collections.singletonMap("predicted_label",
				OnnxTensor.createTensor(ortEnv, predictedLabel));
		OrtSession.Result postprocessResult = postprocessor.run(postprocessInputs);
		OnnxTensor recommendationTensor = (OnnxTensor) postprocessResult.get(0);
		String[] recommendations = (String[]) recommendationTensor.getValue();

		return recommendations;
	}

}
