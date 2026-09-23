import "../users/Users.css";
import { useEffect, useState } from "react";
import {
  FaPlus,
  FaSearch,
  FaEdit,
  FaTrash,
  FaUserShield,
} from "react-icons/fa";

import roleService from "../../services/roleService";
import AddRoleModal from "./AddRoleModal";

function Roles() {

  const [roles, setRoles] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [selectedRole, setSelectedRole] = useState(null);

  useEffect(() => {
    loadRoles();
  }, []);

  const loadRoles = async () => {
    try {
      setLoading(true);

      const response = await roleService.getAll();

      setRoles(response.data);

    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {

    if (!window.confirm("Delete this role?")) return;

    try {
      await roleService.delete(id);
      loadRoles();
    } catch (error) {
      console.error(error);
    }

  };

  const filteredRoles = roles.filter((role) =>
    role.roleName.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">

        <h2>Role Management</h2>

        <button
          className="add-btn"
          onClick={() => {
            setSelectedRole(null);
            setShowModal(true);
          }}
        >
          <FaPlus /> Add Role
        </button>

      </div>

      {/* Statistics */}

      <div className="dashboard-grid">

        <div className="dashboard-card">

          <div
            className="dashboard-icon"
            style={{ background: "#2563eb" }}
          >
            <FaUserShield />
          </div>

          <div>
            <h2>{roles.length}</h2>
            <p>Total Roles</p>
          </div>

        </div>

      </div>

      {/* Search */}

      <div className="search-box">

        <FaSearch className="search-icon" />

        <input
          type="text"
          placeholder="Search Role..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />

      </div>

      {loading && <h3>Loading Roles...</h3>}

      {!loading && (

        <div className="table-container">

          <table className="users-table">

            <thead>

              <tr>

                <th>ID</th>
                <th>Role Name</th>
                <th>Action</th>

              </tr>

            </thead>

            <tbody>

              {filteredRoles.length > 0 ? (

                filteredRoles.map((role) => (

                  <tr key={role.id}>

                    <td>{role.id}</td>

                    <td>{role.roleName}</td>

                    <td>

                      <button
                        className="edit-btn"
                        onClick={() => {
                          setSelectedRole(role);
                          setShowModal(true);
                        }}
                      >
                        <FaEdit />
                      </button>

                      <button
                        className="delete-btn"
                        onClick={() => handleDelete(role.id)}
                      >
                        <FaTrash />
                      </button>

                    </td>

                  </tr>

                ))

              ) : (

                <tr>

                  <td
                    colSpan="3"
                    style={{ textAlign: "center" }}
                  >
                    No Roles Found
                  </td>

                </tr>

              )}

            </tbody>

          </table>

        </div>

      )}

      {showModal && (

        <AddRoleModal
          role={selectedRole}
          closeModal={() => setShowModal(false)}
          refreshRoles={loadRoles}
        />

      )}

    </div>
  );
}

export default Roles;