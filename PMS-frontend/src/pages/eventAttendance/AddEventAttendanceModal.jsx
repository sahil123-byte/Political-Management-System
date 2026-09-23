import { useEffect, useState } from "react";
import eventAttendanceService from "../../services/eventAttendanceService";
import eventService from "../../services/eventService";
import memberService from "../../services/memberService";
import { toast } from "react-toastify";

function AddEventAttendanceModal({ record, closeModal, refreshRecords }) {
  const [events, setEvents] = useState([]);
  const [members, setMembers] = useState([]);

  const [formData, setFormData] = useState({
    eventId: "",
    memberId: "",
    attendanceStatus: "Present",
    remarks: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadEvents();
    loadMembers();

    if (record) {
      setFormData({
        eventId: record.eventId || "",
        memberId: record.memberId || "",
        attendanceStatus: record.attendanceStatus || "Present",
        remarks: record.remarks || "",
      });
    }
  }, [record]);

  const loadEvents = async () => {
    try {
      const response = await eventService.getAll();
      setEvents(response.data);
    } catch (error) {
      console.error(error);
    }
  };

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
    if (!formData.eventId) {
      toast.warning("Please select an event");
      return;
    }

    if (!formData.memberId) {
      toast.warning("Please select a member");
      return;
    }

    try {
      setLoading(true);

      if (record) {
        await eventAttendanceService.update(record.id, formData);
      } else {
        await eventAttendanceService.create(formData);
      }

      toast.success("Saved successfully.");
      refreshRecords();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save attendance record.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>{record ? "Edit Attendance" : "Add Attendance"}</h3>
        </div>

        <div className="modal-body">
          <div className="form-group">
            <label>Event</label>
            <select name="eventId" value={formData.eventId} onChange={handleChange}>
              <option value="">Select Event</option>
              {events.map((ev) => (
                <option key={ev.id} value={ev.id}>{ev.eventName}</option>
              ))}
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

          <div className="form-group">
            <label>Status</label>
            <select name="attendanceStatus" value={formData.attendanceStatus} onChange={handleChange}>
              <option value="Present">Present</option>
              <option value="Absent">Absent</option>
            </select>
          </div>

          <div className="form-group">
            <label>Remarks</label>
            <input type="text" name="remarks" value={formData.remarks} onChange={handleChange} placeholder="Enter remarks" />
          </div>
        </div>

        <div className="modal-footer">
          <button className="cancel-btn" onClick={closeModal}>Cancel</button>
          <button className="save-btn" onClick={handleSubmit} disabled={loading}>
            {loading ? "Saving..." : record ? "Update Attendance" : "Save Attendance"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddEventAttendanceModal;
