import requests
import time
import json
import sys

# stdout 인코딩을 UTF-8로 설정
sys.stdout.reconfigure(encoding='utf-8')
sys.stderr.reconfigure(encoding='utf-8')

# Suno API URL
BASE_URL = "https://suno-api-zn3t.vercel.app/api"

# 음악 생성 요청
def generate_music_from_suno(prompt, make_instrumental):
    url = f"{BASE_URL}/generate"
    payload = {
        "prompt": prompt,
        "make_instrumental": make_instrumental
    }

    try:
        # API 요청 보내기 (타임아웃 15초 설정)
        print(f"음악 생성 요청: {payload}", file=sys.stderr)
        response = requests.post(url, json=payload, timeout=15)
        print(f"응답 코드: {response.status_code}", file=sys.stderr)

        if response.status_code == 200:
            try:
                # JSON 응답을 명시적으로 처리
                response_json = response.json()
                print(f"응답 데이터: {response_json}", file=sys.stderr)
                return response_json  # 정상 응답일 경우 JSON 파싱
            except json.JSONDecodeError as e:
                print(f"JSON 디코딩 오류: {e}", file=sys.stderr)
                return None
        else:
            print(f"API 요청 실패: 상태 코드 {response.status_code}", file=sys.stderr)
            return None
    except requests.exceptions.RequestException as e:
        print(f"API 요청 중 예외 발생: {e}", file=sys.stderr)
        return None

# 음악 상태 확인 (완전히 준비될 때까지 대기)
def get_audio_information_until_complete(audio_id, max_attempts=10):
    url = f"{BASE_URL}/get?ids={audio_id}"
    attempt = 0

    while attempt < max_attempts:
        try:
            print(f"음악 상태 확인 요청 (시도 횟수 {attempt + 1}): {url}", file=sys.stderr)
            response = requests.get(url, timeout=15)  # 타임아웃 15초 설정
            print(f"음악 상태 확인 응답 코드: {response.status_code}", file=sys.stderr)

            if response.status_code == 200:
                try:
                    music_data = response.json()  # JSON 형식으로 변환
                    print(f"음악 상태 응답 데이터: {music_data}", file=sys.stderr)

                    # title, lyric, audio_url이 모두 비어 있지 않은지 확인
                    if (music_data and music_data[0]["status"] == 'streaming' and
                            music_data[0]["title"] and music_data[0]["lyric"] and music_data[0]["audio_url"]):
                        return music_data
                    else:
                        # 음악이 준비되지 않았을 경우 5초 후 다시 시도
                        print(f"음악이 아직 준비되지 않았습니다. 5초 후 다시 확인합니다.", file=sys.stderr)
                        time.sleep(5)
                except json.JSONDecodeError as e:
                    print(f"JSON 디코딩 오류: {e}", file=sys.stderr)
                    return None
            else:
                print(f"상태 확인 실패: 상태 코드 {response.status_code}", file=sys.stderr)
                time.sleep(5)
        except Exception as e:
            print(f"음악 상태 확인 중 예외 발생: {e}", file=sys.stderr)
            time.sleep(5)

        attempt += 1

    # 최대 시도 횟수 초과 시
    print(f"최대 시도 횟수 {max_attempts}번 초과. 음악 준비 실패.", file=sys.stderr)
    return None

# 메인 실행 함수
if __name__ == "__main__":
    # 자바에서 넘겨받은 인자 처리
    if len(sys.argv) > 2:
        prompt = sys.argv[1]
        make_instrumental = sys.argv[2].lower() == 'true'
    else:
        prompt = "praying for God, Gospel, for Mercy"
        make_instrumental = False

    # 음악 생성 요청
    response_data = generate_music_from_suno(prompt, make_instrumental)

    result = []
    if response_data:
        for music_info in response_data:
            audio_id = music_info["id"]

# 음악이 준비될 때까지 상태 확인 (최대 시도 횟수 10회)
music_data = get_audio_information_until_complete(audio_id, max_attempts=10)

if music_data and "audio_url" in music_data[0]:
    title = music_data[0]["title"]
    lyrics = music_data[0]["lyric"]
    audio_url = music_data[0]["audio_url"]
    image_url = music_data[0].get("image_url", "커버 없음")  # 커버 사진이 있을 경우 가져오기
    model_name = music_data[0].get("model_name", "모델 없음")  # 모델 이름
    gpt_description_prompt = music_data[0].get("gpt_description_prompt", "")  # GPT 설명
    music_type = music_data[0].get("type", "gen")  # 타입
    tags = music_data[0].get("tags", "")  # 태그
    error_message = music_data[0].get("error_message", "")  # 오류 메시지

    result.append({
        "title": title,
        "lyric": lyrics,
        "audio_url": audio_url,
        "image_url": image_url,
        "model_name": model_name,
        "gpt_description_prompt": gpt_description_prompt,
        "type": music_type,
        "tags": tags,
        "error_message": error_message
    })


    # 결과를 JSON 형식으로 출력
    print(json.dumps(result, ensure_ascii=False), file=sys.stdout)  # ensure_ascii=False로 UTF-8 출력 보장
