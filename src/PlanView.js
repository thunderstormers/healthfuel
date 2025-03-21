import { useState, useEffect } from 'react';
import Progressbar from './ProgressBar';
import Meal from './Meal';
import LoadingSpinner from './LoadingSpinner';

export default function PlanView({ view, setView, plan }) {
  return (
    <div className="plan-view">
      <Progressbar setView={setView} view={view} />
      {!plan ? (
        <LoadingSpinner />
      ) : (
        <Meal setView={setView} view={view} plan={plan} />
      )}
    </div>
  );
}
