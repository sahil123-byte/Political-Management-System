import "../users/Users.css";
import { FaPlus, FaSearch, FaFileCsv } from "react-icons/fa";
import { useEffect, useState } from "react";
import AddMemberModal from "./AddMemberModal";
import memberService from "../../services/memberService";
import Avatar from "../../components/common/Avatar";
import { exportToCsv } from "../../utils/csvExport";
import { useAuth } from "../../context/AuthContext";

function Members() {
  const { user: currentUser } = useAuth();
  const canWrite = currentUser?.role === "ADMIN" || currentUser?.role === "MANAGER";

  const [members, setMembers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);

  useEffect(() => {
    loadMembers();
  }, []);

  const loadMembers = async () => {
    try {
      setLoading(true);
      const response = await memberService.getAll();
      setMembers(response.data);
    } catch (error) {
      console.error("Error loading members:", error);
    } finally {
      setLoading(false);
    }
  };

  const filtered = members.filter((m) =>
    m.name?.toLowerCase().includes(search.toLowerCase()) ||
    m.mobile?.toLowerCase().includes(search.toLowerCase())
  );

  const handleExport = () => {
    exportToCsv(
      "members.csv",
      filtered,
      [
        { key: "name", label: "Name" },
        { key: "mobile", label: "Mobile" },
        { key: "email", label: "Email" },
        { key: "designation", label: "Designation" },
        { key: "boothName", label: "Booth" },
        { key: "joiningDate", label: "Joining Date" },
      ]
    );
  };

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Member Management</h2>

        <div style={{ display: "flex", gap: "10px" }}>
          <button className="cancel-btn" onClick={handleExport}>
            <FaFileCsv style={{ marginRight: "6px" }} /> Export CSV
          </button>

          {canWrite && (
            <button className="add-btn" onClick={() => setOpenModal(true)}>
              <FaPlus /> Add Member
            </button>
          )}
        </div>
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search member..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Members...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Photo</th>
                <th>Name</th>
                <th>Mobile</th>
                <th>Designation</th>
                <th>Booth</th>
                <th>Joining Date</th>
              </tr>
            </thead>

            <tbody>
              {filtered.length > 0 ? (
                filtered.map((m) => (
                  <tr key={m.id}>
                    <td>{m.id}</td>
                    <td>
                      <Avatar src={m.imageUrl} />
                    </td>
                    <td>{m.name}</td>
                    <td>{m.mobile}</td>
                    <td>{m.designation || "-"}</td>
                    <td>{m.boothName || "-"}</td>
                    <td>{m.joiningDate || "-"}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="7" style={{ textAlign: "center" }}>
                    No Members Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddMemberModal
          closeModal={() => setOpenModal(false)}
          refreshMembers={loadMembers}
        />
      )}

    </div>
  );
}

export default Members;
