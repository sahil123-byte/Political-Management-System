import "../users/Users.css";
import { FaPlus, FaEdit, FaTrash, FaSearch } from "react-icons/fa";
import { useAuth } from "../../context/AuthContext";
import { useEffect, useState } from "react";
import AddEventModal from "./AddEventModal";
import eventService from "../../services/eventService";
import { toast } from "react-toastify";

function Events() {
  const { user: currentUser } = useAuth();
  const canWrite = currentUser?.role === "ADMIN" || currentUser?.role === "MANAGER";

  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
    try {
      setLoading(true);
      const response = await eventService.getAll();
      setEvents(response.data);
    } catch (error) {
      console.error("Error loading events:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this event?")) return;

    try {
      await eventService.delete(id);
      toast.success("Deleted successfully.");
      loadEvents();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to delete event.");
    }
  };

  const filtered = events.filter((e) =>
    e.eventName?.toLowerCase().includes(search.toLowerCase()) ||
    e.venue?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Event Management</h2>

        {canWrite && (
          <button
            className="add-btn"
            onClick={() => {
              setSelected(null);
              setOpenModal(true);
            }}
          >
            <FaPlus /> Add Event
          </button>
        )}
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search event..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Events...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Event Name</th>
                <th>Type</th>
                <th>Venue</th>
                <th>Date</th>
                <th>Campaign</th>
                {canWrite && <th>Action</th>}
              </tr>
            </thead>

            <tbody>
              {filtered.length > 0 ? (
                filtered.map((e) => (
                  <tr key={e.id}>
                    <td>{e.id}</td>
                    <td>{e.eventName}</td>
                    <td>{e.eventType}</td>
                    <td>{e.venue}</td>
                    <td>{e.eventDate || "-"}</td>
                    <td>{e.campaignName || "-"}</td>

                    {canWrite && (
                      <td>
                        <button
                          className="edit-btn"
                          onClick={() => {
                            setSelected(e);
                            setOpenModal(true);
                          }}
                        >
                          <FaEdit />
                        </button>

                        <button className="delete-btn" onClick={() => handleDelete(e.id)}>
                          <FaTrash />
                        </button>
                      </td>
                    )}
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={canWrite ? "7" : "6"} style={{ textAlign: "center" }}>
                    No Events Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddEventModal
          event={selected}
          closeModal={() => setOpenModal(false)}
          refreshEvents={loadEvents}
        />
      )}

    </div>
  );
}

export default Events;
