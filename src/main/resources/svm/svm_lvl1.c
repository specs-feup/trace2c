
void svm_lvl1(float test_vector[4], float sup_vectors[4][4], float sv_coeff[4], float *sum)
// Step 2: Initialize local variables
float diff_w1;
float diff_w2;
float diff_w3;
float diff_w4;
float norma_w1;
float diff_w5;
float diff_w6;
float diff_w7;
float diff_w8;
float norma_w2;
float diff_w9;
float diff_w10;
float diff_w11;
float diff_w12;
float diff_w13;
float diff_w14;
float diff_w15;
float diff_w16;
float diff_w17;
float diff_w18;
float norma_w3;
float diff_w19;
float temp_l111_1_i1_w1;
float diff_w20;
float temp_l111_2_i2_w1;
float diff_w21;
float sum_w1;
float diff_w22;
float diff_w23;
float diff_w24;
float sum_w2;
float norma_w4;
float diff_w25;
float diff_w26;
float norma_w5;
float diff_w27;
float norma_w6;
float diff_w28;
float diff_w29;
float diff_w30;
float diff_w31;
float diff_w32;
float diff_w33;
float sum_w3;
float diff_w34;
float norma_w7;
float norma_w8;
float norma_w9;
float temp_l111_1_i3_w1;
float temp_l111_2_i8_w1;
float temp_l111_2_i4_w1;
float norma_w10;
float norma_w11;
float sum_w4;
float norma_w12;
float diff_w35;
float temp_l111_2_i6_w1;
float diff_w36;
float diff_w37;
float diff_w38;
float norma_w13;
float diff_w39;
float diff_w40;
float sum_w5;
float norma_w14;
float temp_l111_1_i5_w1;
float norma_w15;
float diff_w41;
float diff_w42;
float diff_w43;
float diff_w44;
float diff_w45;
float temp_l111_1_i7_w1;
float diff_w46;
float diff_w47;
float diff_w48;
float norma_w16;
// Initialization done
{
diff_w22 = test_vector[0] - sup_vectors[0][1];
diff_w2 = test_vector[0] - sup_vectors[0][0];
diff_w39 = test_vector[1] - sup_vectors[1][2];
diff_w6 = test_vector[1] - sup_vectors[1][0];
diff_w40 = test_vector[3] - sup_vectors[3][3];
diff_w25 = test_vector[1] - sup_vectors[1][1];
diff_w42 = test_vector[2] - sup_vectors[2][2];
diff_w43 = test_vector[0] - sup_vectors[0][3];
diff_w33 = test_vector[3] - sup_vectors[3][1];
diff_w48 = test_vector[2] - sup_vectors[2][3];
diff_w15 = test_vector[3] - sup_vectors[3][0];
diff_w7 = test_vector[3] - sup_vectors[3][2];
diff_w10 = test_vector[2] - sup_vectors[2][0];
diff_w35 = test_vector[0] - sup_vectors[0][2];
diff_w29 = test_vector[2] - sup_vectors[2][1];
diff_w41 = test_vector[1] - sup_vectors[1][3];

diff_w4 = diff_w2 * diff_w28;
diff_w12 = diff_w10 * diff_w27;
diff_w14 = diff_w6 * diff_w26;
diff_w47 = diff_w39 * diff_w11;
diff_w44 = diff_w48 * diff_w3;
diff_w17 = diff_w15 * diff_w23;
diff_w38 = diff_w40 * diff_w1;
diff_w46 = diff_w41 * diff_w8;
diff_w34 = diff_w33 * diff_w16;
diff_w31 = diff_w25 * diff_w20;
diff_w30 = diff_w29 * diff_w19;
diff_w32 = diff_w7 * diff_w13;
diff_w36 = diff_w43 * diff_w5;
diff_w24 = diff_w22 * diff_w21;
diff_w37 = diff_w35 * diff_w18;
diff_w45 = diff_w42 * diff_w9;

norma_w2 = diff_w14 + diff_w12;
norma_w7 = 0 + diff_w36;
norma_w4 = 0 + diff_w24;
norma_w15 = diff_w47 + diff_w45;
norma_w13 = 0 + diff_w37;
norma_w9 = diff_w46 + diff_w44;
norma_w6 = diff_w31 + diff_w30;
norma_w1 = 0 + diff_w4;

norma_w16 = norma_w13 + norma_w15;
norma_w5 = norma_w7 + norma_w9;
norma_w10 = norma_w4 + norma_w6;
norma_w14 = norma_w1 + norma_w2;

norma_w3 = norma_w14 + diff_w17;
norma_w8 = norma_w10 + diff_w34;
norma_w12 = norma_w5 + diff_w38;
norma_w11 = norma_w16 + diff_w32;

temp_l111_1_i1_w1 = exp(norma_w3 * (-gamma_w3));
temp_l111_1_i3_w1 = exp(norma_w8 * (-gamma_w4));
temp_l111_1_i5_w1 = exp(norma_w11 * (-gamma_w1));
temp_l111_1_i7_w1 = exp(norma_w12 * (-gamma_w2));

temp_l111_2_i8_w1 = sv_coeff[3] * exp(temp_l111_1_i7_w1);
temp_l111_2_i2_w1 = sv_coeff[0] * exp(temp_l111_1_i1_w1);
temp_l111_2_i6_w1 = sv_coeff[2] * exp(temp_l111_1_i5_w1);
temp_l111_2_i4_w1 = sv_coeff[1] * exp(temp_l111_1_i3_w1);

sum_w1 = sum_w2 + temp_l111_2_i2_w1;
sum_w4 = temp_l111_2_i4_w1 + temp_l111_2_i6_w1;

sum_w5 = sum_w1 + sum_w4;

sum_w3 = sum_w5 + temp_l111_2_i8_w1;

*sum = sum_w3 - b_w1;

}
