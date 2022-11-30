import hashlib
import math
import time
from urllib import parse
import urllib.request
from urllib.request import Request


def calculateSig(name, cardnum, timestamp):
    sigStr = "cardNum={}driverName=webname={}privateKey=mN2pgCRI3epqXBvfcPbex1RJ7ocgSKtimestamp={}uuid=f309f260-8879-471d-a098-4acff42a3313".format(
        cardnum,
        name,
        timestamp
    )
    md5Sig = hashlib.md5(sigStr.encode('utf8')).hexdigest()[3:28]
    return hashlib.sha1(md5Sig.encode('utf8')).hexdigest()


def getTimeStamp():
    return str(math.floor(time.time()))


def queryHSData(name, cardNum):
    ts = getTimeStamp()
    sig = calculateSig(name, cardNum, ts)
    url = "https://yqpt.xa.gov.cn/prod-api/naat/open/api/getResultByCardNumAndName?name={}&cardNum={}".format(
        parse.quote(name),
        cardNum
    )

    req = Request(url=url, headers={
        "Accept": "application/json, text/javascript, */*; q=0.01",
        #"Accept-Encoding": "gzip, deflate, br",
        "Accept-Language": "zh-CN,zh;q=0.9",
        "Cache-Control": "no-cache",
        #"Connection": "keep-alive",
        #"Content-Length": "0",
        "Content-Type": "application/json; charset=UTF-8",
        "Host": "yqpt.xa.gov.cn",
        "Origin": "https://yqpt.xa.gov.cn",
        "Pragma": "no-cache",
        "Referer": "https://yqpt.xa.gov.cn/nrt/resultQuery.html",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36",
        "X-Requested-With": "XMLHttpRequest",
        "driverName": "web",
        #"sec-ch-ua": "\"Google Chrome\";v=\"107\", \"Chromium\";v=\"107\", \"Not=A?Brand\";v=\"24\"",
        #"sec-ch-ua-mobile": "?0",
        #"sec-ch-ua-platform": "\"Windows\"",
        "signature": sig,
        "timestamp": ts,
        "uuid": "f309f260-8879-471d-a098-4acff42a3313"
    }, method='POST')

    file = urllib.request.urlopen(req)
    return file.read().decode("utf-8", 'ignore')

#print(calculateSig('郑嘉乐', '610103201602293234', '1669800904'))
for x in range(1,100):
    print(queryHSData('郑嘉乐', '610103201602293234'))
