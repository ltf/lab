from stroke_number import get_stroke_number,stroke_dic
from base import data_dir





with open("wuge_stroke.txt", "w+", encoding='utf-8') as f:
    for k in stroke_dic:
        f.write(k +" "+ str(get_stroke_number(k))  + "\n")

