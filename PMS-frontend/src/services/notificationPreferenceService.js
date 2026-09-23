import api from "./api";

const notificationPreferenceService = {
  getMy: () => api.get("/notification-preferences/me"),
  saveMy: (data) => api.put("/notification-preferences/me", data),
};

export default notificationPreferenceService;
