import requests
import simplejson

LINK = "http://api.locu.com/v1_0/venue/%s/?api_key=a8376a137d22578136c01795483485fedd880d60"

with open('ids.txt') as f:
    dump = open('menus.json', 'w')
    for hash_id in f:
        r = requests.get(LINK % hash_id.strip())
        json = r.json()
        if json['objects'][0]['has_menu']:
            dump.write(simplejson.dumps(json['objects'][0]))
            dump.write('\n')
    dump.close()
