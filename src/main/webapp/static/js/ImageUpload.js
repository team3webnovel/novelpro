import React, { useState, useEffect } from "react";

function ImageUpload() {
  const [images, setImages] = useState([]);
  const [selectedImage, setSelectedImage] = useState(null);

  // 이미지 목록을 가져오는 useEffect
  useEffect(() => {
    fetch("/videos/images")
      .then((response) => response.json())
      .then((data) => setImages(data))
      .catch((error) => console.error("Error fetching image list:", error));
  }, []);

  // 이미지 선택 핸들러
  const handleImageSelect = (image) => {
    setSelectedImage(image);
  };

  // 이미지 선택 후 서버로 전송하는 핸들러
  const handleSubmit = (e) => {
    e.preventDefault();

    if (!selectedImage) {
      alert("이미지를 선택해주세요!");
      return;
    }

    // 선택된 이미지를 서버로 전송
    const formData = new FormData();
    formData.append("initImage", selectedImage.filename); // 서버로 파일 이름 전송

    fetch("/videos/generate", {
      method: "POST",
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.status === "success") {
          alert("비디오 생성이 성공적으로 시작되었습니다.");
        } else {
          alert("오류: " + data.message);
        }
      })
      .catch((error) => console.error("Error generating video:", error));
  };

  return (
    <div>
      <h1>이미지 선택</h1>
      <form onSubmit={handleSubmit}>
        <div className="image-list">
          {images.map((image) => (
            <div key={image.id} onClick={() => handleImageSelect(image)}>
              <img
                src={`/uploads/${image.filename}`}
                alt={image.filename}
                style={{ cursor: "pointer", width: "150px", margin: "10px" }}
              />
              <p>{image.filename}</p>
            </div>
          ))}
        </div>

        {selectedImage && (
          <div>
            <h2>선택된 이미지</h2>
            <img
              src={`/uploads/${selectedImage.filename}`}
              alt={selectedImage.filename}
              style={{ width: "300px" }}
            />
            <p>{selectedImage.filename}</p>
          </div>
        )}

        <button type="submit">비디오 생성</button>
      </form>
    </div>
  );
}

export default ImageUpload;
