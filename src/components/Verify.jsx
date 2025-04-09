import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Signup.css'; // Reusing the same styles

const Verify = () => {
  const [email, setEmail] = React.useState('');
  const navigate = useNavigate();

  useEffect(() => {
    // Check if user came from signup page
    const signupEmail = sessionStorage.getItem('signupEmail');
    
    if (!signupEmail) {
      // If not, redirect to signup
      navigate('/signup');
    } else {
      setEmail(signupEmail);
      // Clear the email from sessionStorage
      sessionStorage.removeItem('signupEmail');
    }
  }, [navigate]);

  return (
    <div className="signup-container">
      <div className="signup-card">
        <div className="signup-success">
          <h2>📧 Verify Your Email</h2>
          <p>We've sent a verification link to <strong>{email}</strong>. Please check your email and click the link to verify your account.</p>
          <p className="resend-link">Didn't receive the email? <a href="/resend-verification">Resend verification email</a></p>
        </div>
      </div>
    </div>
  );
};

export default Verify;