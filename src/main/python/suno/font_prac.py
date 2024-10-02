import tkinter as tk
from tkinter import filedialog, colorchooser
from PIL import Image, ImageDraw, ImageFont, ImageTk

class TextBox:
    def __init__(self, canvas, x, y, text, font, color):
        self.canvas = canvas
        self.text = text
        self.font = font
        self.color = color
        self.x = x
        self.y = y
        self.text_id = canvas.create_text(self.x, self.y, text=self.text, font=self.font, fill=self.color, anchor="nw")
        self.rect_id = canvas.create_rectangle(self.canvas.bbox(self.text_id), outline="red", width=2)

        # 드래그 관련 변수 초기화
        self.drag_data = {"x": 0, "y": 0}
        self.canvas.tag_bind(self.text_id, "<ButtonPress-1>", self.on_press)
        self.canvas.tag_bind(self.text_id, "<B1-Motion>", self.on_drag)

    def on_press(self, event):
        self.drag_data["x"] = event.x
        self.drag_data["y"] = event.y

    def on_drag(self, event):
        delta_x = event.x - self.drag_data["x"]
        delta_y = event.y - self.drag_data["y"]
        self.canvas.move(self.text_id, delta_x, delta_y)
        self.canvas.move(self.rect_id, delta_x, delta_y)
        self.drag_data["x"] = event.x
        self.drag_data["y"] = event.y


class InteractiveImageEditor:
    def __init__(self, root):
        self.root = root
        self.root.title("Interactive Image Text Editor")

        # 이미지 파일 선택 버튼
        self.load_button = tk.Button(root, text="이미지 선택", command=self.load_image)
        self.load_button.pack()

        # 텍스트 입력
        self.text_label = tk.Label(root, text="추가할 텍스트:")
        self.text_label.pack()
        self.text_entry = tk.Entry(root)
        self.text_entry.pack()

        # 폰트 크기 슬라이더
        self.font_size_label = tk.Label(root, text="폰트 크기:")
        self.font_size_label.pack()
        self.font_size_slider = tk.Scale(root, from_=10, to=100, orient=tk.HORIZONTAL)
        self.font_size_slider.set(30)  # 기본값
        self.font_size_slider.pack()

        # 텍스트 색상 선택
        self.color_button = tk.Button(root, text="텍스트 색상 선택", command=self.choose_color)
        self.color_button.pack()
        self.selected_color = "#000000"  # 기본 색상은 검정

        # 캔버스 (이미지를 표시할 영역)
        self.canvas = tk.Canvas(root, width=500, height=500, bg="gray")
        self.canvas.pack()

        # 텍스트박스 리스트
        self.text_boxes = []

        # 적용 버튼
        self.apply_button = tk.Button(root, text="텍스트박스 추가", command=self.add_textbox)
        self.apply_button.pack()

    def load_image(self):
        image_path = filedialog.askopenfilename(title="이미지 파일 선택", filetypes=[("Image Files", "*.png;*.jpg;*.jpeg;*.bmp")])
        if image_path:
            self.image = Image.open(image_path)
            self.update_canvas()

    def choose_color(self):
        color_code = colorchooser.askcolor(title="텍스트 색상 선택")[1]
        if color_code:
            self.selected_color = color_code

    def add_textbox(self):
        text = self.text_entry.get()
        font = ("Arial", self.font_size_slider.get())
        color = self.selected_color
        # 텍스트 박스를 캔버스에 추가하고 드래그 가능하게 설정
        textbox = TextBox(self.canvas, 100, 100, text, font, color)
        self.text_boxes.append(textbox)

    def update_canvas(self):
        if self.image:
            self.tk_image = ImageTk.PhotoImage(self.image.resize((500, 500)))
            self.canvas.create_image(0, 0, anchor=tk.NW, image=self.tk_image)

# Tkinter 실행
root = tk.Tk()
editor = InteractiveImageEditor(root)
root.mainloop()
