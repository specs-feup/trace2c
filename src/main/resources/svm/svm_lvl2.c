
void svm_lvl2(float test_vector[4], float sup_vectors[4][4], float sv_coeff[4], float *sum)
// Step 2: Initialize local variables
int diff_w1;
int diff_w2;
int diff_w3;
int diff_w4;
int norma_w1;
int diff_w5;
int diff_w6;
int diff_w7;
int diff_w8;
int norma_w2;
int diff_w9;
int diff_w10;
int diff_w11;
int diff_w12;
int diff_w13;
int diff_w14;
int gamma_w1;
int diff_w15;
int diff_w16;
int diff_w17;
int diff_w18;
int norma_w3;
int gamma_w2;
int diff_w19;
int temp_l111_1_i1_w1;
int diff_w20;
int temp_l111_2_i2_w1;
int diff_w21;
int *sum_w1;
int gamma_w3;
int diff_w22;
int diff_w23;
int diff_w24;
int *sum_w2;
int norma_w4;
int diff_w25;
int diff_w26;
int norma_w5;
int diff_w27;
int norma_w6;
int diff_w28;
int diff_w29;
int diff_w30;
int b_w1;
int diff_w31;
int diff_w32;
int diff_w33;
int *sum_w3;
int diff_w34;
int norma_w7;
int norma_w8;
int norma_w9;
int temp_l111_1_i3_w1;
int temp_l111_2_i8_w1;
int temp_l111_2_i4_w1;
int norma_w10;
int norma_w11;
int *sum_w4;
int norma_w12;
int diff_w35;
int temp_l111_2_i6_w1;
int diff_w36;
int diff_w37;
int diff_w38;
int gamma_w4;
int norma_w13;
int diff_w39;
int diff_w40;
int *sum_w5;
int norma_w14;
int temp_l111_1_i5_w1;
int norma_w15;
int diff_w41;
int diff_w42;
int diff_w43;
int diff_w44;
int diff_w45;
int temp_l111_1_i7_w1;
int diff_w46;
int diff_w47;
int diff_w48;
int norma_w16;
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

*sum_w1 = *sum_w2 + temp_l111_2_i2_w1;
*sum_w4 = temp_l111_2_i4_w1 + temp_l111_2_i6_w1;

*sum_w5 = *sum_w1 + *sum_w4;

*sum_w3 = *sum_w5 + temp_l111_2_i8_w1;

*sum = *sum_w3 - b_w1;

}
