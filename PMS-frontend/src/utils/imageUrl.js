import api from "../services/api";

// The API base URL is like "http://localhost:8080/api", but uploaded images
// are served from the server root at "/uploads/...", so we strip the
// trailing "/api" to get the server's root URL.
const SERVER_BASE_URL = (api.defaults.baseURL || "http://localhost:8080/api").replace(/\/api\/?$/, "");

export const resolveImageUrl = (path) => {
  if (!path) return null;
  if (path.startsWith("http://") || path.startsWith("https://")) return path;
  return `${SERVER_BASE_URL}${path}`;
};
