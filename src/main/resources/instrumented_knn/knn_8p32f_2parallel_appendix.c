#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <float.h>

#define NUM_CLASSES 2
#define MAXDISTANCE DBL_MAX
#define sqr(x) ((x) * (x))

void parallel_0(double knownFeatures[4][32], double xFeatures[32], double distance_array[4])
{
  // Step 2: Initialize local variables
  float distance_w1;
  ... 
  float distance_w32;
  float temp_l77_i1_w1;
  ... 
  float temp_l77_i32_w1;
  // Initialization done
  // starting Loop
  for (int i = 0; i < 4; i = i + 1) {
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

    distance_w27 = 0 + sqr(temp_l77_i1_w1);
    distance_w4 = sqr(temp_l77_i28_w1) + sqr(temp_l77_i29_w1);
    distance_w32 = sqr(temp_l77_i30_w1) + sqr(temp_l77_i31_w1);
    distance_w24 = distance_w27 + sqr(temp_l77_i2_w1);
    distance_w3 = distance_w4 + distance_w32;
    distance_w23 = distance_w24 + sqr(temp_l77_i3_w1);
    distance_w30 = distance_w23 + sqr(temp_l77_i4_w1);
    distance_w22 = distance_w30 + sqr(temp_l77_i5_w1);
    distance_w21 = distance_w22 + sqr(temp_l77_i6_w1);
    distance_w19 = distance_w21 + sqr(temp_l77_i7_w1);
    distance_w18 = distance_w19 + sqr(temp_l77_i8_w1);
    distance_w16 = distance_w18 + sqr(temp_l77_i9_w1);
    distance_w15 = distance_w16 + sqr(temp_l77_i10_w1);
    distance_w29 = distance_w15 + sqr(temp_l77_i11_w1);
    distance_w28 = distance_w29 + sqr(temp_l77_i12_w1);
    distance_w14 = distance_w28 + sqr(temp_l77_i13_w1);
    distance_w13 = distance_w14 + sqr(temp_l77_i14_w1);
    distance_w12 = distance_w13 + sqr(temp_l77_i15_w1);
    distance_w9 = distance_w12 + sqr(temp_l77_i16_w1);
    distance_w8 = distance_w9 + sqr(temp_l77_i17_w1);
    distance_w20 = distance_w8 + sqr(temp_l77_i18_w1);
    distance_w7 = distance_w20 + sqr(temp_l77_i19_w1);
    distance_w6 = distance_w7 + sqr(temp_l77_i20_w1);
    distance_w17 = distance_w6 + sqr(temp_l77_i21_w1);
    distance_w11 = distance_w17 + sqr(temp_l77_i22_w1);
    distance_w10 = distance_w11 + sqr(temp_l77_i23_w1);
    distance_w2 = distance_w10 + sqr(temp_l77_i24_w1);
    distance_w1 = distance_w2 + sqr(temp_l77_i25_w1);
    distance_w5 = distance_w1 + sqr(temp_l77_i26_w1);
    distance_w31 = distance_w5 + sqr(temp_l77_i27_w1);
    distance_w26 = distance_w31 + distance_w3;
    distance_w25 = distance_w26 + sqr(temp_l77_i32_w1);
    distance_array[i] = sqrtf(distance_w25);
  }
}

void epilogue(char knownClasses[8], double distance_array_1[4], double distance_array_0[4], char *out)
{
  // Step 2: Initialize local variables
  char BestPointsClasses[3];
  double BestPointsDistances[3];
  char c1_w1;
  char c2_w1;
  char c3_w1;
  char cbest_w1;
  ... 
  char cbest_w8;

  char classID_w1;
  char classID_w2;
  char classID_w3;
  char classID_w4;

  double d1_w1;
  double d2_w1;
  double d3_w1;

  float dbest_w1;
  ... 
  float dbest_w32;

  int index_w1;
  int index_w24;

  double max_tmp_w1;
  ... 
  double max_tmp_w24;

  float max_w1;
  ... 
  float max_w24;

  double mindist_w1;
  double mindist_w2;

  double muxOutput_w1;
  ... 
  double muxOutput_w16;

  double operationOutput_w1;
  ... 
  double operationOutput_w38;

  // Initialization done
  max_tmp_w15 = 0;
  max_tmp_w8 = 0;
  max_tmp_w13 = 0;
  max_tmp_w6 = 0;
  max_tmp_w24 = 0;
  max_tmp_w19 = 0;
  max_tmp_w1 = 0;
  max_tmp_w4 = 0;
  BestPointsDistances[2] = MAXDISTANCE;
  BestPointsDistances[0] = MAXDISTANCE;
  BestPointsDistances[1] = MAXDISTANCE;
  BestPointsClasses[0] = NUM_CLASSES;
  BestPointsClasses[1] = NUM_CLASSES;
  BestPointsClasses[2] = NUM_CLASSES;

  dbest_w18 = BestPointsDistances[0];
  dbest_w32 = BestPointsDistances[1];
  dbest_w23 = BestPointsDistances[2];

  operationOutput_w21 = dbest_w18 > max_tmp_w4;

  index_w7 = operationOutput_w21 ? 0 : 0;
  max_w24 = operationOutput_w21 ? dbest_w18 : 0;

  max_tmp_w22 = max_w24;

  ...

  muxOutput_w3 = operationOutput_w2 ? knownClasses[0] : cbest_w3;
  muxOutput_w1 = operationOutput_w2 ? distance_array_0[0] : dbest_w2;

  BestPointsClasses[index_w4] = muxOutput_w3;
  BestPointsDistances[index_w4] = muxOutput_w1;

  dbest_w9 = BestPointsDistances[0];
  dbest_w5 = BestPointsDistances[1];
  dbest_w27 = BestPointsDistances[2];

  ...

  c1_w1 = BestPointsClasses[0];
  c2_w1 = BestPointsClasses[1];
  c3_w1 = BestPointsClasses[2];
  d1_w1 = BestPointsDistances[0];
  d2_w1 = BestPointsDistances[1];
  d3_w1 = BestPointsDistances[2];

  mindist_w1 = d1_w1;
  operationOutput_w6 = c1_w1 == c3_w1;
  operationOutput_w8 = c1_w1 == c2_w1;
  operationOutput_w14 = c2_w1 == c3_w1;

  operationOutput_w5 = mindist_w1 > d2_w1;
  operationOutput_w32 = mindist_w1 > d2_w1;

  classID_w1 = operationOutput_w5 ? c2_w1 : c1_w1;
  mindist_w2 = operationOutput_w32 ? d2_w1 : d1_w1;

  operationOutput_w27 = mindist_w2 > d3_w1;

  classID_w3 = operationOutput_w27 ? c3_w1 : classID_w1;

  classID_w2 = operationOutput_w14 ? c2_w1 : classID_w3;

  classID_w4 = operationOutput_w6 ? c1_w1 : classID_w2;

  *out = operationOutput_w8 ? c1_w1 : classID_w4;
}

void knn_8p32f_2parallel_saveEnergy(double xFeatures[32], char knownClasses[8], double knownFeatures_0[4][32], double knownFeatures_1[4][32], char *out)
{
  // Step 2: Initialize local variables
  double distance_array_0[4];
  double distance_array_1[4];
  #pragma HLS ARRAY_PARTITION variable = xFeatures cyclic factor = 32 dim = 1
  #pragma HLS ARRAY_PARTITION variable = knownFeatures_0 cyclic factor = 3 dim = 2
  #pragma HLS ARRAY_PARTITION variable = knownFeatures_1 cyclic factor = 3 dim = 2
  // Initialization done
  #pragma HLS dataflow

  parallel_0(knownFeatures_0, xFeatures, distance_array_0);
  parallel_0(knownFeatures_1, xFeatures, distance_array_1);

  epilogue(knownClasses, distance_array_1, distance_array_0, out);
}
