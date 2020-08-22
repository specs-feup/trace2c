#include "knn.h"

/*

                Find the set of k best points so far.
                Points represent instances in the kNN model.
                Those instances are the ones used in a previous learning phase.

*/

void findBest(dtype distances[NUM_KNOWN_POINTS], ctype knownClasses[NUM_KNOWN_POINTS], dtype BestPointsDistances[K], ctype BestPointsClasses[K])
{

    for (int j = 0; j < NUM_KNOWN_POINTS; j++)
    {
        updateBest(distances[j], knownClasses[j], BestPointsDistances, BestPointsClasses);
    }
}

/**

                kNN function without classifying but returning the k nearest points

                We use here a linear search.

*/

ctype knn(ftype xFeatures[NUM_FEATURES], ftype knownFeatures[NUM_KNOWN_POINTS][NUM_FEATURES], ctype knownClasses[NUM_KNOWN_POINTS])
{

    dtype BestPointsDistances[K]; // array with the distances of the K nearest points to the point to classify
    ctype BestPointsClasses[K]; // array with the classes of the K nearest points to the point to classify
    dtype distances[NUM_KNOWN_POINTS];
    // initialize the data structures (array) with the K best points

    //initializeBest(BestPointsClasses, BestPointsDistances);
    for(int i=0; i<K; i++) {
		BestPointsDistances[i] = MAXDISTANCE;
		BestPointsClasses[i] = NUM_CLASSES;
	}

    

    // perform the Euclidean distance between the point to classify and each one in the model

    // and update the k best points if needed


    for (int i = 0; i < NUM_KNOWN_POINTS; i++)
    {

        dtype distance = (dtype)0;


        for (int j = 0; j < NUM_FEATURES; j++)
        {

            distance += sqr((dtype)xFeatures[j] - (dtype)knownFeatures[i][j]);
        }

        distances[i] = sqrt(distance);

    }

    // maintains the k best points updated

    //findBest(all_distances, knownClasses, BestPointsDistances, BestPointsClasses);
    for (int j = 0; j < NUM_KNOWN_POINTS; j++)
    {
        updateBest(distances[j], knownClasses[j], BestPointsDistances, BestPointsClasses);
    }

    // classify the point based on the K nearest points

    ctype classifyID;

    classifyID = classify3NN(BestPointsClasses, BestPointsDistances);

    showBestPoints(BestPointsClasses, BestPointsDistances);

    return classifyID;
}