import tkinter as tk
from tkinter import filedialog, messagebox
import cv2
import numpy as np
from PIL import Image, ImageTk

class ImageProcessor(tk.Tk):
    
    def __init__(self):
        super().__init__()
        self.title("Обработка изображений")
        self.geometry("800x600")
        
        self.image_panel = None
        self.original_image = None
        
        self.create_widgets()
    
    def create_widgets(self):
        self.upload_button = tk.Button(self, text="Загрузить изображение", command=self.load_image)
        self.upload_button.pack(pady=10)
        
        self.smooth_button = tk.Button(self, text="Применить сглаживающий фильтр", command=self.apply_smoothing)
        self.smooth_button.pack(pady=10)
        
        self.threshold_button = tk.Button(self, text="Применить локальную пороговую обработку Адаптивная", command=self.local_thresholding)
        self.threshold_button.pack(pady=10)

        self.threshold_button1 = tk.Button(self, text="Применить локальную пороговую обработку Гаусса", command=self.local_thresh)
        self.threshold_button1.pack(pady=10)
        
        self.back_button = tk.Button(self, text="Исходное изображение", command=self.back_image)
        self.back_button.pack(pady=10)
        
        self.exit_button = tk.Button(self, text="Выход", command=self.destroy)
        self.exit_button.pack(pady=10)
    
    def load_image(self):
        file_path = filedialog.askopenfilename()
        if file_path:
            self.original_image = cv2.imread(file_path, cv2.IMREAD_COLOR)
            self.show_image(self.original_image)
    
    def show_image(self, image):
        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)  # Конвертируем BGR в RGB
        image = Image.fromarray(image)
        image = ImageTk.PhotoImage(image)
        
        if self.image_panel is None:
            self.image_panel = tk.Label(self, image=image)
            self.image_panel.image = image
            self.image_panel.pack()
        else:
            self.image_panel.configure(image=image)
            self.image_panel.image = image
    
    def apply_smoothing(self):
        if self.original_image is not None:
            smoothed_image = cv2.GaussianBlur(self.original_image, (5, 5), 0)
            self.show_image(smoothed_image)
        else:
            messagebox.showwarning("Предупреждение", "Сначала загрузите изображение.")
    
    def local_thresholding(self):
        if self.original_image is not None:
            gray_image = cv2.cvtColor(self.original_image, cv2.COLOR_BGR2GRAY)
            adaptive_thresh = cv2.adaptiveThreshold(gray_image, 255, cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY, 11, 2)
            self.show_image(adaptive_thresh)
        else:
            messagebox.showwarning("Предупреждение", "Сначала загрузите изображение.")
            
    def local_thresh(self):
        if self.original_image is not None:
            gray_image = cv2.cvtColor(self.original_image, cv2.COLOR_BGR2GRAY)
            adaptive_thresh = cv2.adaptiveThreshold(gray_image,255,cv2.ADAPTIVE_THRESH_GAUSSIAN_C,cv2.THRESH_BINARY,11,2)
            self.show_image(adaptive_thresh)
        else:
            messagebox.showwarning("Предупреждение", "Сначала загрузите изображение.")
        
    def back_image(self):
        if self.original_image is not None:
            self.show_image(self.original_image)
        else:
            messagebox.showwarning("Предупреждение", "Сначала загрузите изображение.")

# Запуск приложения
if __name__ == "__main__":
    app = ImageProcessor()
    app.mainloop()
