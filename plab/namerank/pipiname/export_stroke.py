from stroke_number import get_stroke_number,stroke_dic
from base import data_dir
import opencc

with open("wuge_stroke.txt", "w+", encoding='utf-8') as f:
    converter = opencc.OpenCC('s2t.json')
    for k in stroke_dic:
        tk = converter.convert(k)
        if tk in stroke_dic:
            f.write(k +" "+ str(get_stroke_number(tk))  + "\n")

