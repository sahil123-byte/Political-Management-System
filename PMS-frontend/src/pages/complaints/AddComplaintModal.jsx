import { useEffect, useState } from "react";
import complaintService from "../../services/complaintService";
import memberService from "../../services/memberService";
import { toast } from "react-toastify";

function AddComplaintModal({ complaint, closeModal, refreshComplaints }) {
  const [members, setMembers] = useState([]);

  const [formData, setFormData] = useState({
    complaintTitle: "",
    complaintDescription: "",
    complaintStatus: "Pending",
    complaintDate: "",
    memberId: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadMembers();

    if (complaint) {
      setFormData({
        complaintTitle: complaint.complaintTitle || "",
        complaintDescription: complaint.complaintDescription || "",
        complaintStatus: complaint.complaintStatus || "Pending",
        complaintDate: complaint.complaintDate || "",
        memberId: complaint.memberId || "",
      });
    }
  }, [complaint]);

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
    if (!formData.complaintTitle.trim()) {
      toast.warning("Complaint title is required");
      return;
    }

    if (!formData.memberId) {
      toast.warning("Please select a member");
      return;
    }

    try {
      setLoading(true);

      if (complaint) {
        await complaintService.update(complaint.id, formData);
      } else {
        await complaintService.create(formData);
      }

      toast.success("Saved successfully.");
      refreshComplaints();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save complaint.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>{complaint ? "Edit Complaint" : "Add Complaint"}</h3>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>Title</label>
            <input type="text" name="complaintTitle" value={formData.complaintTitle} onChange={handleChange} placeholder="Enter complaint title" />
          </div>

          <div className="form-group">
            <label>Description</label>
            <textarea name="complaintDescription" value={formData.complaintDescription} onChange={handleChange} placeholder="Enter description" />
          </div>

          <div className="form-group">
            <label>Status</label>
            <select name="complaintStatus" value={formData.complaintStatus} onChange={handleChange}>
              <option value="Pending">Pending</option>
              <option value="In Progress">In Progress</option>
              <option value="Resolved">Resolved</option>
            </select>
          </div>

          <div className="form-group">
            <label>Date</label>
            <input type="date" name="complaintDate" value={formData.complaintDate} onChange={handleChange} />
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
            {loading ? "Saving..." : complaint ? "Update Complaint" : "Save Complaint"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddComplaintModal;
