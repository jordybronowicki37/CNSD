#!/usr/bin/env python3
"""Some description"""

import requests


def perform_post(baseurl):
    """Perform post request"""
    r = requests.post(
        baseurl,
        data={"course": "Python Training", "name": "Exercise02"},
        headers={"Content-Type": "application/x-www-form-urlencoded", "User-Agent": "Exercise/0.0.02"}
    )
    print(r.text)


if __name__ == "__main__":
    """Run this when called directly"""
    url = 'https://httpbin.org/post'

    perform_post(url)
