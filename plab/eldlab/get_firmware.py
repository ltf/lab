import urllib


### http://s3-us-west-2.amazonaws.com/pt-firmware/PT30-T_L66_V1.1.40.exf

def dl(url):
    secs = url.split("/")
    fn = secs[len(secs) - 1]
    return urllib.request.urlretrieve(url, fn)


def dlall():
    l = 26
    v = 25
    lastl = l
    while v > 0:
        sl = str(l)
        sv = str(v)
        try:
            print(dl("http://s3-us-west-2.amazonaws.com/pt-firmware/PT30-T_L"
                     + sl + "_V1.0." + sv + ".exf"))
            v = v - 1
            l = l - 1
            lastl = l
            print("succ: " + sv + " - " + sl)
        except:
            l = l - 1
            print("fail: " + sv + " - " + sl)
            if l == 0:
                l = lastl
                v = v -1


dlall()
