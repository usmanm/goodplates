import load
import model
import os
from datetime import datetime
import shutil
from rsvd import RSVD
import pickle

MODEL_DIR = "/tmp/model/current"

class ML:
    _model = None
    _itemMap = None
    _userMap = None

    @classmethod
    def build(cls):
        itemIdMap, userIdMap, ratings = load.loadDb()
        #itemIdMap, userIdMap, ratings = load.loadCsv("output1.csv")

        m = model.makeModel(itemIdMap, userIdMap, ratings)

        if os.path.exists(MODEL_DIR):
            shutil.copytree(MODEL_DIR, MODEL_DIR + "-" + datetime.now().isoformat())

        os.makedirs(MODEL_DIR)

        m.save(MODEL_DIR)

        itemFile = open(MODEL_DIR + "/itemMap", 'wb')
        pickle.dump(itemIdMap, itemFile)
        itemFile.close()

        userFile = open(MODEL_DIR + "/userMap", 'wb')
        pickle.dump(userIdMap, userFile)
        userFile.close()

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
    def get(cls, userId, itemId):
        if not os.path.exists(MODEL_DIR):
            return 0.5

        if not ML._model or not ML._userMap or not ML._itemMap:
            ML.loadModel()

        userId = ML._userMap[userId]
        itemId = ML._itemMap[itemId]

        return ML._model(itemId, userId)

