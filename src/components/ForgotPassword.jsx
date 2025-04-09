import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Signup.css';

const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    setError('');
    
    try {
      // Replace this with your actual API endpoint
      const response = await fetch('http://localhost:8082/api/auth/forgot-password', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email }),
      });

      if (!response.ok) {
        throw new Error('Failed to send password reset email. Please try again.');
      }

      const data = await response.json();
      console.log('Password reset email sent:', data);
      setSuccess(true);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  if (success) {
    return (
      <div className="signup-container">
        <div className="signup-success">
          <h2>📧 Password Reset Email Sent</h2>
          <p>We've sent a password reset link to <strong>{email}</strong>. Please check your email and follow the instructions.</p>
          <button 
            className="signup-button" 
            onClick={() => navigate('/login')}
            style={{ marginTop: '20px' }}
          >
            Back to Login
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="signup-container">
      <div className="signup-card">
        <h2>Forgot Password</h2>
        <p className="subtitle">Enter your email to reset your password</p>
        
        {error && <div className="error-message">{error}</div>}
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              placeholder="Enter your email"
            />
          </div>
          
          <button type="submit" disabled={isSubmitting} className="signup-button">
            {isSubmitting ? (
              <>
                <span className="spinner"></span> Sending...
              </>
            ) : (
              'Send Reset Link'
            )}
          </button>
        </form>
        
        <div className="login-link">
          Remember your password? <a href="/login" onClick={(e) => {
            e.preventDefault();
            navigate('/login');
          }}>Log in</a>
        </div>
      </div>
    </div>
  );
};

export default ForgotPassword;