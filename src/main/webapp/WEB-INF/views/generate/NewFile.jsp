<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>비디오 생성</title>
    <style>
        .image-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
            gap: 10px;
        }

        .image-item {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
            cursor: pointer;
        }

        .image-item img {
            max-width: 100%;
            height: auto;
        }

        .image-item.selected {
            border-color: blue; /* 선택된 이미지의 테두리 색을 변경 */
        }
    </style>
</head>
<body>

    <h1>비디오 생성</h1>

    <!-- 이미지 선택 및 업로드 섹션 -->
    <h2>데이터베이스에서 이미지 선택 및 업로드</h2>
    <div class="image-grid" id="imageGrid">
        <c:forEach var="image" items="${imageList}">
            <div class="image-item" data-url="${image.imageUrl}" data-filename="${image.filename}" onclick="selectImage(this)">
                <img src="${image.imageUrl}" alt="데이터베이스 이미지">
                <p>생성일: ${image.createdAt}</p>
                <p>샘플러: ${image.sampler}</p>
                <p>파일명: ${image.filename}</p>
            </div>
        </c:forEach>
    </div>

    <!-- 이미지 업로드 버튼 -->
    <button type="button" onclick="uploadSelectedImage()">이미지 업로드</button>

    <!-- 비디오 생성 폼 -->
    <h2>비디오 생성</h2>
    <form id="videoGenerateForm" method="post" action="<%=request.getContextPath()%>/images/vidgenerate">
        <input type="hidden" id="selectedFilename" name="selectedFilename">
        <input type="hidden" id="uploadedImageUrl" name="uploadedImageUrl">

        <div>
            <label for="samplerName">샘플러 이름:</label>
            <input type="text" id="samplerName" name="sampler_index" value="dpmpp_2m" required>
        </div>

        <div>
            <label for="steps">스텝:</label>
            <input type="number" id="steps" name="steps" value="20" required>
        </div>

        <div>
            <label for="width">너비:</label>
            <input type="number" id="width" name="width" value="512" readonly>
        </div>

        <div>
            <label for="height">높이:</label>
            <input type="number" id="height" name="height" value="768" readonly>
        </div>

        <div>
            <label for="videoFrames">비디오 프레임:</label>
            <input type="number" id="videoFrames" name="videoFrames" value="14" readonly>
        </div>

        <div>
            <label for="fps">FPS (초당 프레임):</label>
            <input type="number" id="fps" name="fps" value="6" required>
        </div>

        <div>
            <label for="seed">시드:</label>
            <input type="number" id="seed" name="seed" value="1">
            <button type="button" onclick="setRandomSeed()">랜덤 시드</button>
        </div>

        <div>
            <label for="cfgScale">CFG 스케일:</label>
            <input type="number" step="0.1" id="cfgScale" name="cfg_scale" value="2.5" required>
        </div>

        <div>
            <label for="checkpoint">체크포인트:</label>
            <input type="text" id="checkpoint" name="checkpoint" value="svd.safetensors" readonly>
        </div>

        <div>
            <label for="motionBucketId">모션 버킷 ID:</label>
            <input type="number" id="motionBucketId" name="motionBucketId" value="127" required>
        </div>

        <button type="submit">비디오 생성</button>
    </form>

    <div id="generationResult"></div>

    <script>
        let selectedImageElement = null;

        // 이미지 선택 함수
        function selectImage(imageElement) {
            if (selectedImageElement) {
                selectedImageElement.classList.remove('selected');
            }
            selectedImageElement = imageElement;
            imageElement.classList.add('selected');

            const selectedFilename = imageElement.dataset.filename;
            document.getElementById('selectedFilename').value = selectedFilename;

            console.log("Selected Filename:", selectedFilename);
        }

        // 랜덤 시드 설정 함수
        function setRandomSeed() {
            const randomSeed = Math.floor(Math.random() * 9999999) + 1;
            document.getElementById('seed').value = randomSeed;
        }

        // 이미지 업로드 함수
        function uploadSelectedImage() {
            if (!selectedImageElement) {
                alert("이미지를 선택하세요.");
                return;
            }

            const selectedImageUrl = selectedImageElement.dataset.url;
            const selectedFilename = selectedImageElement.dataset.filename;

            console.log("이미지 업로드 중...");

            fetch(selectedImageUrl)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('이미지 다운로드 실패');
                    }
                    return response.blob();
                })
                .then(blob => {
                    const formData = new FormData();
                    formData.append('image', blob, selectedFilename);

                    return fetch('http://192.168.0.237:8188/upload/image', {
                        method: 'POST',
                        body: formData
                    });
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('이미지 업로드 실패');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log("이미지 업로드 성공:", data);
                    alert("이미지 업로드 성공!");

                    document.getElementById('uploadedImageUrl').value = data.uploadedImagePath;
                })
                .catch(error => {
                    console.error("업로드 오류:", error);
                    alert("이미지 업로드 실패: " + error.message);
                });
        }
    </script>
</body>
</html>
