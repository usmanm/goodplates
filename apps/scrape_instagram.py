"""
Takes a file on stdin with one search term on each line.
Outputs a url to the top instagram search result on stdout.
"""
import fileinput
from instagram.client import InstagramAPI
import sys


CLIENT_ID = "4b3c44f15a754ea6b3beac97fcf3245b"
CLIENT_SECRET = "fc64271ae045455e946e2cc69970afb3"


api = InstagramAPI(client_id=CLIENT_ID, client_secret=CLIENT_SECRET)
for query in fileinput.input():
    query = query.strip('\n')
    result = api.tag_recent_media(1, 1000000000, query)
    print >> sys.stdout, result[0][0].get_standard_resolution_url()