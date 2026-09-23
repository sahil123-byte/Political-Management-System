import { useState } from "react";
import stateService from "../../services/stateService";
import { toast } from "react-toastify";

function AddStateModal({ closeModal, refreshStates }) {
  const [stateName, setStateName] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    if (!stateName.trim()) {
      toast.warning("State name is required");
      return;
    }

    try {
      setLoading(true);
      await stateService.create({ stateName });
      toast.success("Saved successfully.");
      refreshStates();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save state.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h2>Add State</h2>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>State Name</label>
            <input
              type="text"
              placeholder="Enter state name"
              value={stateName}
              onChange={(e) => setStateName(e.target.value)}
            />
          </div>
        </div>

        <div className="modal-footer">
          <button className="cancel-btn" onClick={closeModal}>
            Cancel
          </button>

          <button className="save-btn" onClick={handleSubmit} disabled={loading}>
            {loading ? "Saving..." : "Save State"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddStateModal;
