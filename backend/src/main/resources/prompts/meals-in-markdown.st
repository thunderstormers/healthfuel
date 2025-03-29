Convert the following JSON output into a well-structured Markdown format. Ensure that:
- The output follows a **consistent and deterministic structure** for easy parsing.
- Markdown contains **no emojis, tables, or complex formatting** that might break in transforming into a PDF.
- The **meal plan** is displayed in a **clear, structured format** with proper sectioning.
- Each meal (breakfast, lunch, dinner, snacks) contains:
  - **Title**
  - **Description**
  - **Nutrient breakdown** (calories, carbs, proteins, fats).
- A **summary** is provided for the **estimated daily nutritional values**.
- The **general dietary advice** is listed as bullet points in a readable format.
- A **summary of the user's input data** is included at the top, extracted and structured from the raw user input.
- The response must include an **insights section** based on the extracted user data, focusing on:
  - How the user's condition (disease type, severity) affects their nutritional needs.
  - Recommended macronutrient balance based on their physical activity level.
  - Any necessary dietary precautions (e.g., avoiding allergens, managing cholesterol/glucose levels).
  - The impact of adherence to the diet plan on long-term health.

The output format should strictly follow this structure:

---

# **Title of the Report**

---

## User Profile Summary
Extract and summarize key details from `{userInputs}` in a structured format, including:
- Age, weight, height, gender
- Dietary restrictions, allergies
- Disease type and severity
- Physical activity level and exercise habits
- Preferred cuisine and dietary adherence
- Key health metrics (BMI, cholesterol, glucose levels, blood pressure)
- Any other relevant details provided in the user input

---

## Insights Based on User Data
Analyze `{userInputs}` to generate insights on:
- The user's dietary needs based on health conditions
- Recommended macronutrient balance for their lifestyle
- Any dietary precautions they should follow
- How adherence to the diet plan might influence their long-term health

---

## Personalized Diet Meal Plan
Extract and format `{dietMealPlan}` into a structured meal plan, ensuring:
- Each meal (breakfast, lunch, dinner, snacks) is presented with:
  - Title  
  - Description  
  - Nutritional values (calories, carbs, proteins, fats)
- Estimated total daily nutritional values are summarized

---

## General Dietary Advice
Extract and format general dietary advice from `{dietMealPlan}`, ensuring clarity and readability.

---
