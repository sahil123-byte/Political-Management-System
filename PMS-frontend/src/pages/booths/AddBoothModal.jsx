import { useEffect, useState } from "react";
import boothService from "../../services/boothService";
import constituencyService from "../../services/constituencyService";
import { toast } from "react-toastify";

function AddBoothModal({ booth, closeModal, refreshBooths }) {
  const [constituencies, setConstituencies] = useState([]);

  const [formData, setFormData] = useState({
    boothNumber: "",
    boothName: "",
    location: "",
    constituencyId: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadConstituencies();

    if (booth) {
      setFormData({
        boothNumber: booth.boothNumber || "",
        boothName: booth.boothName || "",
        location: booth.location || "",
        constituencyId: booth.constituencyId || "",
      });
    }
  }, [booth]);

  const loadConstituencies = async () => {
    try {
      const response = await constituencyService.getAll();
      setConstituencies(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (!formData.boothName.trim()) {
      toast.warning("Booth name is required");
      return;
    }

    if (!formData.constituencyId) {
      toast.warning("Please select a constituency");
      return;
    }

    try {
      setLoading(true);

      if (booth) {
        await boothService.update(booth.id, formData);
      } else {
        await boothService.create(formData);
      }

      toast.success("Saved successfully.");
      refreshBooths();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save booth.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>{booth ? "Edit Booth" : "Add Booth"}</h3>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>Booth Number</label>
            <input
              type="text"
              name="boothNumber"
              value={formData.boothNumber}
              onChange={handleChange}
              placeholder="Enter booth number"
            />
          </div>

          <div className="form-group">
            <label>Booth Name</label>
            <input
              type="text"
              name="boothName"
              value={formData.boothName}
              onChange={handleChange}
              placeholder="Enter booth name"
            />
          </div>

          <div className="form-group">
            <label>Location</label>
            <input
              type="text"
              name="location"
              value={formData.location}
              onChange={handleChange}
              placeholder="Enter location"
            />
          </div>

          <div className="form-group">
            <label>Constituency</label>
            <select
              name="constituencyId"
              value={formData.constituencyId}
              onChange={handleChange}
            >
              <option value="">Select Constituency</option>
              {constituencies.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.constituencyName}
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
            {loading ? "Saving..." : booth ? "Update Booth" : "Save Booth"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddBoothModal;
