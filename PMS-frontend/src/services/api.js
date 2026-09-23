import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api",
  timeout: 15000,
  headers: {
    "Content-Type": "application/json",
  },
});

// ============================
// Request Interceptor
// ============================

api.interceptors.request.use(
  (config) => {

    const token = localStorage.getItem("token");

    if (token && token.trim() !== "") {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;

  },
  (error) => Promise.reject(error)
);

// ============================
// Response Interceptor
// ============================

api.interceptors.response.use(

  (response) => response,

  (error) => {

    if (error.response) {

      const status = error.response.status;

      switch (status) {

        case 401:

          // A failed login should not erase an otherwise valid session.
          if (!error.config?.url?.includes("/auth/login")) {
            localStorage.removeItem("token");
            localStorage.removeItem("user");

            // Let AuthContext know the session is gone so the navbar/sidebar
            // clear immediately instead of showing stale user info until
            // the next route change happens to re-check isLoggedIn().
            window.dispatchEvent(new Event("pms:session-expired"));

            // Send the user back to the login screen right away rather than
            // leaving them stuck on a page whose data calls will now all
            // fail 401. Skip it if we're already there.
            if (window.location.pathname !== "/") {
              window.location.href = "/";
            }
          }

          console.warn("Unauthorized");

          break;

        case 403:

          console.warn("Access Denied");

          break;

        case 404:

          console.warn("API Not Found");

          break;

        case 500:

          console.error("Internal Server Error");

          break;

        default:

          console.error(error.response.data);

      }

    } else {

      console.error("Network Error");

    }

    return Promise.reject(error);
  }

);

export default api;
