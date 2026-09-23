import { useEffect, useState } from "react";
import campaignService from "../../services/campaignService";
import partyService from "../../services/partyService";
import { toast } from "react-toastify";

function AddCampaignModal({ campaign, closeModal, refreshCampaigns }) {
  const [parties, setParties] = useState([]);

  const [formData, setFormData] = useState({
    campaignName: "",
    description: "",
    startDate: "",
    endDate: "",
    budget: "",
    partyId: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadParties();

    if (campaign) {
      setFormData({
        campaignName: campaign.campaignName || "",
        description: campaign.description || "",
        startDate: campaign.startDate || "",
        endDate: campaign.endDate || "",
        budget: campaign.budget ?? "",
        partyId: campaign.partyId || "",
      });
    }
  }, [campaign]);

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
    if (!formData.campaignName.trim()) {
      toast.warning("Campaign name is required");
      return;
    }

    if (!formData.partyId) {
      toast.warning("Please select a party");
      return;
    }

    try {
      setLoading(true);

      const payload = {
        ...formData,
        budget: formData.budget ? Number(formData.budget) : null,
      };

      if (campaign) {
        await campaignService.update(campaign.id, payload);
      } else {
        await campaignService.create(payload);
      }

      toast.success("Saved successfully.");
      refreshCampaigns();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save campaign.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>{campaign ? "Edit Campaign" : "Add Campaign"}</h3>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>Campaign Name</label>
            <input type="text" name="campaignName" value={formData.campaignName} onChange={handleChange} placeholder="Enter campaign name" />
          </div>

          <div className="form-group">
            <label>Description</label>
            <textarea name="description" value={formData.description} onChange={handleChange} placeholder="Enter description" />
          </div>

          <div className="form-group">
            <label>Start Date</label>
            <input type="date" name="startDate" value={formData.startDate} onChange={handleChange} />
          </div>

          <div className="form-group">
            <label>End Date</label>
            <input type="date" name="endDate" value={formData.endDate} onChange={handleChange} />
          </div>

          <div className="form-group">
            <label>Budget</label>
            <input type="number" name="budget" value={formData.budget} onChange={handleChange} placeholder="Enter budget" />
          </div>

          <div className="form-group">
            <label>Party</label>
            <select name="partyId" value={formData.partyId} onChange={handleChange}>
              <option value="">Select Party</option>
              {parties.map((p) => (
                <option key={p.id} value={p.id}>{p.partyName}</option>
              ))}
            </select>
          </div>
        </div>

        <div className="modal-footer">
          <button className="cancel-btn" onClick={closeModal}>Cancel</button>
          <button className="save-btn" onClick={handleSubmit} disabled={loading}>
            {loading ? "Saving..." : campaign ? "Update Campaign" : "Save Campaign"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddCampaignModal;
