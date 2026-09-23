import "../users/Users.css";
import { FaPlus, FaEdit, FaTrash, FaSearch } from "react-icons/fa";
import { useEffect, useState } from "react";
import AddBoothModal from "./AddBoothModal";
import boothService from "../../services/boothService";
import { toast } from "react-toastify";

function Booths() {
  const [booths, setBooths] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    loadBooths();
  }, []);

  const loadBooths = async () => {
    try {
      setLoading(true);
      const response = await boothService.getAll();
      setBooths(response.data);
    } catch (error) {
      console.error("Error loading booths:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this booth?")) return;

    try {
      await boothService.delete(id);
      toast.success("Deleted successfully.");
      loadBooths();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to delete booth.");
    }
  };

  const filtered = booths.filter((b) =>
    b.boothName?.toLowerCase().includes(search.toLowerCase()) ||
    b.boothNumber?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Booth Management</h2>

        <button
          className="add-btn"
          onClick={() => {
            setSelected(null);
            setOpenModal(true);
          }}
        >
          <FaPlus /> Add Booth
        </button>
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search booth..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Booths...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Booth No.</th>
                <th>Booth Name</th>
                <th>Location</th>
                <th>Constituency</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {filtered.length > 0 ? (
                filtered.map((b) => (
                  <tr key={b.id}>
                    <td>{b.id}</td>
                    <td>{b.boothNumber}</td>
                    <td>{b.boothName}</td>
                    <td>{b.location}</td>
                    <td>{b.constituencyName || "-"}</td>

                    <td>
                      <button
                        className="edit-btn"
                        onClick={() => {
                          setSelected(b);
                          setOpenModal(true);
                        }}
                      >
                        <FaEdit />
                      </button>

                      <button className="delete-btn" onClick={() => handleDelete(b.id)}>
                        <FaTrash />
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" style={{ textAlign: "center" }}>
                    No Booths Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddBoothModal
          booth={selected}
          closeModal={() => setOpenModal(false)}
          refreshBooths={loadBooths}
        />
      )}

    </div>
  );
}

export default Booths;
