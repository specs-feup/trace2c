
void svmNoFold(float test_vector[2], float sup_vectors[2][4], float sv_coeff[4], int *y) {
// Step 2: Initialize local variables
float diff_w1;
float diff_w2;
float diff_w3;
float diff_w4;
float diff_w5;
float diff_w6;
float diff_w7;
float diff_w8;
float norma_w1;
float norma_w2;
float norma_w3;
float norma_w4;
float norma_w5;
float norma_w6;
float norma_w7;
float norma_w8;
double operationOutput_w1;
float sum_w1;
float sum_w2;
float sum_w3;
float sum_w4;
float sum_w5;
float temp_l111_1_i1_w1;
float temp_l111_1_i3_w1;
float temp_l111_1_i5_w1;
float temp_l111_1_i7_w1;
float temp_l111_2_i2_w1;
float temp_l111_2_i4_w1;
float temp_l111_2_i6_w1;
float temp_l111_2_i8_w1;
// Initialization done
diff_w2 = test_vector[0] - sup_vectors[0][0];
diff_w6 = test_vector[0] - sup_vectors[0][1];
diff_w1 = test_vector[0] - sup_vectors[0][2];
diff_w4 = test_vector[0] - sup_vectors[0][3];
diff_w3 = test_vector[1] - sup_vectors[1][0];
diff_w8 = test_vector[1] - sup_vectors[1][1];
diff_w5 = test_vector[1] - sup_vectors[1][2];
diff_w7 = test_vector[1] - sup_vectors[1][3];

norma_w5 = 0 + square(diff_w1);
norma_w3 = 0 + square(diff_w2);
norma_w2 = 0 + square(diff_w4);
norma_w8 = 0 + square(diff_w6);

norma_w4 = norma_w3 + square(diff_w3);
norma_w6 = norma_w5 + square(diff_w5);
norma_w7 = norma_w2 + square(diff_w7);
norma_w1 = norma_w8 + square(diff_w8);

temp_l111_1_i7_w1 = norma_w7 * (-gamma);
temp_l111_1_i3_w1 = norma_w1 * (-gamma);
temp_l111_1_i5_w1 = norma_w6 * (-gamma);
temp_l111_1_i1_w1 = norma_w4 * (-gamma);

temp_l111_2_i4_w1 = sv_coeff[1] * expf(temp_l111_1_i3_w1);
temp_l111_2_i6_w1 = sv_coeff[2] * expf(temp_l111_1_i5_w1);
temp_l111_2_i8_w1 = sv_coeff[3] * expf(temp_l111_1_i7_w1);
temp_l111_2_i2_w1 = sv_coeff[0] * expf(temp_l111_1_i1_w1);

sum_w5 = temp_l111_2_i4_w1 + temp_l111_2_i6_w1;
sum_w4 = 0 + temp_l111_2_i2_w1;

sum_w3 = sum_w4 + sum_w5;

sum_w2 = sum_w3 + temp_l111_2_i8_w1;

sum_w1 = sum_w2 - b;

operationOutput_w1 = sum_w1 < 0;

*y = operationOutput_w1 ? -1:1;

}
