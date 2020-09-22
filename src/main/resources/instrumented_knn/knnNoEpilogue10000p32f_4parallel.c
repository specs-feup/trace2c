#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <float.h>

#define NUM_CLASSES 2
#define MAXDISTANCE DBL_MAX
#define sqr(x) ((x) * (x))

void parallel_0(float knownFeatures[2500][32], float xFeatures[32], float distances0_array[2500])
{
    // Step 2: Initialize local variables
    float distance_w1;
    float distance_w10;
    float distance_w11;
    float distance_w12;
    float distance_w13;
    float distance_w14;
    float distance_w15;
    float distance_w16;
    float distance_w17;
    float distance_w18;
    float distance_w19;
    float distance_w2;
    float distance_w20;
    float distance_w21;
    float distance_w22;
    float distance_w23;
    float distance_w24;
    float distance_w25;
    float distance_w26;
    float distance_w27;
    float distance_w28;
    float distance_w29;
    float distance_w3;
    float distance_w30;
    float distance_w31;
    float distance_w32;
    float distance_w4;
    float distance_w5;
    float distance_w6;
    float distance_w7;
    float distance_w8;
    float distance_w9;
    float temp_l77_i10_w1;
    float temp_l77_i11_w1;
    float temp_l77_i12_w1;
    float temp_l77_i13_w1;
    float temp_l77_i14_w1;
    float temp_l77_i15_w1;
    float temp_l77_i16_w1;
    float temp_l77_i17_w1;
    float temp_l77_i18_w1;
    float temp_l77_i19_w1;
    float temp_l77_i1_w1;
    float temp_l77_i20_w1;
    float temp_l77_i21_w1;
    float temp_l77_i22_w1;
    float temp_l77_i23_w1;
    float temp_l77_i24_w1;
    float temp_l77_i25_w1;
    float temp_l77_i26_w1;
    float temp_l77_i27_w1;
    float temp_l77_i28_w1;
    float temp_l77_i29_w1;
    float temp_l77_i2_w1;
    float temp_l77_i30_w1;
    float temp_l77_i31_w1;
    float temp_l77_i32_w1;
    float temp_l77_i3_w1;
    float temp_l77_i4_w1;
    float temp_l77_i5_w1;
    float temp_l77_i6_w1;
    float temp_l77_i7_w1;
    float temp_l77_i8_w1;
    float temp_l77_i9_w1;
    // Initialization done
    // starting Loop
    for (int i = 0; i < 2500; i = i + 1)
    {
#pragma HLS pipeline II=6

        temp_l77_i1_w1 = xFeatures[0] - knownFeatures[i][0];
        temp_l77_i2_w1 = xFeatures[1] - knownFeatures[i][1];
        temp_l77_i3_w1 = xFeatures[2] - knownFeatures[i][2];
        temp_l77_i4_w1 = xFeatures[3] - knownFeatures[i][3];
        temp_l77_i5_w1 = xFeatures[4] - knownFeatures[i][4];
        temp_l77_i6_w1 = xFeatures[5] - knownFeatures[i][5];
        temp_l77_i7_w1 = xFeatures[6] - knownFeatures[i][6];
        temp_l77_i8_w1 = xFeatures[7] - knownFeatures[i][7];
        temp_l77_i9_w1 = xFeatures[8] - knownFeatures[i][8];
        temp_l77_i10_w1 = xFeatures[9] - knownFeatures[i][9];
        temp_l77_i11_w1 = xFeatures[10] - knownFeatures[i][10];
        temp_l77_i12_w1 = xFeatures[11] - knownFeatures[i][11];
        temp_l77_i13_w1 = xFeatures[12] - knownFeatures[i][12];
        temp_l77_i14_w1 = xFeatures[13] - knownFeatures[i][13];
        temp_l77_i15_w1 = xFeatures[14] - knownFeatures[i][14];
        temp_l77_i16_w1 = xFeatures[15] - knownFeatures[i][15];
        temp_l77_i17_w1 = xFeatures[16] - knownFeatures[i][16];
        temp_l77_i18_w1 = xFeatures[17] - knownFeatures[i][17];
        temp_l77_i19_w1 = xFeatures[18] - knownFeatures[i][18];
        temp_l77_i20_w1 = xFeatures[19] - knownFeatures[i][19];
        temp_l77_i21_w1 = xFeatures[20] - knownFeatures[i][20];
        temp_l77_i22_w1 = xFeatures[21] - knownFeatures[i][21];
        temp_l77_i23_w1 = xFeatures[22] - knownFeatures[i][22];
        temp_l77_i24_w1 = xFeatures[23] - knownFeatures[i][23];
        temp_l77_i25_w1 = xFeatures[24] - knownFeatures[i][24];
        temp_l77_i26_w1 = xFeatures[25] - knownFeatures[i][25];
        temp_l77_i27_w1 = xFeatures[26] - knownFeatures[i][26];
        temp_l77_i28_w1 = xFeatures[27] - knownFeatures[i][27];
        temp_l77_i29_w1 = xFeatures[28] - knownFeatures[i][28];
        temp_l77_i30_w1 = xFeatures[29] - knownFeatures[i][29];
        temp_l77_i31_w1 = xFeatures[30] - knownFeatures[i][30];
        temp_l77_i32_w1 = xFeatures[31] - knownFeatures[i][31];

        distance_w31 = sqr(temp_l77_i22_w1) + sqr(temp_l77_i23_w1);
        distance_w23 = sqr(temp_l77_i26_w1) + sqr(temp_l77_i27_w1);
        distance_w10 = sqr(temp_l77_i12_w1) + sqr(temp_l77_i13_w1);
        distance_w15 = sqr(temp_l77_i14_w1) + sqr(temp_l77_i15_w1);
        distance_w8 = sqr(temp_l77_i16_w1) + sqr(temp_l77_i17_w1);
        distance_w13 = 0 + sqr(temp_l77_i1_w1);
        distance_w30 = sqr(temp_l77_i20_w1) + sqr(temp_l77_i21_w1);
        distance_w17 = sqr(temp_l77_i24_w1) + sqr(temp_l77_i25_w1);
        distance_w12 = sqr(temp_l77_i18_w1) + sqr(temp_l77_i19_w1);
        distance_w3 = sqr(temp_l77_i28_w1) + sqr(temp_l77_i29_w1);
        distance_w20 = sqr(temp_l77_i30_w1) + sqr(temp_l77_i31_w1);
        distance_w27 = sqr(temp_l77_i10_w1) + sqr(temp_l77_i11_w1);

        distance_w28 = distance_w13 + sqr(temp_l77_i2_w1);
        distance_w11 = distance_w12 + distance_w30;
        distance_w22 = distance_w15 + distance_w8;
        distance_w4 = distance_w23 + distance_w3;
        distance_w14 = distance_w27 + distance_w10;
        distance_w18 = distance_w31 + distance_w17;

        distance_w26 = distance_w28 + sqr(temp_l77_i3_w1);
        distance_w32 = distance_w11 + distance_w18;
        distance_w9 = distance_w14 + distance_w22;
        distance_w21 = distance_w4 + distance_w20;

        distance_w25 = distance_w26 + sqr(temp_l77_i4_w1);
        distance_w7 = distance_w9 + distance_w32;

        distance_w24 = distance_w25 + sqr(temp_l77_i5_w1);
        distance_w19 = distance_w7 + distance_w21;

        distance_w6 = distance_w24 + sqr(temp_l77_i6_w1);

        distance_w5 = distance_w6 + sqr(temp_l77_i7_w1);

        distance_w2 = distance_w5 + sqr(temp_l77_i8_w1);

        distance_w1 = distance_w2 + sqr(temp_l77_i9_w1);

        distance_w29 = distance_w1 + distance_w19;

        distance_w16 = distance_w29 + sqr(temp_l77_i32_w1);

        distances0_array[i] = distance_w16;
    }
}

void epilogue_0(char knownClasses[2500], float distance_array[2500], float BPD[3], char BPC[3])
{
    // Step 2: Initialize local variables
    char BestPointsClasses[3];
    float BestPointsDistances[3];
#pragma HLS ARRAY_PARTITION variable = BestPointsClasses complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BestPointsDistances complete dim = 1

    BestPointsDistances[0] = MAXDISTANCE;
    BestPointsDistances[2] = MAXDISTANCE;
    BestPointsDistances[1] = MAXDISTANCE;
    BestPointsClasses[1] = NUM_CLASSES;
    BestPointsClasses[0] = NUM_CLASSES;
    BestPointsClasses[2] = NUM_CLASSES;
    float max;
    int index;
    float distance;
    float dbest;
    float max_tmp;
    char cbest;
    for (int pi0 = 0; pi0 < 2500; pi0++)
    {
#pragma HLS PIPELINE
        max = 0;
        index = 0;
        distance = distance_array[pi0];

        //find the worst point in the BestPoints
        for (int i = 0; i < 3; i++)
        {
            dbest = BestPointsDistances[i];
            max_tmp = max;
            max = (dbest > max_tmp) ? dbest : max;
            index = (dbest > max_tmp) ? i : index;
        }
        // if the point is better (shorter distance) than the worst one (longer distance) in the BestPoints
        // update BestPoints substituting the wrost one

        dbest = BestPointsDistances[index];
        cbest = BestPointsClasses[index];

        BestPointsDistances[index] = (distance < max) ? distance : dbest;
        BestPointsClasses[index] = (distance < max) ? knownClasses[pi0] : cbest;
    }
    BPD[0] = BestPointsDistances[0];
    BPD[1] = BestPointsDistances[1];
    BPD[2] = BestPointsDistances[2];
    BPC[0] = BestPointsClasses[0];
    BPC[1] = BestPointsClasses[1];
    BPC[2] = BestPointsClasses[2];
}

void epilogue(
    float BPD0[3], char BPC0[3], float BPD1[3], char BPC1[3],
    float BPD2[3], char BPC2[3], float BPD3[3], char BPC3[3],
    char *out)
{
    int bestDistances[12];
    int bestClasses[12];
#pragma HLS ARRAY_PARTITION variable = bestDistances complete dim = 1
#pragma HLS ARRAY_PARTITION variable = bestClasses complete dim = 1
    float d1 = MAXDISTANCE, d2 = MAXDISTANCE, d3 = MAXDISTANCE;
    char c1, c2, c3;
    for (int i = 0; i < 3; i++)
    {
        bestDistances[i] = BPD0[i];
        bestClasses[i] = BPC0[i];
        bestDistances[i + 3] = BPD1[i];
        bestClasses[i + 3] = BPC1[i];
        bestDistances[i + 6] = BPD2[i];
        bestClasses[i + 6] = BPC2[i];
        bestDistances[i + 9] = BPD3[i];
        bestClasses[i + 9] = BPC3[i];
    }

    for (int j = 0; j < 12; j++)
    {
        if (bestDistances[j] < d1)
        {
            d3 = d2;
            c3 = c2;
            d2 = d1;
            c2 = c1;
            d1 = bestDistances[j];
            c1 = bestClasses[j];
        }
        else if (bestDistances[j] < d2)
        {
            d3 = d2;
            c3 = c2;
            d2 = bestDistances[j];
            c2 = bestClasses[j];
        }
        else if (bestDistances[j] < d3)
        {
            d3 = bestDistances[j];
            c3 = bestClasses[j];
        }
    }

    char classID = c1;

    classID = (c2 == c3) ? c2 : classID;

    *out = classID;
}

void knnNoEpilogue10000p32f_4parallel(
    float xFeatures[32], char knownClasses0[2500], char knownClasses1[2500], char knownClasses2[2500], char knownClasses3[2500],
    float knownFeatures_0[2500][32], float knownFeatures_1[2500][32], float knownFeatures_2[2500][32], float knownFeatures_3[2500][32], char *out)
{
    // Step 2: Initialize local variables
    float distances0_array_0[2500];
    float distances0_array_1[2500];
    float distances0_array_2[2500];
    float distances0_array_3[2500];
    float BPD0[3];
    float BPD1[3];
    float BPD2[3];
    float BPD3[3];
    char BPC0[3];
    char BPC1[3];
    char BPC2[3];
    char BPC3[3];
#pragma HLS ARRAY_PARTITION variable = BPD0 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPD1 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPD2 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPD3 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPC0 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPC1 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPC2 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPC3 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = xFeatures cyclic factor = 32 dim = 1
#pragma HLS ARRAY_PARTITION variable = knownFeatures_0 cyclic factor = 8 dim = 2
#pragma HLS ARRAY_PARTITION variable = knownFeatures_1 cyclic factor = 8 dim = 2
#pragma HLS ARRAY_PARTITION variable = knownFeatures_2 cyclic factor = 8 dim = 2
#pragma HLS ARRAY_PARTITION variable = knownFeatures_3 cyclic factor = 8 dim = 2
// Initialization done
#pragma HLS dataflow

    parallel_0(knownFeatures_0, xFeatures, distances0_array_0);
    parallel_0(knownFeatures_1, xFeatures, distances0_array_1);
    parallel_0(knownFeatures_2, xFeatures, distances0_array_2);
    parallel_0(knownFeatures_3, xFeatures, distances0_array_3);

    epilogue_0(knownClasses0, distances0_array_0, BPD0, BPC0);
    epilogue_0(knownClasses1, distances0_array_1, BPD1, BPC1);
    epilogue_0(knownClasses2, distances0_array_2, BPD2, BPC2);
    epilogue_0(knownClasses3, distances0_array_3, BPD3, BPC3);
    epilogue(BPD0, BPC0, BPD1, BPC1, BPD2, BPC2, BPD3, BPC3, out);
}
