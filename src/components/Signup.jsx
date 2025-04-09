// // import React, { useState } from 'react';
// // import { useNavigate } from 'react-router-dom';
// // import './Signup.css';

// // const Signup = () => {
// //   const [formData, setFormData] = useState({
// //     name: '',
// //     email: '',
// //     password: '',
// //     mobile: ''
// //   });
// //   const [isSubmitting, setIsSubmitting] = useState(false);
// //   const [error, setError] = useState('');
// //   const navigate = useNavigate();

// //   const handleChange = (e) => {
// //     const { name, value } = e.target;
// //     setFormData(prev => ({
// //       ...prev,
// //       [name]: value
// //     }));
// //   };

// //   const handleSubmit = async (e) => {
// //     e.preventDefault();
// //     setIsSubmitting(true);
// //     setError('');
    
// //     try {
// //       const response = await fetch('http://localhost:8082/api/auth/signup', {
// //         method: 'POST',
// //         headers: {
// //           'Content-Type': 'application/json',
// //         },
// //         body: JSON.stringify({
// //           name: formData.name,
// //           email: formData.email,
// //           password: formData.password,
// //           phoneNumber: formData.mobile
// //         }),
// //       });
  
// //       // First check if the response is JSON
// //       const contentType = response.headers.get('content-type');
// //       let data;
// //       console.log("i am in sign up page 1");
      
// //       if (contentType && contentType.includes('application/json')) {
// //         data = await response.json();
// //       } else {
// //         const text = await response.text();
// //         throw new Error(text || 'Signup failed');
// //       }
  
// //       if (!response.ok) {
// //         throw new Error(data.message || 'Signup failed. Please try again.');
// //       }
// //   console.log("i am in sign up page 2");
// //       sessionStorage.setItem('signupEmail', formData.email);
// //       //   console.log(navigate('/verify-email'));
// //       navigate('/verify');
// //     // window.location.href = '/verify'; // Full page reload
// //       console.log("i am in sign up page 3");
// //     } catch (err) {
// //       setError(err.message);
// //     } finally {
// //       setIsSubmitting(false);
// //     }
// //   };
// //   return (
// //     <div className="signup-container">
// //       <div className="signup-card">
// //         <h2>Create an Account</h2>
// //         <p className="subtitle">Join us today!</p>
        
// //         {error && <div className="error-message">{error}</div>}
        
// //         <form onSubmit={handleSubmit}>
// //           <div className="form-group">
// //             <label htmlFor="name">Full Name</label>
// //             <input
// //               type="text"
// //               id="name"
// //               name="name"
// //               value={formData.name}
// //               onChange={handleChange}
// //               required
// //               placeholder="Enter your full name"
// //             />
// //           </div>
          
// //           <div className="form-group">
// //             <label htmlFor="email">Email</label>
// //             <input
// //               type="email"
// //               id="email"
// //               name="email"
// //               value={formData.email}
// //               onChange={handleChange}
// //               required
// //               placeholder="Enter your email"
// //             />
// //           </div>
          
// //           <div className="form-group">
// //             <label htmlFor="password">Password</label>
// //             <input
// //               type="password"
// //               id="password"
// //               name="password"
// //               value={formData.password}
// //               onChange={handleChange}
// //               required
// //               placeholder="Create a password"
// //               minLength="6"
// //             />
// //           </div>
          
// //           <div className="form-group">
// //             <label htmlFor="mobile">Mobile Number</label>
// //             <input
// //               type="tel"
// //               id="mobile"
// //               name="mobile"
// //               value={formData.mobile}
// //               onChange={handleChange}
// //               required
// //               placeholder="Enter your mobile number"
// //             />
// //           </div>
          
// //           <button type="submit" disabled={isSubmitting} className="signup-button" >
// //             {isSubmitting ? (
// //               <>
// //                 <span className="spinner"></span> Signing Up...
// //               </>
// //             ) : (
// //               'Sign Up'
// //             )}
// //           </button>
// //         </form>
        
// //         <div className="login-link">
// //           Already have an account? <a href="/login">Log in</a>
// //         </div>
// //       </div>
// //     </div>
// //   );
// // };

// // export default Signup;



























// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import './Signup.css';

// const Signup = () => {
//   const [formData, setFormData] = useState({
//     name: '',
//     email: '',
//     password: '',
//     mobile: ''
//   });
//   const [isSubmitting, setIsSubmitting] = useState(false);
//   const [error, setError] = useState('');
//   const navigate = useNavigate();

//   // Password requirements state
//   const [passwordRequirements, setPasswordRequirements] = useState({
//     length: false,
//     uppercase: false,
//     lowercase: false,
//     number: false,
//     specialChar: false
//   });

//   const handleChange = (e) => {
//     const { name, value } = e.target;
    
//     if (name === 'password') {
//       // Check password strength
//       const password = value;
//       setPasswordRequirements({
//         length: password.length >= 6,
//         uppercase: /[A-Z]/.test(password),
//         lowercase: /[a-z]/.test(password),
//         number: /[0-9]/.test(password),
//         specialChar: /[!@#$%^&*(),.?":{}|<>]/.test(password)
//       });
//     }
    
//     setFormData(prev => ({
//       ...prev,
//       [name]: value
//     }));
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
    
//     // Check if all password requirements are met
//     const isPasswordValid = Object.values(passwordRequirements).every(Boolean);
//     if (!isPasswordValid) {
//       setError('Password does not meet all requirements');
//       return;
//     }
    
//     setIsSubmitting(true);
//     setError('');
    
//     try {
//       const response = await fetch('http://localhost:8082/api/auth/signup', {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//         },
//         body: JSON.stringify({
//           name: formData.name,
//           email: formData.email,
//           password: formData.password,
//           phoneNumber: formData.mobile
//         }),
//       });
  
//       const contentType = response.headers.get('content-type');
//       let data;
      
//       if (contentType && contentType.includes('application/json')) {
//         data = await response.json();
//       } else {
//         const text = await response.text();
//         throw new Error(text || 'Signup failed');
//       }
  
//       if (!response.ok) {
//         throw new Error(data.message || 'Signup failed. Please try again.');
//       }
  
//       sessionStorage.setItem('signupEmail', formData.email);
//       navigate('/verify');
//     } catch (err) {
//       setError(err.message);
//     } finally {
//       setIsSubmitting(false);
//     }
//   };

//   return (
//     <div className="signup-container">
//       <div className="signup-card">
//         <h2>Create an Account</h2>
//         <p className="subtitle">Join us today!</p>
        
//         {error && <div className="error-message">{error}</div>}
        
//         <form onSubmit={handleSubmit}>
//           <div className="form-group">
//             <label htmlFor="name">Full Name</label>
//             <input
//               type="text"
//               id="name"
//               name="name"
//               value={formData.name}
//               onChange={handleChange}
//               required
//               placeholder="Enter your full name"
//             />
//           </div>
          
//           <div className="form-group">
//             <label htmlFor="email">Email</label>
//             <input
//               type="email"
//               id="email"
//               name="email"
//               value={formData.email}
//               onChange={handleChange}
//               required
//               placeholder="Enter your email"
//             />
//           </div>
          
//           <div className="form-group">
//             <label htmlFor="password">Password</label>
//             <input
//               type="password"
//               id="password"
//               name="password"
//               value={formData.password}
//               onChange={handleChange}
//               required
//               placeholder="Create a password"
//               minLength="6"
//             />
//             <div className="password-requirements">
//               <p>Password must contain:</p>
//               <ul>
//                 <li className={passwordRequirements.length ? 'valid' : 'invalid'}>
//                   At least 6 characters
//                 </li>
//                 <li className={passwordRequirements.uppercase ? 'valid' : 'invalid'}>
//                   At least one uppercase letter (A-Z)
//                 </li>
//                 <li className={passwordRequirements.lowercase ? 'valid' : 'invalid'}>
//                   At least one lowercase letter (a-z)
//                 </li>
//                 <li className={passwordRequirements.number ? 'valid' : 'invalid'}>
//                   At least one number (0-9)
//                 </li>
//                 <li className={passwordRequirements.specialChar ? 'valid' : 'invalid'}>
//                   At least one special character (!@#$%^&*)
//                 </li>
//               </ul>
//             </div>
//           </div>
          
//           <div className="form-group">
//             <label htmlFor="mobile">Mobile Number</label>
//             <input
//               type="tel"
//               id="mobile"
//               name="mobile"
//               value={formData.mobile}
//               onChange={handleChange}
//               required
//               placeholder="Enter your mobile number"
//             />
//           </div>
          
//           <button 
//             type="submit" 
//             disabled={isSubmitting} 
//             className="signup-button"
//           >
//             {isSubmitting ? (
//               <>
//                 <span className="spinner"></span> Signing Up...
//               </>
//             ) : (
//               'Sign Up'
//             )}
//           </button>
//         </form>
        
//         <div className="login-link">
//           Already have an account? <a href="/login">Log in</a>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default Signup;




import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Signup.css';

const Signup = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    mobile: ''
  });
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const navigate = useNavigate();

  // Track password requirements
  const [passwordRequirements, setPasswordRequirements] = useState({
    length: false,
    uppercase: false,
    lowercase: false,
    number: false,
    specialChar: false
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    
    if (name === 'password') {
      // Check password strength in real-time
      const password = value;
      const newRequirements = {
        length: password.length >= 6,
        uppercase: /[A-Z]/.test(password),
        lowercase: /[a-z]/.test(password),
        number: /[0-9]/.test(password),
        specialChar: /[!@#$%^&*(),.?":{}|<>]/.test(password)
      };
      setPasswordRequirements(newRequirements);
      
      // Update password error message
      if (password.length > 0) {
        const missingRequirements = Object.entries(newRequirements)
          .filter(([_, met]) => !met)
          .map(([req]) => {
            switch(req) {
              case 'length': return '6 characters';
              case 'uppercase': return 'uppercase letter';
              case 'lowercase': return 'lowercase letter';
              case 'number': return 'number';
              case 'specialChar': return 'special character';
              default: return '';
            }
          });
        
        if (missingRequirements.length > 0) {
          setPasswordError(`Missing: ${missingRequirements.join(', ')}`);
        } else {
          setPasswordError('');
        }
      } else {
        setPasswordError('');
      }
    }
    
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Check password requirements before submitting
    const isPasswordValid = Object.values(passwordRequirements).every(Boolean);
    if (!isPasswordValid) {
      setError('Password does not meet all requirements');
      return;
    }
    
    setIsSubmitting(true);
    setError('');
    
    try {
      const response = await fetch('http://localhost:8082/api/auth/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: formData.name,
          email: formData.email,
          password: formData.password,
          phoneNumber: formData.mobile
        }),
      });
  
      const contentType = response.headers.get('content-type');
      let data;
      
      if (contentType && contentType.includes('application/json')) {
        data = await response.json();
      } else {
        const text = await response.text();
        throw new Error(text || 'Signup failed');
      }
  
      if (!response.ok) {
        throw new Error(data.message || 'Signup failed. Please try again.');
      }
  
      sessionStorage.setItem('signupEmail', formData.email);
      navigate('/verify');
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="signup-container">
      <div className="signup-card">
        <h2>Create an Account</h2>
        <p className="subtitle">Join us today!</p>
        
        {error && <div className="error-message">{error}</div>}
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="name">Full Name</label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
              placeholder="Enter your full name"
            />
          </div>
          
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
              placeholder="Create a password"
              minLength="6"
            />
            {passwordError && (
              <div className="password-error">{passwordError}</div>
            )}
            {formData.password.length > 0 && (
              <div className="password-strength">
                <div className={`strength-indicator ${passwordRequirements.length ? 'valid' : 'invalid'}`}>
                  ✓ At least 6 characters
                </div>
                <div className={`strength-indicator ${passwordRequirements.uppercase ? 'valid' : 'invalid'}`}>
                  ✓ Uppercase letter
                </div>
                <div className={`strength-indicator ${passwordRequirements.lowercase ? 'valid' : 'invalid'}`}>
                  ✓ Lowercase letter
                </div>
                <div className={`strength-indicator ${passwordRequirements.number ? 'valid' : 'invalid'}`}>
                  ✓ Number
                </div>
                <div className={`strength-indicator ${passwordRequirements.specialChar ? 'valid' : 'invalid'}`}>
                  ✓ Special character
                </div>
              </div>
            )}
          </div>
          
          <div className="form-group">
            <label htmlFor="mobile">Mobile Number</label>
            <input
              type="tel"
              id="mobile"
              name="mobile"
              value={formData.mobile}
              onChange={handleChange}
              required
              placeholder="Enter your mobile number"
            />
          </div>
          
          <button type="submit" disabled={isSubmitting} className="signup-button">
            {isSubmitting ? (
              <>
                <span className="spinner"></span> Signing Up...
              </>
            ) : (
              'Sign Up'
            )}
          </button>
        </form>
        
        <div className="login-link">
          Already have an account? <a href="/login">Log in</a>
        </div>
      </div>
    </div>
  );
};

export default Signup;