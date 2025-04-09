import React from 'react';
import { useNavigate } from 'react-router-dom';
import { removeAuthToken } from './auth';
import './Signup.css';

const Dashboard = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    removeAuthToken();
    navigate('/login');
  };

  return (
    <div className="signup-container">
      <div className="signup-card">
        <h2>Welcome to Your Dashboard</h2>
        <p className="subtitle">You're successfully logged in!</p>
        
        <div className="dashboard-content">
          <p>This is your protected dashboard page.</p>
          <p>You can add your content here.</p>
        </div>
        
        <button 
          onClick={handleLogout}
          className="signup-button"
          style={{ marginTop: '20px' }}
        >
          Logout
        </button>
      </div>
    </div>
  );
};

export default Dashboard;