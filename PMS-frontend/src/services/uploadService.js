import api from "./api";

const uploadService = {
  // Uploads an image file and returns { url } where url is a relative path
  // like "/uploads/xxxxx.jpg". Use resolveImageUrl() to render it.
  uploadImage: (file) => {
    const formData = new FormData();
    formData.append("file", file);

    return api.post("/upload/image", formData, {
      // Let the browser set the correct multipart boundary itself
      // instead of using the default JSON content-type.
      headers: { "Content-Type": undefined },
    });
  },
};

export default uploadService;
