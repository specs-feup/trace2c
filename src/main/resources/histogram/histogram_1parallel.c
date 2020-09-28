#include <stdio.h>
#include "traps.h"

void parallel_0(int image[1][16], int histogram[1][16]) {
// Step 2: Initialize local variables
double operationOutput_w1;
// Initialization done
// starting Loop
for( int i = 0; i < 16;i=i+1){
#pragma HLS pipeline



operationOutput_w1 = histogram[0][i] + 1;

histogram[0][i] = operationOutput_w1;

}
}

void epilogue(int histogram[4][4], int image[4][4], int image[4][4]) {
// Step 2: Initialize local variables
float cdf_w1;
float cdf_w2;
float cdf_w3;
float cdf_w4;
float cdf_w5;
float cdf_w6;
float cdf_w7;
float cdf_w8;
float temp145_i1_w1;
float temp145_i2_w1;
float temp145_i3_w1;
float temp145_i4_w1;
float temp145_i5_w1;
float temp145_i6_w1;
float temp145_i7_w1;
float temp145_i8_w1;
// Initialization done

temp145_i1_w1 = histogram[0] / 16.0;
temp145_i2_w1 = histogram[1] / 16.0;
temp145_i3_w1 = histogram[2] / 16.0;
temp145_i4_w1 = histogram[3] / 16.0;
temp145_i5_w1 = histogram[4] / 16.0;
temp145_i6_w1 = histogram[5] / 16.0;
temp145_i7_w1 = histogram[6] / 16.0;
temp145_i8_w1 = histogram[7] / 16.0;

cdf_w3 = temp145_i4_w1 + temp145_i5_w1;
cdf_w5 = 0.0 + temp145_i1_w1;
cdf_w8 = temp145_i2_w1 + temp145_i3_w1;
cdf_w2 = temp145_i6_w1 + temp145_i7_w1;

glm[6] = 255.0 * cdf_w2;
cdf_w1 = cdf_w3 + cdf_w2;
glm[4] = 255.0 * cdf_w3;
cdf_w4 = cdf_w5 + cdf_w8;
glm[0] = 255.0 * cdf_w5;
glm[2] = 255.0 * cdf_w8;

glm[5] = 255.0 * cdf_w1;
cdf_w7 = cdf_w4 + cdf_w1;
glm[1] = 255.0 * cdf_w4;

cdf_w6 = cdf_w7 + temp145_i8_w1;
glm[3] = 255.0 * cdf_w7;

glm[7] = 255.0 * cdf_w6;


image[0][0] = glm[image[0][0]];
image[0][1] = glm[image[0][1]];
image[0][2] = glm[image[0][2]];
image[0][3] = glm[image[0][3]];
image[1][0] = glm[image[1][0]];
image[1][1] = glm[image[1][1]];
image[1][2] = glm[image[1][2]];
image[1][3] = glm[image[1][3]];
image[2][0] = glm[image[2][0]];
image[2][1] = glm[image[2][1]];
image[2][2] = glm[image[2][2]];
image[2][3] = glm[image[2][3]];
image[3][0] = glm[image[3][0]];
image[3][1] = glm[image[3][1]];
image[3][2] = glm[image[3][2]];
image[3][3] = glm[image[3][3]];

}

void histogram_1parallel(int image[4][4], int histogram[8], int glm[8]) {
// Step 2: Initialize local variables
// Initialization done
#pragma HLS dataflow

parallel_0(image_0,histogram_0);


epilogue(histogram,image,image);

}
