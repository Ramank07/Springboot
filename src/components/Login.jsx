




import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Signup.css';
import { setAuthToken } from './auth';

const Login = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState('');
  const [retryAfter, setRetryAfter] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    setError('');
    setRetryAfter(null);
    
    try {
      const response = await fetch('http://localhost:8082/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include', // Important for CORS with credentials
        body: JSON.stringify(formData),
      });

      // Handle rate limiting (429 status)
      if (response.status === 429 || response.headers === 'Access-Control-Allow-Origin') {
        const waitTime = response.headers.get('Retry-After') || 
                        response.headers.get('X-Rate-Limit-Retry-After-Seconds') || 
                        60;
        setRetryAfter(parseInt(waitTime));
        throw new Error(`Too many attempts. Please try again in ${waitTime} seconds.`);
      }

      // Handle CORS errors
      if (!response.ok && response.type === 'opaque') {
        throw new Error('Network error. Please check your connection.');
      }

      const data = await response.json();
      
      if (!response.ok) {
        throw new Error(data.message || 'Login failed. Please check your credentials.');
      }

      setAuthToken(data.token);
      navigate('/dashboard');
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  // Countdown timer for rate limiting
  React.useEffect(() => {
    if (retryAfter) {
      const timer = setInterval(() => {
        setRetryAfter(prev => {
          if (prev <= 1) {
            clearInterval(timer);
            window.location.reload();
            return null;
          }
          return prev - 1;
        });
      }, 1000);
      return () => clearInterval(timer);
    }
  }, [retryAfter]);

  return (
    <div className="signup-container">
      <div className="signup-card">
        <h2>Login</h2>
        <p className="subtitle">Welcome back!</p>
        
        {/* Rate limit error */}
        {retryAfter && (
          <div className="rate-limit-error">
            <i className="fa fa-clock-o"></i> Too many attempts. Please try again in {retryAfter} seconds.
          </div>
        )}
        
        {/* Other errors */}
        {error && !retryAfter && (
          <div className="error-message">{error}</div>
        )}
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
              placeholder="Enter your email"
              disabled={retryAfter}
            />
          </div>
          
          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
              placeholder="Enter your password"
              minLength="6"
              disabled={retryAfter}
            />
          </div>
          
          <div className="forgot-password">
            <a href="/forgot-password" onClick={(e) => {
              e.preventDefault();
              navigate('/forgot-password');
            }}>Forgot Password?</a>
          </div>
          
          <button 
            type="submit" 
            disabled={isSubmitting || retryAfter} 
            className="signup-button"
          >
            {isSubmitting ? (
              <>
                <span className="spinner"></span> Logging in...
              </>
            ) : (
              'Login'
            )}
          </button>
        </form>
        
        <div className="login-link">
          Don't have an account? <a href="/signup">Sign up</a>
        </div>
      </div>
    </div>
  );
};

export default Login;