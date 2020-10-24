#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <float.h>

#define NUM_CLASSES 2
#define MAXDISTANCE DBL_MAX
#define sqr(x) ((x)*(x))

void knnFloatNoSqrt3p2f_noFold(float xFeatures[2], float knownFeatures[3][2], char knownClasses[3], char *out) {
// Step 2: Initialize local variables
char BestPointsClasses[3];
float BestPointsDistances[3];
char c1_w1;
char c2_w1;
char c3_w1;
char cbest_w1;
char cbest_w2;
char cbest_w3;
char classID_w1;
char classID_w2;
char classID_w3;
char classID_w4;
float d1_w1;
float d2_w1;
float d3_w1;
float dbest_w1;
float dbest_w10;
float dbest_w11;
float dbest_w12;
float dbest_w2;
float dbest_w3;
float dbest_w4;
float dbest_w5;
float dbest_w6;
float dbest_w7;
float dbest_w8;
float dbest_w9;
float distance_w1;
float distance_w2;
float distance_w3;
float distance_w4;
float distance_w5;
float distance_w6;
int index_w1;
int index_w2;
int index_w3;
int index_w4;
int index_w5;
int index_w6;
int index_w7;
int index_w8;
int index_w9;
float max_tmp_w1;
float max_tmp_w2;
float max_tmp_w3;
float max_tmp_w4;
float max_tmp_w5;
float max_tmp_w6;
float max_tmp_w7;
float max_tmp_w8;
float max_tmp_w9;
float max_w1;
float max_w2;
float max_w3;
float max_w4;
float max_w5;
float max_w6;
float max_w7;
float max_w8;
float max_w9;
float mindist_w1;
float mindist_w2;
double muxOutput_w1;
double muxOutput_w2;
double muxOutput_w3;
double muxOutput_w4;
double muxOutput_w5;
double muxOutput_w6;
double operationOutput_w1;
double operationOutput_w10;
double operationOutput_w11;
double operationOutput_w12;
double operationOutput_w13;
double operationOutput_w14;
double operationOutput_w15;
double operationOutput_w16;
double operationOutput_w17;
double operationOutput_w18;
double operationOutput_w2;
double operationOutput_w3;
double operationOutput_w4;
double operationOutput_w5;
double operationOutput_w6;
double operationOutput_w7;
double operationOutput_w8;
double operationOutput_w9;
float temp_l77_i1_w1;
float temp_l77_i2_w1;
float temp_l77_i3_w1;
float temp_l77_i4_w1;
float temp_l77_i5_w1;
float temp_l77_i6_w1;
// Initialization done
temp_l77_i1_w1 = xFeatures[0] - knownFeatures[0][0];
temp_l77_i5_w1 = xFeatures[0] - knownFeatures[2][0];
temp_l77_i3_w1 = xFeatures[0] - knownFeatures[1][0];
temp_l77_i4_w1 = xFeatures[1] - knownFeatures[1][1];
temp_l77_i6_w1 = xFeatures[1] - knownFeatures[2][1];
temp_l77_i2_w1 = xFeatures[1] - knownFeatures[0][1];
max_tmp_w4 = 0;
max_tmp_w9 = 0;
max_tmp_w7 = 0;
BestPointsDistances[2] = MAXDISTANCE;
BestPointsDistances[1] = MAXDISTANCE;
BestPointsDistances[0] = MAXDISTANCE;
BestPointsClasses[0] = NUM_CLASSES;
BestPointsClasses[2] = NUM_CLASSES;
BestPointsClasses[1] = NUM_CLASSES;

dbest_w4 = BestPointsDistances[0];
dbest_w5 = BestPointsDistances[1];
dbest_w6 = BestPointsDistances[2];
distance_w6 = 0 + sqr(temp_l77_i5_w1);
distance_w1 = 0 + sqr(temp_l77_i1_w1);
distance_w3 = 0 + sqr(temp_l77_i3_w1);

distance_w2 = distance_w1 + sqr(temp_l77_i2_w1);
distance_w4 = distance_w3 + sqr(temp_l77_i4_w1);
distance_w5 = distance_w6 + sqr(temp_l77_i6_w1);
operationOutput_w10 = dbest_w4 > max_tmp_w4;

max_w4 = operationOutput_w10 ? dbest_w4:0;
index_w4 = operationOutput_w10 ? 0:0;

max_tmp_w5 = max_w4;

operationOutput_w11 = dbest_w5 > max_tmp_w5;

max_w6 = operationOutput_w11 ? dbest_w5:max_w4;
index_w5 = operationOutput_w11 ? 1:index_w4;

max_tmp_w6 = max_w6;

operationOutput_w14 = dbest_w6 > max_tmp_w6;

max_w7 = operationOutput_w14 ? dbest_w6:max_w6;
index_w7 = operationOutput_w14 ? 2:index_w5;

operationOutput_w15 = distance_w2 < max_w7;

cbest_w2 = BestPointsClasses[index_w7];
dbest_w8 = BestPointsDistances[index_w7];

muxOutput_w5 = operationOutput_w15 ? distance_w2:dbest_w8;
muxOutput_w6 = operationOutput_w15 ? knownClasses[0]:cbest_w2;

BestPointsDistances[index_w7] = muxOutput_w5;
BestPointsClasses[index_w7] = muxOutput_w6;


dbest_w12 = BestPointsDistances[0];
dbest_w2 = BestPointsDistances[1];
dbest_w3 = BestPointsDistances[2];

operationOutput_w18 = dbest_w12 > max_tmp_w9;

max_w1 = operationOutput_w18 ? dbest_w12:0;
index_w1 = operationOutput_w18 ? 0:0;

max_tmp_w2 = max_w1;

operationOutput_w8 = dbest_w2 > max_tmp_w2;

index_w2 = operationOutput_w8 ? 1:index_w1;
max_w2 = operationOutput_w8 ? dbest_w2:max_w1;

max_tmp_w3 = max_w2;

operationOutput_w9 = dbest_w3 > max_tmp_w3;

max_w5 = operationOutput_w9 ? dbest_w3:max_w2;
index_w6 = operationOutput_w9 ? 2:index_w2;

operationOutput_w17 = distance_w4 < max_w5;

cbest_w3 = BestPointsClasses[index_w6];
dbest_w10 = BestPointsDistances[index_w6];

muxOutput_w2 = operationOutput_w17 ? knownClasses[1]:cbest_w3;
muxOutput_w1 = operationOutput_w17 ? distance_w4:dbest_w10;

BestPointsDistances[index_w6] = muxOutput_w1;
BestPointsClasses[index_w6] = muxOutput_w2;


dbest_w9 = BestPointsDistances[0];
dbest_w1 = BestPointsDistances[1];
dbest_w11 = BestPointsDistances[2];

operationOutput_w12 = dbest_w9 > max_tmp_w7;

max_w3 = operationOutput_w12 ? dbest_w9:0;
index_w3 = operationOutput_w12 ? 0:0;

max_tmp_w1 = max_w3;

operationOutput_w1 = dbest_w1 > max_tmp_w1;

index_w9 = operationOutput_w1 ? 1:index_w3;
max_w9 = operationOutput_w1 ? dbest_w1:max_w3;

max_tmp_w8 = max_w9;

operationOutput_w16 = dbest_w11 > max_tmp_w8;

index_w8 = operationOutput_w16 ? 2:index_w9;
max_w8 = operationOutput_w16 ? dbest_w11:max_w9;

operationOutput_w13 = distance_w5 < max_w8;

cbest_w1 = BestPointsClasses[index_w8];
dbest_w7 = BestPointsDistances[index_w8];

muxOutput_w4 = operationOutput_w13 ? distance_w5:dbest_w7;
muxOutput_w3 = operationOutput_w13 ? knownClasses[2]:cbest_w1;

BestPointsClasses[index_w8] = muxOutput_w3;
BestPointsDistances[index_w8] = muxOutput_w4;


c1_w1 = BestPointsClasses[0];
c2_w1 = BestPointsClasses[1];
c3_w1 = BestPointsClasses[2];
d1_w1 = BestPointsDistances[0];
d2_w1 = BestPointsDistances[1];
d3_w1 = BestPointsDistances[2];

mindist_w2 = d1_w1;
operationOutput_w4 = c2_w1 == c3_w1;
operationOutput_w3 = c1_w1 == c3_w1;
operationOutput_w2 = c1_w1 == c2_w1;

operationOutput_w6 = mindist_w2 > d2_w1;
operationOutput_w7 = mindist_w2 > d2_w1;

mindist_w1 = operationOutput_w6 ? d2_w1:d1_w1;
classID_w4 = operationOutput_w7 ? c2_w1:c1_w1;

operationOutput_w5 = mindist_w1 > d3_w1;

classID_w3 = operationOutput_w5 ? c3_w1:classID_w4;

classID_w2 = operationOutput_w4 ? c2_w1:classID_w3;

classID_w1 = operationOutput_w3 ? c1_w1:classID_w2;

*out = operationOutput_w2 ? c1_w1:classID_w1;

}
