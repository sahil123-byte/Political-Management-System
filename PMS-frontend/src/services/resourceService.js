import api from "./api";

export const createCrudService = (resource) => ({
  getAll: () => api.get(resource),
  getById: (id) => api.get(`${resource}/${id}`),
  create: (data) => api.post(resource, data),
  update: (id, data) => api.put(`${resource}/${id}`, data),
  delete: (id) => api.delete(`${resource}/${id}`),
});

export const createListAndCreateService = (resource) => ({
  getAll: () => api.get(resource),
  create: (data) => api.post(resource, data),
});
