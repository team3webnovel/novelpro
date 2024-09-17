import requests
import os

# Suno API URL
BASE_URL = "http://localhost:3000/api"


# 음악 생성 요청 (비동기 대신 동기 함수로 변경)
def generate_music_from_suno(prompt, make_instrumental):
    url = f"{BASE_URL}/generate"
    payload = {
        "prompt": prompt,
        "make_instrumental": make_instrumental
    }

    print(f"보낼 URL: {url}")
    print(f"보낼 Payload: {payload}")

    try:
        # API 요청 보내기
        response = requests.post(url, json=payload)
        print(f"응답 코드: {response.status_code}")

        # 상태 코드 확인
        if response.status_code == 200:
            try:
                # 정상 응답일 경우 JSON 파싱
                response_data = response.json()
                print(f"응답 데이터: {response_data}")
                return response_data
            except ValueError:
                # JSON 파싱 오류 처리
                print("응답이 JSON 형식이 아닙니다.")
                print(f"응답 내용: {response.text}")
                return None
        else:
            # 오류 발생 시 상태 코드 및 오류 메시지 출력
            print(f"오류 발생: 상태 코드 {response.status_code}")
            print(f"응답 내용: {response.text}")
            print(f"응답 헤더: {response.headers}")
            return None

    except requests.exceptions.RequestException as e:
        print(f"API 요청 중 예외 발생: {e}")
        return None


# 음악 다운로드 (비동기 대신 동기 함수로 변경)
def download_music(audio_url, file_path):
    try:
        # 음악 파일 다운로드 요청
        response = requests.get(audio_url, stream=True)

        # 상태 코드 확인
        if response.status_code == 200:
            # 다운로드 시작
            with open(file_path, 'wb') as f:
                for chunk in response.iter_content(chunk_size=1024):
                    if chunk:
                        f.write(chunk)
            print(f"음악 파일 저장 완료: {file_path}")
        else:
            print(f"음악 파일 다운로드 실패: 상태 코드 {response.status_code}")
            print(f"응답 내용: {response.text}")
    except Exception as e:
        print(f"음악 파일 다운로드 중 예외 발생: {e}")


# 테스트를 위한 메인 실행 코드
if __name__ == "__main__":
    # 테스트를 위한 프롬프트와 설정
    prompt = "test prompt"
    make_instrumental = False

    # 음악 생성 요청
    response_data = generate_music_from_suno(prompt, make_instrumental)

    # 응답이 정상적이면 음악 다운로드 실행
    if response_data and "audio_url" in response_data:
        audio_url = response_data["audio_url"]
        download_music(audio_url, "output_music.mp3")
    else:
        print("음악 다운로드를 할 수 없습니다. 응답 데이터를 확인하세요.")
