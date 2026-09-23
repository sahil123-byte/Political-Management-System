import { useEffect, useState } from "react";
import "../users/Users.css";
import { useAuth } from "../../context/AuthContext";
import { useTheme } from "../../context/ThemeContext";
import notificationPreferenceService from "../../services/notificationPreferenceService";
import { toast } from "react-toastify";

const CHANNELS = [
  { key: "EMAIL", label: "Email", description: "Notifications aapke email par bhi jayengi" },
  { key: "SMS", label: "SMS", description: "Notifications aapke mobile par SMS se bhi jayengi" },
  { key: "INAPP", label: "In-App", description: "Notification bell mein dikhengi (ye kabhi band nahi hoti)" },
];

function Settings() {
  const { user } = useAuth();
  const { darkMode, toggleDarkMode } = useTheme();

  // Keyed by channel - true unless the user has explicitly disabled it
  // (matches the backend's opt-out-by-default behaviour).
  const [preferences, setPreferences] = useState({ EMAIL: true, SMS: true, INAPP: true });
  const [saving, setSaving] = useState(null);

  useEffect(() => {
    loadPreferences();
  }, []);

  const loadPreferences = async () => {
    try {
      const response = await notificationPreferenceService.getMy();
      const map = { EMAIL: true, SMS: true, INAPP: true };
      (response.data || []).forEach((p) => {
        map[p.channelType] = !!p.isEnabled;
      });
      setPreferences(map);
    } catch (error) {
      console.error(error);
    }
  };

  const toggleChannel = async (channelKey) => {
    const nextValue = !preferences[channelKey];

    setPreferences((prev) => ({ ...prev, [channelKey]: nextValue }));

    try {
      setSaving(channelKey);
      await notificationPreferenceService.saveMy({
        channelType: channelKey,
        isEnabled: nextValue,
      });
      toast.success("Saved successfully.");
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save preference.");
      // Roll back the optimistic toggle on failure.
      setPreferences((prev) => ({ ...prev, [channelKey]: !nextValue }));
    } finally {
      setSaving(null);
    }
  };

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Settings</h2>
      </div>

      <div className="table-container" style={{ padding: "24px", maxWidth: "500px" }}>

        <h3 style={{ marginTop: 0 }}>Appearance</h3>

        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            padding: "14px 0",
            borderBottom: "1px solid #e5e7eb",
          }}
        >
          <div>
            <strong>Dark Mode</strong>
            <p style={{ margin: "4px 0 0", color: "#64748b", fontSize: "14px" }}>
              Poore app ka theme dark kar dein
            </p>
          </div>

          <label style={{ position: "relative", display: "inline-block", width: "50px", height: "26px" }}>
            <input
              type="checkbox"
              checked={darkMode}
              onChange={toggleDarkMode}
              style={{ opacity: 0, width: 0, height: 0 }}
            />
            <span
              onClick={toggleDarkMode}
              style={{
                position: "absolute",
                cursor: "pointer",
                inset: 0,
                background: darkMode ? "#2563eb" : "#cbd5e1",
                borderRadius: "999px",
                transition: ".3s",
              }}
            >
              <span
                style={{
                  position: "absolute",
                  height: "20px",
                  width: "20px",
                  left: darkMode ? "27px" : "3px",
                  bottom: "3px",
                  background: "white",
                  borderRadius: "50%",
                  transition: ".3s",
                }}
              />
            </span>
          </label>
        </div>

        <h3>Notification Channels</h3>

        {CHANNELS.map((channel) => (
          <div
            key={channel.key}
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              padding: "14px 0",
              borderBottom: "1px solid #e5e7eb",
              opacity: channel.key === "INAPP" ? 0.6 : 1,
            }}
          >
            <div>
              <strong>{channel.label}</strong>
              <p style={{ margin: "4px 0 0", color: "#64748b", fontSize: "14px" }}>
                {channel.description}
              </p>
            </div>

            <label style={{ position: "relative", display: "inline-block", width: "50px", height: "26px" }}>
              <input
                type="checkbox"
                checked={!!preferences[channel.key]}
                onChange={() => toggleChannel(channel.key)}
                disabled={channel.key === "INAPP" || saving === channel.key}
                style={{ opacity: 0, width: 0, height: 0 }}
              />
              <span
                onClick={() => channel.key !== "INAPP" && toggleChannel(channel.key)}
                style={{
                  position: "absolute",
                  cursor: channel.key === "INAPP" ? "not-allowed" : "pointer",
                  inset: 0,
                  background: preferences[channel.key] ? "#2563eb" : "#cbd5e1",
                  borderRadius: "999px",
                  transition: ".3s",
                }}
              >
                <span
                  style={{
                    position: "absolute",
                    height: "20px",
                    width: "20px",
                    left: preferences[channel.key] ? "27px" : "3px",
                    bottom: "3px",
                    background: "white",
                    borderRadius: "50%",
                    transition: ".3s",
                  }}
                />
              </span>
            </label>
          </div>
        ))}

        <h3>Account</h3>

        <div style={{ padding: "14px 0" }}>
          <p style={{ margin: "6px 0" }}><strong>Name:</strong> {user?.name || "-"}</p>
          <p style={{ margin: "6px 0" }}><strong>Email:</strong> {user?.email || "-"}</p>
          <p style={{ margin: "6px 0" }}><strong>Role:</strong> {user?.role || "-"}</p>
        </div>

        <p style={{ color: "#64748b", fontSize: "13px" }}>
          Name/Email/Password change karne ke liye &quot;My Profile&quot; page use karein.
        </p>

      </div>

    </div>
  );
}

export default Settings;
