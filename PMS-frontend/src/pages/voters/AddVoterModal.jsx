import { useEffect, useState } from "react";
import voterService from "../../services/voterService";
import boothService from "../../services/boothService";
import ImageUpload from "../../components/common/ImageUpload";
import { toast } from "react-toastify";

function AddVoterModal({ voter, closeModal, refreshVoters }) {
  const [booths, setBooths] = useState([]);

  const [formData, setFormData] = useState({
    voterId: "",
    name: "",
    fatherName: "",
    gender: "",
    age: "",
    mobile: "",
    address: "",
    boothId: "",
    imageUrl: "",
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadBooths();

    if (voter) {
      setFormData({
        voterId: voter.voterId || "",
        name: voter.name || "",
        fatherName: voter.fatherName || "",
        gender: voter.gender || "",
        age: voter.age || "",
        mobile: voter.mobile || "",
        address: voter.address || "",
        boothId: voter.boothId || "",
        imageUrl: voter.imageUrl || "",
      });
    }
  }, [voter]);

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

    if (!formData.voterId.trim()) {
      toast.warning("Voter ID is required");
      return;
    }

    try {
      setLoading(true);

      const payload = { ...formData, age: formData.age ? Number(formData.age) : null };

      if (voter) {
        await voterService.update(voter.id, payload);
      } else {
        await voterService.create(payload);
      }

      toast.success("Saved successfully.");
      refreshVoters();
      closeModal();
    } catch (error) {
      console.error(error);
      toast.error(error?.response?.data?.message || "Failed to save voter.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <div className="modal-header">
          <h3>{voter ? "Edit Voter" : "Add Voter"}</h3>
        </div>

        <div className="modal-body">
          <ImageUpload
            label="Voter Photo"
            value={formData.imageUrl}
            onChange={(url) => setFormData((prev) => ({ ...prev, imageUrl: url }))}
          />

          <div className="form-group">
            <label>Voter ID</label>
            <input type="text" name="voterId" value={formData.voterId} onChange={handleChange} placeholder="Enter voter ID" />
          </div>

          <div className="form-group">
            <label>Name</label>
            <input type="text" name="name" value={formData.name} onChange={handleChange} placeholder="Enter name" />
          </div>

          <div className="form-group">
            <label>Father&apos;s Name</label>
            <input type="text" name="fatherName" value={formData.fatherName} onChange={handleChange} placeholder="Enter father's name" />
          </div>

          <div className="form-group">
            <label>Gender</label>
            <select name="gender" value={formData.gender} onChange={handleChange}>
              <option value="">Select Gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Other">Other</option>
            </select>
          </div>

          <div className="form-group">
            <label>Age</label>
            <input type="number" name="age" value={formData.age} onChange={handleChange} placeholder="Enter age" />
          </div>

          <div className="form-group">
            <label>Mobile</label>
            <input type="text" name="mobile" value={formData.mobile} onChange={handleChange} placeholder="Enter mobile number" />
          </div>

          <div className="form-group">
            <label>Address</label>
            <input type="text" name="address" value={formData.address} onChange={handleChange} placeholder="Enter address" />
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
            {loading ? "Saving..." : voter ? "Update Voter" : "Save Voter"}
          </button>
        </div>

      </div>
    </div>
  );
}

export default AddVoterModal;
