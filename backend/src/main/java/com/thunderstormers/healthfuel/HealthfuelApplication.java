package com.thunderstormers.healthfuel;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ai.onnxruntime.NodeInfo;
import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import ai.onnxruntime.TensorInfo;

@SpringBootApplication
public class HealthfuelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthfuelApplication.class, args);
	}

	// private static void printNodeInfo(String name, NodeInfo nodeInfo) {
	// 	System.out.println("Name: " + name);
	// 	if (nodeInfo.getInfo() instanceof TensorInfo) {
	// 		TensorInfo tensorInfo = (TensorInfo) nodeInfo.getInfo();
	// 		System.out.println("  Type: " + tensorInfo.type);
	// 		System.out.println("  Shape: " + java.util.Arrays.toString(tensorInfo.getShape()));
	// 	} else {
	// 		System.out.println("  Non-tensor type: " + nodeInfo.getInfo());
	// 	}
	// }

	@Bean
	CommandLineRunner runner() {
		return (args) -> {
			/*
			try (OrtEnvironment env = OrtEnvironment.getEnvironment();
					InputStream modelStream = getClass().getClassLoader()
							.getResourceAsStream("models/diet_recommendation.onnx");
					OrtSession session = env.createSession(modelStream.readAllBytes(),
							new OrtSession.SessionOptions())) {

				// Get model metadata
				var metadata = session.getMetadata();
				System.out.println("Model Domain: " + metadata.getDomain());
				System.out.println("Model Producer: " + metadata.getProducerName());
				System.out.println("Model Description: " + metadata.getDescription());
				System.out.println("Model GraphName: " + metadata.getGraphName());
				System.out.println("Model GraphDescription: " + metadata.getGraphDescription());
				System.out.println("Model Version: " + metadata.getVersion());
				System.out.println("Custom Metadata Map: " + metadata.getCustomMetadata());

				// Get input metadata
				Map<String, NodeInfo> inputs = session.getInputInfo();
				System.out.println("\n=== Model Inputs ===");
				for (Map.Entry<String, NodeInfo> entry : inputs.entrySet()) {
					printNodeInfo(entry.getKey(), entry.getValue());
				}

				// Get output metadata
				Map<String, NodeInfo> outputs = session.getOutputInfo();
				System.out.println("\n=== Model Outputs ===");
				for (Map.Entry<String, NodeInfo> entry : outputs.entrySet()) {
					printNodeInfo(entry.getKey(), entry.getValue());
				}

				// Prepare input data (1 sample, 18 features)
				float[][] inputData = new float[][] {
						{ 0.5f, 1.2f, -0.3f, 0.7f, 1.1f, 0.0f, -1.2f, 3.3f, 0.9f, 2.5f,
								-0.5f, 1.7f, 0.4f, -2.1f, 0.8f, 1.5f, 0.2f, -0.9f }
				};

				// Convert to ONNX Tensor
				OnnxTensor inputTensor = OnnxTensor.createTensor(env, inputData);

				// Run inference
				Map<String, OnnxTensor> input = Collections.singletonMap("float_input", inputTensor);
				OrtSession.Result results = session.run(input);

				// Extract Output: Label (INT64)
				long[] labels = (long[]) results.get("output_label").get().getValue();
				System.out.println("Predicted Label: " + Arrays.toString(labels));

				// Extract Output: Probability (Sequence of Maps)
				Object rawProbabilities = results.get("output_probability").get().getValue();
				if (rawProbabilities instanceof List) {
					List<Map<Long, Float>> probabilityList = (List<Map<Long, Float>>) rawProbabilities;
					for (Map<Long, Float> probabilityMap : probabilityList) {
						System.out.println("Predicted Probabilities: " + probabilityMap);
					}
				}
				*/


				var preprocessModel = getClass().getClassLoader().getResourceAsStream("models/diet_recommendation.preprocess.onnx").readAllBytes();
				var mainModel = getClass().getClassLoader().getResourceAsStream("models/diet_recommendation.model.onnx").readAllBytes();
				var postprocessModel = getClass().getClassLoader().getResourceAsStream("models/diet_recommendation.postprocess.onnx").readAllBytes();

				try (OrtEnvironment env = OrtEnvironment.getEnvironment();
						OrtSession preprocessSession = env.createSession(preprocessModel, new OrtSession.SessionOptions());
						OrtSession mainSession = env.createSession(mainModel, new OrtSession.SessionOptions());
						OrtSession postprocessSession = env.createSession(postprocessModel, new OrtSession.SessionOptions())) {

					// === Step 1: Preprocessing Model ===
					// Example raw user input (replace with real data)
					Map<String, OnnxTensor> preprocessInputs = new HashMap<>();
					preprocessInputs.put("Age", OnnxTensor.createTensor(env, new float[] { 25 }));
					preprocessInputs.put("Gender", OnnxTensor.createTensor(env, new String[] { "Male" }));
					preprocessInputs.put("Weight_kg", OnnxTensor.createTensor(env, new float[] { 70 }));
					preprocessInputs.put("Height_cm", OnnxTensor.createTensor(env, new float[] { 175 }));
					preprocessInputs.put("BMI", OnnxTensor.createTensor(env, new float[] { 22.9f }));
					preprocessInputs.put("Disease_Type", OnnxTensor.createTensor(env, new String[] { "Diabetes" }));
					preprocessInputs.put("Severity", OnnxTensor.createTensor(env, new String[] { "Moderate" }));
					preprocessInputs.put("Physical_Activity_Level", OnnxTensor.createTensor(env, new String[] { "Active" }));
					preprocessInputs.put("Daily_Caloric_Intake", OnnxTensor.createTensor(env, new float[] { 2000 }));
					preprocessInputs.put("Cholesterol_mg_dL", OnnxTensor.createTensor(env, new float[] { 180 }));
					preprocessInputs.put("Blood_Pressure_mmHg", OnnxTensor.createTensor(env, new float[] { 120 }));
					preprocessInputs.put("Glucose_mg_dL", OnnxTensor.createTensor(env, new float[] { 90 }));
					preprocessInputs.put("Dietary_Restrictions", OnnxTensor.createTensor(env, new String[] { "None" }));
					preprocessInputs.put("Allergies", OnnxTensor.createTensor(env, new String[] { "None" }));
					preprocessInputs.put("Preferred_Cuisine", OnnxTensor.createTensor(env, new String[] { "Asian" }));
					preprocessInputs.put("Weekly_Exercise_Hours", OnnxTensor.createTensor(env, new float[] { 5 }));
					preprocessInputs.put("Adherence_to_Diet_Plan", OnnxTensor.createTensor(env, new float[] { 80 }));
					preprocessInputs.put("Dietary_Nutrient_Imbalance_Score", OnnxTensor.createTensor(env, new float[] { 0.5f }));

					// Run the preprocessing model
					OrtSession.Result preprocessResult = preprocessSession.run(preprocessInputs);
					OnnxTensor preprocessedData = (OnnxTensor) preprocessResult.get(0);

					// === Step 2: Main Model Inference ===
					Map<String, OnnxTensor> mainInputs = Collections.singletonMap("float_input", preprocessedData);
					OrtSession.Result mainResult = mainSession.run(mainInputs);
					OnnxTensor predictionTensor = (OnnxTensor) mainResult.get(0);
					long[] predictedLabel = (long[]) predictionTensor.getValue();

					// === Step 3: Postprocessing Model ===
					Map<String, OnnxTensor> postprocessInputs = Collections.singletonMap("predicted_label",
							OnnxTensor.createTensor(env, predictedLabel));
					OrtSession.Result postprocessResult = postprocessSession.run(postprocessInputs);
					OnnxTensor recommendationTensor = (OnnxTensor) postprocessResult.get(0);
					String[] recommendation = (String[]) recommendationTensor.getValue();

					// Print the final diet recommendation
					System.out.println("Recommended Diet: " + recommendation[0]);
					
			}
		};
	}
}
