#include "knn.h"


/*
	Update the set of k best points so far.
	Points represent instances in the kNN model.
	Those instances are the ones used in a previous learning phase.
*/
void updateBest(dtype distance, ctype classifID, dtype BestPointsDistances[K], ctype BestPointsClasses[K]) {
	dtype max = 0;
	int index =0;

	//find the worst point in the BestPoints
	for(int i=0; i<K; i++) {
		dtype dbest = BestPointsDistances[i];
		dtype max_tmp = max;
		max = (dbest > max_tmp) ? dbest : max; 
		index = (dbest > max_tmp) ? i : index;
	}

	dtype dbest = BestPointsDistances[index];
	ctype cbest = BestPointsClasses[index];
	
	BestPointsDistances[index] = (distance < max) ? distance : dbest;
	BestPointsClasses[index] = (distance < max) ? classifID : cbest;
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


	// perform the Euclidean distance between the point to classify and each one in the model
	// and update the k best points if needed
	for(int i=0; i<NUM_KNOWN_POINTS; i++) {
		dtype distance = (dtype) 0;
		
		// perform Euclidean distance
		for(int j=0; j<NUM_FEATURES; j++) {
			distance += sqr((dtype) xFeatures[j]-(dtype) knownFeatures[i][j]);
		}
		distance = sqrt(distance);
		//printf("distance %e\n", distance);
		
		// maintains the k best points updated
		updateBest(distance, knownClasses[i], BestPointsDistances, BestPointsClasses);
	}
	
		// classify the point based on the K nearest points
		ctype classifyID = classify3NN(BestPointsClasses, BestPointsDistances);
   	
   	return classifyID;
}

/**
	Classify based on the K BestPoints returned by the kNN function
	Specialized code when using K = 3
*/
ctype classify3NN(ctype BestPointsClasses[K], dtype BestPointsDistances[K]) {
		
		ctype c1 = BestPointsClasses[0];
		dtype d1 = BestPointsDistances[0];
		
		ctype c2 = BestPointsClasses[1];
		dtype d2 = BestPointsDistances[1];
		
		ctype c3 = BestPointsClasses[2];
		dtype d3 = BestPointsDistances[2];
		
		ctype classID;
		dtype mindist = d1;
		
		classID = (mindist > d2) ? c2 : c1;
		mindist = (mindist > d2) ? d2 : d1;
			
		classID = (mindist > d3) ? c3 : classID;
		mindist = (mindist > d3) ? d3 : mindist;
		
		classID = (c2 == c3) ? c2 : classID;
		classID = (c1 == c3) ? c1 : classID;
		classID = (c1 == c2) ? c1 : classID; 
		
		
	return classID;
}

