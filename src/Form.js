import { useState, useEffect } from 'react';

export default function Form({ setView, setplan }) {
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => setIsVisible(true), []);

  const [formData, setFormData] = useState({
    age: '',
    gender: 'Female',
    weightKg: '',
    heightCm: '',
    bMI: '',
    diseaseType: 'Diabetes',
    severity: 'Mild',
    physicalActivityLevel: 'Active',
    dailyCaloricIntake: '',
    cholesterolMgDL: '',
    bloodPressureMmHg: '',
    glucoseMgDL: '',
    dietaryRestrictions: 'Low_Sodium',
    allergy: 'Gluten',
    preferredCuisine: 'Chinese',
    weeklyExerciseHours: '',
    adherenceToDietPlan: '',
    dietaryNutrientImbalance_Score: '',
  });

  const handleChange = e => {
    const { name, value } = e.target;
    setFormData(prevData => ({ ...prevData, [name]: value }));
  };

  // const sendData = async function (params) {
  //   const data = await fetch(
  //     'http://3ec1-156-203-202-17.ngrok-free.app/api/personalized-diet'
  //   );
  // };

  // const handleSubmit = function (e) {
  //   e.preventDefault();
  //   console.log(formData);
  //   setView(2);
  // };

  const handleSubmit = async e => {
    e.preventDefault();
    setView(2);

    try {
      const response = await fetch(
        'https://3ec1-156-203-202-17.ngrok-free.app/api/personalized-diet',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(formData),
        }
      );

      if (!response.ok) throw new Error('Failed to submit form');
      const data = await response.json();
      setplan(data);

      console.log('Success:', data);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  // const handleSubmit = (e) => {
  //   e.preventDefault();
  //   onSubmit(formData);
  // };

  return (
    <form
      className={`data-form ${isVisible ? 'fade-in' : ''}`}
      onSubmit={handleSubmit}
    >
      {/* User Related Data */}
      <div className="form__section form__section-user">
        <h3>User Information</h3>

        <div className="form__row">
          <label>Age:</label>
          <input
            type="number"
            name="age"
            value={formData.age}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form__row">
          <label>Gender:</label>
          <select name="gender" value={formData.gender} onChange={handleChange}>
            <option value="Female">Female</option>
            <option value="Male">Male</option>
          </select>
        </div>

        <div className="form__row">
          <label>Weight (kg):</label>
          <input
            type="number"
            name="weightKg"
            value={formData.weightKg}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form__row">
          <label>Height (cm):</label>
          <input
            type="number"
            name="heightCm"
            value={formData.heightCm}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form__row">
          <label>BMI:</label>
          <input
            type="number"
            step="0.1"
            name="bMI"
            value={formData.bMI}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form__row">
          <label>Disease Type:</label>
          <select
            name="diseaseType"
            value={formData.diseaseType}
            onChange={handleChange}
          >
            <option value="Diabetes">Diabetes</option>
            <option value="Hypertension">Hypertension</option>
            <option value="Obesity">Obesity</option>
          </select>
        </div>

        <div className="form__row">
          <label>Severity:</label>
          <select
            name="severity"
            value={formData.severity}
            onChange={handleChange}
          >
            <option value="Mild">Mild</option>
            <option value="Moderate">Moderate</option>
            <option value="Severe">Severe</option>
          </select>
        </div>

        <div className="form__row">
          <label>Physical Activity Level:</label>
          <select
            name="physicalActivityLevel"
            value={formData.physicalActivityLevel}
            onChange={handleChange}
          >
            <option value="Active">Active</option>
            <option value="Moderate">Moderate</option>
            <option value="Sedentary">Sedentary</option>
          </select>
        </div>
      </div>

      {/* Plan Preferences */}
      <div className="form__section form__section-plan">
        <h3>Plan Preferences</h3>

        <div className="form__row">
          <label>Daily Caloric Intake:</label>
          <input
            type="number"
            name="dailyCaloricIntake"
            value={formData.dailyCaloricIntake}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form__row">
          <label>Cholesterol (mg/dL):</label>
          <input
            type="number"
            name="cholesterolMgDL"
            value={formData.cholesterolMgDL}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form__row">
          <label>Blood Pressure (mmHg):</label>
          <input
            type="number"
            name="bloodPressureMmHg"
            value={formData.bloodPressureMmHg}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form__row">
          <label>Glucose (mg/dL):</label>
          <input
            type="number"
            name="glucoseMgDL"
            value={formData.glucoseMgDL}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form__row">
          <label>Dietary Restrictions:</label>
          <select
            name="dietaryRestrictions"
            value={formData.dietaryRestrictions}
            onChange={handleChange}
          >
            <option value="Low_Sodium">Low Sodium</option>
            <option value="Low_Sugar">Low Sugar</option>
          </select>
        </div>

        <div className="form__row">
          <label>Allergy:</label>
          <select
            name="allergy"
            value={formData.allergy}
            onChange={handleChange}
          >
            <option value="Gluten">Gluten</option>
            <option value="Peanuts">Peanuts</option>
          </select>
        </div>

        <div className="form__row">
          <label>Preferred Cuisine:</label>
          <select
            name="preferredCuisine"
            value={formData.preferredCuisine}
            onChange={handleChange}
          >
            <option value="Chinese">Chinese</option>
            <option value="Indian">Indian</option>
            <option value="Italian">Italian</option>
            <option value="Mexican">Mexican</option>
          </select>
        </div>

        <div className="form__row">
          <label>Weekly Exercise Hours:</label>
          <input
            type="number"
            step="0.5"
            name="weeklyExerciseHours"
            value={formData.weeklyExerciseHours}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form__row">
          <label>Adherence to Diet Plan (%):</label>
          <input
            type="number"
            name="adherenceToDietPlan"
            value={formData.adherenceToDietPlan}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form__row">
          <label>Dietary Nutrient Imbalance Score:</label>
          <input
            type="number"
            name="dietaryNutrientImbalance_Score"
            value={formData.dietaryNutrientImbalance_Score}
            onChange={handleChange}
            required
          />
        </div>
      </div>

      <button className="btn btn__submit btn-glow" type="submit">
        Submit
      </button>
    </form>
  );
}
