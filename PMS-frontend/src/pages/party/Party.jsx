import "../users/Users.css";
import { FaPlus, FaEdit, FaTrash, FaSearch } from "react-icons/fa";
import { useEffect, useState } from "react";
import AddPartyModal from "./AddPartyModal";
import partyService from "../../services/partyService";
import Avatar from "../../components/common/Avatar";
import { toast } from "react-toastify";

function Party() {
  const [parties, setParties] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const [selectedParty, setSelectedParty] = useState(null);

  useEffect(() => {
    loadParties();
  }, []);

  const loadParties = async () => {
    try {
      setLoading(true);
      const response = await partyService.getAll();
      setParties(response.data);
    } catch (error) {
      console.error("Error loading parties:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this party?")) return;

    try {
      await partyService.delete(id);
      toast.success("Deleted successfully.");
      loadParties();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to delete party.");
    }
  };

  const filteredParties = parties.filter((party) =>
    party.partyName?.toLowerCase().includes(search.toLowerCase()) ||
    party.partyPresident?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Political Party Management</h2>

        <button
          className="add-btn"
          onClick={() => {
            setSelectedParty(null);
            setOpenModal(true);
          }}
        >
          <FaPlus /> Add Party
        </button>
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search party..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Parties...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">

            <thead>
              <tr>
                <th>ID</th>
                <th>Logo</th>
                <th>Party Name</th>
                <th>Symbol</th>
                <th>President</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {filteredParties.length > 0 ? (
                filteredParties.map((party) => (
                  <tr key={party.id}>
                    <td>{party.id}</td>
                    <td>
                      <Avatar src={party.logoUrl} />
                    </td>
                    <td>{party.partyName}</td>
                    <td>{party.partySymbol}</td>
                    <td>{party.partyPresident}</td>

                    <td>
                      <button
                        className="edit-btn"
                        onClick={() => {
                          setSelectedParty(party);
                          setOpenModal(true);
                        }}
                      >
                        <FaEdit />
                      </button>

                      <button className="delete-btn" onClick={() => handleDelete(party.id)}>
                        <FaTrash />
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" style={{ textAlign: "center" }}>
                    No Parties Found
                  </td>
                </tr>
              )}
            </tbody>

          </table>
        </div>
      )}

      {openModal && (
        <AddPartyModal
          party={selectedParty}
          closeModal={() => setOpenModal(false)}
          refreshParties={loadParties}
        />
      )}

    </div>
  );
}

export default Party;
