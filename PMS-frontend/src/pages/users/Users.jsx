import { useEffect, useState } from "react";
import {
  FaPlus,
  FaSearch,
  FaEdit,
  FaTrash,
  FaUsers,
  FaUserCheck,
  FaUserTimes,
} from "react-icons/fa";

import "./Users.css";
import userService from "../../services/userService";
import AddUserModal from "./AddUserModal";
import Avatar from "../../components/common/Avatar";
import { useAuth } from "../../context/AuthContext";

function Users() {
  const { user: currentUser } = useAuth();
  const isAdmin = currentUser?.role === "ADMIN";

  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      setLoading(true);
      const response = await userService.getAll();
      setUsers(response.data);
    } catch (error) {
      console.error("Error loading users:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this user?")) return;

    try {
      await userService.delete(id);
      loadUsers();
    } catch (error) {
      console.error(error);
    }
  };

  const handleStatus = async (id) => {
    try {
      await userService.changeStatus(id);
      loadUsers();
    } catch (error) {
      console.error(error);
    }
  };

  const filteredUsers = users.filter((user) => {
    return (
      user.name.toLowerCase().includes(search.toLowerCase()) ||
      user.email.toLowerCase().includes(search.toLowerCase()) ||
      (user.roleName &&
        user.roleName.toLowerCase().includes(search.toLowerCase()))
    );
  });

  const totalUsers = users.length;
  const activeUsers = users.filter((u) => u.status).length;
  const inactiveUsers = users.filter((u) => !u.status).length;

  return (
    <div className="users-page">
      {/* Header */}

      <div className="users-header">
        <h2>User Management</h2>

        {isAdmin && (
          <button
            className="add-btn"
            onClick={() => {
              setSelectedUser(null);
              setShowModal(true);
            }}
          >
            <FaPlus /> Add User
          </button>
        )}
      </div>

      {/* Statistics */}

      <div className="dashboard-grid">
        <div className="dashboard-card">
          <div
            className="dashboard-icon"
            style={{ background: "#2563eb" }}
          >
            <FaUsers />
          </div>

          <div>
            <h2>{totalUsers}</h2>
            <p>Total Users</p>
          </div>
        </div>

        <div className="dashboard-card">
          <div
            className="dashboard-icon"
            style={{ background: "#16a34a" }}
          >
            <FaUserCheck />
          </div>

          <div>
            <h2>{activeUsers}</h2>
            <p>Active Users</p>
          </div>
        </div>

        <div className="dashboard-card">
          <div
            className="dashboard-icon"
            style={{ background: "#dc2626" }}
          >
            <FaUserTimes />
          </div>

          <div>
            <h2>{inactiveUsers}</h2>
            <p>Inactive Users</p>
          </div>
        </div>
      </div>

      {/* Search */}

      <div className="search-box">
        <FaSearch className="search-icon" />

        <input
          type="text"
          placeholder="Search user..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {/* Loading */}

      {loading && <h3>Loading Users...</h3>}

      {/* Table */}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Photo</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Linked Member</th>
                <th>Status</th>
                {isAdmin && <th width="180">Action</th>}
              </tr>
            </thead>

            <tbody>
              {filteredUsers.length > 0 ? (
                filteredUsers.map((user) => (
                  <tr key={user.id}>
                    <td>{user.id}</td>

                    <td>
                      <Avatar src={user.imageUrl} />
                    </td>

                    <td>{user.name}</td>

                    <td>{user.email}</td>

                    <td>{user.roleName}</td>

                    <td>{user.memberName || "-"}</td>

                    <td>
                      <span
                        className={
                          user.status
                            ? "status active"
                            : "status inactive"
                        }
                      >
                        {user.status ? "Active" : "Inactive"}
                      </span>
                    </td>

                    {isAdmin && (
                      <td>
                        <button
                          className="edit-btn"
                          onClick={() => {
                            setSelectedUser(user);
                            setShowModal(true);
                          }}
                        >
                          <FaEdit />
                        </button>

                        <button
                          className="delete-btn"
                          onClick={() => handleDelete(user.id)}
                        >
                          <FaTrash />
                        </button>

                        <button
                          className="status-btn"
                          onClick={() => handleStatus(user.id)}
                        >
                          Status
                        </button>
                      </td>
                    )}
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={isAdmin ? "8" : "7"} style={{ textAlign: "center" }}>
                    No Users Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {/* Modal */}

      {showModal && (
        <AddUserModal
          user={selectedUser}
          closeModal={() => setShowModal(false)}
          refreshUsers={loadUsers}
        />
      )}
    </div>
  );
}

export default Users;