import "../users/Users.css";
import { FaPlus, FaEdit, FaTrash, FaSearch } from "react-icons/fa";
import { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext";
import AddComplaintModal from "./AddComplaintModal";
import complaintService from "../../services/complaintService";
import { toast } from "react-toastify";

function Complaints() {
  const { user: currentUser } = useAuth();
  // MEMBER can file (Own) a complaint, but only ADMIN/MANAGER can edit/delete
  const canManage = currentUser?.role === "ADMIN" || currentUser?.role === "MANAGER";

  const [complaints, setComplaints] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    loadComplaints();
  }, []);

  const loadComplaints = async () => {
    try {
      setLoading(true);
      const response = await complaintService.getAll();
      setComplaints(response.data);
    } catch (error) {
      console.error("Error loading complaints:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this complaint?")) return;

    try {
      await complaintService.delete(id);
      toast.success("Deleted successfully.");
      loadComplaints();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to delete complaint.");
    }
  };

  const filtered = complaints.filter((c) =>
    c.complaintTitle?.toLowerCase().includes(search.toLowerCase()) ||
    c.memberName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Complaint Management</h2>

        <button
          className="add-btn"
          onClick={() => {
            setSelected(null);
            setOpenModal(true);
          }}
        >
          <FaPlus /> Add Complaint
        </button>
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search complaint..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Complaints...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Member</th>
                <th>Date</th>
                <th>Status</th>
                {canManage && <th>Action</th>}
              </tr>
            </thead>

            <tbody>
              {filtered.length > 0 ? (
                filtered.map((c) => (
                  <tr key={c.id}>
                    <td>{c.id}</td>
                    <td>{c.complaintTitle}</td>
                    <td>{c.memberName || "-"}</td>
                    <td>{c.complaintDate || "-"}</td>

                    <td>
                      <span
                        className={
                          c.complaintStatus === "Resolved"
                            ? "status active"
                            : "status pending"
                        }
                      >
                        {c.complaintStatus}
                      </span>
                    </td>

                    {canManage && (
                      <td>
                        <button
                          className="edit-btn"
                          onClick={() => {
                            setSelected(c);
                            setOpenModal(true);
                          }}
                        >
                          <FaEdit />
                        </button>

                        <button className="delete-btn" onClick={() => handleDelete(c.id)}>
                          <FaTrash />
                        </button>
                      </td>
                    )}
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={canManage ? "6" : "5"} style={{ textAlign: "center" }}>
                    No Complaints Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddComplaintModal
          complaint={selected}
          closeModal={() => setOpenModal(false)}
          refreshComplaints={loadComplaints}
        />
      )}

    </div>
  );
}

export default Complaints;
