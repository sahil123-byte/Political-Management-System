import { useEffect, useState } from "react";
import constituencyService from "../../services/constituencyService";
import districtService from "../../services/districtService";
import partyService from "../../services/partyService";
import { toast } from "react-toastify";

function AddConstituencyModal({ constituency, closeModal, refreshConstituencies }) {
  const [districts, setDistricts] = useState([]);
  const [parties, setParties] = useState([]);

  const [formData, setFormData] = useState({
    constituencyName: "",
    districtId: "",
    partyId: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadDistricts();
    loadParties();

    if (constituency) {
      setFormData({
        constituencyName: constituency.constituencyName || "",
        districtId: constituency.districtId || "",
        partyId: constituency.partyId || "",
      });
    }
  }, [constituency]);

  const loadDistricts = async () => {
    try {
      const response = await districtService.getAll();
      setDistricts(response.data);
    } catch (error) {
      console.error(error);
    }
  };

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
    if (!formData.constituencyName.trim()) {
      toast.warning("Constituency name is required");
      return;
    }

    if (!formData.districtId) {
      toast.warning("Please select a district");
      return;
    }

    if (!formData.partyId) {
      toast.warning("Please select a party");
      return;
    }

    try {
      setLoading(true);

      if (constituency) {
        await constituencyService.update(constituency.id, formData);
      } else {
        await constituencyService.create(formData);
      }

      toast.success("Saved successfully.");
      refreshConstituencies();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save constituency.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>{constituency ? "Edit Constituency" : "Add Constituency"}</h3>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>Constituency Name</label>
            <input
              type="text"
              name="constituencyName"
              value={formData.constituencyName}
              onChange={handleChange}
              placeholder="Enter constituency name"
            />
          </div>

          <div className="form-group">
            <label>District</label>
            <select name="districtId" value={formData.districtId} onChange={handleChange}>
              <option value="">Select District</option>
              {districts.map((d) => (
                <option key={d.id} value={d.id}>
                  {d.districtName} ({d.stateName})
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Party</label>
            <select name="partyId" value={formData.partyId} onChange={handleChange}>
              <option value="">Select Party</option>
              {parties.map((p) => (
                <option key={p.id} value={p.id}>
                  {p.partyName}
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
            {loading ? "Saving..." : constituency ? "Update Constituency" : "Save Constituency"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddConstituencyModal;
