import "../users/Users.css";
import { FaPlus, FaEdit, FaTrash, FaSearch } from "react-icons/fa";
import { useEffect, useState } from "react";
import AddOrganizationModal from "./AddOrganizationModal";
import organizationService from "../../services/organizationService";
import { toast } from "react-toastify";

function Organization() {
  const [organizations, setOrganizations] = useState([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const [selectedOrg, setSelectedOrg] = useState(null);

  useEffect(() => {
    loadOrganizations();
  }, []);

  const loadOrganizations = async () => {
    try {
      setLoading(true);
      const response = await organizationService.getAll();
      setOrganizations(response.data);
    } catch (error) {
      console.error("Error loading organizations:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this organization?")) return;

    try {
      await organizationService.delete(id);
      toast.success("Deleted successfully.");
      loadOrganizations();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to delete organization.");
    }
  };

  const filtered = organizations.filter((org) =>
    org.organizationName?.toLowerCase().includes(search.toLowerCase()) ||
    org.headName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Organization Management</h2>

        <button
          className="add-btn"
          onClick={() => {
            setSelectedOrg(null);
            setOpenModal(true);
          }}
        >
          <FaPlus /> Add Organization
        </button>
      </div>

      <div className="search-box">
        <FaSearch className="search-icon" />
        <input
          type="text"
          placeholder="Search organization..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading && <h3>Loading Organizations...</h3>}

      {!loading && (
        <div className="table-container">
          <table className="users-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Type</th>
                <th>Head</th>
                <th>Contact</th>
                <th>Party</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {filtered.length > 0 ? (
                filtered.map((org) => (
                  <tr key={org.id}>
                    <td>{org.id}</td>
                    <td>{org.organizationName}</td>
                    <td>{org.organizationType}</td>
                    <td>{org.headName}</td>
                    <td>{org.contactNumber}</td>
                    <td>{org.partyName || "-"}</td>

                    <td>
                      <button
                        className="edit-btn"
                        onClick={() => {
                          setSelectedOrg(org);
                          setOpenModal(true);
                        }}
                      >
                        <FaEdit />
                      </button>

                      <button className="delete-btn" onClick={() => handleDelete(org.id)}>
                        <FaTrash />
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="7" style={{ textAlign: "center" }}>
                    No Organizations Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {openModal && (
        <AddOrganizationModal
          organization={selectedOrg}
          closeModal={() => setOpenModal(false)}
          refreshOrganizations={loadOrganizations}
        />
      )}

    </div>
  );
}

export default Organization;
