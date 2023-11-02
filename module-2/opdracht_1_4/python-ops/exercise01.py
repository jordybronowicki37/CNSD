#!/usr/bin/env python3
"""Some description"""

import requests


def perform_get(baseurl):
    """Perform get request"""
    r = requests.get(baseurl)
    print(r.text)


if __name__ == "__main__":
    """Run this when called directly"""
    url = 'https://httpbin.org/get'

    perform_get(url)
