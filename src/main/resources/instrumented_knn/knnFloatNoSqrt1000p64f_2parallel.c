#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <float.h>

#define NUM_CLASSES 2
#define MAXDISTANCE DBL_MAX
#define sqr(x) ((x) * (x))

void parallel_0(float knownFeatures[500][64], float xFeatures[64], float distance_array[500])
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
    float distance_w33;
    float distance_w34;
    float distance_w35;
    float distance_w36;
    float distance_w37;
    float distance_w38;
    float distance_w39;
    float distance_w4;
    float distance_w40;
    float distance_w41;
    float distance_w42;
    float distance_w43;
    float distance_w44;
    float distance_w45;
    float distance_w46;
    float distance_w47;
    float distance_w48;
    float distance_w49;
    float distance_w5;
    float distance_w50;
    float distance_w51;
    float distance_w52;
    float distance_w53;
    float distance_w54;
    float distance_w55;
    float distance_w56;
    float distance_w57;
    float distance_w58;
    float distance_w59;
    float distance_w6;
    float distance_w60;
    float distance_w61;
    float distance_w62;
    float distance_w63;
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
    float temp_l77_i33_w1;
    float temp_l77_i34_w1;
    float temp_l77_i35_w1;
    float temp_l77_i36_w1;
    float temp_l77_i37_w1;
    float temp_l77_i38_w1;
    float temp_l77_i39_w1;
    float temp_l77_i3_w1;
    float temp_l77_i40_w1;
    float temp_l77_i41_w1;
    float temp_l77_i42_w1;
    float temp_l77_i43_w1;
    float temp_l77_i44_w1;
    float temp_l77_i45_w1;
    float temp_l77_i46_w1;
    float temp_l77_i47_w1;
    float temp_l77_i48_w1;
    float temp_l77_i49_w1;
    float temp_l77_i4_w1;
    float temp_l77_i50_w1;
    float temp_l77_i51_w1;
    float temp_l77_i52_w1;
    float temp_l77_i53_w1;
    float temp_l77_i54_w1;
    float temp_l77_i55_w1;
    float temp_l77_i56_w1;
    float temp_l77_i57_w1;
    float temp_l77_i58_w1;
    float temp_l77_i59_w1;
    float temp_l77_i5_w1;
    float temp_l77_i60_w1;
    float temp_l77_i61_w1;
    float temp_l77_i62_w1;
    float temp_l77_i63_w1;
    float temp_l77_i64_w1;
    float temp_l77_i6_w1;
    float temp_l77_i7_w1;
    float temp_l77_i8_w1;
    float temp_l77_i9_w1;
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
        temp_l77_i33_w1 = xFeatures[32] - knownFeatures[i][32];
        temp_l77_i34_w1 = xFeatures[33] - knownFeatures[i][33];
        temp_l77_i35_w1 = xFeatures[34] - knownFeatures[i][34];
        temp_l77_i36_w1 = xFeatures[35] - knownFeatures[i][35];
        temp_l77_i37_w1 = xFeatures[36] - knownFeatures[i][36];
        temp_l77_i38_w1 = xFeatures[37] - knownFeatures[i][37];
        temp_l77_i39_w1 = xFeatures[38] - knownFeatures[i][38];
        temp_l77_i40_w1 = xFeatures[39] - knownFeatures[i][39];
        temp_l77_i41_w1 = xFeatures[40] - knownFeatures[i][40];
        temp_l77_i42_w1 = xFeatures[41] - knownFeatures[i][41];
        temp_l77_i43_w1 = xFeatures[42] - knownFeatures[i][42];
        temp_l77_i44_w1 = xFeatures[43] - knownFeatures[i][43];
        temp_l77_i45_w1 = xFeatures[44] - knownFeatures[i][44];
        temp_l77_i46_w1 = xFeatures[45] - knownFeatures[i][45];
        temp_l77_i47_w1 = xFeatures[46] - knownFeatures[i][46];
        temp_l77_i48_w1 = xFeatures[47] - knownFeatures[i][47];
        temp_l77_i49_w1 = xFeatures[48] - knownFeatures[i][48];
        temp_l77_i50_w1 = xFeatures[49] - knownFeatures[i][49];
        temp_l77_i51_w1 = xFeatures[50] - knownFeatures[i][50];
        temp_l77_i52_w1 = xFeatures[51] - knownFeatures[i][51];
        temp_l77_i53_w1 = xFeatures[52] - knownFeatures[i][52];
        temp_l77_i54_w1 = xFeatures[53] - knownFeatures[i][53];
        temp_l77_i55_w1 = xFeatures[54] - knownFeatures[i][54];
        temp_l77_i56_w1 = xFeatures[55] - knownFeatures[i][55];
        temp_l77_i57_w1 = xFeatures[56] - knownFeatures[i][56];
        temp_l77_i58_w1 = xFeatures[57] - knownFeatures[i][57];
        temp_l77_i59_w1 = xFeatures[58] - knownFeatures[i][58];
        temp_l77_i60_w1 = xFeatures[59] - knownFeatures[i][59];
        temp_l77_i61_w1 = xFeatures[60] - knownFeatures[i][60];
        temp_l77_i62_w1 = xFeatures[61] - knownFeatures[i][61];
        temp_l77_i63_w1 = xFeatures[62] - knownFeatures[i][62];
        temp_l77_i64_w1 = xFeatures[63] - knownFeatures[i][63];

        distance_w10 = sqr(temp_l77_i36_w1) + sqr(temp_l77_i37_w1);
        distance_w60 = sqr(temp_l77_i60_w1) + sqr(temp_l77_i61_w1);
        distance_w20 = sqr(temp_l77_i28_w1) + sqr(temp_l77_i29_w1);
        distance_w46 = sqr(temp_l77_i4_w1) + sqr(temp_l77_i5_w1);
        distance_w41 = sqr(temp_l77_i40_w1) + sqr(temp_l77_i41_w1);
        distance_w59 = sqr(temp_l77_i26_w1) + sqr(temp_l77_i27_w1);
        distance_w34 = sqr(temp_l77_i22_w1) + sqr(temp_l77_i23_w1);
        distance_w62 = 0 + sqr(temp_l77_i1_w1);
        distance_w49 = sqr(temp_l77_i24_w1) + sqr(temp_l77_i25_w1);
        distance_w23 = sqr(temp_l77_i10_w1) + sqr(temp_l77_i11_w1);
        distance_w58 = sqr(temp_l77_i16_w1) + sqr(temp_l77_i17_w1);
        distance_w30 = sqr(temp_l77_i42_w1) + sqr(temp_l77_i43_w1);
        distance_w1 = sqr(temp_l77_i32_w1) + sqr(temp_l77_i33_w1);
        distance_w12 = sqr(temp_l77_i14_w1) + sqr(temp_l77_i15_w1);
        distance_w36 = sqr(temp_l77_i34_w1) + sqr(temp_l77_i35_w1);
        distance_w53 = sqr(temp_l77_i44_w1) + sqr(temp_l77_i45_w1);
        distance_w54 = sqr(temp_l77_i52_w1) + sqr(temp_l77_i53_w1);
        distance_w44 = sqr(temp_l77_i56_w1) + sqr(temp_l77_i57_w1);
        distance_w15 = sqr(temp_l77_i38_w1) + sqr(temp_l77_i39_w1);
        distance_w31 = sqr(temp_l77_i48_w1) + sqr(temp_l77_i49_w1);
        distance_w7 = sqr(temp_l77_i58_w1) + sqr(temp_l77_i59_w1);
        distance_w27 = sqr(temp_l77_i50_w1) + sqr(temp_l77_i51_w1);
        distance_w19 = sqr(temp_l77_i46_w1) + sqr(temp_l77_i47_w1);
        distance_w51 = sqr(temp_l77_i18_w1) + sqr(temp_l77_i19_w1);
        distance_w4 = sqr(temp_l77_i8_w1) + sqr(temp_l77_i9_w1);
        distance_w16 = sqr(temp_l77_i30_w1) + sqr(temp_l77_i31_w1);
        distance_w63 = sqr(temp_l77_i54_w1) + sqr(temp_l77_i55_w1);
        distance_w43 = sqr(temp_l77_i6_w1) + sqr(temp_l77_i7_w1);
        distance_w32 = sqr(temp_l77_i12_w1) + sqr(temp_l77_i13_w1);
        distance_w5 = sqr(temp_l77_i20_w1) + sqr(temp_l77_i21_w1);
        distance_w9 = sqr(temp_l77_i62_w1) + sqr(temp_l77_i63_w1);

        distance_w39 = distance_w62 + sqr(temp_l77_i2_w1);
        distance_w37 = distance_w1 + distance_w36;
        distance_w3 = distance_w4 + distance_w23;
        distance_w35 = distance_w5 + distance_w34;
        distance_w25 = distance_w10 + distance_w15;
        distance_w17 = distance_w20 + distance_w16;
        distance_w28 = distance_w31 + distance_w27;
        distance_w13 = distance_w32 + distance_w12;
        distance_w40 = distance_w41 + distance_w30;
        distance_w8 = distance_w44 + distance_w7;
        distance_w50 = distance_w46 + distance_w43;
        distance_w48 = distance_w49 + distance_w59;
        distance_w52 = distance_w53 + distance_w19;
        distance_w61 = distance_w54 + distance_w63;
        distance_w57 = distance_w58 + distance_w51;
        distance_w26 = distance_w60 + distance_w9;

        distance_w38 = distance_w39 + sqr(temp_l77_i3_w1);
        distance_w24 = distance_w13 + distance_w57;
        distance_w2 = distance_w17 + distance_w37;
        distance_w14 = distance_w25 + distance_w40;
        distance_w33 = distance_w35 + distance_w48;
        distance_w42 = distance_w50 + distance_w3;
        distance_w18 = distance_w52 + distance_w28;
        distance_w45 = distance_w61 + distance_w8;

        distance_w29 = distance_w14 + distance_w18;
        distance_w56 = distance_w45 + distance_w26;
        distance_w21 = distance_w33 + distance_w2;
        distance_w22 = distance_w42 + distance_w24;

        distance_w11 = distance_w21 + distance_w29;
        distance_w47 = distance_w38 + distance_w22;

        distance_w6 = distance_w47 + distance_w11;

        distance_w55 = distance_w6 + distance_w56;

        distance_array[i] = distance_w55 + sqr(temp_l77_i64_w1);
    }
}

void epilogue_0(char knownClasses[500], float distance_array[500], float BPD[3], char BPC[3])
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
    for (int pi0 = 0; pi0 < 500; pi0++)
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

void epilogue(float BPD0[3], char BPC0[3], float BPD1[3], char BPC1[3], char *out)
{
    int bestDistances[6];
    int bestClasses[6];
#pragma HLS ARRAY_PARTITION variable = bestDistances complete dim = 1
#pragma HLS ARRAY_PARTITION variable = bestClasses complete dim = 1
    float d1 = MAXDISTANCE, d2 = MAXDISTANCE, d3 = MAXDISTANCE;
    char c1, c2, c3;
    for (int i = 0; i < 6; i++)
    {
        bestDistances[i] = BPD0[i];
        bestDistances[i + 3] = BPD1[i];
        bestClasses[i] = BPC0[i];
        bestClasses[i + 3] = BPC1[i];
    }

    for (int j = 0; j < 6; j++)
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
    float mindist = d1;

    classID = (c2 == c3) ? c2 : classID;

    *out = classID;
}

void knnFloatNoSqrt1000p64f_2parallel(
    float xFeatures[64], char knownClasses0[500], char knownClasses1[500],
    float knownFeatures_0[500][64], float knownFeatures_1[500][64], char *out)
{
    // Step 2: Initialize local variables
    float distance_array_0[500];
    float distance_array_1[500];
    float BPD0[3];
    float BPD1[3];
    char BPC0[3];
    char BPC1[3];
#pragma HLS ARRAY_PARTITION variable = BPD0 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPD1 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPC0 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPC1 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = xFeatures cyclic factor = 64 dim = 1
#pragma HLS ARRAY_PARTITION variable = knownFeatures_0 cyclic factor = 4 dim = 2
#pragma HLS ARRAY_PARTITION variable = knownFeatures_1 cyclic factor = 4 dim = 2
// Initialization done
#pragma HLS dataflow

    parallel_0(knownFeatures_0, xFeatures, distance_array_0);
    parallel_0(knownFeatures_1, xFeatures, distance_array_1);

    epilogue_0(knownClasses0, distance_array_0, BPD0, BPC0);
    epilogue_0(knownClasses1, distance_array_1, BPD1, BPC1);
    epilogue(BPD0, BPC0, BPD1, BPC1, out);
}
