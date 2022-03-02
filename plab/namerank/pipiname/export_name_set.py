import json
import re

import opencc

from base import data_dir
from name import Name
from stroke_number import get_stroke_number

# 简体转繁体
s2tConverter = opencc.OpenCC('s2t.json')
# 繁体转简体
t2sConverter = opencc.OpenCC('t2s.json')


def get_source(out):
    names = set()
    # 诗经
    print('>>加载诗经...')
    get_name_json('诗经', names, 'content', out)
    # 楚辞
    print('>>加载楚辞...')
    get_name_txt('楚辞', names, out)
    # # 论语
    # print('>>加载论语...')
    # get_name_json('论语', names, 'paragraphs', out)
    # # 周易
    # print('>>加载周易...')
    # get_name_txt('周易', names, out)
    #唐诗
    print('>>加载唐诗...')
    for i in range(0, 58000, 1000):
        get_name_json('唐诗/poet.tang.' + str(i), names, 'paragraphs', out)
    # 宋诗
    print('>>加载宋诗...')
    for i in range(0, 255000, 1000):
        get_name_json('宋诗/poet.song.' + str(i), names, 'paragraphs', out)
    # 宋词
    print('>>加载宋词...')
    for i in range(0, 22000, 1000):
        get_name_json('宋词/ci.song.' + str(i), names, 'paragraphs', out)

    return names

def get_name_txt(path, names, out):
    with open(data_dir + 'data/' + path + '.txt', encoding='utf-8') as f:
        line_list = f.readlines()
        size = len(line_list)
        progress = 0
        for i in range(0, size):
            # 生成进度
            if (i + 1) * 100 / size - progress >= 10:
                progress += 10
                print('>>正在生成名字...' + str(progress) + '%')
            # 转繁体
            string = t2sConverter.convert(line_list[i])
            if re.search(r'\w', string) is None:
                continue
            string_list = re.split(r'[！？；，。,.?!\n]', string.strip())
            check_and_add_names(names, string_list, out)


def get_name_json(path, names, column, out):
    with open(data_dir + 'data/' + path + '.json', encoding='utf-8') as f:
        data = json.loads(f.read())
        size = len(data)
        progress = 0
        for j in range(0, size):
            # 生成进度
            if (j + 1) * 100 / size - progress >= 10:
                progress += 10
                print('>>正在生成名字...' + str(progress) + '%')
            out.write("\n")
            for string in data[j][column]:
                # 转繁体
                string = t2sConverter.convert(string)
                string_list = re.split(r'[！？；，。,.?!\n]', string.strip())
                check_and_add_names(names, string_list, out)


def check_and_add_names(names, string_list, out):
    for sentence in string_list:
        if len(sentence.strip()) != 0:
            f.write(sentence)
            out.write("\n")

with open("res_text.txt", "w+", encoding='utf-8') as f:
    get_source(f)