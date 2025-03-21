export default function Home({ setView }) {
  return (
    <div className="home">
      <div className="home-content">
        <h1>
          <span className="steps-number">3</span> Steps to healthier
          <img src="/imgs/fit.png" alt="fit" />
          <span style={{ textTransform: 'lowercase' }}>ou</span>
        </h1>
        <p className="paragraph">
          Achieving your health goals has never been easier with AI-powered meal
          suggestions! Simply enter your details, choose your goal, and let our
          intelligent system generate a personalized meal plan tailored to your
          preferences and lifestyle. Whether you want to lose weight, build
          muscle, or maintain a balanced diet, our AI ensures you get the
          perfect nutrition plan—customized, flexible, and easy to follow.
        </p>
        <button className="btn btn__start btn-glow" onClick={() => setView(1)}>
          Get Started!
        </button>
      </div>
      <figure className="hero-img">
        <img src="/imgs/hero.png" alt="3 steps hero images" />
      </figure>
    </div>
  );
}
