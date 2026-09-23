import "../users/Users.css";
import { FaPlus, FaEdit, FaTrash, FaSearch } from "react-icons/fa";
import { useEffect, useState } from "react";
import AddNotificationModal from "./AddNotificationModal";
import notificationService from "../../services/notificationService";
import { toast } from "react-toastify";

function Notifications() {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    loadNotifications();
  }, []);

  const loadNotifications = async () => {
    try {
      setLoading(true);
      const response = await notificationService.getAll();
      setNotifications(response.data);
    } catch (error) {
      console.error("Error loading notifications:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this notification?")) return;

    try {
      await notificationService.delete(id);
      toast.success("Deleted successfully.");
      loadNotifications();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to delete notification.");
    }
  };

  const filtered = notifications.filter((n) =>
    n.title?.toLowerCase().includes(search.toLowerCase()) ||
    n.memberName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Notification Management</h2>

        <button
          className="add-btn"
          onClick={() => {
            setSelected(null);
            setOpenModal(true);
          }}
        >
          <FaPlus /> Add Notification
        </button>
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search notification..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Notifications...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Type</th>
                <th>Member</th>
                <th>Read</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {filtered.length > 0 ? (
                filtered.map((n) => (
                  <tr key={n.id}>
                    <td>{n.id}</td>
                    <td>{n.title}</td>
                    <td>{n.notificationType || "-"}</td>
                    <td>{n.memberName || "-"}</td>

                    <td>
                      <span className={n.isRead ? "status active" : "status pending"}>
                        {n.isRead ? "Read" : "Unread"}
                      </span>
                    </td>

                    <td>
                      <button
                        className="edit-btn"
                        onClick={() => {
                          setSelected(n);
                          setOpenModal(true);
                        }}
                      >
                        <FaEdit />
                      </button>

                      <button className="delete-btn" onClick={() => handleDelete(n.id)}>
                        <FaTrash />
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" style={{ textAlign: "center" }}>
                    No Notifications Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddNotificationModal
          notification={selected}
          closeModal={() => setOpenModal(false)}
          refreshNotifications={loadNotifications}
        />
      )}

    </div>
  );
}

export default Notifications;
