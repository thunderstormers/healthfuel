export default function LoadingSpinner() {
  return (
    <div className="loading-spinner">
      <svg className="spinner">
        <g className="draw">
          <polyline points="0,45.486 38.514,45.486 44.595,33.324 50.676,45.486 57.771,45.486 62.838,55.622 71.959,9 80.067,63.729 84.122,45.486 97.297,45.486 103.379,40.419 110.473,45.486 150,45.486" />
        </g>
      </svg>
    </div>
  );
}
