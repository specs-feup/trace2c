
void parallel_0(float sup_vectors[18][80], float test_vector[18], float sv_coeff[80], int width, float temp_l111_2_i2_array[80])
{
    // Step 2: Initialize local variables
    float diff_w1;
    float norma_w1;
    float temp_l111_1_i1_w1;
    ...
    // Initialization done
    // starting Loop
    for (int i = 0; i < width; i = i + 1) {
    #pragma HLS pipeline

        diff_w16 = test_vector[9] - sup_vectors[9][i];
        diff_w10 = test_vector[8] - sup_vectors[8][i];
        diff_w18 = test_vector[17] - sup_vectors[17][i];
        diff_w9 = test_vector[10] - sup_vectors[10][i];
        diff_w4 = test_vector[2] - sup_vectors[2][i];
        diff_w11 = test_vector[5] - sup_vectors[5][i];
        diff_w12 = test_vector[11] - sup_vectors[11][i];
        diff_w14 = test_vector[1] - sup_vectors[1][i];
        diff_w5 = test_vector[14] - sup_vectors[14][i];
        diff_w17 = test_vector[15] - sup_vectors[15][i];
        diff_w7 = test_vector[12] - sup_vectors[12][i];
        diff_w13 = test_vector[13] - sup_vectors[13][i];
        diff_w1 = test_vector[7] - sup_vectors[7][i];
        diff_w8 = test_vector[0] - sup_vectors[0][i];
        diff_w15 = test_vector[3] - sup_vectors[3][i];
        diff_w3 = test_vector[6] - sup_vectors[6][i];
        diff_w2 = test_vector[4] - sup_vectors[4][i];
        diff_w6 = test_vector[16] - sup_vectors[16][i];

        norma_w14 = 0 + square(diff_w8);
        norma_w4 = norma_w14 + square(diff_w14);
        norma_w15 = norma_w4 + square(diff_w4);
        norma_w2 = norma_w15 + square(diff_w15);
        norma_w11 = norma_w2 + square(diff_w2);
        norma_w3 = norma_w11 + square(diff_w11);
        norma_w1 = norma_w3 + square(diff_w3);
        norma_w9 = norma_w1 + square(diff_w1);
        norma_w16 = norma_w9 + square(diff_w10);
        norma_w8 = norma_w16 + square(diff_w16);
        norma_w12 = norma_w8 + square(diff_w9);
        norma_w7 = norma_w12 + square(diff_w12);
        norma_w13 = norma_w7 + square(diff_w7);
        norma_w5 = norma_w13 + square(diff_w13);
        norma_w17 = norma_w5 + square(diff_w5);
        norma_w6 = norma_w17 + square(diff_w17);
        norma_w18 = norma_w6 + square(diff_w6);
        norma_w10 = norma_w18 + square(diff_w18);
        temp_l111_1_i1_w1 = norma_w10 * (-gamma);
        temp_l111_2_i2_array[i] = sv_coeff[i] * expf(temp_l111_1_i1_w1);
    }
}

void epilogue(float temp_l111_2_i2_array_15[80], float temp_l111_2_i2_array_14[80], float temp_l111_2_i2_array_12[80], float temp_l111_2_i2_array_10[80], float temp_l111_2_i2_array_7[80], float temp_l111_2_i2_array_1[80], float temp_l111_2_i2_array_3[80], float temp_l111_2_i2_array_11[80], float temp_l111_2_i2_array_0[80], float temp_l111_2_i2_array_9[80], float temp_l111_2_i2_array_6[80], float temp_l111_2_i2_array_8[80], float temp_l111_2_i2_array_2[80], float temp_l111_2_i2_array_13[80], float temp_l111_2_i2_array_4[80], float temp_l111_2_i2_array_5[80], int *y)
{
    // Initialize local variables
    double operationOutput_w1;
    float sum_w1;
    float sum_w2;
    float sum_w3;
    ...
    // Initialization done
    sum_w787 = temp_l111_2_i2_array_3[7] + temp_l111_2_i2_array_3[8];
    sum_w672 = temp_l111_2_i2_array_8[29] + temp_l111_2_i2_array_8[30];
    sum_w368 = temp_l111_2_i2_array_4[51] + temp_l111_2_i2_array_4[52];
    ... 
    sum_w1191 = sum_w5 + sum_w6;
    sum_w1189 = sum_w295 + sum_w296;
    sum_w437 = sum_w692 + sum_w693;
    ... 
    sum_w669 = sum_w1159 - b;
    operationOutput_w1 = sum_w669 < 0;

    *y = operationOutput_w1 ? -1 : 1;
}

void svmResult(int *y)
{
    // Step 2: Initialize local variables
    float test_vector[18];
    float sup_vectors_0[18][80];
    ... 
    float sup_vectors_15[18][80];
    float sv_coeff_0[80];
    ... 
    float sv_coeff_15[80];
    float temp_l111_2_i2_array_0[80];
    ... 
    float temp_l111_2_i2_array_15[80];
    // Initialization done
    #pragma HLS dataflow

    parallel_0(sup_vectors_0, test_vector, sv_coeff_0, 80, temp_l111_2_i2_array_0);
    parallel_0(sup_vectors_1, test_vector, sv_coeff_1, 80, temp_l111_2_i2_array_1);
    parallel_0(sup_vectors_2, test_vector, sv_coeff_2, 80, temp_l111_2_i2_array_2);
    parallel_0(sup_vectors_3, test_vector, sv_coeff_3, 80, temp_l111_2_i2_array_3);
    parallel_0(sup_vectors_4, test_vector, sv_coeff_4, 80, temp_l111_2_i2_array_4);
    parallel_0(sup_vectors_5, test_vector, sv_coeff_5, 80, temp_l111_2_i2_array_5);
    parallel_0(sup_vectors_6, test_vector, sv_coeff_6, 80, temp_l111_2_i2_array_6);
    parallel_0(sup_vectors_7, test_vector, sv_coeff_7, 80, temp_l111_2_i2_array_7);
    parallel_0(sup_vectors_8, test_vector, sv_coeff_8, 80, temp_l111_2_i2_array_8);
    parallel_0(sup_vectors_9, test_vector, sv_coeff_9, 80, temp_l111_2_i2_array_9);
    parallel_0(sup_vectors_10, test_vector, sv_coeff_10, 80, temp_l111_2_i2_array_10);
    parallel_0(sup_vectors_11, test_vector, sv_coeff_11, 80, temp_l111_2_i2_array_11);
    parallel_0(sup_vectors_12, test_vector, sv_coeff_12, 80, temp_l111_2_i2_array_12);
    parallel_0(sup_vectors_13, test_vector, sv_coeff_13, 80, temp_l111_2_i2_array_13);
    parallel_0(sup_vectors_14, test_vector, sv_coeff_14, 80, temp_l111_2_i2_array_14);
    parallel_0(sup_vectors_15, test_vector, sv_coeff_15, 74, temp_l111_2_i2_array_15);

    epilogue(temp_l111_2_i2_array_15, temp_l111_2_i2_array_14,
             temp_l111_2_i2_array_12, temp_l111_2_i2_array_10, temp_l111_2_i2_array_7,
             temp_l111_2_i2_array_1, temp_l111_2_i2_array_3, temp_l111_2_i2_array_11,
             temp_l111_2_i2_array_0, temp_l111_2_i2_array_9, temp_l111_2_i2_array_6,
             temp_l111_2_i2_array_8, temp_l111_2_i2_array_2, temp_l111_2_i2_array_13,
             temp_l111_2_i2_array_4, temp_l111_2_i2_array_5, y);
}
