import { useEffect, useState } from "react";
import districtService from "../../services/districtService";
import stateService from "../../services/stateService";
import { toast } from "react-toastify";

function AddDistrictModal({ closeModal, refreshDistricts }) {
  const [states, setStates] = useState([]);
  const [districtName, setDistrictName] = useState("");
  const [stateId, setStateId] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadStates();
  }, []);

  const loadStates = async () => {
    try {
      const response = await stateService.getAll();
      setStates(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleSubmit = async () => {
    if (!districtName.trim()) {
      toast.warning("District name is required");
      return;
    }

    if (!stateId) {
      toast.warning("Please select a state");
      return;
    }

    try {
      setLoading(true);
      await districtService.create({ districtName, stateId });
      toast.success("Saved successfully.");
      refreshDistricts();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save district.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h2>Add District</h2>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>District Name</label>
            <input
              type="text"
              placeholder="Enter district name"
              value={districtName}
              onChange={(e) => setDistrictName(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>State</label>
            <select value={stateId} onChange={(e) => setStateId(e.target.value)}>
              <option value="">Select State</option>
              {states.map((state) => (
                <option key={state.id} value={state.id}>
                  {state.stateName}
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
            {loading ? "Saving..." : "Save District"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddDistrictModal;
