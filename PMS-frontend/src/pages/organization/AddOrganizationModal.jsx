import { useEffect, useState } from "react";
import organizationService from "../../services/organizationService";
import partyService from "../../services/partyService";
import { toast } from "react-toastify";

function AddOrganizationModal({ organization, closeModal, refreshOrganizations }) {
  const [parties, setParties] = useState([]);

  const [formData, setFormData] = useState({
    organizationName: "",
    organizationType: "",
    headName: "",
    contactNumber: "",
    email: "",
    address: "",
    partyId: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadParties();

    if (organization) {
      setFormData({
        organizationName: organization.organizationName || "",
        organizationType: organization.organizationType || "",
        headName: organization.headName || "",
        contactNumber: organization.contactNumber || "",
        email: organization.email || "",
        address: organization.address || "",
        partyId: organization.partyId || "",
      });
    }
  }, [organization]);

  const loadParties = async () => {
    try {
      const response = await partyService.getAll();
      setParties(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (!formData.organizationName.trim()) {
      toast.warning("Organization name is required");
      return;
    }

    if (!formData.partyId) {
      toast.warning("Please select a party");
      return;
    }

    try {
      setLoading(true);

      if (organization) {
        await organizationService.update(organization.id, formData);
      } else {
        await organizationService.create(formData);
      }

      toast.success("Saved successfully.");
      refreshOrganizations();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save organization.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>{organization ? "Edit Organization" : "Add Organization"}</h3>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>Organization Name</label>
            <input
              type="text"
              name="organizationName"
              value={formData.organizationName}
              onChange={handleChange}
              placeholder="Enter organization name"
            />
          </div>

          <div className="form-group">
            <label>Type</label>
            <input
              type="text"
              name="organizationType"
              value={formData.organizationType}
              onChange={handleChange}
              placeholder="Enter type"
            />
          </div>

          <div className="form-group">
            <label>Head Name</label>
            <input
              type="text"
              name="headName"
              value={formData.headName}
              onChange={handleChange}
              placeholder="Enter head name"
            />
          </div>

          <div className="form-group">
            <label>Contact Number</label>
            <input
              type="text"
              name="contactNumber"
              value={formData.contactNumber}
              onChange={handleChange}
              placeholder="Enter contact number"
            />
          </div>

          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Enter email"
            />
          </div>

          <div className="form-group">
            <label>Address</label>
            <input
              type="text"
              name="address"
              value={formData.address}
              onChange={handleChange}
              placeholder="Enter address"
            />
          </div>

          <div className="form-group">
            <label>Party</label>
            <select name="partyId" value={formData.partyId} onChange={handleChange}>
              <option value="">Select Party</option>
              {parties.map((party) => (
                <option key={party.id} value={party.id}>
                  {party.partyName}
                </option>
              ))}
            </select>
          </div>
        </div>

        <div className="modal-footer">
          <button className="cancel-btn" onClick={closeModal}>
            Cancel
          </button>

          <button className="save-btn" onClick={handleSubmit} disabled={loading}>
            {loading ? "Saving..." : organization ? "Update Organization" : "Save Organization"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddOrganizationModal;
