import { FaSignOutAlt, FaTimes } from "react-icons/fa";
import { NavLink, useNavigate } from "react-router-dom";

import { menuItems } from "../../../config/menuConfig";
import { logout as clearSession } from "../../../utils/auth";
import { useAuth } from "../../../context/AuthContext";

import "./Sidebar.css";

function Sidebar({ mobileOpen = false, onClose = () => {} }) {
  const navigate = useNavigate();
  const { user, logout } = useAuth();

  // No fallback to ADMIN here - if the role hasn't loaded yet, show only
  // the routes open to everyone rather than briefly leaking admin-only links.
  const userRole = user?.role || null;

  const handleLogout = () => {
    clearSession();
    logout();
    navigate("/", { replace: true });
  };

  const filteredMenu = menuItems.filter((item) =>
    !item.roles || item.roles.includes(userRole)
  );

  return (
    <aside className={`sidebar ${mobileOpen ? "sidebar-open" : ""}`}>
      <div className="sidebar-logo">
        <h2>PMS</h2>

        <button
          className="sidebar-close-btn"
          onClick={onClose}
          type="button"
          aria-label="Close menu"
        >
          <FaTimes />
        </button>
      </div>

      <ul className="sidebar-menu">
        {filteredMenu.map((item) => {
          const Icon = item.icon;

          return (
            <li key={item.path}>
              <NavLink
                to={item.path}
                className={({ isActive }) =>
                  isActive ? "active" : ""
                }
                onClick={onClose}
              >
                <Icon />
                <span>{item.name}</span>
              </NavLink>
            </li>
          );
        })}
      </ul>

      <div className="sidebar-footer">
        <button
          className="logout-btn"
          onClick={handleLogout}
          type="button"
        >
          <FaSignOutAlt />
          <span>Logout</span>
        </button>
      </div>
    </aside>
  );
}

export default Sidebar;
