import "../users/Users.css";
import { FaPlus, FaEdit, FaTrash, FaSearch } from "react-icons/fa";
import { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext";
import AddEventAttendanceModal from "./AddEventAttendanceModal";
import eventAttendanceService from "../../services/eventAttendanceService";
import { toast } from "react-toastify";

function EventAttendance() {
  const { user: currentUser } = useAuth();
  const canWrite = currentUser?.role === "ADMIN" || currentUser?.role === "MANAGER";

  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    loadRecords();
  }, []);

  const loadRecords = async () => {
    try {
      setLoading(true);
      const response = await eventAttendanceService.getAll();
      setRecords(response.data);
    } catch (error) {
      console.error("Error loading event attendance:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this attendance record?")) return;

    try {
      await eventAttendanceService.delete(id);
      toast.success("Deleted successfully.");
      loadRecords();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to delete record.");
    }
  };

  const filtered = records.filter((r) =>
    r.eventName?.toLowerCase().includes(search.toLowerCase()) ||
    r.memberName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Event Attendance</h2>

        {canWrite && (
          <button
            className="add-btn"
            onClick={() => {
              setSelected(null);
              setOpenModal(true);
            }}
          >
            <FaPlus /> Add Attendance
          </button>
        )}
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search event or member..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Attendance Records...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Event</th>
                <th>Member</th>
                <th>Status</th>
                <th>Remarks</th>
                {canWrite && <th>Action</th>}
              </tr>
            </thead>

            <tbody>
              {filtered.length > 0 ? (
                filtered.map((r) => (
                  <tr key={r.id}>
                    <td>{r.id}</td>
                    <td>{r.eventName}</td>
                    <td>{r.memberName}</td>

                    <td>
                      <span
                        className={
                          r.attendanceStatus === "Present"
                            ? "status active"
                            : "status inactive"
                        }
                      >
                        {r.attendanceStatus}
                      </span>
                    </td>

                    <td>{r.remarks || "-"}</td>

                    {canWrite && (
                      <td>
                        <button
                          className="edit-btn"
                          onClick={() => {
                            setSelected(r);
                            setOpenModal(true);
                          }}
                        >
                          <FaEdit />
                        </button>

                        <button className="delete-btn" onClick={() => handleDelete(r.id)}>
                          <FaTrash />
                        </button>
                      </td>
                    )}
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={canWrite ? "6" : "5"} style={{ textAlign: "center" }}>
                    No Attendance Records Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddEventAttendanceModal
          record={selected}
          closeModal={() => setOpenModal(false)}
          refreshRecords={loadRecords}
        />
      )}

    </div>
  );
}

export default EventAttendance;
