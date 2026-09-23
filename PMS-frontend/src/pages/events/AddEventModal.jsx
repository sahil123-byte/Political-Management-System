import { useEffect, useState } from "react";
import eventService from "../../services/eventService";
import campaignService from "../../services/campaignService";
import { toast } from "react-toastify";

function AddEventModal({ event, closeModal, refreshEvents }) {
  const [campaigns, setCampaigns] = useState([]);

  const [formData, setFormData] = useState({
    eventName: "",
    eventType: "",
    venue: "",
    description: "",
    eventDate: "",
    campaignId: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadCampaigns();

    if (event) {
      setFormData({
        eventName: event.eventName || "",
        eventType: event.eventType || "",
        venue: event.venue || "",
        description: event.description || "",
        eventDate: event.eventDate || "",
        campaignId: event.campaignId || "",
      });
    }
  }, [event]);

  const loadCampaigns = async () => {
    try {
      const response = await campaignService.getAll();
      setCampaigns(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (!formData.eventName.trim()) {
      toast.warning("Event name is required");
      return;
    }

    if (!formData.campaignId) {
      toast.warning("Please select a campaign");
      return;
    }

    try {
      setLoading(true);

      if (event) {
        await eventService.update(event.id, formData);
      } else {
        await eventService.create(formData);
      }

      toast.success("Saved successfully.");
      refreshEvents();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save event.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>{event ? "Edit Event" : "Add Event"}</h3>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>Event Name</label>
            <input type="text" name="eventName" value={formData.eventName} onChange={handleChange} placeholder="Enter event name" />
          </div>

          <div className="form-group">
            <label>Event Type</label>
            <input type="text" name="eventType" value={formData.eventType} onChange={handleChange} placeholder="Enter event type" />
          </div>

          <div className="form-group">
            <label>Venue</label>
            <input type="text" name="venue" value={formData.venue} onChange={handleChange} placeholder="Enter venue" />
          </div>

          <div className="form-group">
            <label>Description</label>
            <textarea name="description" value={formData.description} onChange={handleChange} placeholder="Enter description" />
          </div>

          <div className="form-group">
            <label>Event Date</label>
            <input type="date" name="eventDate" value={formData.eventDate} onChange={handleChange} />
          </div>

          <div className="form-group">
            <label>Campaign</label>
            <select name="campaignId" value={formData.campaignId} onChange={handleChange}>
              <option value="">Select Campaign</option>
              {campaigns.map((c) => (
                <option key={c.id} value={c.id}>{c.campaignName}</option>
              ))}
            </select>
          </div>
        </div>

        <div className="modal-footer">
          <button className="cancel-btn" onClick={closeModal}>Cancel</button>
          <button className="save-btn" onClick={handleSubmit} disabled={loading}>
            {loading ? "Saving..." : event ? "Update Event" : "Save Event"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddEventModal;
