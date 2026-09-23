import { createContext, useContext, useEffect, useState } from "react";
import { getUser, saveUser, removeToken, removeUser } from "../utils/auth";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

  const [user, setUser] = useState(() => getUser());

  const login = (userData) => {
    setUser(userData);
    saveUser(userData);
  };

  const logout = () => {
    setUser(null);
    removeToken();
    removeUser();
  };

  // The api.js response interceptor fires this when a request comes back
  // 401 on an already-logged-in session (token expired/invalid server-side)
  // so the UI reflects "logged out" right away instead of on the next nav.
  useEffect(() => {
    const handleSessionExpired = () => setUser(null);

    window.addEventListener("pms:session-expired", handleSessionExpired);

    return () =>
      window.removeEventListener("pms:session-expired", handleSessionExpired);
  }, []);

  return (
    <AuthContext.Provider
      value={{
        user,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);