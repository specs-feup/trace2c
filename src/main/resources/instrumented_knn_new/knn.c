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
    FILE *log_file_0 = fopen("knn.dot", "w");
    int n_const = 0;
    int n_temp = 0;
    int ne = 0;
    int n_op = 0;
    int n_mux = 0;

    int n_xFeatures[NUM_FEATURES] = {0};
    int n_knownFeatures[NUM_KNOWN_POINTS][NUM_FEATURES] = {0};
    int n_knownClasses[NUM_KNOWN_POINTS] = {0};
    int n_BestPointsDistances[K] = {0};
    int n_BestPointsClasses[K] = {0};
    int n_distance = 0;
    int n_max_tmp = 0;
    int n_max = 0;
    int n_index = 0;
    int n_dbest = 0;
    int n_cbest = 0;
    int n_classID = 0;
    int n_c1 = 0;
    int n_d1 = 0;
    int n_c2 = 0;
    int n_d2 = 0;
    int n_c3 = 0;
    int n_d3 = 0;
    int n_mindist = 0;

    fprintf(log_file_0, "Digraph G{\n");
    for (int i = 0; i < NUM_FEATURES; i++)
    {
        n_xFeatures[i]++;
        fprintf(log_file_0, "\"xFeatures[%d]_%d_l\" [label=\"xFeatures[%d]\", att1=var, att2=inte, att3=double ];\n", i, n_xFeatures[i], i);
    }
    for (int i = 0; i < NUM_KNOWN_POINTS; i++)
    {
        for (int j = 0; j < NUM_FEATURES; j++)
        {
            n_knownFeatures[NUM_KNOWN_POINTS][NUM_FEATURES]++;
            fprintf(log_file_0, "\"knownFeatures[%d][%d]_%d_l\" [label=\"knownFeatures[%d][%d]\", att1=var, att2=inte, att3=double ];\n", i, j, n_knownFeatures[i][j], i, j);
        }
    }
    for (int i = 0; i < NUM_KNOWN_POINTS; i++)
    {
        n_knownClasses[i]++;
        fprintf(log_file_0, "\"knownClasses[%d]_%d_l\" [label=\"knownClasses[%d]\", att1=var, att2=inte, att3=double ];\n", i, n_knownClasses[i], i);
    }

    dtype BestPointsDistances[K];
    ctype BestPointsClasses[K];

    for (int i = 0; i < K; i++)
    {
        //---------------------
        n_const++;
        fprintf(log_file_0, "const%d [label=\"%d\", att1=const];\n", n_const, MAXDISTANCE);
        n_BestPointsDistances[i]++;
        fprintf(log_file_0, "\"BestPointsDistances[%d]_%d_l\" [label=\"BestPointsDistances[%d]\", att1=var, att2=loc, att3=double ];\n", i, n_BestPointsDistances[i], i);
        ne++;
        fprintf(log_file_0, "const%d->\"BestPointsDistances[%d]_%d_l\" [label=\"%d\", ord=\"%d\"];\n", n_const, i, n_BestPointsDistances[i], ne, ne);
        //---------------------
        BestPointsDistances[i] = MAXDISTANCE;

        //---------------------
        n_const++;
        fprintf(log_file_0, "const%d [label=\"%d\", att1=const];\n", n_const, NUM_CLASSES);
        n_BestPointsClasses[i]++;
        fprintf(log_file_0, "\"BestPointsClasses[%d]_%d_l\" [label=\"BestPointsClasses[%d]\", att1=var, att2=loc, att3=char ];\n", i, n_BestPointsClasses[i], i);
        ne++;
        fprintf(log_file_0, "const%d->\"BestPointsClasses[%d]_%d_l\" [label=\"%d\", ord=\"%d\"];\n", n_const, i, n_BestPointsClasses[i], ne, ne);
        //---------------------
        BestPointsClasses[i] = NUM_CLASSES; // unknown
    }

    //initializeBest(BestPointsClasses, BestPointsDistances);

    for (int i = 0; i < NUM_KNOWN_POINTS; i++)
    {
        //---------------------
        n_const++;
        fprintf(log_file_0, "const%d [label=\"%d\", att1=const];\n", n_const, 0);
        n_distance++;
        fprintf(log_file_0, "\"distance_%d\" [label=\"distance\", att1=var, att2=loc, att3=double ];\n", n_distance);
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
            fprintf(log_file_0, "temp%d->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_temp, n_op, ne);
            ne++;
            fprintf(log_file_0, "distance_%d->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_distance, n_op, ne);
            n_distance++;
            fprintf(log_file_0, "\"distance_%d\" [label=distance, att1=var, att2=loc, att3=float ];\n", n_distance);
            ne++;
            fprintf(log_file_0, "op%d->distance_%d [label=\"%d\", ord=\"%d\", mod=\"sqr(\"];\n", n_op, n_distance, ne);
            //---------------------
            distance += sqr((dtype)xFeatures[j] - (dtype)knownFeatures[i][j]);
        }
        //---------------------
        n_distance++;
        fprintf(log_file_0, "\"distance_%d\" [label=\"distance\", att1=var, att2=loc, att3=double ];\n", n_distance);
        ne++;
        fprintf(log_file_0, "\"distance_%d\"->\"distance_%d\" [label=\"%d\", ord=\"%d\", mod=\"sqrt(\"];\n", n_distance - 1, n_distance, ne, ne);
        //---------------------
        distance = sqrt(distance);

        //---------------------
        n_const++;
        fprintf(log_file_0, "const%d [label=\"%d\", att1=const];\n", n_const, 0);
        n_max++;
        fprintf(log_file_0, "\"max_%d\" [label=\"max\", att1=var, att2=loc, att3=double ];\n", n_max);
        ne++;
        fprintf(log_file_0, "\"const%d\"->\"max_%d\" [label=\"%d\", ord=\"%d\"];\n", n_const, n_max, ne, ne);
        //---------------------
        dtype max = 0;

        //---------------------
        n_const++;
        fprintf(log_file_0, "const%d [label=\"%d\", att1=const];\n", n_const, 0);
        n_index++;
        fprintf(log_file_0, "\"index_%d\" [label=\"index\", att1=var, att2=loc, att3=double ];\n", n_index);
        ne++;
        fprintf(log_file_0, "\"const%d\"->\"index_%d\" [label=\"%d\", ord=\"%d\"];\n", n_const, n_index, ne, ne);
        //---------------------
        int index = 0;

        for (int i = 0; i < K; i++)
        {
            //---------------------
            n_dbest++;
            fprintf(log_file_0, "\"dbest_%d\" [label=\"dbest\", att1=var, att2=loc, att3=float ];\n", n_dbest);
            ne++;
            fprintf(log_file_0, "\"BestPointsDistances[%d]_%d_l\"->\"dbest_%d\" [label=\"%d\", ord=\"%d\"];\n", i, n_BestPointsDistances[i], n_dbest, ne, ne);
            //---------------------
            dtype dbest = BestPointsDistances[i];

            //---------------------

            //---------------------
            dtype max_tmp = max;

            //---------------------
            n_op++;
            fprintf(log_file_0, "op%d [label=\">\", att1=op];\n", n_op);
            ne++;
            fprintf(log_file_0, "\"dbest_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_dbest, n_op, ne, ne);
            ne++;
            fprintf(log_file_0, "\"max_tmp%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_max_tmp, n_op, ne, ne);
            n_mux++;
            fprintf(log_file_0, "mux%d [label=mux%d, att1=mux, att2=op%d];\n", n_mux, n_mux, n_op);
            ne++;
            fprintf(log_file_0, "\"op%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);
            ne++;
            fprintf(log_file_0, "\"dbest_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", n_dbest, n_mux, ne, ne);
            ne++;
            fprintf(log_file_0, "\"max_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_max, n_mux, ne, ne);
            n_max++;
            fprintf(log_file_0, "\"max_%d\" [label=\"max\", att1=var, att2=loc, att3=float ];\n", n_max);
            ne++;
            fprintf(log_file_0, "\"mux%d\"->max_%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mux, n_max, ne, ne);
            //---------------------
            max = (dbest > max_tmp) ? dbest : max;

            //---------------------
            n_op++;
            fprintf(log_file_0, "op%d [label=\">\", att1=op];\n", n_op);
            ne++;
            fprintf(log_file_0, "\"dbest_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_dbest, n_op, ne, ne);
            ne++;
            fprintf(log_file_0, "\"max_tmp%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_max_tmp, n_op, ne, ne);
            n_mux++;
            fprintf(log_file_0, "mux%d [label=mux%d, att1=mux, att2=op%d];\n", n_mux, n_mux, n_op);
            ne++;
            fprintf(log_file_0, "\"op%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);
            n_const++;
            fprintf(log_file_0, "const%d [label=\"%d\", att1=const];\n", n_const, i);
            ne++;
            fprintf(log_file_0, "\"const%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", n_const, n_mux, ne, ne);
            ne++;
            fprintf(log_file_0, "\"index_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_index, n_mux, ne, ne);
            n_index++;
            fprintf(log_file_0, "\"index_%d\" [label=\"index\", att1=var, att2=loc, att3=int ];\n", n_index);
            ne++;
            fprintf(log_file_0, "\"mux%d\"->index_%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mux, n_index, ne, ne);
            //---------------------
            index = (dbest > max_tmp) ? i : index;
        }

        //---------------------
        n_dbest++;
        fprintf(log_file_0, "\"dbest_%d\" [label=\"dbest\", att1=var, att2=loc, att3=double ];\n", n_dbest);
        ne++;
        fprintf(log_file_0, "\"BestPointsDistances[%d]_%d_l\"->\"dbest_%d\" [label=\"%d\", ord=\"%d\"];\n", index, n_BestPointsDistances[index], n_dbest, ne, ne);
        //---------------------
        dtype dbest = BestPointsDistances[index];

        //---------------------
        n_cbest++;
        fprintf(log_file_0, "\"cbest_%d\" [label=\"cbest\", att1=var, att2=loc, att3=char ];\n", n_cbest);
        ne++;
        fprintf(log_file_0, "\"BestPointsClasses[%d]_%d_l\"->\"cbest_%d\" [label=\"%d\", ord=\"%d\"];\n", index, n_BestPointsClasses[index], n_cbest, ne, ne);
        //---------------------
        ctype cbest = BestPointsClasses[index];

        //---------------------
        n_op++;
        fprintf(log_file_0, "op%d [label=\">\", att1=op];\n", n_op);
        ne++;
        fprintf(log_file_0, "\"distance_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_distance, n_op, ne, ne);
        ne++;
        fprintf(log_file_0, "\"max_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_max, n_op, ne, ne);
        n_mux++;
        fprintf(log_file_0, "mux%d [label=mux%d, att1=mux, att2=op%d];\n", n_mux, n_mux, n_op);
        ne++;
        fprintf(log_file_0, "\"op%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);
        ne++;
        fprintf(log_file_0, "\"distance_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", n_distance, n_mux, ne, ne);
        ne++;
        fprintf(log_file_0, "\"dbest_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_dbest, n_mux, ne, ne);
        n_BestPointsDistances[index]++;
        fprintf(log_file_0, "\"BestPointsDistances[%d]_%d_l\" [label=\"BestPointsDistances[%d]\", att1=var, att2=loc, att3=double ];\n", index, n_BestPointsDistances[index], index);
        ne++;
        fprintf(log_file_0, "\"mux%d\"->\"BestPointsDistances[%d]_%d_l\" [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mux, index, n_BestPointsDistances[index], ne, ne);
        //---------------------
        BestPointsDistances[index] = (distance < max) ? distance : dbest;

        //---------------------
        n_op++;
        fprintf(log_file_0, "op%d [label=\">\", att1=op];\n", n_op);
        ne++;
        fprintf(log_file_0, "\"distance_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_distance, n_op, ne, ne);
        ne++;
        fprintf(log_file_0, "\"max_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_max, n_op, ne, ne);
        n_mux++;
        fprintf(log_file_0, "mux%d [label=mux%d, att1=mux, att2=op%d];\n", n_mux, n_mux, n_op);
        ne++;
        fprintf(log_file_0, "\"op%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);
        ne++;
        fprintf(log_file_0, "\"knownClasses[%d]_%d_l\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", i, n_knownClasses[i], n_mux, ne, ne);
        ne++;
        fprintf(log_file_0, "\"cbest_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_cbest, n_mux, ne, ne);
        n_BestPointsClasses[index]++;
        fprintf(log_file_0, "\"BestPointsClasses[%d]_%d_l\" [label=\"BestPointsClasses[%d]\", att1=var, att2=loc, att3=double ];\n", index, n_BestPointsClasses[index], index);
        ne++;
        fprintf(log_file_0, "\"mux%d\"->\"BestPointsClasses[%d]_%d_l\" [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mux, index, n_BestPointsClasses[index], ne, ne);
        //---------------------
        BestPointsClasses[index] = (distance < max) ? knownClasses[i] : cbest;
    }

    ctype classID;
#if K == 1
    classID = classify1NN(BestPointsClasses);
#elif K == 3
    //---------------------
    n_c1++;
    fprintf(log_file_0, "\"c1_%d\" [label=\"c1\", att1=var, att2=loc, att3=char ];\n", n_c1);
    ne++;
    fprintf(log_file_0, "\"BestPointsClasses[%d]_%d_l\"->\"c1_%d\" [label=\"%d\", ord=\"%d\"];\n", 0, n_BestPointsClasses[0], n_c1, ne, ne);
    //---------------------
    ctype c1 = BestPointsClasses[0];

    //---------------------
    n_d1++;
    fprintf(log_file_0, "\"d1_%d\" [label=\"d1\", att1=var, att2=loc, att3=double ];\n", n_d1);
    ne++;
    fprintf(log_file_0, "\"BestPointsDistances[%d]_%d_l\"->\"d1_%d\" [label=\"%d\", ord=\"%d\"];\n", 0, n_BestPointsDistances[0], n_d1, ne, ne);
    //---------------------
    dtype d1 = BestPointsDistances[0];

    //---------------------
    n_c2++;
    fprintf(log_file_0, "\"c2_%d\" [label=\"c2\", att1=var, att2=loc, att3=char ];\n", n_c2);
    ne++;
    fprintf(log_file_0, "\"BestPointsClasses[%d]_%d_l\"->\"c2_%d\" [label=\"%d\", ord=\"%d\"];\n", 1, n_BestPointsClasses[1], n_c2, ne, ne);
    //---------------------
    ctype c2 = BestPointsClasses[1];

    //---------------------
    n_d2++;
    fprintf(log_file_0, "\"d2_%d\" [label=\"d2\", att1=var, att2=loc, att3=double ];\n", n_d2);
    ne++;
    fprintf(log_file_0, "\"BestPointsDistances[%d]_%d_l\"->\"d2_%d\" [label=\"%d\", ord=\"%d\"];\n", 1, n_BestPointsDistances[1], n_d2, ne, ne);
    //---------------------
    dtype d2 = BestPointsDistances[1];

    //---------------------
    n_c3++;
    fprintf(log_file_0, "\"c3_%d\" [label=\"c3\", att1=var, att2=loc, att3=char ];\n", n_c3);
    ne++;
    fprintf(log_file_0, "\"BestPointsClasses[%d]_%d_l\"->\"c3_%d\" [label=\"%d\", ord=\"%d\"];\n", 2, n_BestPointsClasses[2], n_c3, ne, ne);
    //---------------------
    ctype c3 = BestPointsClasses[2];

    //---------------------
    n_d3++;
    fprintf(log_file_0, "\"d3_%d\" [label=\"d3\", att1=var, att2=loc, att3=double ];\n", n_d3);
    ne++;
    fprintf(log_file_0, "\"BestPointsDistances[%d]_%d_l\"->\"d3_%d\" [label=\"%d\", ord=\"%d\"];\n", 2, n_BestPointsDistances[2], n_d3, ne, ne);
    //---------------------
    dtype d3 = BestPointsDistances[2];

    //---------------------
    n_mindist++;
    fprintf(log_file_0, "\"mindist_%d\" [label=\"mindist\", att1=var, att2=loc, att3=double ];\n", n_d3);
    ne++;
    fprintf(log_file_0, "\"d1_%d\"->\"mindist_%d\" [label=\"%d\", ord=\"%d\"];\n", n_d1, n_mindist, ne, ne);
    //---------------------
    dtype mindist = d1;

    //---------------------
    n_op++;
    fprintf(log_file_0, "op%d [label=\">\", att1=op];\n", n_op);
    ne++;
    fprintf(log_file_0, "\"mindist_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mindist, n_op, ne, ne);
    ne++;
    fprintf(log_file_0, "\"d2_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_d2, n_op, ne, ne);
    n_mux++;
    fprintf(log_file_0, "mux%d [label=mux%d, att1=mux, att2=op%d];\n", n_mux, n_mux, n_op);
    ne++;
    fprintf(log_file_0, "\"op%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"c2_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", n_c2, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"c1_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_c1, n_mux, ne, ne);
    n_classID++;
    fprintf(log_file_0, "\"classID_%d\" [label=\"classID\", att1=var, att2=loc, att3=char ];\n", n_classID);
    ne++;
    fprintf(log_file_0, "\"mux%d\"->classID_%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mux, n_classID, ne, ne);
    //---------------------
    classID = (mindist > d2) ? c2 : c1;

    //---------------------
    n_op++;
    fprintf(log_file_0, "op%d [label=\">\", att1=op];\n", n_op);
    ne++;
    fprintf(log_file_0, "\"mindist_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mindist, n_op, ne, ne);
    ne++;
    fprintf(log_file_0, "\"d2_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_d2, n_op, ne, ne);
    n_mux++;
    fprintf(log_file_0, "mux%d [label=mux%d, att1=mux, att2=op%d];\n", n_mux, n_mux, n_op);
    ne++;
    fprintf(log_file_0, "\"op%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"d2_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", n_d2, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"d1_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_d1, n_mux, ne, ne);
    n_mindist++;
    fprintf(log_file_0, "\"mindist_%d\" [label=\"mindist\", att1=var, att2=loc, att3=double ];\n", n_mindist);
    ne++;
    fprintf(log_file_0, "\"mux%d\"->mindist_%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mux, n_mindist, ne, ne);
    //---------------------
    mindist = (mindist > d2) ? d2 : d1;

    //---------------------
    n_op++;
    fprintf(log_file_0, "op%d [label=\">\", att1=op];\n", n_op);
    ne++;
    fprintf(log_file_0, "\"mindist_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mindist, n_op, ne, ne);
    ne++;
    fprintf(log_file_0, "\"d3_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_d3, n_op, ne, ne);
    n_mux++;
    fprintf(log_file_0, "mux%d [label=mux%d, att1=mux, att2=op%d];\n", n_mux, n_mux, n_op);
    ne++;
    fprintf(log_file_0, "\"op%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"c3_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", n_c3, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"classID_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_classID, n_mux, ne, ne);
    n_classID++;
    fprintf(log_file_0, "\"classID_%d\" [label=\"classID\", att1=var, att2=loc, att3=char ];\n", n_classID);
    ne++;
    fprintf(log_file_0, "\"mux%d\"->classID_%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mux, n_classID, ne, ne);
    //---------------------
    classID = (mindist > d3) ? c3 : classID;

    //---------------------
    n_op++;
    fprintf(log_file_0, "op%d [label=\">\", att1=op];\n", n_op);
    ne++;
    fprintf(log_file_0, "\"mindist_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mindist, n_op, ne, ne);
    ne++;
    fprintf(log_file_0, "\"d3_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_d3, n_op, ne, ne);
    n_mux++;
    fprintf(log_file_0, "mux%d [label=mux%d, att1=mux, att2=op%d];\n", n_mux, n_mux, n_op);
    ne++;
    fprintf(log_file_0, "\"op%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"d3_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", n_d3, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"mindist_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_mindist, n_mux, ne, ne);
    n_mindist++;
    fprintf(log_file_0, "\"mindist_%d\" [label=\"mindist\", att1=var, att2=loc, att3=char ];\n", n_mindist);
    ne++;
    fprintf(log_file_0, "\"mux%d\"->mindist_%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mux, n_mindist, ne, ne);
    //---------------------
    mindist = (mindist > d3) ? d3 : mindist;

    //---------------------
    n_op++;
    fprintf(log_file_0, "op%d [label=\"=\", att1=op];\n", n_op);
    ne++;
    fprintf(log_file_0, "\"c2_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_c2, n_op, ne, ne);
    ne++;
    fprintf(log_file_0, "\"c3_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_c3, n_op, ne, ne);
    n_mux++;
    fprintf(log_file_0, "mux%d [label=mux%d, att1=mux, att2=op%d];\n", n_mux, n_mux, n_op);
    ne++;
    fprintf(log_file_0, "\"op%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"c2_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", n_c2, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"classID_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_classID, n_mux, ne, ne);
    n_classID++;
    fprintf(log_file_0, "\"classID_%d\" [label=\"classID\", att1=var, att2=loc, att3=char ];\n", n_classID);
    ne++;
    fprintf(log_file_0, "\"mux%d\"->classID_%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mux, n_classID, ne, ne);
    //---------------------
    classID = (c2 == c3) ? c2 : classID;

    //---------------------
    n_op++;
    fprintf(log_file_0, "op%d [label=\"=\", att1=op];\n", n_op);
    ne++;
    fprintf(log_file_0, "\"c1_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_c1, n_op, ne, ne);
    ne++;
    fprintf(log_file_0, "\"c3_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_c3, n_op, ne, ne);
    n_mux++;
    fprintf(log_file_0, "mux%d [label=mux%d, att1=mux, att2=op%d];\n", n_mux, n_mux, n_op);
    ne++;
    fprintf(log_file_0, "\"op%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"c1_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", n_c1, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"classID_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_classID, n_mux, ne, ne);
    n_classID++;
    fprintf(log_file_0, "\"classID_%d\" [label=\"classID\", att1=var, att2=loc, att3=char ];\n", n_classID);
    ne++;
    fprintf(log_file_0, "\"mux%d\"->classID_%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mux, n_classID, ne, ne);
    //---------------------
    classID = (c1 == c3) ? c1 : classID;

    //---------------------
    n_op++;
    fprintf(log_file_0, "op%d [label=\"=\", att1=op];\n", n_op);
    ne++;
    fprintf(log_file_0, "\"c1_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_c1, n_op, ne, ne);
    ne++;
    fprintf(log_file_0, "\"c2_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_c2, n_op, ne, ne);
    n_mux++;
    fprintf(log_file_0, "mux%d [label=mux%d, att1=mux, att2=op%d];\n", n_mux, n_mux, n_op);
    ne++;
    fprintf(log_file_0, "\"op%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"c1_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", n_c1, n_mux, ne, ne);
    ne++;
    fprintf(log_file_0, "\"classID_%d\"->mux%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_classID, n_mux, ne, ne);
    n_classID++;
    fprintf(log_file_0, "\"classID_%d\" [label=\"classID\", att1=var, att2=loc, att3=char ];\n", n_classID);
    ne++;
    fprintf(log_file_0, "\"mux%d\"->classID_%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_mux, n_classID, ne, ne);
    //---------------------
    classID = (c1 == c2) ? c1 : classID;
#else
    classID = classifyKNN(BestPointsClasses, BestPointsDistances);
#endif

    showBestPoints(BestPointsClasses, BestPointsDistances);

    //---------------------
    fprintf(log_file_0, "\"out1\" [label=\"out\", att1=var, att2=loc, att3=char ];\n");
    ne++;
    fprintf(log_file_0, "\"classID_%d\"->\"out1\" [label=\"%d\", ord=\"%d\"];\n", n_classID, ne, ne);
    fprintf(log_file_0, "}\n");
    fclose(log_file_0);
    //---------------------
    return classID;
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
