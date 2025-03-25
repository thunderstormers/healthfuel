# StandardScaler transformation applied column by column
@script(default_opset=op)
def standard_scaler_columnwise(X: FLOAT[1], mean: FLOAT[1], scale: FLOAT[1]) -> FLOAT[1]:
    return (X - mean) / scale


# LabelEncoder transformation
@script(default_opset=op)
def label_encoder(X: STRING["N"], classes: STRING["M"]) -> FLOAT["N"]:
    """Encodes input strings into class indices."""
    X_expanded = op.Unsqueeze(X, op.Constant(value_ints=[1]))  # Shape: [N, 1]
    classes_expanded = op.Unsqueeze(classes, op.Constant(value_ints=[0]))  # Shape: [1, M]

    equal_mask = op.Equal(X_expanded, classes_expanded)  # Boolean tensor [N, M]
    equal_mask_int = op.Cast(equal_mask, to=onnx.TensorProto.INT64)  # Cast to INT64

    indices = op.ArgMax(equal_mask_int, axis=1, keepdims=False)  # Shape: [N]
    
    return op.Cast(indices, to=onnx.TensorProto.FLOAT)  # Convert to FLOAT for concatenation


# Preprocessing function
@script(default_opset=op)
def preprocess(
    Age: FLOAT[1],
    Gender: STRING[1],
    Weight_kg: FLOAT[1],
    Height_cm: FLOAT[1],
    BMI: FLOAT[1],
    Disease_Type: STRING[1],
    Severity: STRING[1],
    Physical_Activity_Level: STRING[1],
    Daily_Caloric_Intake: FLOAT[1],
    Cholesterol_mg_dL: FLOAT[1],
    Blood_Pressure_mmHg: FLOAT[1],
    Glucose_mg_dL: FLOAT[1],
    Dietary_Restrictions: STRING[1],
    Allergies: STRING[1],
    Preferred_Cuisine: STRING[1],
    Weekly_Exercise_Hours: FLOAT[1],
    Adherence_to_Diet_Plan: FLOAT[1],
    Dietary_Nutrient_Imbalance_Score: FLOAT[1]
) -> FLOAT[1, 18]:

    # Encode categorical variables
    Gender = label_encoder(Gender, GENDER_CLASSES)
    Disease_Type = label_encoder(Disease_Type, DISEASE_TYPE_CLASSES)
    Severity = label_encoder(Severity, SEVERITY_CLASSES)
    Physical_Activity_Level = label_encoder(Physical_Activity_Level, PHYSICAL_ACTIVITY_CLASSES)
    Dietary_Restrictions = label_encoder(Dietary_Restrictions, DIETARY_RESTRICTIONS_CLASSES)
    Allergies = label_encoder(Allergies, ALLERGIES_CLASSES)
    Preferred_Cuisine = label_encoder(Preferred_Cuisine, PREFERRED_CUISINE_CLASSES)

    # Scale numerical inputs
    Age = standard_scaler_columnwise(Age, op.Constant(value_floats=[MEAN[0]]), op.Constant(value_floats=[SCALE[0]]))
    Weight_kg = standard_scaler_columnwise(Weight_kg, op.Constant(value_floats=[MEAN[1]]), op.Constant(value_floats=[SCALE[1]]))
    Height_cm = standard_scaler_columnwise(Height_cm, op.Constant(value_floats=[MEAN[2]]), op.Constant(value_floats=[SCALE[2]]))
    BMI = standard_scaler_columnwise(BMI, op.Constant(value_floats=[MEAN[3]]), op.Constant(value_floats=[SCALE[3]]))
    Daily_Caloric_Intake = standard_scaler_columnwise(Daily_Caloric_Intake, op.Constant(value_floats=[MEAN[4]]), op.Constant(value_floats=[SCALE[4]]))
    Cholesterol_mg_dL = standard_scaler_columnwise(Cholesterol_mg_dL, op.Constant(value_floats=[MEAN[5]]), op.Constant(value_floats=[SCALE[5]]))
    Blood_Pressure_mmHg = standard_scaler_columnwise(Blood_Pressure_mmHg, op.Constant(value_floats=[MEAN[6]]), op.Constant(value_floats=[SCALE[6]]))
    Glucose_mg_dL = standard_scaler_columnwise(Glucose_mg_dL, op.Constant(value_floats=[MEAN[7]]), op.Constant(value_floats=[SCALE[7]]))
    Weekly_Exercise_Hours = standard_scaler_columnwise(Weekly_Exercise_Hours, op.Constant(value_floats=[MEAN[8]]), op.Constant(value_floats=[SCALE[8]]))
    Adherence_to_Diet_Plan = standard_scaler_columnwise(Adherence_to_Diet_Plan, op.Constant(value_floats=[MEAN[9]]), op.Constant(value_floats=[SCALE[9]]))
    Dietary_Nutrient_Imbalance_Score = standard_scaler_columnwise(Dietary_Nutrient_Imbalance_Score, op.Constant(value_floats=[MEAN[10]]), op.Constant(value_floats=[SCALE[10]]))

    # Concatenate all processed features
    processed_values = op.Concat(
        Age,
        Gender,
        Weight_kg,
        Height_cm,
        BMI,
        Disease_Type,
        Severity,
        Physical_Activity_Level,
        Daily_Caloric_Intake,
        Cholesterol_mg_dL,
        Blood_Pressure_mmHg,
        Glucose_mg_dL,
        Dietary_Restrictions,
        Allergies,
        Preferred_Cuisine,
        Weekly_Exercise_Hours,
        Adherence_to_Diet_Plan,
        Dietary_Nutrient_Imbalance_Score,
        axis=0
    )

    return processed_values


# Convert to ONNX model
onnx_model = preprocess.to_model_proto()

# Save the model
onnx.save(onnx_model, "diet_recommendation.preprocess.onnx")

# Check the model validity
try:
    onnx.checker.check_model(onnx_model)
except onnx.checker.ValidationError as e:
    print(f"The model is invalid: {e}")
else:
    print("The model is valid!")
