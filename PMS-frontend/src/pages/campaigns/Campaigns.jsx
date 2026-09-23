import "../users/Users.css";
import { FaPlus, FaEdit, FaTrash, FaSearch } from "react-icons/fa";
import { useAuth } from "../../context/AuthContext";
import { useEffect, useState } from "react";
import AddCampaignModal from "./AddCampaignModal";
import campaignService from "../../services/campaignService";
import { toast } from "react-toastify";

function Campaigns() {
  const { user: currentUser } = useAuth();
  const canWrite = currentUser?.role === "ADMIN" || currentUser?.role === "MANAGER";

  const [campaigns, setCampaigns] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    loadCampaigns();
  }, []);

  const loadCampaigns = async () => {
    try {
      setLoading(true);
      const response = await campaignService.getAll();
      setCampaigns(response.data);
    } catch (error) {
      console.error("Error loading campaigns:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this campaign?")) return;

    try {
      await campaignService.delete(id);
      toast.success("Deleted successfully.");
      loadCampaigns();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to delete campaign.");
    }
  };

  const filtered = campaigns.filter((c) =>
    c.campaignName?.toLowerCase().includes(search.toLowerCase()) ||
    c.partyName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Campaign Management</h2>

        {canWrite && (
          <button
            className="add-btn"
            onClick={() => {
              setSelected(null);
              setOpenModal(true);
            }}
          >
            <FaPlus /> Add Campaign
          </button>
        )}
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search campaign..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Campaigns...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Campaign Name</th>
                <th>Party</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Budget</th>
                <th>Status</th>
                {canWrite && <th>Action</th>}
              </tr>
            </thead>

            <tbody>
              {filtered.length > 0 ? (
                filtered.map((c) => (
                  <tr key={c.id}>
                    <td>{c.id}</td>
                    <td>{c.campaignName}</td>
                    <td>{c.partyName || "-"}</td>
                    <td>{c.startDate || "-"}</td>
                    <td>{c.endDate || "-"}</td>
                    <td>{c.budget ?? "-"}</td>

                    <td>
                      <span className={c.status ? "status active" : "status inactive"}>
                        {c.status ? "Active" : "Inactive"}
                      </span>
                    </td>

                    {canWrite && (
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
                  <td colSpan={canWrite ? "8" : "7"} style={{ textAlign: "center" }}>
                    No Campaigns Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddCampaignModal
          campaign={selected}
          closeModal={() => setOpenModal(false)}
          refreshCampaigns={loadCampaigns}
        />
      )}

    </div>
  );
}

export default Campaigns;
