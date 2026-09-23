import "../users/Users.css";
import { useEffect, useState } from "react";
import { FaPlus, FaSearch } from "react-icons/fa";
import AddDistrictModal from "./AddDistrictModal";
import districtService from "../../services/districtService";

function District() {
  const [districts, setDistricts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);

  useEffect(() => {
    loadDistricts();
  }, []);

  const loadDistricts = async () => {
    try {
      setLoading(true);
      const response = await districtService.getAll();
      setDistricts(response.data);
    } catch (error) {
      console.error("Error loading districts:", error);
    } finally {
      setLoading(false);
    }
  };

  const filteredDistricts = districts.filter((district) =>
    district.districtName?.toLowerCase().includes(search.toLowerCase()) ||
    district.stateName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>District Management</h2>

        <button className="add-btn" onClick={() => setOpenModal(true)}>
          <FaPlus /> Add District
        </button>
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search district..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Districts...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>District Name</th>
                <th>State</th>
              </tr>
            </thead>

            <tbody>
              {filteredDistricts.length > 0 ? (
                filteredDistricts.map((district) => (
                  <tr key={district.id}>
                    <td>{district.id}</td>
                    <td>{district.districtName}</td>
                    <td>{district.stateName}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="3" style={{ textAlign: "center" }}>
                    No Districts Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddDistrictModal
          closeModal={() => setOpenModal(false)}
          refreshDistricts={loadDistricts}
        />
      )}

    </div>
  );
}

export default District;
