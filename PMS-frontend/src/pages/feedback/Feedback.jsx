import "../users/Users.css";
import { FaPlus, FaEdit, FaTrash, FaSearch, FaStar } from "react-icons/fa";
import { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext";
import AddFeedbackModal from "./AddFeedbackModal";
import feedbackService from "../../services/feedbackService";
import { toast } from "react-toastify";

function Feedback() {
  const { user: currentUser } = useAuth();
  const canManage = currentUser?.role === "ADMIN" || currentUser?.role === "MANAGER";

  const [feedbacks, setFeedbacks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    loadFeedbacks();
  }, []);

  const loadFeedbacks = async () => {
    try {
      setLoading(true);
      const response = await feedbackService.getAll();
      setFeedbacks(response.data);
    } catch (error) {
      console.error("Error loading feedbacks:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this feedback?")) return;

    try {
      await feedbackService.delete(id);
      toast.success("Deleted successfully.");
      loadFeedbacks();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to delete feedback.");
    }
  };

  const filtered = feedbacks.filter((f) =>
    f.feedbackTitle?.toLowerCase().includes(search.toLowerCase()) ||
    f.memberName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Feedback Management</h2>

        <button
          className="add-btn"
          onClick={() => {
            setSelected(null);
            setOpenModal(true);
          }}
        >
          <FaPlus /> Add Feedback
        </button>
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search feedback..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Feedbacks...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Member</th>
                <th>Rating</th>
                <th>Date</th>
                {canManage && <th>Action</th>}
              </tr>
            </thead>

            <tbody>
              {filtered.length > 0 ? (
                filtered.map((f) => (
                  <tr key={f.id}>
                    <td>{f.id}</td>
                    <td>{f.feedbackTitle}</td>
                    <td>{f.memberName || "-"}</td>

                    <td>
                      {Array.from({ length: f.rating || 0 }).map((_, i) => (
                        <FaStar key={i} color="#f59e0b" />
                      ))}
                    </td>

                    <td>{f.feedbackDate || "-"}</td>

                    {canManage && (
                      <td>
                        <button
                          className="edit-btn"
                          onClick={() => {
                            setSelected(f);
                            setOpenModal(true);
                          }}
                        >
                          <FaEdit />
                        </button>

                        <button className="delete-btn" onClick={() => handleDelete(f.id)}>
                          <FaTrash />
                        </button>
                      </td>
                    )}
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={canManage ? "6" : "5"} style={{ textAlign: "center" }}>
                    No Feedbacks Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddFeedbackModal
          feedback={selected}
          closeModal={() => setOpenModal(false)}
          refreshFeedbacks={loadFeedbacks}
        />
      )}

    </div>
  );
}

export default Feedback;
