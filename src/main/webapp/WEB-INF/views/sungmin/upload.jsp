<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload Image from URL</title>
    <script>
        function uploadImage() {
            const imageUrl = document.getElementById("imageUrl").value;
            const fileName = document.getElementById("fileName").value || "uploaded_image.png"; // 기본 파일명 설정
            
            // 이미지 URL에서 이미지 가져오기
            fetch(imageUrl)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to fetch image from the URL');
                    }
                    return response.blob(); // 이미지를 Blob으로 변환
                })
                .then(blob => {
                    const formData = new FormData();
                    formData.append('image', blob, fileName); // Blob과 파일명을 FormData에 추가

                    // 이미지 서버로 업로드
                    return fetch('http://192.168.0.237:8188/upload/image', {
                        method: 'POST',
                        body: formData
                    });
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to upload image to the server');
                    }
                    return response.json(); // 서버의 응답을 JSON으로 변환
                })
                .then(data => {
                    console.log("Image uploaded successfully:", data);
                    alert("Image uploaded successfully!");
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("Error uploading image: " + error.message);
                });
        }
    </script>
</head>
<body>
    <h1>Upload Image from URL</h1>
    <form onsubmit="event.preventDefault(); uploadImage();">
        <label for="imageUrl">Image URL:</label><br>
        <input type="text" id="imageUrl" name="imageUrl" required><br><br>
        
        <label for="fileName">File Name (optional):</label><br>
        <input type="text" id="fileName" name="fileName"><br><br>

        <input type="submit" value="Upload Image">
    </form>
</body>
</html>
