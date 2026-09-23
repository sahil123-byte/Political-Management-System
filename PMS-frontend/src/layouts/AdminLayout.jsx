import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import Sidebar from "../components/layout/Sidebar/Sidebar";
import Navbar from "../components/layout/Navbar/Navbar";
import Footer from "../components/layout/Footer/Footer";
import { useTheme } from "../context/ThemeContext";

import "./AdminLayout.css";

function AdminLayout({ children }) {
  const { darkMode } = useTheme();
  const [mobileSidebarOpen, setMobileSidebarOpen] = useState(false);
  const location = useLocation();

  // Close the mobile sidebar drawer automatically on every navigation
  useEffect(() => {
    setMobileSidebarOpen(false);
  }, [location.pathname]);

  return (
    <div className={`admin-layout ${darkMode ? "dark" : ""}`}>
      <Sidebar
        mobileOpen={mobileSidebarOpen}
        onClose={() => setMobileSidebarOpen(false)}
      />

      {mobileSidebarOpen && (
        <div
          className="sidebar-backdrop"
          onClick={() => setMobileSidebarOpen(false)}
        />
      )}

      <div className="admin-content">
        <Navbar onMenuClick={() => setMobileSidebarOpen((prev) => !prev)} />

        <main className="main-content" role="main">
          {children}
        </main>

        <Footer />
      </div>
    </div>
  );
}

export default AdminLayout;
