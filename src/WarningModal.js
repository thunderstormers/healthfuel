import { useState, useEffect } from 'react';

export default function WarningModal({ onConfirm, onCancel }) {
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    setIsOpen(true);
  }, []);

  return (
    <div className={`warning ${isOpen ? 'fade-in' : ''}`}>
      <h3>⚠️ Warning!</h3>
      <p className="paragraph">
        Going back will erase the data you've entered in this step. Are you
        sure?
      </p>
      <div className="warning-actions">
        <button className="btn btn__confirm" onClick={onConfirm}>
          Yes, go back
        </button>
        <button className="btn btn__cancel" onClick={onCancel}>
          Cancel
        </button>
      </div>
    </div>
  );
}
