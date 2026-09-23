import { useEffect, useState } from "react";
import "../users/Users.css";
import roleService from "../../services/roleService";
import { toast } from "react-toastify";

// The rest of the app (frontend menu visibility + backend @PreAuthorize
// checks) only recognizes these exact role names. A role created with any
// other name would look fine in this list but the user assigned to it
// wouldn't get any of the ADMIN/MANAGER menu items or write permissions -
// so we only let people pick from this fixed set instead of typing anything.
const SUPPORTED_ROLES = ["ADMIN", "MANAGER", "MEMBER"];

function AddRoleModal({ role, closeModal, refreshRoles }) {

  const [roleName, setRoleName] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (role) {
      setRoleName(role.roleName);
    }
  }, [role]);

  const isCustomExistingRole = role && !SUPPORTED_ROLES.includes(role.roleName);

  const handleSubmit = async () => {

    if (!roleName.trim()) {
      toast.warning("Please select a role");
      return;
    }

    try {

      setLoading(true);

      const data = {
        roleName: roleName
      };

      if (role) {
        await roleService.update(role.id, data);
      } else {
        await roleService.create(data);
      }

      toast.success("Saved successfully.");
      refreshRoles();
      closeModal();

    } catch (error) {

      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save role.");

    } finally {

      setLoading(false);

    }

  };

  return (

    <div className="modal-overlay">

      <div className="modal">

        <div className="modal-header">
          <h2>{role ? "Edit Role" : "Add Role"}</h2>
        </div>

        <div className="modal-body">

          <div className="form-group">

            <label>Role Name</label>

            <select
              value={roleName}
              onChange={(e) => setRoleName(e.target.value)}
            >
              <option value="">Select Role</option>

              {isCustomExistingRole && (
                <option value={role.roleName}>{role.roleName} (unsupported)</option>
              )}

              {SUPPORTED_ROLES.map((name) => (
                <option key={name} value={name}>{name}</option>
              ))}
            </select>

            <p style={{ color: "#64748b", fontSize: "12px", marginTop: "6px" }}>
              Only ADMIN, MANAGER and MEMBER are recognized by the app for menu
              access and permissions. {isCustomExistingRole && "This role's name isn't one of them, so anyone assigned to it will see a stripped-down menu - pick one of the supported names to fix that."}
            </p>

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
                : role
                ? "Update Role"
                : "Save Role"
            }
          </button>

        </div>

      </div>

    </div>

  );
}

export default AddRoleModal;
