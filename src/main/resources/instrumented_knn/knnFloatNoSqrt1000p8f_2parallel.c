#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <float.h>

#define NUM_CLASSES 2
#define MAXDISTANCE DBL_MAX
#define sqr(x) ((x) * (x))

void parallel_0(float knownFeatures[500][8], float xFeatures[8], float distance_array[500])
{
    // Step 2: Initialize local variables
    float distance_w1;
    float distance_w2;
    float distance_w3;
    float distance_w4;
    float distance_w5;
    float distance_w6;
    float distance_w7;
    float temp_l77_i1_w1;
    float temp_l77_i2_w1;
    float temp_l77_i3_w1;
    float temp_l77_i4_w1;
    float temp_l77_i5_w1;
    float temp_l77_i6_w1;
    float temp_l77_i7_w1;
    float temp_l77_i8_w1;
    // Initialization done
    // starting Loop
    for (int i = 0; i < 500; i = i + 1)
    {
#pragma HLS pipeline

        temp_l77_i1_w1 = xFeatures[0] - knownFeatures[i][0];
        temp_l77_i2_w1 = xFeatures[1] - knownFeatures[i][1];
        temp_l77_i3_w1 = xFeatures[2] - knownFeatures[i][2];
        temp_l77_i4_w1 = xFeatures[3] - knownFeatures[i][3];
        temp_l77_i5_w1 = xFeatures[4] - knownFeatures[i][4];
        temp_l77_i6_w1 = xFeatures[5] - knownFeatures[i][5];
        temp_l77_i7_w1 = xFeatures[6] - knownFeatures[i][6];
        temp_l77_i8_w1 = xFeatures[7] - knownFeatures[i][7];

        distance_w4 = 0 + sqr(temp_l77_i1_w1);

        distance_w5 = distance_w4 + sqr(temp_l77_i2_w1);

        distance_w6 = distance_w5 + sqr(temp_l77_i3_w1);

        distance_w7 = distance_w6 + sqr(temp_l77_i4_w1);

        distance_w3 = distance_w7 + sqr(temp_l77_i5_w1);

        distance_w2 = distance_w3 + sqr(temp_l77_i6_w1);

        distance_w1 = distance_w2 + sqr(temp_l77_i7_w1);

        distance_array[i] = distance_w1 + sqr(temp_l77_i8_w1);
    }
}

void epilogue(char knownClasses[1000], float distance_array_1[500], float distance_array_0[500], char *out)
{

    char BestPointsClasses[3];
    float BestPointsDistances[3];

    BestPointsDistances[0] = MAXDISTANCE;
    BestPointsDistances[2] = MAXDISTANCE;
    BestPointsDistances[1] = MAXDISTANCE;
    BestPointsClasses[1] = NUM_CLASSES;
    BestPointsClasses[0] = NUM_CLASSES;
    BestPointsClasses[2] = NUM_CLASSES;
    float max;
    int index;
    float distance;
    for (int pi = 0; pi < 500; pi++)
    {
        max = 0;
        index = 0;
        distance = distance_array_0[pi];

        //find the worst point in the BestPoints
        for (int i = 0; i < 3; i++)
        {

            if (BestPointsDistances[i] > max)
            {
                max = BestPointsDistances[i];
                index = i;
            }
        }
        // if the point is better (shorter distance) than the worst one (longer distance) in the BestPoints
        // update BestPoints substituting the wrost one

        if (distance < max)
        {
            //printf("point is better %e\n", distance);
            BestPointsDistances[index] = distance;
            BestPointsClasses[index] = knownClasses[pi];
        }
    }

    for (int pi1 = 0; pi1 < 500; pi1++)
    {
        max = 0;
        index = 0;
        distance = distance_array_1[pi1];

        //find the worst point in the BestPoints
        for (int i = 0; i < 3; i++)
        {

            if (BestPointsDistances[i] > max)
            {
                max = BestPointsDistances[i];
                index = i;
            }
        }
        // if the point is better (shorter distance) than the worst one (longer distance) in the BestPoints
        // update BestPoints substituting the wrost one

        if (distance < max)
        {
            //printf("point is better %e\n", distance);
            BestPointsDistances[index] = distance;
            BestPointsClasses[index] = knownClasses[pi1];
        }
    }

    char c1 = BestPointsClasses[0];
    float d1 = BestPointsDistances[0];

    char c2 = BestPointsClasses[1];
    float d2 = BestPointsDistances[1];

    char c3 = BestPointsClasses[2];
    float d3 = BestPointsDistances[2];

    char classID;
    float mindist = d1;

    classID = (mindist > d2) ? c2 : c1;
    mindist = (mindist > d2) ? d2 : d1;

    classID = (mindist > d3) ? c3 : classID;
    mindist = (mindist > d3) ? d3 : mindist;

    classID = (c2 == c3) ? c2 : classID;

    classID = (c1 == c3) ? c1 : classID;

    classID = (c1 == c2) ? c1 : classID;

    *out = classID;
}

void knnFloatNoSqrt1000p8f_2parallel(float xFeatures[8], char knownClasses[1000], float knownFeatures_0[500][8], float knownFeatures_1[500][8], char *out)
{
    // Step 2: Initialize local variables
    float distance_array_0[500];
    float distance_array_1[500];
#pragma HLS ARRAY_PARTITION variable = xFeatures cyclic factor = 8 dim = 1
#pragma HLS ARRAY_PARTITION variable = knownFeatures_0 cyclic factor = 4 dim = 2
#pragma HLS ARRAY_PARTITION variable = knownFeatures_1 cyclic factor = 4 dim = 2
// Initialization done
#pragma HLS dataflow

    parallel_0(knownFeatures_0, xFeatures, distance_array_0);
    parallel_0(knownFeatures_1, xFeatures, distance_array_1);

    epilogue(knownClasses, distance_array_1, distance_array_0, out);
}
