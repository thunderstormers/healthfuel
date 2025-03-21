import NutrietionList from './NutrientionList';

export default function MealContent({ meal }) {
  return (
    <div className="meal-content">
      <h2>{meal.title}</h2>
      {/* <p className="paragraph">{meal.description}</p> */}
      <ul className="list ingredient__list">
        {meal.description.split(', ').map((el, i) => (
          <li key={i}>{el}</li>
        ))}
      </ul>
      <NutrietionList nutrietions={meal.nutrients} />
    </div>
  );
}
