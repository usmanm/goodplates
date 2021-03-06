import load
import model
import os
from datetime import datetime
import shutil
from rsvd import RSVD
import pickle
import math
from time import time

MODEL_DIR = "/tmp/model/current"

class ML:
    _model = None
    _itemMap = None
    _userMap = None

    @classmethod
    def build(cls, factors=None, csvFileName=None):
        if csvFileName:
            itemIdMap, userIdMap, ratings = load.loadCsv(csvFileName)
        else:
            start = time()
            itemIdMap, userIdMap, ratings = load.loadDb()
            elapsed = time() - start
            print "loadDb: ", elapsed

        print "Number of Users: ", len(userIdMap)
        print "Number of Items: ", len(itemIdMap)

        start = time()
        m = model.makeModel(itemIdMap, userIdMap, ratings, factors=factors or int(math.ceil(math.sqrt(len(userIdMap)))))
        elapsed = time() - start
        print "makeModel(): ", elapsed

        start = time()
        if os.path.exists(MODEL_DIR):
            shutil.move(MODEL_DIR, MODEL_DIR + "-" + datetime.now().isoformat())

        os.makedirs(MODEL_DIR)

        m.save(MODEL_DIR)

        itemFile = open(MODEL_DIR + "/itemMap", 'wb')
        pickle.dump(itemIdMap, itemFile)
        itemFile.close()

        userFile = open(MODEL_DIR + "/userMap", 'wb')
        pickle.dump(userIdMap, userFile)
        userFile.close()
        elapsed = time() - start
        print "Save All: ", elapsed

    @classmethod
    def loadModel(cls):
        itemFile = open(MODEL_DIR + "/itemMap", 'rb')
        ML._itemMap = pickle.load(itemFile)
        itemFile.close()

        userFile = open(MODEL_DIR + "/userMap", 'rb')
        ML._userMap = pickle.load(userFile)
        userFile.close()

        ML._model = RSVD.load(MODEL_DIR)

    @classmethod
    def get(cls, userId, itemId=None, mapIds=True):
        if not os.path.exists(MODEL_DIR):
            return 3

        if not ML._model or not ML._userMap or not ML._itemMap:
            ML.loadModel()

        if mapIds:
            userId = ML._userMap[userId]

            if not itemId:
                return dict((itid, ML._model(ML._itemMap[itid], userId)) for itid in ML._itemMap.keys())
            else:
                itemId = ML._itemMap[itemId]
                return ML._model(itemId, userId)
    
        else:
            if not itemId:
                return dict((itid, ML._model(itid, userId)) for itid in ML._itemMap.values())
            else:
                return ML._model(itemId, userId)
