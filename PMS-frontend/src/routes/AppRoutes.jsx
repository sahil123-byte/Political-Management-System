import { Routes, Route, Navigate } from "react-router-dom";

import Login from "../pages/auth/Login";
import PrivateRoute from "./PrivateRoute";
import AdminLayout from "../layouts/AdminLayout";
import { isLoggedIn } from "../utils/auth";

import { menuItems } from "../config/menuConfig";

function AppRoutes() {
  return (
    <Routes>

      {/* Public Route */}
      <Route
        path="/"
        element={
          isLoggedIn() ? <Navigate to="/dashboard" replace /> : <Login />
        }
      />

      {/* Protected Routes */}
      {menuItems.map((item) => {
        const Component = item.component;

        return (
          <Route
            key={item.path}
            path={item.path}
            element={
              <PrivateRoute allowedRoles={item.roles}>
                <AdminLayout>
                  <Component />
                </AdminLayout>
              </PrivateRoute>
            }
          />
        );
      })}

      {/* 404 */}
      <Route
        path="*"
        element={
          <Navigate
            to={isLoggedIn() ? "/dashboard" : "/"}
            replace
          />
        }
      />

    </Routes>
  );
}

export default AppRoutes;