import api from "./api";

const userService = {

    getAll: () => api.get("/users"),

    getById: (id) => api.get(`/users/${id}`),

    create: (data) => api.post("/users", data),

    update: (id, data) => api.put(`/users/${id}`, data),

    delete: (id) => api.delete(`/users/${id}`),

    changeStatus: (id) => api.patch(`/users/${id}/status`)

};

export default userService;
