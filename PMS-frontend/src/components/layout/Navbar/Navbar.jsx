import {
  FaBars,
  FaBell,
  FaSearch,
} from "react-icons/fa";
import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";

import { useAuth } from "../../../context/AuthContext";
import notificationService from "../../../services/notificationService";
import Avatar from "../../common/Avatar";

import "./Navbar.css";

function Navbar({ onMenuClick = () => {} }) {
  const [search, setSearch] = useState("");
  const [notifications, setNotifications] = useState([]);
  const [showNotifications, setShowNotifications] = useState(false);
  const navigate = useNavigate();
  const { user } = useAuth();
  const dropdownRef = useRef(null);

  const displayUser = {
    name: user?.name || "Admin",
    role: user?.role || "User",
  };

  useEffect(() => {
    loadNotifications();

    // Refresh every 60 seconds so the badge count stays reasonably current
    const interval = setInterval(loadNotifications, 60000);
    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    const handleClickOutside = (e) => {
      if (dropdownRef.current && !dropdownRef.current.contains(e.target)) {
        setShowNotifications(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const loadNotifications = async () => {
    try {
      const response = await notificationService.getAll();
      setNotifications(response.data || []);
    } catch (error) {
      console.error("Error loading notifications:", error);
    }
  };

  const unreadCount = notifications.filter((n) => !n.isRead).length;
  const recentNotifications = [...notifications]
    .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
    .slice(0, 5);

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    if (!search.trim()) return;

    navigate(`/voters?q=${encodeURIComponent(search.trim())}`);
  };

  return (
    <header className="navbar">
      <button
        className="navbar-menu-btn"
        type="button"
        aria-label="Toggle menu"
        onClick={onMenuClick}
      >
        <FaBars />
      </button>

      <form className="navbar-search" onSubmit={handleSearchSubmit}>
        <FaSearch />

        <input
          type="text"
          placeholder="Search voters by name or ID..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          aria-label="Search"
        />
      </form>

      <div className="navbar-right">
        <div className="notification-wrapper" ref={dropdownRef}>
          <button
            className="notification-btn"
            type="button"
            aria-label="Notifications"
            onClick={() => setShowNotifications((prev) => !prev)}
          >
            <FaBell />
            {unreadCount > 0 && (
              <span className="notification-badge">
                {unreadCount > 9 ? "9+" : unreadCount}
              </span>
            )}
          </button>

          {showNotifications && (
            <div className="notification-dropdown">
              <div className="notification-dropdown-header">Notifications</div>

              <div className="notification-dropdown-list">
                {recentNotifications.length === 0 ? (
                  <p className="notification-empty">No notifications yet</p>
                ) : (
                  recentNotifications.map((n) => (
                    <div
                      key={n.id}
                      className={`notification-item ${!n.isRead ? "unread" : ""}`}
                    >
                      <p className="notification-title">{n.title}</p>
                      <p className="notification-message">{n.message}</p>
                    </div>
                  ))
                )}
              </div>

              <div className="notification-dropdown-footer">
                <span
                  onClick={() => {
                    setShowNotifications(false);
                    navigate("/notifications");
                  }}
                >
                  View all
                </span>
              </div>
            </div>
          )}
        </div>

        <div
          className="profile"
          onClick={() => navigate("/profile")}
          role="button"
          tabIndex={0}
          title="View profile"
        >
          <Avatar src={user?.imageUrl} size={36} />

          <div className="profile-info">
            <h4>{displayUser.name}</h4>
            <span>{displayUser.role}</span>
          </div>
        </div>
      </div>
    </header>
  );
}

export default Navbar;
