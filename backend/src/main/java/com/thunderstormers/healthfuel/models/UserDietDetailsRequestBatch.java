package com.thunderstormers.healthfuel.models;

public record UserDietDetailsRequestBatch(
    float[] age,
    String[] gender,
    float[] weightKg,
    float[] heightCm,
    float[] BMI,
    String[] diseaseType,
    String[] severity,
    String[] physicalActivityLevel,
    float[] dailyCaloricIntake,
    float[] cholesterolMgDL,
    float[] bloodPressureMmHg,
    float[] glucoseMgDL,
    String[] dietaryRestrictions,
    String[] allergies,
    String[] preferredCuisine,
    float[] weeklyExerciseHours,
    float[] adherenceToDietPlan,
    float[] dietaryNutrientImbalanceScore) {

    public static UserDietDetailsRequestBatch of(UserDietDetailsRequest request) {
        return new UserDietDetailsRequestBatch(
            new float[] { request.age() },
            new String[] { request.gender() },
            new float[] { request.weightKg() },
            new float[] { request.heightCm() },
            new float[] { request.BMI() },
            new String[] { request.diseaseType() },
            new String[] { request.severity() },
            new String[] { request.physicalActivityLevel() },
            new float[] { request.dailyCaloricIntake() },
            new float[] { request.cholesterolMgDL() },
            new float[] { request.bloodPressureMmHg() },
            new float[] { request.glucoseMgDL() },
            new String[] { request.dietaryRestrictions() },
            new String[] { request.allergy() },
            new String[] { request.preferredCuisine() },
            new float[] { request.weeklyExerciseHours() },
            new float[] { request.adherenceToDietPlan() },
            new float[] { request.dietaryNutrientImbalanceScore() }
        );
    }

}
