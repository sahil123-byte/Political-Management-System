import "../users/Users.css";
import { FaPlus, FaEdit, FaTrash, FaSearch, FaFileCsv } from "react-icons/fa";
import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import AddVoterModal from "./AddVoterModal";
import voterService from "../../services/voterService";
import Avatar from "../../components/common/Avatar";
import { useAuth } from "../../context/AuthContext";
import { exportToCsv } from "../../utils/csvExport";
import { toast } from "react-toastify";

function Voters() {
  const { user: currentUser } = useAuth();
  const canWrite = currentUser?.role === "ADMIN" || currentUser?.role === "MANAGER";

  const [searchParams] = useSearchParams();
  const [voters, setVoters] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState(searchParams.get("q") || "");
  const [openModal, setOpenModal] = useState(false);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    loadVoters();
  }, []);

  useEffect(() => {
    const q = searchParams.get("q");
    if (q) setSearch(q);
  }, [searchParams]);

  const loadVoters = async () => {
    try {
      setLoading(true);
      const response = await voterService.getAll();
      setVoters(response.data);
    } catch (error) {
      console.error("Error loading voters:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this voter?")) return;

    try {
      await voterService.delete(id);
      toast.success("Deleted successfully.");
      loadVoters();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to delete voter.");
    }
  };

  const filtered = voters.filter((v) =>
    v.name?.toLowerCase().includes(search.toLowerCase()) ||
    v.voterId?.toLowerCase().includes(search.toLowerCase())
  );

  const handleExport = () => {
    exportToCsv(
      "voters.csv",
      filtered,
      [
        { key: "voterId", label: "Voter ID" },
        { key: "name", label: "Name" },
        { key: "fatherName", label: "Father Name" },
        { key: "gender", label: "Gender" },
        { key: "age", label: "Age" },
        { key: "mobile", label: "Mobile" },
        { key: "address", label: "Address" },
        { key: "boothName", label: "Booth" },
      ]
    );
  };

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Voter Management</h2>

        <div style={{ display: "flex", gap: "10px" }}>
          <button className="cancel-btn" onClick={handleExport}>
            <FaFileCsv style={{ marginRight: "6px" }} /> Export CSV
          </button>

          {canWrite && (
            <button
              className="add-btn"
              onClick={() => {
                setSelected(null);
                setOpenModal(true);
              }}
            >
              <FaPlus /> Add Voter
            </button>
          )}
        </div>
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search voter..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Voters...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Photo</th>
                <th>Voter ID</th>
                <th>Name</th>
                <th>Gender</th>
                <th>Age</th>
                <th>Mobile</th>
                <th>Booth</th>
                {canWrite && <th>Action</th>}
              </tr>
            </thead>

            <tbody>
              {filtered.length > 0 ? (
                filtered.map((v) => (
                  <tr key={v.id}>
                    <td>{v.id}</td>
                    <td>
                      <Avatar src={v.imageUrl} />
                    </td>
                    <td>{v.voterId}</td>
                    <td>{v.name}</td>
                    <td>{v.gender}</td>
                    <td>{v.age}</td>
                    <td>{v.mobile}</td>
                    <td>{v.boothName || "-"}</td>

                    {canWrite && (
                      <td>
                        <button
                          className="edit-btn"
                          onClick={() => {
                            setSelected(v);
                            setOpenModal(true);
                          }}
                        >
                          <FaEdit />
                        </button>

                        <button className="delete-btn" onClick={() => handleDelete(v.id)}>
                          <FaTrash />
                        </button>
                      </td>
                    )}
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={canWrite ? "9" : "8"} style={{ textAlign: "center" }}>
                    No Voters Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddVoterModal
          voter={selected}
          closeModal={() => setOpenModal(false)}
          refreshVoters={loadVoters}
        />
      )}

    </div>
  );
}

export default Voters;
