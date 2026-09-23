// Save JWT Token
export const saveToken = (token) => {
    if (token) {
        localStorage.setItem("token", token);
    }
};

// Get JWT Token
export const getToken = () => {
    return localStorage.getItem("token") || null;
};

// Remove JWT Token
export const removeToken = () => {
    localStorage.removeItem("token");
};

// Save logged-in user info (name/email/role)
export const saveUser = (user) => {
    if (user) {
        localStorage.setItem("user", JSON.stringify(user));
    }
};

// Get logged-in user info
export const getUser = () => {
    const raw = localStorage.getItem("user");

    if (!raw) {
        return null;
    }

    try {
        return JSON.parse(raw);
    } catch {
        return null;
    }
};

// Remove logged-in user info
export const removeUser = () => {
    localStorage.removeItem("user");
};

// Check Login Status
export const isLoggedIn = () => {
    const token = getToken();
    return token !== null && token.trim() !== "";
};

// Logout
export const logout = () => {
    removeToken();
    removeUser();
};