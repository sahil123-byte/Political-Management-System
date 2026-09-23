import api from "./api";

const authService = {
  async login(loginData) {
    try {
      const { data } = await api.post("/auth/login", loginData);
      return data;
    } catch (error) {
      console.error("Login API Error:", error);
      throw error;
    }
  },
};

export default authService;