import numpy as np
from rsvd import rating_t, RSVD

def makeModel(itemIdMap, userIdMap, ratings, factors=20, learnRate=0.001, regularization=0.011):
    """
    Makes a RSVD model from ratings
    """
    ratings = np.array(ratings, rating_t)
    np.random.shuffle(ratings)

    n = int(ratings.shape[0] * 0.8)
    train = ratings[:n]
    test = ratings[n:]
    v = int(train.shape[0] * 0.9)
    val = train[v:]
    train = train[:v]

    dims = (len(itemIdMap), len(userIdMap))

    model = RSVD.train(factors, train, dims, probeArray=val,
                learnRate=learnRate, regularization=regularization,
                maxEpochs=1000)

    sqerr=0.0
    for itemID,userID,rating in test:
        err = rating - model(itemID,userID)
        sqerr += err * err
    sqerr /= test.shape[0]
    print "Test RMSE: ", np.sqrt(sqerr)

    return model

def tableFromModel(model, itemIdMap, userIdMap, orig_keys=False):
    itemIds = itemIdMap.values()
    userIds = userIdMap.values()

    revUser = reverseMap(userIdMap)
    revItem = reverseMap(itemIdMap)

    table = dict((revUser[userID] if orig_keys else userID, dict((revItem[itemID] if orig_keys else itemID, model(itemID, userID)) for itemID in itemIds)) for userID in userIds)

    return table

def csvFromModel(model, itemIdMap, userIdMap):
    table = tableFromModel(model, itemIdMap, userIdMap, True)

    itemIds = sorted(itemIdMap.keys())
    userIds = sorted(userIdMap.keys())

    # revUser = reverseMap(userIdMap)
    # revItem = reverseMap(itemIdMap)

    #print itemIds
    lines = [[""] + map(str, itemIds)]

    lines += [map(str, [userId] + [table[userId][itemId] for itemId in itemIds]) for userId in userIds]

    #return "\n".join([",".join(line) for line in lines])
    return lines

def reverseMap(m):
    return dict((v, k) for k, v in m.items())

