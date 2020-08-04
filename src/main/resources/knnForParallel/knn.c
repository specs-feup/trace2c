#include "knn.h"

/*

                Find the set of k best points so far.

                Points represent instances in the kNN model.

                Those instances are the ones used in a previous learning phase.

*/

void findBest(dtype distances[NUM_KNOWN_POINTS], ctype knownClasses[NUM_KNOWN_POINTS], dtype BestPointsDistances[K], ctype BestPointsClasses[K]) {

               

                for(int j=0; j< NUM_KNOWN_POINTS; j++) {        

                                updateBest(distances[j], knownClasses[j], BestPointsDistances, BestPointsClasses);

                }

}

 

 

/**

                kNN function without classifying but returning the k nearest points

                We use here a linear search.

*/

ctype knn(ftype xFeatures[NUM_FEATURES], ftype knownFeatures[NUM_KNOWN_POINTS][NUM_FEATURES], ctype knownClasses[NUM_KNOWN_POINTS]) {

 

                dtype BestPointsDistances[K]; // array with the distances of the K nearest points to the point to classify

                ctype BestPointsClasses[K]; // array with the classes of the K nearest points to the point to classify

 

 

                // initialize the data structures (array) with the K best points

                initializeBest(BestPointsClasses, BestPointsDistances);

 

                dtype all_distances[NUM_KNOWN_POINTS];

 

                // perform the Euclidean distance between the point to classify and each one in the model

                // and update the k best points if needed

                //omp_set_dynamic(0);

                #pragma omp parallel for num_threads(4) schedule(static)

                for(int i=0; i<NUM_KNOWN_POINTS; i++) {

                                dtype distance = (dtype) 0;

                               

                                // perform Euclidean distance

                                #pragma omp linear(i:1)

                                #pragma omp simd

                                for(int j=0; j<NUM_FEATURES; j++) {

                                                distance += sqr((dtype) xFeatures[j]-(dtype) knownFeatures[i][j]);

                                }

                                all_distances[i] = sqrt(distance);

                                //printf("distance %e\n", distance);

                               

                }

                // maintains the k best points updated

                findBest(all_distances, knownClasses, BestPointsDistances, BestPointsClasses);

 

               

                                // classify the point based on the K nearest points

                ctype classifyID;

                #if K == 1

                                classifyID = classify1NN(BestPointsClasses);

                #elif K == 3

                                classifyID = classify3NN(BestPointsClasses, BestPointsDistances);

                #else

                                classifyID = classifyKNN(BestPointsClasses, BestPointsDistances);

                #endif

 

                //showBestPoints(BestPointsClasses, BestPointsDistances);

               

                return classifyID;

}