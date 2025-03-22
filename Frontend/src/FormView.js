import Progressbar from './ProgressBar';
import Form from './Form';

export default function FormVeiw({ view, setView, setplan }) {
  return (
    <div className="form-view">
      <Progressbar setView={setView} view={view} />
      <h2>Personalized Nutrition Plan</h2>
      <Form setView={setView} setplan={setplan} />
    </div>
  );
}
