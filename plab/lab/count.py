#!/usr/bin/env python
# -*- coding:utf-8 -*-  


def countline(line, counter, uids):
    secs = line.split('|')
    i = 1
    if len(secs)>i:
        if (secs[0] in uids):
            return
        else:
            uids.add(secs[0])
            
        if (counter.has_key(secs[i])):
            counter[secs[i]]=counter[secs[i]]+1
        else:
            counter[secs[i]]=1


def count(fin):
    f = open(fin)
    counter = {}
    uids = set()
    ac = 0
    while 1:
        lines = f.readlines(10000)
        if not lines:
            break
        for line in lines:
            ac+=1
            countline(line, counter, uids)


    print len(counter)
    print ac
    i = 0
    for kv in sorted(counter.items(), key=lambda it: it[1], reverse=True):
        print "%s \t\t\t\t %d \t\t\t\t %.2f"%(kv+(100.*kv[1]/ac,))
        i+=1
        if (i > 500): break 
    
    ## distinct chars, low performance


count("/Users/f/downloads/android_51-715_model.txt")
#count("/Users/f/downloads/android_51-715_model-.txt")
