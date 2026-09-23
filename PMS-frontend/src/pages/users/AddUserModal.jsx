import { useEffect, useState } from "react";
import "./Users.css";
import userService from "../../services/userService";
import roleService from "../../services/roleService";
import memberService from "../../services/memberService";
import ImageUpload from "../../components/common/ImageUpload";
import { toast } from "react-toastify";

function AddUserModal({ user, closeModal, refreshUsers }) {

  const [roles, setRoles] = useState([]);
  const [members, setMembers] = useState([]);

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    roleId: "",
    imageUrl: "",
    memberId: ""
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadRoles();
    loadMembers();

    if (user) {
      setFormData({
        name: user.name || "",
        email: user.email || "",
        password: "",
        roleId: "",
        imageUrl: user.imageUrl || "",
        memberId: user.memberId || ""
      });
    }
  }, [user]);

  // Backend only sends roleName (not roleId) on the user object,
  // so once roles are loaded we resolve the matching roleId for editing.
  useEffect(() => {
    if (user && user.roleName && roles.length > 0) {
      const matchedRole = roles.find((r) => r.roleName === user.roleName);

      if (matchedRole) {
        setFormData((prev) => ({ ...prev, roleId: matchedRole.id }));
      }
    }
  }, [user, roles]);

  const loadRoles = async () => {
    try {
      const response = await roleService.getAll();
      setRoles(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const loadMembers = async () => {
    try {
      const response = await memberService.getAll();
      setMembers(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
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

    if (!user && !formData.password.trim()) {
      toast.warning("Password is required");
      return;
    }

    if (!formData.roleId) {
      toast.warning("Please select role");
      return;
    }

    const selectedRole = roles.find((r) => String(r.id) === String(formData.roleId));
    const isMemberRole = selectedRole?.roleName === "MEMBER";

    if (isMemberRole && !formData.memberId) {
      toast.warning("Please link this account to a Member record (required for the MEMBER role)");
      return;
    }

    try {

      setLoading(true);

      if (user) {
        await userService.update(user.id, formData);
      } else {
        await userService.create(formData);
      }

      toast.success("Saved successfully.");
      refreshUsers();
      closeModal();

    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save user.");
    } finally {
      setLoading(false);
    }

  };

  return (

    <div className="modal-overlay">

      <div className="modal">

        <div className="modal-header">
          <h3>{user ? "Edit User" : "Add User"}</h3>
        </div>

        <div className="modal-body">

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
              placeholder="Enter Name"
            />

          </div>

          <div className="form-group">
            <label>Email</label>

            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Enter Email"
            />

          </div>

          <div className="form-group">
            <label>Password</label>

            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder={
                user
                  ? "Leave blank to keep old password"
                  : "Enter Password"
              }
            />

          </div>

          <div className="form-group">

            <label>Role</label>

            <select
              name="roleId"
              value={formData.roleId}
              onChange={handleChange}
            >

              <option value="">
                Select Role
              </option>

              {roles.map((role) => (

                <option
                  key={role.id}
                  value={role.id}
                >
                  {role.roleName}
                </option>

              ))}

            </select>

          </div>

          <div className="form-group">

            <label>Link to Member Record (optional, required for MEMBER role)</label>

            <select
              name="memberId"
              value={formData.memberId}
              onChange={handleChange}
            >

              <option value="">
                No linked Member record
              </option>

              {members.map((member) => (

                <option
                  key={member.id}
                  value={member.id}
                >
                  {member.name} ({member.mobile})
                </option>

              ))}

            </select>

          </div>

        </div>

        <div className="modal-footer">

          <button
            className="cancel-btn"
            onClick={closeModal}
          >
            Cancel
          </button>

          <button
            className="save-btn"
            onClick={handleSubmit}
            disabled={loading}
          >

            {
              loading
                ? "Saving..."
                : user
                ? "Update User"
                : "Save User"
            }

          </button>

        </div>

      </div>

    </div>

  );
}

export default AddUserModal;