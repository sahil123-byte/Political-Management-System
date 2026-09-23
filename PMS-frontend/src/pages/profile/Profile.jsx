import "../users/Users.css";
import { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext";
import userService from "../../services/userService";
import roleService from "../../services/roleService";
import ImageUpload from "../../components/common/ImageUpload";
import Avatar from "../../components/common/Avatar";
import { toast } from "react-toastify";

function Profile() {
  const { user, login } = useAuth();

  const [roles, setRoles] = useState([]);
  const [editing, setEditing] = useState(false);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const [formData, setFormData] = useState({
    name: user?.name || "",
    email: user?.email || "",
    password: "",
    imageUrl: user?.imageUrl || "",
  });

  useEffect(() => {
    loadRoles();
  }, []);

  const loadRoles = async () => {
    try {
      const response = await roleService.getAll();
      setRoles(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (!formData.name.trim()) {
      toast.warning("Name is required");
      return;
    }

    if (!formData.email.trim()) {
      toast.warning("Email is required");
      return;
    }

    const matchedRole = roles.find((r) => r.roleName === user?.role);

    if (!matchedRole) {
      toast.error("Could not determine your current role. Please refresh and try again.");
      return;
    }

    try {
      setLoading(true);
      setMessage("");

      const payload = {
        name: formData.name,
        email: formData.email,
        roleId: matchedRole.id,
        imageUrl: formData.imageUrl,
      };

      if (formData.password.trim()) {
        payload.password = formData.password;
      }

      await userService.update(user.userId, payload);

      // Keep the navbar/sidebar in sync with the updated name/email/photo
      login({
        ...user,
        name: formData.name,
        email: formData.email,
        imageUrl: formData.imageUrl,
      });

      setMessage("Profile updated successfully.");
      setEditing(false);
      setFormData({ ...formData, password: "" });
    } catch (error) {
      console.error(error);

      if (error?.response?.status === 403) {
        setMessage(
          "Aapke role ke paas profile edit karne ki permission nahi hai. Apne administrator se sampark karein."
        );
      } else {
        setMessage(error?.response?.data?.message || "Failed to update profile.");
      }
    } finally {
      setLoading(false);
    }
  };

  if (!user) {
    return (
      <div className="users-page">
        <div className="users-header">
          <h2>Profile</h2>
        </div>
        <p>Loading profile...</p>
      </div>
    );
  }

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>My Profile</h2>

        {!editing && (
          <button className="add-btn" onClick={() => setEditing(true)}>
            Edit Profile
          </button>
        )}
      </div>

      {message && <p>{message}</p>}

      <div className="table-container" style={{ padding: "24px", maxWidth: "480px" }}>

        <div style={{ display: "flex", alignItems: "center", gap: "16px", marginBottom: "20px" }}>
          <Avatar src={user.imageUrl} size={60} />
          <div>
            <h3 style={{ margin: 0 }}>{user.name}</h3>
            <span className="status active">{user.role}</span>
          </div>
        </div>

        {!editing ? (
          <>
            <div className="form-group">
              <label>Name</label>
              <p>{user.name}</p>
            </div>

            <div className="form-group">
              <label>Email</label>
              <p>{user.email}</p>
            </div>

            <div className="form-group">
              <label>Role</label>
              <p>{user.role}</p>
            </div>

            <div className="form-group">
              <label>User ID</label>
              <p>{user.userId}</p>
            </div>
          </>
        ) : (
          <>
            <ImageUpload
              label="Profile Photo"
              value={formData.imageUrl}
              onChange={(url) => setFormData((prev) => ({ ...prev, imageUrl: url }))}
            />

            <div className="form-group">
              <label>Name</label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
                placeholder="Enter name"
              />
            </div>

            <div className="form-group">
              <label>Email</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Enter email"
              />
            </div>

            <div className="form-group">
              <label>New Password</label>
              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="Leave blank to keep current password"
              />
            </div>

            <div className="modal-footer" style={{ paddingLeft: 0 }}>
              <button
                className="cancel-btn"
                onClick={() => {
                  setEditing(false);
                  setFormData({ name: user.name, email: user.email, password: "", imageUrl: user.imageUrl || "" });
                }}
              >
                Cancel
              </button>

              <button className="save-btn" onClick={handleSubmit} disabled={loading}>
                {loading ? "Saving..." : "Save Changes"}
              </button>
            </div>
          </>
        )}

      </div>

    </div>
  );
}

export default Profile;
