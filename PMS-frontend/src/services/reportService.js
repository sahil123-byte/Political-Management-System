import api from "./api";

const reportService = {
  members: () => api.get("/reports/members"),
  voters: () => api.get("/reports/voters"),
  campaigns: () => api.get("/reports/campaigns"),
  events: () => api.get("/reports/events"),
  complaints: () => api.get("/reports/complaints"),
  feedbacks: () => api.get("/reports/feedbacks"),
  organizations: () => api.get("/reports/organizations"),
};

export default reportService;
