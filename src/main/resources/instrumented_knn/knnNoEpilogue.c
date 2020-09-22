/**
	k-NN
	
	Version by Jo�o MP Cardoso
	Email: jmpc@fe.up.pt
	
	April 2020
	FEUP, Porto, Portugal
*/

#include "knn.h"

/*
	Update the set of k best points so far.
	Points represent instances in the kNN model.
	Those instances are the ones used in a previous learning phase.
*/
void updateBest(dtype distance, ctype classifID, dtype BestPointsDistances[K], ctype BestPointsClasses[K])
{
    dtype max = 0;
    int index = 0;

    //find the worst point in the BestPoints
    for (int i = 0; i < K; i++)
    {

        /*if(BestPointsDistances[i] > max) {
			max = BestPointsDistances[i];
			index = i;
		}*/
        dtype dbest = BestPointsDistances[i];
        max = (dbest > max) ? dbest : max;
        index = (dbest > max) ? i : index;
    }
    // if the point is better (shorter distance) than the worst one (longer distance) in the BestPoints
    // update BestPoints substituting the wrost one

    /*if(distance < max) {
		//printf("point is better %e\n", distance);
		BestPointsDistances[index] = distance;
		BestPointsClasses[index] = classifID;
	}*/
    dtype dbest = BestPointsDistances[index];
    ctype cbest = BestPointsClasses[index];

    BestPointsDistances[index] = (distance < max) ? distance : dbest;
    BestPointsClasses[index] = (distance < max) ? classifID : cbest;
}

/**
	kNN function without classifying but returning the k nearest points
	We use here a linear search.
*/
ctype knn(ftype xFeatures[NUM_FEATURES], ftype knownFeatures[NUM_KNOWN_POINTS][NUM_FEATURES], ctype knownClasses[NUM_KNOWN_POINTS])
{
    //---------------------
    FILE *log_file_0 = fopen("knnNoEpilogue10000p32f.dot", "w");
    int n_temp = 0;
    int ne = 0;       // edges
    int n_op = 0;     // operations
    int n_mergePoint = 0;
    int n_distance = 0;
    int n_const = 0;
    int n_assign = 0;
    int n_xFeatures[NUM_FEATURES] = {0};
    int n_knownFeatures[NUM_KNOWN_POINTS][NUM_FEATURES] = {0};
    int n_distances[NUM_KNOWN_POINTS] = {0};

    fprintf(log_file_0, "Digraph G{\n");
    for (int i = 0; i < NUM_FEATURES; i++)
    {
        n_xFeatures[i]++;
        fprintf(log_file_0, "\"xFeatures[%d]_%d_l\" [label=\"xFeatures[%d]\", att1=var, att2=param, att3=float ];\n", i, n_xFeatures[i], i);
    }
    for (int i = 0; i < NUM_KNOWN_POINTS; i++)
    {
        for (int j = 0; j < NUM_FEATURES; j++)
        {
            n_knownFeatures[NUM_KNOWN_POINTS][NUM_FEATURES]++;
            fprintf(log_file_0, "\"knownFeatures[%d][%d]_%d_l\" [label=\"knownFeatures[%d][%d]\", att1=var, att2=param, att3=float ];\n", i, j, n_knownFeatures[i][j], i, j);
        }
    }
    for (int i = 0; i < NUM_KNOWN_POINTS; i++)
    {
        n_distances[i]++;
        fprintf(log_file_0, "\"distances[%d]_%d\" [label=\"distances[%d]\", att1=var, att2=loc, att3=float];\n", i, n_distances[i], i);
    }

    n_mergePoint++;
    fprintf(log_file_0, "MergePoint_%d [label=\"MergePoint_%d\", att1=nop];\n", n_mergePoint, n_mergePoint);

    //initializeBest(BestPointsClasses, BestPointsDistances);
    dtype distances[NUM_KNOWN_POINTS];
    for (int i = 0; i < NUM_KNOWN_POINTS; i++)
    {
        n_const++;
        fprintf(log_file_0, "const%d [label=\"%d\", att1=const];\n", n_const, 0);
        n_distance++;
        fprintf(log_file_0, "\"distance_%d\" [label=\"distance\", att1=var, att2=loc, att3=float ];\n", n_distance);
        ne++;
        fprintf(log_file_0, "\"const%d\"->\"distance_%d\" [label=\"%d\", ord=\"%d\"];\n", n_const, n_distance, ne, ne);
        //---------------------
        dtype distance = (dtype)0;

        for (int j = 0; j < NUM_FEATURES; j++)
        {
            //---------------------
            n_op++;
            fprintf(log_file_0, "op%d [label=\"-\", att1=op];\n", n_op);
            n_temp++;
            fprintf(log_file_0, "temp%d [label=\"temp_l77_i%d\", att1=var, att2=loc, att3=float ];\n", n_temp, n_temp);
            ne++;
            fprintf(log_file_0, "\"xFeatures[%d]_%d_l\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", j, n_xFeatures[j], n_op, ne, ne);
            ne++;
            fprintf(log_file_0, "\"knownFeatures[%d][%d]_%d_l\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", i, j, n_knownFeatures[i][j], n_op, ne, ne);
             ne++;
            fprintf(log_file_0, "op%d->temp%d [label=\"%d\", ord=\"%d\"];\n", n_op, n_temp, ne, ne);
            n_op++;
            fprintf(log_file_0, "op%d [label=\"+\", att1=op];\n", n_op);
            ne++;
            fprintf(log_file_0, "temp%d->op%d [label=\"%d\", ord=\"%d\", pos=\"r\", mod=\"sqr(\"];\n", n_temp, n_op, ne, ne);
            ne++;
            fprintf(log_file_0, "\"distance_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_distance, n_op, ne, ne);
            n_distance++;
            fprintf(log_file_0, "\"distance_%d\" [label=\"distance\", att1=var, att2=loc, att3=float ];\n", n_distance);
            ne++;
            fprintf(log_file_0, "op%d->\"distance_%d\" [label=\"%d\", ord=\"%d\"];\n", n_op, n_distance, ne, ne);
            //---------------------
            distance += sqr((dtype)xFeatures[j] - (dtype)knownFeatures[i][j]);
        }
        n_assign++;
        fprintf(log_file_0, "Assign_%d [label=\"Assign_%d\", att1=assignment];\n", n_assign, n_assign);
        ne++;
        fprintf(log_file_0, "\"distance_%d\"->Assign_%d [label=\"%d\"];\n", n_distance, n_assign, ne);
        ne++;
        fprintf(log_file_0, "Assign_%d->\"distances[%d]_%d\" [label=\"%d\"];\n", n_assign, i, n_distances[i], ne);
        distances[i] = distance;
        fprintf(log_file_0, "\"distances[%d]_%d\"->MergePoint_%d [];\n", i, n_distances[i], n_mergePoint);
    }
    
    ne++;
    fprintf(log_file_0, "MergePoint_%d->out1 [];\n", n_mergePoint);

    //---------------------
    fprintf(log_file_0, "\"out1\" [label=\"*out\", att1=var, att2=param, att3=char ];\n");
    fprintf(log_file_0, "}\n");
    fclose(log_file_0);
    //---------------------
    return distances[0];
}

/**
	Classify based on the K BestPoints returned by the kNN function
	Specialized code when using K = 1
*/
ctype classify1NN(ctype BestPointsClasses[K])
{
    ctype classifID = BestPointsClasses[0]; // there is only one point
    return classifID;
}

/**
	Classify based on the K BestPoints returned by the kNN function
	Specialized code when using K = 3
*/
ctype classify3NN(ctype BestPointsClasses[K], dtype BestPointsDistances[K])
{

    ctype c1 = BestPointsClasses[0];
    dtype d1 = BestPointsDistances[0];

    ctype c2 = BestPointsClasses[1];
    dtype d2 = BestPointsDistances[1];

    ctype c3 = BestPointsClasses[2];
    dtype d3 = BestPointsDistances[2];

    ctype classID;

    dtype mindist = d1;
    classID = c1;

    classID = (mindist > d2) ? c2 : c1;
    mindist = (mindist > d2) ? d2 : c1;

    classID = (mindist > d3) ? c3 : c1;
    //mindist = (mindist > d3) ? d3 : c1;

    classID = (c1 == c2) ? c1 : classID;
    classID = (c1 == c3) ? c1 : classID;
    classID = (c1 == c3) ? c2 : classID;
    classID = ((c1 != c2) && (c2 != c3)) ? c2 : classID;

    return classID;
}

/**
	This function can be heavely optimized by using specialized versions according to the value of K.
	K=1, K=2, K=3, etc. Previous functions are examples of specialized code for K=1 and K=3.
*/
ctype classifyKNN(ctype BestPointsClasses[K], dtype BestPointsDistances[K])
{

    unsigned char histogram[NUM_CLASSES + 1];

    for (int i = 0; i < NUM_CLASSES + 1; i++) // last is not a class
        histogram[i] = 0;

    dtype min_distance = MAXDISTANCE;
    int index;

    for (int i = 0; i < K; i++)
    {
        dtype distance = BestPointsDistances[i];
        if (distance < min_distance)
        {
            min_distance = distance;
            index = i;
        }
        histogram[(unsigned int)BestPointsClasses[i]] += 1;
    }
    unsigned char max = 0;
    ctype classID = NUM_CLASSES; // the default is the unknwown
    for (int i = 0; i < NUM_CLASSES + 1; i++)
    {
        if (histogram[i] > max)
        {
            max = histogram[i];
            classID = i;
        }
    }
    if (max == 1)
        classID = BestPointsClasses[index];

    //printf("\n#### Final kNN classification: %s: \n", classif[index]);
    return classID;
}
