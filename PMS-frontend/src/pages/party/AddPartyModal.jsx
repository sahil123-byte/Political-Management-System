import { useEffect, useState } from "react";
import partyService from "../../services/partyService";
import ImageUpload from "../../components/common/ImageUpload";
import { toast } from "react-toastify";

function AddPartyModal({ party, closeModal, refreshParties }) {
  const [formData, setFormData] = useState({
    partyName: "",
    partySymbol: "",
    partyPresident: "",
    description: "",
    logoUrl: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (party) {
      setFormData({
        partyName: party.partyName || "",
        partySymbol: party.partySymbol || "",
        partyPresident: party.partyPresident || "",
        description: party.description || "",
        logoUrl: party.logoUrl || "",
      });
    }
  }, [party]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (!formData.partyName.trim()) {
      toast.warning("Party name is required");
      return;
    }

    try {
      setLoading(true);

      if (party) {
        await partyService.update(party.id, formData);
      } else {
        await partyService.create(formData);
      }

      toast.success("Saved successfully.");
      refreshParties();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save party.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>{party ? "Edit Party" : "Add Party"}</h3>
        </div>

        <div className="modal-body">
          <ImageUpload
            label="Party Logo"
            shape="square"
            value={formData.logoUrl}
            onChange={(url) => setFormData((prev) => ({ ...prev, logoUrl: url }))}
          />

          <div className="form-group">
            <label>Party Name</label>
            <input
              type="text"
              name="partyName"
              value={formData.partyName}
              onChange={handleChange}
              placeholder="Enter party name"
            />
          </div>

          <div className="form-group">
            <label>Symbol</label>
            <input
              type="text"
              name="partySymbol"
              value={formData.partySymbol}
              onChange={handleChange}
              placeholder="Enter symbol"
            />
          </div>

          <div className="form-group">
            <label>President</label>
            <input
              type="text"
              name="partyPresident"
              value={formData.partyPresident}
              onChange={handleChange}
              placeholder="Enter president name"
            />
          </div>

          <div className="form-group">
            <label>Description</label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              placeholder="Enter description"
            />
          </div>
        </div>

        <div className="modal-footer">
          <button className="cancel-btn" onClick={closeModal}>
            Cancel
          </button>

          <button className="save-btn" onClick={handleSubmit} disabled={loading}>
            {loading ? "Saving..." : party ? "Update Party" : "Save Party"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddPartyModal;
