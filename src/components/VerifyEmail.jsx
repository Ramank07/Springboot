import React from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./VerifyEmail.css"; // Import CSS file

const VerifyEmail = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const email = location.state?.email || "your email";

  return (
    <div className="verify-container">
      <div className="verify-box">
        <h2>Email Verification</h2>
        <p>
          A verification email has been sent to <strong>{email}</strong>.
          Please check your inbox and follow the instructions to verify your email.
        </p>
        <p>If you haven't received the email, check your spam folder.</p>
        <button onClick={() => navigate("/login")}>Go to Login</button>
      </div>
    </div>
  );
};

export default VerifyEmail;
