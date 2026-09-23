import { Navigate, useLocation } from "react-router-dom";
import { isLoggedIn, getUser } from "../utils/auth";

// allowedRoles: optional array of roles permitted for this route.
// When omitted, any authenticated user may access it (matches menuConfig,
// where items without a `roles` array are open to everyone).
function PrivateRoute({ children, allowedRoles }) {
  const location = useLocation();

  if (!isLoggedIn()) {
    return (
      <Navigate
        to="/"
        replace
        state={{ from: location }}
      />
    );
  }

  if (allowedRoles && allowedRoles.length > 0) {
    const currentRole = getUser()?.role;

    if (!currentRole || !allowedRoles.includes(currentRole)) {
      // Logged in, but this role isn't permitted here (e.g. typed the
      // URL directly) - send back to the dashboard instead of rendering
      // a page whose data calls would just fail with 403s.
      return <Navigate to="/dashboard" replace />;
    }
  }

  return children;
}

export default PrivateRoute;
