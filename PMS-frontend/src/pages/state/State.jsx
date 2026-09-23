import "../users/Users.css";
import { useEffect, useState } from "react";
import { FaPlus, FaSearch } from "react-icons/fa";
import AddStateModal from "./AddStateModal";
import stateService from "../../services/stateService";

function State() {
  const [states, setStates] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);

  useEffect(() => {
    loadStates();
  }, []);

  const loadStates = async () => {
    try {
      setLoading(true);
      const response = await stateService.getAll();
      setStates(response.data);
    } catch (error) {
      console.error("Error loading states:", error);
    } finally {
      setLoading(false);
    }
  };

  const filteredStates = states.filter((state) =>
    state.stateName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>State Management</h2>

        <button className="add-btn" onClick={() => setOpenModal(true)}>
          <FaPlus /> Add State
        </button>
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search state..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading States...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>State Name</th>
              </tr>
            </thead>

            <tbody>
              {filteredStates.length > 0 ? (
                filteredStates.map((state) => (
                  <tr key={state.id}>
                    <td>{state.id}</td>
                    <td>{state.stateName}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="2" style={{ textAlign: "center" }}>
                    No States Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddStateModal
          closeModal={() => setOpenModal(false)}
          refreshStates={loadStates}
        />
      )}

    </div>
  );
}

export default State;
