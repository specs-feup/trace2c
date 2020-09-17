
void parallel_0(short x[256], short y[256], int sum_array[16])
{
  // Step 2: Initialize local variables
  int sum_w1;
  ... 
  int sum_w14;

  int temp_l83_i14_w1;
  ... 
  int temp_l83_i29_w1;
  // Initialization done
  // starting Loop
  for (int i = 0; i < 16; i = i + 1) {
  #pragma HLS pipeline

    temp_l83_i14_w1 = x[(16) * i] * y[(16) * i];
    temp_l83_i15_w1 = x[(16) * i + 1] * y[(16) * i + 1];
    temp_l83_i16_w1 = x[(16) * i + 2] * y[(16) * i + 2];
    temp_l83_i17_w1 = x[(16) * i + 3] * y[(16) * i + 3];
    temp_l83_i18_w1 = x[(16) * i + 4] * y[(16) * i + 4];
    temp_l83_i19_w1 = x[(16) * i + 5] * y[(16) * i + 5];
    temp_l83_i20_w1 = x[(16) * i + 6] * y[(16) * i + 6];
    temp_l83_i21_w1 = x[(16) * i + 7] * y[(16) * i + 7];
    temp_l83_i22_w1 = x[(16) * i + 8] * y[(16) * i + 8];
    temp_l83_i23_w1 = x[(16) * i + 9] * y[(16) * i + 9];
    temp_l83_i24_w1 = x[(16) * i + 10] * y[(16) * i + 10];
    temp_l83_i25_w1 = x[(16) * i + 11] * y[(16) * i + 11];
    temp_l83_i26_w1 = x[(16) * i + 12] * y[(16) * i + 12];
    temp_l83_i27_w1 = x[(16) * i + 13] * y[(16) * i + 13];
    temp_l83_i28_w1 = x[(16) * i + 14] * y[(16) * i + 14];
    temp_l83_i29_w1 = x[(16) * i + 15] * y[(16) * i + 15];

    sum_w13 = temp_l83_i14_w1 + temp_l83_i15_w1;
    sum_w14 = temp_l83_i16_w1 + temp_l83_i17_w1;
    sum_w11 = temp_l83_i18_w1 + temp_l83_i19_w1;
    sum_w12 = temp_l83_i20_w1 + temp_l83_i21_w1;
    sum_w1 = temp_l83_i22_w1 + temp_l83_i23_w1;
    sum_w2 = temp_l83_i24_w1 + temp_l83_i25_w1;
    sum_w5 = temp_l83_i26_w1 + temp_l83_i27_w1;
    sum_w6 = temp_l83_i28_w1 + temp_l83_i29_w1;

    sum_w3 = sum_w1 + sum_w2;
    sum_w8 = sum_w11 + sum_w12;
    sum_w7 = sum_w13 + sum_w14;
    sum_w4 = sum_w5 + sum_w6;

    sum_w10 = sum_w3 + sum_w4;
    sum_w9 = sum_w7 + sum_w8;

    sum_array[i] = sum_w9 + sum_w10;
  }
}

void parallel_1(short x[192], short y[192], int sum_array[12])
{
  // Step 2: Initialize local variables
  int sum_w1;
  ... 
  int sum_w14;

  int temp_l83_i1806_w1;
  ... 
  int temp_l83_i1821_w1;
  // Initialization done
  // starting Loop
  for (int i = 0; i < 12; i = i + 1) {
  #pragma HLS pipeline

    temp_l83_i1806_w1 = x[(16) * i] * y[(16) * i];
    temp_l83_i1807_w1 = x[(16) * i + 1] * y[(16) * i + 1];
    temp_l83_i1808_w1 = x[(16) * i + 2] * y[(16) * i + 2];
    temp_l83_i1809_w1 = x[(16) * i + 3] * y[(16) * i + 3];
    temp_l83_i1810_w1 = x[(16) * i + 4] * y[(16) * i + 4];
    temp_l83_i1811_w1 = x[(16) * i + 5] * y[(16) * i + 5];
    temp_l83_i1812_w1 = x[(16) * i + 6] * y[(16) * i + 6];
    temp_l83_i1813_w1 = x[(16) * i + 7] * y[(16) * i + 7];
    temp_l83_i1814_w1 = x[(16) * i + 8] * y[(16) * i + 8];
    temp_l83_i1815_w1 = x[(16) * i + 9] * y[(16) * i + 9];
    temp_l83_i1816_w1 = x[(16) * i + 10] * y[(16) * i + 10];
    temp_l83_i1817_w1 = x[(16) * i + 11] * y[(16) * i + 11];
    temp_l83_i1818_w1 = x[(16) * i + 12] * y[(16) * i + 12];
    temp_l83_i1819_w1 = x[(16) * i + 13] * y[(16) * i + 13];
    temp_l83_i1820_w1 = x[(16) * i + 14] * y[(16) * i + 14];
    temp_l83_i1821_w1 = x[(16) * i + 15] * y[(16) * i + 15];

    sum_w11 = temp_l83_i1806_w1 + temp_l83_i1807_w1;
    sum_w12 = temp_l83_i1808_w1 + temp_l83_i1809_w1;
    sum_w13 = temp_l83_i1810_w1 + temp_l83_i1811_w1;
    sum_w14 = temp_l83_i1812_w1 + temp_l83_i1813_w1;
    sum_w7 = temp_l83_i1814_w1 + temp_l83_i1815_w1;
    sum_w8 = temp_l83_i1816_w1 + temp_l83_i1817_w1;
    sum_w3 = temp_l83_i1818_w1 + temp_l83_i1819_w1;
    sum_w4 = temp_l83_i1820_w1 + temp_l83_i1821_w1;

    sum_w9 = sum_w11 + sum_w12;
    sum_w10 = sum_w13 + sum_w14;
    sum_w6 = sum_w3 + sum_w4;
    sum_w5 = sum_w7 + sum_w8;

    sum_w2 = sum_w5 + sum_w6;
    sum_w1 = sum_w9 + sum_w10;

    sum_array[i] = sum_w1 + sum_w2;
  }
}

void epilogue(int sum_array_3[16], int sum_array_2[16], short y_9[3], int sum_array_5[16], short x_9[3], int sum_array_4[16], int sum_array_7[12], int sum_array_6[16], short x_0[13], short y_0[13], int sum_array_1[16], int sum_array_0[16], int *out)
{
  // Step 2: Initialize local variables
  int sum_w1;
  ... 
  int sum_w139;

  int temp_l83_i1_w1;
  ... 
  int temp_l83_i2000_w1;

  // Initialization done
  sum_w137 = sum_array_0[0] + sum_array_0[1];
  ... 
  temp_l83_i1_w1 = x_0[0] * y_0[0];
  ... 
  temp_l83_i2000_w1 = x_9[2] * y_9[2];

  sum_w123 = sum_w100 + sum_w101;
  ... 
  sum_w79 = sum_w104 + sum_w105;

  *out = sum_w79 + temp_l83_i2000_w1;
}

void dotprod_parallel8(short x_0[13], short x_1[256], short x_2[256], short x_3[256], short x_4[256], short x_5[256], short x_6[256], short x_7[256], short x_8[192], short x_9[3], short y_0[13], short y_1[256], short y_2[256], short y_3[256], short y_4[256], short y_5[256], short y_6[256], short y_7[256], short y_8[192], short y_9[3], int *out) {
  // Step 2: Initialize local variables
  int sum_array_0[16];
  int sum_array_1[16];
  int sum_array_2[16];
  int sum_array_3[16];
  int sum_array_4[16];
  int sum_array_5[16];
  int sum_array_6[16];
  int sum_array_7[12];
#pragma HLS ARRAY_PARTITION variable = x_0 cyclic factor = 13 dim = 0
#pragma HLS ARRAY_PARTITION variable = x_1 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = x_2 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = x_3 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = x_4 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = x_5 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = x_6 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = x_7 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = x_8 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = x_9 cyclic factor = 3 dim = 0
#pragma HLS ARRAY_PARTITION variable = y_0 cyclic factor = 13 dim = 0
#pragma HLS ARRAY_PARTITION variable = y_1 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = y_2 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = y_3 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = y_4 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = y_5 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = y_6 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = y_7 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = y_8 cyclic factor = 16 dim = 0
#pragma HLS ARRAY_PARTITION variable = y_9 cyclic factor = 3 dim = 0
#pragma HLS ARRAY_PARTITION variable = sum_array_0 cyclic factor = 2 dim = 0
#pragma HLS ARRAY_PARTITION variable = sum_array_1 cyclic factor = 2 dim = 0
#pragma HLS ARRAY_PARTITION variable = sum_array_2 cyclic factor = 2 dim = 0
#pragma HLS ARRAY_PARTITION variable = sum_array_3 cyclic factor = 2 dim = 0
#pragma HLS ARRAY_PARTITION variable = sum_array_4 cyclic factor = 2 dim = 0
#pragma HLS ARRAY_PARTITION variable = sum_array_5 cyclic factor = 2 dim = 0
#pragma HLS ARRAY_PARTITION variable = sum_array_6 cyclic factor = 2 dim = 0
#pragma HLS ARRAY_PARTITION variable = sum_array_7 cyclic factor = 2 dim = 0
// Initialization done
#pragma HLS dataflow

  parallel_0(x_1, y_1, sum_array_0);
  parallel_0(x_2, y_2, sum_array_1);
  parallel_0(x_3, y_3, sum_array_2);
  parallel_0(x_4, y_4, sum_array_3);
  parallel_0(x_5, y_5, sum_array_4);
  parallel_0(x_6, y_6, sum_array_5);
  parallel_0(x_7, y_7, sum_array_6);
  parallel_1(x_8, y_8, sum_array_7);

  epilogue(sum_array_3, sum_array_2, y_9, sum_array_5, x_9, sum_array_4, sum_array_7, sum_array_6, x_0, y_0, sum_array_1, sum_array_0, out);
}
