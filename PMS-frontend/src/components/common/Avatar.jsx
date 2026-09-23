import { FaUserCircle } from "react-icons/fa";
import { resolveImageUrl } from "../../utils/imageUrl";

// Small circular thumbnail used in table rows. Falls back to a generic icon
// when no image has been uploaded.
function Avatar({ src, size = 36 }) {
  const url = resolveImageUrl(src);

  if (!url) {
    return <FaUserCircle size={size} color="#94a3b8" />;
  }

  return (
    <img
      src={url}
      alt=""
      style={{
        width: `${size}px`,
        height: `${size}px`,
        borderRadius: "50%",
        objectFit: "cover",
      }}
    />
  );
}

export default Avatar;
