export default function NutrietionList({ nutrietions }) {
  const totalnutrient =
    Number.parseInt(nutrietions.carbs) +
    Number.parseInt(nutrietions.fats) +
    Number.parseInt(nutrietions.proteins);

  return (
    <div className="nutrietions">
      <h3>Calories: {nutrietions.calories}</h3>
      <ul className="list nutrients__list">
        <li
          className="nutrient__item carbs"
          style={{
            '--progress': `${
              (Number.parseInt(nutrietions.carbs) / totalnutrient) * 100
            }%`,
          }}
        >
          <span className="progress"> {nutrietions.carbs}</span>
          <span className="nutrient">carbs</span>
        </li>
        <li
          className="nutrient__item fats"
          style={{
            '--progress': `${
              (Number.parseInt(nutrietions.fats) / totalnutrient) * 100
            }%`,
          }}
        >
          <span className="progress"> {nutrietions.fats} </span>
          <span className="nutrient">fats</span>
        </li>
        <li
          className="nutrient__item protein"
          style={{
            '--progress': `${
              (Number.parseInt(nutrietions.proteins) / totalnutrient) * 100
            }%`,
          }}
        >
          <span className="progress"> {nutrietions.proteins}</span>
          <span className="nutrient">proteins</span>
        </li>
      </ul>
    </div>
  );
}
