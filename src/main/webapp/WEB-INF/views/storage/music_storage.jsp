<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Stored Music</title>
    <style>
        .music-item {
            display: inline-block;
            text-align: center;
            margin: 10px;
            position: relative;
        }

        .music-item img {
            display: block;
            width: 200px;
            height: 200px;
            object-fit: cover;
            cursor: pointer;
        }

        .music-item h3 {
            margin-top: 10px;
            font-size: 16px;
            color: #333;
        }

        .music-item audio {
            display: block;
            margin-top: 10px;
            width: 100%;
        }

        .play-button {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            font-size: 30px;
            color: white;
            background-color: rgba(0, 0, 0, 0.6);
            border: none;
            border-radius: 50%;
            padding: 10px 20px;
            display: none;
        }

        .playing .play-button {
            display: block;
        }
    </style>
</head>
<body>
    <h1>Your Stored Music</h1>
    <div class="music-gallery">
        <c:forEach var="music" items="${musicList}">
            <div class="music-item" id="musicItem${music.creationId}">
                <!-- 이미지 클릭 시 오디오 재생 -->
                <img src="${music.imageUrl}" alt="Cover Image" onclick="playMusic('${music.creationId}', '${music.audioUrl}')">
                 <h3><a href="${pageContext.request.contextPath}/music_detail/${music.creationId}">${music.title}</a></h3>
                <button class="play-button" id="playButton${music.creationId}">⏵</button>
                <audio id="audioPlayer${music.creationId}" controls>
                    <source id="audioSource${music.creationId}" src="https://cdn1.suno.ai/${music.audioUrl.split('=')[1]}.mp4" type="audio/mp4">
                    Your browser does not support the audio element.
                </audio>
            </div>
        </c:forEach>
    </div>

    <script>
        var currentPlayingItem = null;

        function playMusic(itemId, audioUrl) {
            var audioPlayer = document.getElementById('audioPlayer' + itemId);
            var audioSource = document.getElementById('audioSource' + itemId);
            var playButton = document.getElementById('playButton' + itemId);

            // 오디오가 이미 로드된 상태라면 다른 음악 재생 중지
            if (currentPlayingItem && currentPlayingItem !== itemId) {
                var currentAudioPlayer = document.getElementById('audioPlayer' + currentPlayingItem);
                currentAudioPlayer.pause();
                document.getElementById('musicItem' + currentPlayingItem).classList.remove('playing');
            }

            // 음악 재생 및 일시정지 기능
            if (audioPlayer.paused) {
                audioPlayer.play();
                document.getElementById('musicItem' + itemId).classList.add('playing');
                currentPlayingItem = itemId;
            } else {
                audioPlayer.pause();
                document.getElementById('musicItem' + itemId).classList.remove('playing');
                currentPlayingItem = null;
            }

            // 음악이 끝나면 재생 중 표시 제거
            audioPlayer.onended = function() {
                document.getElementById('musicItem' + itemId).classList.remove('playing');
                currentPlayingItem = null;
            };
        }
    </script>
</body>
</html>