#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <float.h>

#define NUM_CLASSES 2
#define MAXDISTANCE DBL_MAX
#define sqr(x) ((x)*(x))

void parallel_0(double knownFeatures[2][32], double xFeatures[32], double distance_array[2]) {
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
for( int i = 0; i < 2;i=i+1){
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

void epilogue(char knownClasses[8], double distance_array_1[2], double distance_array_0[2], double distance_array_3[2], double distance_array_2[2], char *out) {
// Step 2: Initialize local variables
char BestPointsClasses[3];
double BestPointsDistances[3];
char c1_w1;
char c2_w1;
char c3_w1;
char cbest_w1;
char cbest_w2;
char cbest_w3;
char cbest_w4;
char cbest_w5;
char cbest_w6;
char cbest_w7;
char cbest_w8;
char classID_w1;
char classID_w2;
char classID_w3;
char classID_w4;
double d1_w1;
double d2_w1;
double d3_w1;
float dbest_w1;
float dbest_w10;
double dbest_w11;
float dbest_w12;
double dbest_w13;
float dbest_w14;
float dbest_w15;
float dbest_w16;
double dbest_w17;
float dbest_w18;
float dbest_w19;
double dbest_w2;
float dbest_w20;
float dbest_w21;
float dbest_w22;
float dbest_w23;
float dbest_w24;
float dbest_w25;
float dbest_w26;
float dbest_w27;
double dbest_w28;
double dbest_w29;
float dbest_w3;
double dbest_w30;
double dbest_w31;
float dbest_w32;
float dbest_w4;
float dbest_w5;
float dbest_w6;
float dbest_w7;
float dbest_w8;
float dbest_w9;
int index_w1;
int index_w10;
int index_w11;
int index_w12;
int index_w13;
int index_w14;
int index_w15;
int index_w16;
int index_w17;
int index_w18;
int index_w19;
int index_w2;
int index_w20;
int index_w21;
int index_w22;
int index_w23;
int index_w24;
int index_w3;
int index_w4;
int index_w5;
int index_w6;
int index_w7;
int index_w8;
int index_w9;
double max_tmp_w1;
double max_tmp_w10;
double max_tmp_w11;
double max_tmp_w12;
double max_tmp_w13;
double max_tmp_w14;
double max_tmp_w15;
double max_tmp_w16;
double max_tmp_w17;
double max_tmp_w18;
double max_tmp_w19;
double max_tmp_w2;
double max_tmp_w20;
double max_tmp_w21;
double max_tmp_w22;
double max_tmp_w23;
double max_tmp_w24;
double max_tmp_w3;
double max_tmp_w4;
double max_tmp_w5;
double max_tmp_w6;
double max_tmp_w7;
double max_tmp_w8;
double max_tmp_w9;
float max_w1;
float max_w10;
float max_w11;
float max_w12;
float max_w13;
float max_w14;
float max_w15;
float max_w16;
float max_w17;
float max_w18;
float max_w19;
float max_w2;
float max_w20;
float max_w21;
float max_w22;
float max_w23;
float max_w24;
float max_w3;
float max_w4;
float max_w5;
float max_w6;
float max_w7;
float max_w8;
float max_w9;
double mindist_w1;
double mindist_w2;
double muxOutput_w1;
double muxOutput_w10;
double muxOutput_w11;
double muxOutput_w12;
double muxOutput_w13;
double muxOutput_w14;
double muxOutput_w15;
double muxOutput_w16;
double muxOutput_w2;
double muxOutput_w3;
double muxOutput_w4;
double muxOutput_w5;
double muxOutput_w6;
double muxOutput_w7;
double muxOutput_w8;
double muxOutput_w9;
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
double operationOutput_w19;
double operationOutput_w2;
double operationOutput_w20;
double operationOutput_w21;
double operationOutput_w22;
double operationOutput_w23;
double operationOutput_w24;
double operationOutput_w25;
double operationOutput_w26;
double operationOutput_w27;
double operationOutput_w28;
double operationOutput_w29;
double operationOutput_w3;
double operationOutput_w30;
double operationOutput_w31;
double operationOutput_w32;
double operationOutput_w33;
double operationOutput_w34;
double operationOutput_w35;
double operationOutput_w36;
double operationOutput_w37;
double operationOutput_w38;
double operationOutput_w4;
double operationOutput_w5;
double operationOutput_w6;
double operationOutput_w7;
double operationOutput_w8;
double operationOutput_w9;
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

index_w7 = operationOutput_w21 ? 0:0;
max_w24 = operationOutput_w21 ? dbest_w18:0;

max_tmp_w22 = max_w24;

operationOutput_w38 = dbest_w32 > max_tmp_w22;

index_w24 = operationOutput_w38 ? 1:index_w7;
max_w20 = operationOutput_w38 ? dbest_w32:max_w24;

max_tmp_w21 = max_w20;

operationOutput_w34 = dbest_w23 > max_tmp_w21;

index_w4 = operationOutput_w34 ? 2:index_w24;
max_w19 = operationOutput_w34 ? dbest_w23:max_w20;

operationOutput_w2 = distance_array_0[0] < max_w19;

cbest_w3 = BestPointsClasses[index_w4];
dbest_w2 = BestPointsDistances[index_w4];

muxOutput_w3 = operationOutput_w2 ? knownClasses[0]:cbest_w3;
muxOutput_w1 = operationOutput_w2 ? distance_array_0[0]:dbest_w2;

BestPointsClasses[index_w4] = muxOutput_w3;
BestPointsDistances[index_w4] = muxOutput_w1;


dbest_w9 = BestPointsDistances[0];
dbest_w5 = BestPointsDistances[1];
dbest_w27 = BestPointsDistances[2];

operationOutput_w12 = dbest_w9 > max_tmp_w6;

index_w23 = operationOutput_w12 ? 0:0;
max_w3 = operationOutput_w12 ? dbest_w9:0;

max_tmp_w3 = max_w3;

operationOutput_w4 = dbest_w5 > max_tmp_w3;

max_w15 = operationOutput_w4 ? dbest_w5:max_w3;
index_w22 = operationOutput_w4 ? 1:index_w23;

max_tmp_w12 = max_w15;

operationOutput_w30 = dbest_w27 > max_tmp_w12;

max_w6 = operationOutput_w30 ? dbest_w27:max_w15;
index_w2 = operationOutput_w30 ? 2:index_w22;

operationOutput_w11 = distance_array_0[1] < max_w6;

cbest_w1 = BestPointsClasses[index_w2];
dbest_w29 = BestPointsDistances[index_w2];

muxOutput_w11 = operationOutput_w11 ? knownClasses[1]:cbest_w1;
muxOutput_w9 = operationOutput_w11 ? distance_array_0[1]:dbest_w29;

BestPointsClasses[index_w2] = muxOutput_w11;
BestPointsDistances[index_w2] = muxOutput_w9;


dbest_w14 = BestPointsDistances[0];
dbest_w26 = BestPointsDistances[1];
dbest_w15 = BestPointsDistances[2];

operationOutput_w17 = dbest_w14 > max_tmp_w13;

max_w10 = operationOutput_w17 ? dbest_w14:0;
index_w18 = operationOutput_w17 ? 0:0;

max_tmp_w16 = max_w10;

operationOutput_w29 = dbest_w26 > max_tmp_w16;

max_w12 = operationOutput_w29 ? dbest_w26:max_w10;
index_w14 = operationOutput_w29 ? 1:index_w18;

max_tmp_w11 = max_w12;

operationOutput_w19 = dbest_w15 > max_tmp_w11;

index_w3 = operationOutput_w19 ? 2:index_w14;
max_w11 = operationOutput_w19 ? dbest_w15:max_w12;

operationOutput_w37 = distance_array_1[0] < max_w11;

cbest_w6 = BestPointsClasses[index_w3];
dbest_w31 = BestPointsDistances[index_w3];

muxOutput_w2 = operationOutput_w37 ? distance_array_1[0]:dbest_w31;
muxOutput_w16 = operationOutput_w37 ? knownClasses[2]:cbest_w6;

BestPointsDistances[index_w3] = muxOutput_w2;
BestPointsClasses[index_w3] = muxOutput_w16;


dbest_w1 = BestPointsDistances[0];
dbest_w24 = BestPointsDistances[1];
dbest_w20 = BestPointsDistances[2];

operationOutput_w1 = dbest_w1 > max_tmp_w19;

max_w1 = operationOutput_w1 ? dbest_w1:0;
index_w5 = operationOutput_w1 ? 0:0;

max_tmp_w10 = max_w1;

operationOutput_w26 = dbest_w24 > max_tmp_w10;

index_w13 = operationOutput_w26 ? 1:index_w5;
max_w9 = operationOutput_w26 ? dbest_w24:max_w1;

max_tmp_w9 = max_w9;

operationOutput_w24 = dbest_w20 > max_tmp_w9;

max_w16 = operationOutput_w24 ? dbest_w20:max_w9;
index_w1 = operationOutput_w24 ? 2:index_w13;

operationOutput_w22 = distance_array_1[1] < max_w16;

cbest_w5 = BestPointsClasses[index_w1];
dbest_w11 = BestPointsDistances[index_w1];

muxOutput_w6 = operationOutput_w22 ? knownClasses[3]:cbest_w5;
muxOutput_w15 = operationOutput_w22 ? distance_array_1[1]:dbest_w11;

BestPointsDistances[index_w1] = muxOutput_w15;
BestPointsClasses[index_w1] = muxOutput_w6;


dbest_w4 = BestPointsDistances[0];
dbest_w3 = BestPointsDistances[1];
dbest_w7 = BestPointsDistances[2];

operationOutput_w3 = dbest_w4 > max_tmp_w1;

index_w19 = operationOutput_w3 ? 0:0;
max_w22 = operationOutput_w3 ? dbest_w4:0;

max_tmp_w5 = max_w22;

operationOutput_w10 = dbest_w3 > max_tmp_w5;

max_w2 = operationOutput_w10 ? dbest_w3:max_w22;
index_w9 = operationOutput_w10 ? 1:index_w19;

max_tmp_w2 = max_w2;

operationOutput_w7 = dbest_w7 > max_tmp_w2;

max_w14 = operationOutput_w7 ? dbest_w7:max_w2;
index_w6 = operationOutput_w7 ? 2:index_w9;

operationOutput_w28 = distance_array_2[0] < max_w14;

cbest_w8 = BestPointsClasses[index_w6];
dbest_w13 = BestPointsDistances[index_w6];

muxOutput_w5 = operationOutput_w28 ? knownClasses[4]:cbest_w8;
muxOutput_w12 = operationOutput_w28 ? distance_array_2[0]:dbest_w13;

BestPointsDistances[index_w6] = muxOutput_w12;
BestPointsClasses[index_w6] = muxOutput_w5;


dbest_w12 = BestPointsDistances[0];
dbest_w6 = BestPointsDistances[1];
dbest_w8 = BestPointsDistances[2];

operationOutput_w16 = dbest_w12 > max_tmp_w24;

max_w8 = operationOutput_w16 ? dbest_w12:0;
index_w12 = operationOutput_w16 ? 0:0;

max_tmp_w23 = max_w8;

operationOutput_w33 = dbest_w6 > max_tmp_w23;

index_w17 = operationOutput_w33 ? 1:index_w12;
max_w5 = operationOutput_w33 ? dbest_w6:max_w8;

max_tmp_w17 = max_w5;

operationOutput_w9 = dbest_w8 > max_tmp_w17;

index_w10 = operationOutput_w9 ? 2:index_w17;
max_w4 = operationOutput_w9 ? dbest_w8:max_w5;

operationOutput_w15 = distance_array_2[1] < max_w4;

cbest_w7 = BestPointsClasses[index_w10];
dbest_w30 = BestPointsDistances[index_w10];

muxOutput_w13 = operationOutput_w15 ? distance_array_2[1]:dbest_w30;
muxOutput_w14 = operationOutput_w15 ? knownClasses[5]:cbest_w7;

BestPointsDistances[index_w10] = muxOutput_w13;
BestPointsClasses[index_w10] = muxOutput_w14;


dbest_w22 = BestPointsDistances[0];
dbest_w21 = BestPointsDistances[1];
dbest_w16 = BestPointsDistances[2];

operationOutput_w36 = dbest_w22 > max_tmp_w15;

index_w21 = operationOutput_w36 ? 0:0;
max_w21 = operationOutput_w36 ? dbest_w22:0;

max_tmp_w14 = max_w21;

operationOutput_w23 = dbest_w21 > max_tmp_w14;

index_w20 = operationOutput_w23 ? 1:index_w21;
max_w7 = operationOutput_w23 ? dbest_w21:max_w21;

max_tmp_w7 = max_w7;

operationOutput_w20 = dbest_w16 > max_tmp_w7;

max_w13 = operationOutput_w20 ? dbest_w16:max_w7;
index_w11 = operationOutput_w20 ? 2:index_w20;

operationOutput_w18 = distance_array_3[0] < max_w13;

cbest_w2 = BestPointsClasses[index_w11];
dbest_w17 = BestPointsDistances[index_w11];

muxOutput_w10 = operationOutput_w18 ? distance_array_3[0]:dbest_w17;
muxOutput_w4 = operationOutput_w18 ? knownClasses[6]:cbest_w2;

BestPointsClasses[index_w11] = muxOutput_w4;
BestPointsDistances[index_w11] = muxOutput_w10;


dbest_w10 = BestPointsDistances[0];
dbest_w25 = BestPointsDistances[1];
dbest_w19 = BestPointsDistances[2];

operationOutput_w13 = dbest_w10 > max_tmp_w8;

index_w16 = operationOutput_w13 ? 0:0;
max_w17 = operationOutput_w13 ? dbest_w10:0;

max_tmp_w18 = max_w17;

operationOutput_w25 = dbest_w25 > max_tmp_w18;

max_w18 = operationOutput_w25 ? dbest_w25:max_w17;
index_w15 = operationOutput_w25 ? 1:index_w16;

max_tmp_w20 = max_w18;

operationOutput_w35 = dbest_w19 > max_tmp_w20;

index_w8 = operationOutput_w35 ? 2:index_w15;
max_w23 = operationOutput_w35 ? dbest_w19:max_w18;

operationOutput_w31 = distance_array_3[1] < max_w23;

cbest_w4 = BestPointsClasses[index_w8];
dbest_w28 = BestPointsDistances[index_w8];

muxOutput_w7 = operationOutput_w31 ? knownClasses[7]:cbest_w4;
muxOutput_w8 = operationOutput_w31 ? distance_array_3[1]:dbest_w28;

BestPointsClasses[index_w8] = muxOutput_w7;
BestPointsDistances[index_w8] = muxOutput_w8;


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

classID_w1 = operationOutput_w5 ? c2_w1:c1_w1;
mindist_w2 = operationOutput_w32 ? d2_w1:d1_w1;

operationOutput_w27 = mindist_w2 > d3_w1;

classID_w3 = operationOutput_w27 ? c3_w1:classID_w1;

classID_w2 = operationOutput_w14 ? c2_w1:classID_w3;

classID_w4 = operationOutput_w6 ? c1_w1:classID_w2;

*out = operationOutput_w8 ? c1_w1:classID_w4;

}

void knn_8p32f_4parallel_saveEnergy(double xFeatures[32], char knownClasses[8], double knownFeatures_0[2][32], double knownFeatures_1[2][32], double knownFeatures_2[2][32], double knownFeatures_3[2][32], char *out) {
// Step 2: Initialize local variables
double distance_array_0[2];
double distance_array_1[2];
double distance_array_2[2];
double distance_array_3[2];
#pragma HLS ARRAY_PARTITION variable=xFeatures cyclic factor=32 dim=1
// Initialization done
#pragma HLS dataflow

parallel_0(knownFeatures_0,xFeatures,distance_array_0);
parallel_0(knownFeatures_1,xFeatures,distance_array_1);
parallel_0(knownFeatures_2,xFeatures,distance_array_2);
parallel_0(knownFeatures_3,xFeatures,distance_array_3);


epilogue(knownClasses,distance_array_1,distance_array_0,distance_array_3,distance_array_2,out);

}
