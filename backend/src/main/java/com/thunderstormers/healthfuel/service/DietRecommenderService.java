// package com.thunderstormers.healthfuel.service;

// import ai.onnxruntime.*;
// import groovyjarjarasm.asm.Label;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import com.thunderstormers.healthfuel.models.DietResponse;
// import com.thunderstormers.healthfuel.models.LabelMaps;
// import com.thunderstormers.healthfuel.models.ModelInputs;
// import com.thunderstormers.healthfuel.models.UserDietDetailsRequest;

// import java.io.File;
// import java.nio.FloatBuffer;
// import java.util.Arrays;
// import java.util.Collections;
// import java.util.List;
// import java.util.Map;

// @Service
// public class DietRecommenderService {
//     private final OrtEnvironment env;
//     private final OrtSession session;

//     @Value("${MODEL_PATH:src/main/resources/models/model.onnx}")
//     private String MODEL_PATH;

//     public DietRecommenderService() throws OrtException {
//         // System.out.println(OrtEnvironment.getAvailableProviders());
//         env = OrtEnvironment.getEnvironment();

//         OrtSession.SessionOptions opts = new OrtSession.SessionOptions();
//         opts.addCPU(true); // Change from AzureExecutionProvider to CPU

//         // // Create a File object
//         // File directory = new File(".");

//         // // Print the directory tree
//         // printDirectoryTree(directory, 0);

//         session = env.createSession("BOOT-INF/classes/models/model.onnx", opts);
//     }

//     public static void printDirectoryTree(File dir, int level) {
//         if (!dir.exists()) {
//             System.out.println("Directory does not exist!");
//             return;
//         }

//         // Print indentation based on directory depth
//         for (int i = 0; i < level; i++) {
//             System.out.print("    "); // Indentation for hierarchy
//         }

//         System.out.println("|-- " + dir.getName());

//         // Get all files and directories
//         File[] files = dir.listFiles();
//         if (files != null) {
//             for (File file : files) {
//                 if (file.isDirectory()) {
//                     printDirectoryTree(file, level + 1);
//                 } else {
//                     for (int i = 0; i < level + 1; i++) {
//                         System.out.print("    ");
//                     }
//                     System.out.println("|-- " + file.getName());
//                 }
//             }
//         }
//     }

//     public float[] standardize(float[] input) {
//         float[] mean = { 4.98654618e+01f, 5.24096386e-01f, 8.44701807e+01f, 1.74913655e+02f,
//                 2.80971888e+01f, 9.63855422e-01f, 1.03212851e+00f, 9.96987952e-01f,
//                 2.47732329e+03f, 1.99765462e+02f, 1.45044177e+02f, 1.36776406e+02f,
//                 3.16265060e-01f, 3.32329317e-01f, 1.54518072e+00f, 5.15622490e+00f,
//                 7.48350402e+01f, 2.46787149e+00f };
//         float[] std = { 1.80972932e+01f, 4.99419027e-01f, 2.00091275e+01f, 1.42737358e+01f,
//                 7.91258289e+00f, 6.91818797e-01f, 8.10926813e-01f, 8.14644429e-01f,
//                 5.64005864e+02f, 2.91072289e+01f, 2.02378938e+01f, 3.79393441e+01f,
//                 4.65017711e-01f, 4.71048344e-01f, 1.12874405e+00f, 2.84746266e+00f,
//                 1.48236700e+01f, 1.45985499e+00f };
//         float[] standardized = new float[input.length];
//         for (int i = 0; i < input.length; i++) {
//             standardized[i] = (input[i] - mean[i]) / std[i];
//         }
//         return standardized;
//     }

//     private float[] processCategorical(ModelInputs in) {
//         System.out.println(in);
//         return new float[] {
//                 in.age(),
//                 LabelMaps.gender.get(in.gender()),
//                 in.weightKg(),
//                 in.heightCm(),
//                 in.bMI(),
//                 LabelMaps.diseaseType.get(in.diseaseType()),
//                 LabelMaps.severity.get(in.severity()),
//                 LabelMaps.physicalActivityLevel.get(in.physicalActivityLevel()),
//                 in.weeklyExerciseHours(),
//                 in.dailyCaloricIntake(),
//                 in.cholesterolMgDL(),
//                 in.bloodPressureMmHg(),
//                 in.glucoseMgDL(),
//                 LabelMaps.dietaryRestrictions.get(in.dietaryRestrictions()),
//                 LabelMaps.allergies.get(in.allergy()),
//                 LabelMaps.preferredCuisine.get(in.preferredCuisine()),
//                 in.adherenceToDietPlan(),
//                 in.dietaryNutrientImbalance_Score(),
//         };
//     }

//     public String predict(ModelInputs request) {

//         // Example input (scaled numerical features)
//         float[] inputData = {
//                 56f, // Age
//                 1f, // Gender
//                 58.4f, // Weight_kg
//                 160f, // Height_cm
//                 22.8f, // BMI
//                 2f, // Disease_Type
//                 1f, // Severity
//                 1f, // Physical_Activity_Level
//                 3079f, // Daily_Caloric_Intake
//                 173.3f, // Cholesterol_mg/dL
//                 133f, // Blood_Pressure_mmHg
//                 116.3f, // Glucose_mg/dL
//                 0f, // Dietary_Restrictions
//                 1f, // Allergies
//                 3f, // Preferred_Cuisine
//                 3.1f, // Weekly_Exercise_Hours
//                 96.6f, // Adherence_to_Diet_Plan
//                 3.1f, // Dietary_Nutrient_Imbalance_Score
//         }; // f, 0f };

//         inputData = processCategorical(request);

//         // Apply Standard Scaler transformation
//         inputData = standardize(inputData);

//         // Convert to ONNX tensor
//         long[] shape = { 1, inputData.length }; // Adjust based on your model input

//         // Create ONNX input tensor
//         FloatBuffer inputBuffer = FloatBuffer.wrap(inputData);
//         OnnxTensor inputTensor = null;
//         long[] output = null;
//         try {
//             inputTensor = OnnxTensor.createTensor(env, inputBuffer, shape);

//             // Run inference
//             Map<String, OnnxTensor> inputs = Collections.singletonMap("float_input", inputTensor);
//             OrtSession.Result result = session.run(inputs);

//             // Get and print predictions
//             output = (long[]) result.get(0).getValue();
//             System.out.println("Predicted Diet Recommendation: " + output[0]);
//         } catch (OrtException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//             throw new IllegalStateException();
//         }

//         // Get output tensor and convert to float array
//         System.out.println(output);
//         return LabelMaps.dietRecommendation.get(output[0]);
//     }

//     // public DietResponse getPersonalizedDiet() {
//     // DietData dietData = mapper.readValue(new
//     // File("classpath:/docs/diet_data.json"), DietData.class);
//     // }

// }
// // mvn clean package -DskipTests && docker build -t healthfuel-app . && docker
// // run -p 8080:8080 healthfuel-app
