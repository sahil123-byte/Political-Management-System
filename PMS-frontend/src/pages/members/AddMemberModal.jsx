import { useEffect, useState } from "react";
import memberService from "../../services/memberService";
import boothService from "../../services/boothService";
import ImageUpload from "../../components/common/ImageUpload";
import { toast } from "react-toastify";

function AddMemberModal({ closeModal, refreshMembers }) {
  const [booths, setBooths] = useState([]);

  const [formData, setFormData] = useState({
    name: "",
    mobile: "",
    email: "",
    address: "",
    designation: "",
    joiningDate: "",
    boothId: "",
    imageUrl: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadBooths();
  }, []);

  const loadBooths = async () => {
    try {
      const response = await boothService.getAll();
      setBooths(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (!formData.name.trim()) {
      toast.warning("Name is required");
      return;
    }

    if (!formData.mobile.trim()) {
      toast.warning("Mobile number is required");
      return;
    }

    if (!formData.boothId) {
      toast.warning("Please select a booth");
      return;
    }

    try {
      setLoading(true);
      await memberService.create(formData);
      toast.success("Saved successfully.");
      refreshMembers();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save member.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>Add Member</h3>
        </div>

        <div className="modal-body">
          <ImageUpload
            label="Member Photo"
            value={formData.imageUrl}
            onChange={(url) => setFormData((prev) => ({ ...prev, imageUrl: url }))}
          />

          <div className="form-group">
            <label>Name</label>
            <input type="text" name="name" value={formData.name} onChange={handleChange} placeholder="Enter name" />
          </div>

          <div className="form-group">
            <label>Mobile</label>
            <input type="text" name="mobile" value={formData.mobile} onChange={handleChange} placeholder="Enter mobile number" />
          </div>

          <div className="form-group">
            <label>Email</label>
            <input type="email" name="email" value={formData.email} onChange={handleChange} placeholder="Enter email" />
          </div>

          <div className="form-group">
            <label>Address</label>
            <input type="text" name="address" value={formData.address} onChange={handleChange} placeholder="Enter address" />
          </div>

          <div className="form-group">
            <label>Designation</label>
            <input type="text" name="designation" value={formData.designation} onChange={handleChange} placeholder="Enter designation" />
          </div>

          <div className="form-group">
            <label>Joining Date</label>
            <input type="date" name="joiningDate" value={formData.joiningDate} onChange={handleChange} />
          </div>

          <div className="form-group">
            <label>Booth</label>
            <select name="boothId" value={formData.boothId} onChange={handleChange}>
              <option value="">Select Booth</option>
              {booths.map((b) => (
                <option key={b.id} value={b.id}>{b.boothName}</option>
              ))}
            </select>
          </div>
        </div>

        <div className="modal-footer">
          <button className="cancel-btn" onClick={closeModal}>Cancel</button>
          <button className="save-btn" onClick={handleSubmit} disabled={loading}>
            {loading ? "Saving..." : "Save Member"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddMemberModal;
