import { useEffect, useState } from "react";
import feedbackService from "../../services/feedbackService";
import memberService from "../../services/memberService";
import { toast } from "react-toastify";

function AddFeedbackModal({ feedback, closeModal, refreshFeedbacks }) {
  const [members, setMembers] = useState([]);

  const [formData, setFormData] = useState({
    feedbackTitle: "",
    feedbackMessage: "",
    rating: 5,
    feedbackDate: "",
    memberId: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadMembers();

    if (feedback) {
      setFormData({
        feedbackTitle: feedback.feedbackTitle || "",
        feedbackMessage: feedback.feedbackMessage || "",
        rating: feedback.rating || 5,
        feedbackDate: feedback.feedbackDate || "",
        memberId: feedback.memberId || "",
      });
    }
  }, [feedback]);

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
    if (!formData.feedbackTitle.trim()) {
      toast.warning("Title is required");
      return;
    }

    if (!formData.memberId) {
      toast.warning("Please select a member");
      return;
    }

    try {
      setLoading(true);

      const payload = { ...formData, rating: Number(formData.rating) };

      if (feedback) {
        await feedbackService.update(feedback.id, payload);
      } else {
        await feedbackService.create(payload);
      }

      toast.success("Saved successfully.");
      refreshFeedbacks();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save feedback.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>{feedback ? "Edit Feedback" : "Add Feedback"}</h3>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>Title</label>
            <input type="text" name="feedbackTitle" value={formData.feedbackTitle} onChange={handleChange} placeholder="Enter title" />
          </div>

          <div className="form-group">
            <label>Message</label>
            <textarea name="feedbackMessage" value={formData.feedbackMessage} onChange={handleChange} placeholder="Enter message" />
          </div>

          <div className="form-group">
            <label>Rating</label>
            <select name="rating" value={formData.rating} onChange={handleChange}>
              {[1, 2, 3, 4, 5].map((r) => (
                <option key={r} value={r}>{r} Star{r > 1 ? "s" : ""}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Date</label>
            <input type="date" name="feedbackDate" value={formData.feedbackDate} onChange={handleChange} />
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
            {loading ? "Saving..." : feedback ? "Update Feedback" : "Save Feedback"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddFeedbackModal;
