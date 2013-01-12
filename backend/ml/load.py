import csv
import random
from api.models import Rating
from time import time

def loadCsv(fileName):
    """
    Loads up a CrowdFlower CSV file and returns the item id map, the user id map, and the list of ratings
    """
    rows = [l for l in csv.reader(open(fileName, 'rb'))]    

    itemIdx = rows[0].index("id")
    userIdx = rows[0].index("_worker_id")
    answerIdx = rows[0].index("i_would_like_to_eat_this_dish")
    
    rows = [(r[itemIdx], r[userIdx], 5 if r[answerIdx] == "Yes" else 1) for r in rows[1:]]
    
    itemIdMap = uniqMap([r[0] for r in rows], 1)
    userIdMap = uniqMap([r[1] for r in rows])

    rows = [(itemIdMap[r[0]], userIdMap[r[1]], r[2]) for r in rows]

    return itemIdMap, userIdMap, rows

def loadDb():
    """
    Loads up ratings from the DB
    """
    start = time()
    ratings = Rating.objects.all().select_related()
    print "Rating.objects.all(): ", time() - start

    start = time()
    rows = [(r.menu_item.locu_id, r.user.username, r.value + random.gauss(0, 0.5)) for r in ratings]
    

    itemIdMap = uniqMap([r[0] for r in rows], 1)
    userIdMap = uniqMap([r[1] for r in rows])

    rows = [(itemIdMap[r[0]], userIdMap[r[1]], r[2]) for r in rows]

    return itemIdMap, userIdMap, rows

def uniqMap(ids, startAt=0):
    """
    Creates a map from each unique id in ids to a new id starting at startAt
    """
    ids = list(set(ids))
    return dict((ids[i], i + startAt) for i in xrange(len(ids)))
