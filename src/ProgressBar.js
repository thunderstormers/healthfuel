import { useState, useEffect } from 'react';
import WarningModal from './WarningModal';

export default function Progressbar({ view, setView }) {
  const [isVisible, setIsVisible] = useState(false);
  const [warning, setWarning] = useState(false);

  useEffect(() => {
    setIsVisible(true);
  }, []);

  const handleClick = function (e) {
    const toView = +e.target.value;
    if (toView > view) return;
    if (toView < view) {
      setWarning(toView);
      return;
    }
    setView(toView);
  };

  const onConfirm = function (e) {
    setView(v => view - (view - warning));
  };

  const onCancel = function () {
    setWarning(false);
  };

  return (
    <aside
      className="progressbar"
      style={isVisible ? { backgroundSize: ` ${(view - 1) * 50}%` } : {}}
    >
      <button
        className={`btn progress-step ${view === 1 ? 'selected' : ''}`}
        value="1"
        onClick={handleClick}
      >
        {view > 1 ? '✔' : '1'}
      </button>
      <button
        className={`btn progress-step ${view === 2 ? 'selected' : ''}`}
        value="2"
        onClick={handleClick}
      >
        {view > 2 ? '✔' : '2'}
      </button>
      <button
        className={`btn progress-step ${view === 3 ? 'selected' : ''}`}
        value="3"
        onClick={handleClick}
      >
        {view > 3 ? '✔' : '3'}
      </button>
      {warning && <WarningModal onConfirm={onConfirm} onCancel={onCancel} />}
    </aside>
  );
}
