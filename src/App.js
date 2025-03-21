import { useState } from 'react';
import Header from './Header';
import Home from './Home';
import FormVeiw from './FormView';
import PlanView from './PlanView';
import PrintVeiw from './PrintView';
import '../src/index.css';

export default function App() {
  const [view, setView] = useState(0);
  const [plan, setPlan] = useState(null);
  return (
    <div className="container">
      <img
        src="/imgs/top.png"
        className="decoration top-left"
        alt="weight"
        aria-hidden="true"
      />
      <Header />
      {view === 0 && <Home setView={setView} />}
      {view === 1 && <FormVeiw view={view} setView={setView} setPlan={plan} />}
      {view === 2 && <PlanView view={view} setView={setView} plan={plan} />}
      {view === 3 && <PrintVeiw view={view} setView={setView} plan={plan} />}
      <img
        src="/imgs/bottom-right.png"
        className="decoration bottom-right"
        alt="weight"
        aria-hidden="true"
      />
    </div>
  );
}
