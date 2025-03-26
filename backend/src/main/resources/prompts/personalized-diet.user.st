User input details:
- Age: {age} years  
- Weight: {weightKg} kg  
- Height: {heightCm} cm  
- Gender: {gender}  
- Dietary Restrictions: {dietaryRestrictions}  
- Allergy: {allergy}  
- Disease Type: {diseaseType}  
- Severity: {severity}  
- Physical Activity Level: {physicalActivityLevel}  
- Preferred Cuisine: {preferredCuisine}  
- Weekly Exercise Hours: {weeklyExerciseHours}  
- Adherence to Diet Plan: {adherenceToDietPlan}  
- Dietary Nutrient Imbalance Score: {dietaryNutrientImbalanceScore}  
- BMI: {BMI}  
- Daily Caloric Intake: {dailyCaloricIntake} kcal  
- Cholesterol: {cholesterolMgDL} mg/dL  
- Blood Pressure: {bloodPressureMmHg} mmHg  
- Glucose: {glucoseMgDL} mg/dL  

### **Task:**  
Based on the above user profile, generate a **personalized daily meal plan** that consists of:  
- **Breakfast, Lunch, Dinner, and Snacks**, each with:  
  - A **title**  
  - A **brief description**  
  - A **breakdown of nutrients** (calories, carbs, proteins, fats)  

Additionally, provide:  
- **Estimated Daily Nutritional Value** (total calories, protein, carbs, fat)  
- **General Dietary Advice** (5-7 bullet points) based on their health conditions and dietary restrictions.  

Return the response in **JSON format** structured like this:

{format}
