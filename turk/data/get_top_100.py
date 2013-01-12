import requests

LINK = "http://www.sfgate.com/cgi-bin/listings/restaurants/listtop?cuisine=&year=2012&Submit=Nav&o=%s"

names = []

for i in xrange(0, 100, 10):
    r = requests.get(LINK % i)
    anchors = (line.strip() for line in r.text.split('\n') if "/cgi-bin/listings/restaurants/venuetop" in line and not "Chronicle Review" in line and not "Write a Review" in line)
    names.extend((anchor[anchor.find('>')+1:-4] for anchor in anchors))

with open('names.txt', 'w') as f:
    for name in names:
        f.write(name)
        f.write('\n')
