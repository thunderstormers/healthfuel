import { useState, useEffect } from 'react';
import MealContent from './MealContent';

const response = {
  user: {
    name: 'John Doe',
    goal: 'Weight Loss',
    dietary_preferences: 'Low-Carb',
    activity_level: 'Active',
  },
  suggested_meals: {
    breakfast: {
      title: 'Oatmeal with Fruits',
      description:
        'Oats 50g, Banana 1 medium, Almond milk 200ml, Chia seeds 1 tbsp, Honey 1 tsp',
      nutrients: {
        calories: '350 kcal',
        carbs: '60g',
        fats: '10g',
        proteins: '12g',
      },
    },
    lunch: {
      title: 'Grilled Chicken with Quinoa Salad',
      description:
        'Chicken breast 150g, Quinoa 100g, Cherry tomatoes 50g, Cucumber 50g, Olive oil 1 tbsp, Lemon juice 1 tbsp',
      nutrients: {
        calories: '600 kcal',
        carbs: '50g',
        fats: '20g',
        proteins: '45g',
      },
    },
    dinner: {
      title: 'Salmon with Steamed Vegetables',
      description: 'Salmon fillet 200g',
      nutrients: {
        calories: '500 kcal',
        carbs: '30g',
        fats: '25g',
        proteins: '40g',
      },
    },
    snacks: {
      title: 'Greek Yogurt with Nuts',
      description:
        'Greek yogurt 150g, Almonds 20g, Honey 1 tsp, Blueberries 30g',
      nutrients: {
        calories: '250 kcal',
        carbs: '30g',
        fats: '8g',
        proteins: '15g',
      },
    },
  },
};

export default function Meal({ setView, setPlan }) {
  const [isVisible, setIsVisible] = useState(false);
  const [mealType, setMealType] = useState('breakfast');

  useEffect(() => setIsVisible(true), []);

  const handleClick = function (e) {
    setMealType(e.target.value);
  };

  setPlan(response);
  const meals = response.suggested_meals;

  return (
    <div className={`meal ${isVisible ? 'fade-in' : ''}`}>
      <ul className="list meals-options">
        <li className={mealType === 'breakfast' ? 'selected' : ''}>
          <button className="btn" value="breakfast" onClick={handleClick}>
            breakfast
          </button>
        </li>
        <li className={mealType === 'lunch' ? 'selected' : ''}>
          <button className="btn" value="lunch" onClick={handleClick}>
            lunch
          </button>
        </li>
        <li className={mealType === 'dinner' ? 'selected' : ''}>
          <button className="btn" value="dinner" onClick={handleClick}>
            dinner
          </button>
        </li>
        <li className={mealType === 'snacks' ? 'selected' : ''}>
          <button className="btn" value="snacks" onClick={handleClick}>
            snacks
          </button>
        </li>
      </ul>

      <MealContent meal={meals[mealType]} />

      <div className="decoration-img" role="img" aria-hidden="true">
        <img src="/imgs/breakfast.jpg" alt="decoration"></img>
      </div>

      <button
        className="btn btn__confirm-plan btn-glow"
        onClick={() => setView(3)}
      >
        Confirm
      </button>
    </div>
  );
}
