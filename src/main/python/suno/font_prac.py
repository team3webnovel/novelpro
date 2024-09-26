import tkinter as tk
from tkinter import filedialog
from PIL import Image, ImageDraw, ImageFont, ImageTk


def add_text_to_image(image_path, output_path, text):
    # 이미지 열기
    image = Image.open(image_path)

    # RGBA 모드인 경우 RGB로 변환
    if image.mode == 'RGBA':
        image = image.convert('RGB')

    # 글자를 그릴 수 있는 객체 생성
    draw = ImageDraw.Draw(image)

    # 글꼴과 크기 설정 (원하는 경로에 있는 TTF 파일을 사용)
    font = ImageFont.truetype("arial.ttf", 50)  # 글꼴 파일과 크기

    # 텍스트 위치 및 색상 설정
    position = (50, 50)  # (x, y) 좌표
    color = (255, 255, 255)  # 흰색 글자

    # 이미지에 텍스트 추가
    draw.text(position, text, font=font, fill=color)

    # 수정된 이미지를 저장
    image.save(output_path)
    print(f"이미지가 저장되었습니다: {output_path}")

    return image


# tkinter 파일 다이얼로그로 이미지 선택
def select_image():
    root = tk.Tk()
    root.withdraw()  # 메인 윈도우 숨기기

    # 파일 선택 창 띄우기
    file_path = filedialog.askopenfilename(title="이미지 파일 선택", filetypes=[("Image Files", "*.png;*.jpg;*.jpeg;*.bmp")])

    if file_path:
        return file_path
    else:
        print("파일을 선택하지 않았습니다.")
        return None


# 결과 이미지를 tkinter로 화면에 표시
def show_image(image):
    root = tk.Tk()
    root.title("결과 이미지")

    # 이미지를 Tk 형식으로 변환
    tk_image = ImageTk.PhotoImage(image)

    # 레이블에 이미지 표시
    label = tk.Label(root, image=tk_image)
    label.image = tk_image  # 이미지 객체 참조를 유지합니다.
    label.pack()

    root.mainloop()


if __name__ == "__main__":
    # 이미지 선택 창 띄우기
    image_path = select_image()

    if image_path:
        output_path = "output_image.jpg"  # 출력 이미지 경로
        text = "Hello, World!"  # 이미지에 넣을 텍스트

        # 이미지에 텍스트 추가 및 결과 이미지 얻기
        modified_image = add_text_to_image(image_path, output_path, text)

        # 결과 이미지 표시
        show_image(modified_image)
