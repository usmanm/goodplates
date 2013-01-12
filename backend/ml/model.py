import numpy as np
from rsvd import rating_t, RSVD

def makeModel(itemIdMap, userIdMap, ratings, factors=20, learnRate=0.0005, regularization=0.005):
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
                learnRate=learnRate, regularization=regularization)

    sqerr=0.0
    for itemID,userID,rating in test:
        err = rating - model(itemID,userID)
        sqerr += err * err
    sqerr /= test.shape[0]
    print "Test RMSE: ", np.sqrt(sqerr)

    return model