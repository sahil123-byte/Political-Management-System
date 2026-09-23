import { useRef, useState } from "react";
import { FaCamera, FaUserCircle } from "react-icons/fa";
import uploadService from "../../services/uploadService";
import { resolveImageUrl } from "../../utils/imageUrl";

// Reusable image upload control with preview.
// Props:
//  - value: current relative image URL (e.g. "/uploads/xxx.jpg") or ""
//  - onChange: (newRelativeUrl) => void
//  - label: field label text
//  - shape: "circle" | "square" (default "circle")
function ImageUpload({ value, onChange, label = "Photo", shape = "circle" }) {
  const inputRef = useRef(null);
  const [uploading, setUploading] = useState(false);
  const [error, setError] = useState("");

  const handleFileChange = async (e) => {
    const file = e.target.files?.[0];
    if (!file) return;

    setError("");

    if (!["image/jpeg", "image/jpg", "image/png", "image/webp", "image/gif"].includes(file.type)) {
      setError("Only JPG, PNG, WEBP or GIF images are allowed");
      return;
    }

    if (file.size > 5 * 1024 * 1024) {
      setError("Image must be under 5MB");
      return;
    }

    try {
      setUploading(true);
      const response = await uploadService.uploadImage(file);
      onChange(response.data.url);
    } catch (err) {
      console.error(err);
      setError(err?.response?.data?.message || "Failed to upload image");
    } finally {
      setUploading(false);
    }
  };

  const previewUrl = resolveImageUrl(value);

  return (
    <div className="form-group">
      <label>{label}</label>

      <div style={{ display: "flex", alignItems: "center", gap: "16px" }}>
        <div
          style={{
            width: "72px",
            height: "72px",
            borderRadius: shape === "circle" ? "50%" : "10px",
            overflow: "hidden",
            background: "#e5e7eb",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            flexShrink: 0,
          }}
        >
          {previewUrl ? (
            <img
              src={previewUrl}
              alt="preview"
              style={{ width: "100%", height: "100%", objectFit: "cover" }}
            />
          ) : (
            <FaUserCircle size={40} color="#94a3b8" />
          )}
        </div>

        <div>
          <button
            type="button"
            className="cancel-btn"
            onClick={() => inputRef.current?.click()}
            disabled={uploading}
          >
            <FaCamera style={{ marginRight: "6px" }} />
            {uploading ? "Uploading..." : "Choose Photo"}
          </button>

          <input
            ref={inputRef}
            type="file"
            accept="image/png, image/jpeg, image/webp, image/gif"
            onChange={handleFileChange}
            style={{ display: "none" }}
          />
        </div>
      </div>

      {error && <p style={{ color: "#dc2626", fontSize: "13px", marginTop: "6px" }}>{error}</p>}
    </div>
  );
}

export default ImageUpload;
