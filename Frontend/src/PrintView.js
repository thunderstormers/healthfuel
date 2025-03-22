import Progressbar from './ProgressBar';

export default function PrintVeiw({ view, setView, plan }) {
  const handleClick = function (e) {
    e.preventDefault();
  };

  return (
    <div className="print-view">
      <Progressbar setView={setView} view={view} />
      <ul className="list printed-list">
        {Object.entries(plan.suggested_meals).map((el, i) => (
          <li key={i}>
            <h3>{el[0]}</h3>
            <h4>{el[1].title}</h4>
            <p className="paragraph">{el[1].description}</p>
            <ul className="printed__nutrients-list">
              {Object.entries(el[1].nutrients).map((el, i) => (
                <li key={i}>
                  {el[0]} - {el[1]}
                </li>
              ))}
            </ul>
          </li>
        ))}
      </ul>
      <a href="a" className="btn btn-glow" onClick={handleClick}>
        Download
      </a>
    </div>
  );
}
