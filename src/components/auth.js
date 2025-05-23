export const isAuthenticated = () => {
    return localStorage.getItem('token') !== null;
  };
  
  export const setAuthToken = (token) => {
    localStorage.setItem('token', token);
  };
  
  export const removeAuthToken = () => {
    localStorage.removeItem('token');
  };
  
  export const getAuthToken = () => {
    return localStorage.getItem('token');
  };