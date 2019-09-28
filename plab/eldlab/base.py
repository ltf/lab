#!/usr/bin/env python
# -*- coding:utf-8 -*-

import base64
import json
import ssl
import urllib.parse
import urllib.request

ssl._create_default_https_context = ssl._create_unverified_context


def make_headers():
    headers = {
        'User-Agent': 'Mozilla/5.0 Chrome/67.0.3396.99'
    }
    return headers

def sync_get(url):
    request = urllib.request.Request(url, None, make_headers())
    return urllib.request.urlopen(request).read().decode('utf-8')

