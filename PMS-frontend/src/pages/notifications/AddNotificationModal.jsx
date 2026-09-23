import { useEffect, useState } from "react";
import notificationService from "../../services/notificationService";
import memberService from "../../services/memberService";
import { toast } from "react-toastify";

function AddNotificationModal({ notification, closeModal, refreshNotifications }) {
  const [members, setMembers] = useState([]);

  const [formData, setFormData] = useState({
    title: "",
    message: "",
    notificationType: "",
    memberId: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadMembers();

    if (notification) {
      setFormData({
        title: notification.title || "",
        message: notification.message || "",
        notificationType: notification.notificationType || "",
        memberId: notification.memberId || "",
      });
    }
  }, [notification]);

  const loadMembers = async () => {
    try {
      const response = await memberService.getAll();
      setMembers(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (!formData.title.trim()) {
      toast.warning("Title is required");
      return;
    }

    if (!formData.message.trim()) {
      toast.warning("Message is required");
      return;
    }

    if (!formData.memberId) {
      toast.warning("Please select a member");
      return;
    }

    try {
      setLoading(true);

      const payload = { ...formData, createdAt: new Date().toISOString() };

      if (notification) {
        await notificationService.update(notification.id, payload);
      } else {
        await notificationService.create(payload);
      }

      toast.success("Saved successfully.");
      refreshNotifications();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save notification.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>{notification ? "Edit Notification" : "Add Notification"}</h3>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>Title</label>
            <input type="text" name="title" value={formData.title} onChange={handleChange} placeholder="Enter title" />
          </div>

          <div className="form-group">
            <label>Message</label>
            <textarea name="message" value={formData.message} onChange={handleChange} placeholder="Enter message" />
          </div>

          <div className="form-group">
            <label>Type</label>
            <select name="notificationType" value={formData.notificationType} onChange={handleChange}>
              <option value="">Select Type</option>
              <option value="Info">Info</option>
              <option value="Alert">Alert</option>
              <option value="Reminder">Reminder</option>
            </select>
          </div>

          <div className="form-group">
            <label>Member</label>
            <select name="memberId" value={formData.memberId} onChange={handleChange}>
              <option value="">Select Member</option>
              {members.map((m) => (
                <option key={m.id} value={m.id}>{m.name}</option>
              ))}
            </select>
          </div>
        </div>

        <div className="modal-footer">
          <button className="cancel-btn" onClick={closeModal}>Cancel</button>
          <button className="save-btn" onClick={handleSubmit} disabled={loading}>
            {loading ? "Saving..." : notification ? "Update Notification" : "Save Notification"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddNotificationModal;
