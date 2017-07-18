#!/usr/bin/env python
# -*- coding:utf-8 -*-  



# pick char(s) for each line
def pickChar(line, outlist):
    begin = line.find('( ')
    if begin>0:
        end = line.find(' )')
        pair=line[begin+2:end] 
        pair=pair.replace(' ','')
        chars = pair.split('→')
        ##print chars[0]
        ##print chars[1]
        outlist.append(chars[0])
        outlist.append(chars[1])



# main entry
def pickChars(fin, fout):
    f = open(fin)
    chars = []
    while 1:
        lines = f.readlines(10000)
        if not lines:
            break
        for line in lines:
            pickChar(line, chars)


    ## print len(chars)

    ## distinct chars, low performance
    uniqChars = reduce(lambda x,y:x if y in x else x+[y], [[],]+chars)

    # print len(uniqChars)
    # print uniqChars

    fo = open(fout,'w')
    fo.writelines(map(lambda c: c+"\n", uniqChars))


pickChars("./confusables.txt","./confuschars.txt")
