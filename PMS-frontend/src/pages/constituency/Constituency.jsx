import "../users/Users.css";
import { FaPlus, FaEdit, FaTrash, FaSearch } from "react-icons/fa";
import { useEffect, useState } from "react";
import AddConstituencyModal from "./AddConstituencyModal";
import constituencyService from "../../services/constituencyService";
import { toast } from "react-toastify";

function Constituency() {
  const [constituencies, setConstituencies] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    loadConstituencies();
  }, []);

  const loadConstituencies = async () => {
    try {
      setLoading(true);
      const response = await constituencyService.getAll();
      setConstituencies(response.data);
    } catch (error) {
      console.error("Error loading constituencies:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this constituency?")) return;

    try {
      await constituencyService.delete(id);
      toast.success("Deleted successfully.");
      loadConstituencies();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to delete constituency.");
    }
  };

  const filtered = constituencies.filter((c) =>
    c.constituencyName?.toLowerCase().includes(search.toLowerCase()) ||
    c.districtName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Constituency Management</h2>

        <button
          className="add-btn"
          onClick={() => {
            setSelected(null);
            setOpenModal(true);
          }}
        >
          <FaPlus /> Add Constituency
        </button>
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search constituency..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Constituencies...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Constituency Name</th>
                <th>District</th>
                <th>State</th>
                <th>Party</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {filtered.length > 0 ? (
                filtered.map((c) => (
                  <tr key={c.id}>
                    <td>{c.id}</td>
                    <td>{c.constituencyName}</td>
                    <td>{c.districtName}</td>
                    <td>{c.stateName}</td>
                    <td>{c.partyName || "-"}</td>

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
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" style={{ textAlign: "center" }}>
                    No Constituencies Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddConstituencyModal
          constituency={selected}
          closeModal={() => setOpenModal(false)}
          refreshConstituencies={loadConstituencies}
        />
      )}

    </div>
  );
}

export default Constituency;
