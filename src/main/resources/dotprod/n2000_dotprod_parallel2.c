
void parallel_0(short x[992], short y[992], int sum_array[62]) {
// Step 2: Initialize local variables
int sum_w1;
int sum_w10;
int sum_w11;
int sum_w12;
int sum_w13;
int sum_w14;
int sum_w2;
int sum_w3;
int sum_w4;
int sum_w5;
int sum_w6;
int sum_w7;
int sum_w8;
int sum_w9;
int temp_l83_i14_w1;
int temp_l83_i15_w1;
int temp_l83_i16_w1;
int temp_l83_i17_w1;
int temp_l83_i18_w1;
int temp_l83_i19_w1;
int temp_l83_i20_w1;
int temp_l83_i21_w1;
int temp_l83_i22_w1;
int temp_l83_i23_w1;
int temp_l83_i24_w1;
int temp_l83_i25_w1;
int temp_l83_i26_w1;
int temp_l83_i27_w1;
int temp_l83_i28_w1;
int temp_l83_i29_w1;
// Initialization done
// starting Loop
for( int i = 0; i < 62;i=i+1){
#pragma HLS pipeline


temp_l83_i14_w1 = x[(16)*i] * y[(16)*i];
temp_l83_i15_w1 = x[(16)*i+1] * y[(16)*i+1];
temp_l83_i16_w1 = x[(16)*i+2] * y[(16)*i+2];
temp_l83_i17_w1 = x[(16)*i+3] * y[(16)*i+3];
temp_l83_i18_w1 = x[(16)*i+4] * y[(16)*i+4];
temp_l83_i19_w1 = x[(16)*i+5] * y[(16)*i+5];
temp_l83_i20_w1 = x[(16)*i+6] * y[(16)*i+6];
temp_l83_i21_w1 = x[(16)*i+7] * y[(16)*i+7];
temp_l83_i22_w1 = x[(16)*i+8] * y[(16)*i+8];
temp_l83_i23_w1 = x[(16)*i+9] * y[(16)*i+9];
temp_l83_i24_w1 = x[(16)*i+10] * y[(16)*i+10];
temp_l83_i25_w1 = x[(16)*i+11] * y[(16)*i+11];
temp_l83_i26_w1 = x[(16)*i+12] * y[(16)*i+12];
temp_l83_i27_w1 = x[(16)*i+13] * y[(16)*i+13];
temp_l83_i28_w1 = x[(16)*i+14] * y[(16)*i+14];
temp_l83_i29_w1 = x[(16)*i+15] * y[(16)*i+15];

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

void epilogue(short x_0[13], short y_0[13], short y_3[3], short x_3[3], int sum_array_1[62], int sum_array_0[62], int *out) {
// Step 2: Initialize local variables
int sum_w1;
int sum_w10;
int sum_w100;
int sum_w101;
int sum_w102;
int sum_w103;
int sum_w104;
int sum_w105;
int sum_w106;
int sum_w107;
int sum_w108;
int sum_w109;
int sum_w11;
int sum_w110;
int sum_w111;
int sum_w112;
int sum_w113;
int sum_w114;
int sum_w115;
int sum_w116;
int sum_w117;
int sum_w118;
int sum_w119;
int sum_w12;
int sum_w120;
int sum_w121;
int sum_w122;
int sum_w123;
int sum_w124;
int sum_w125;
int sum_w126;
int sum_w127;
int sum_w128;
int sum_w129;
int sum_w13;
int sum_w130;
int sum_w131;
int sum_w132;
int sum_w133;
int sum_w134;
int sum_w135;
int sum_w136;
int sum_w137;
int sum_w138;
int sum_w139;
int sum_w14;
int sum_w15;
int sum_w16;
int sum_w17;
int sum_w18;
int sum_w19;
int sum_w2;
int sum_w20;
int sum_w21;
int sum_w22;
int sum_w23;
int sum_w24;
int sum_w25;
int sum_w26;
int sum_w27;
int sum_w28;
int sum_w29;
int sum_w3;
int sum_w30;
int sum_w31;
int sum_w32;
int sum_w33;
int sum_w34;
int sum_w35;
int sum_w36;
int sum_w37;
int sum_w38;
int sum_w39;
int sum_w4;
int sum_w40;
int sum_w41;
int sum_w42;
int sum_w43;
int sum_w44;
int sum_w45;
int sum_w46;
int sum_w47;
int sum_w48;
int sum_w49;
int sum_w5;
int sum_w50;
int sum_w51;
int sum_w52;
int sum_w53;
int sum_w54;
int sum_w55;
int sum_w56;
int sum_w57;
int sum_w58;
int sum_w59;
int sum_w6;
int sum_w60;
int sum_w61;
int sum_w62;
int sum_w63;
int sum_w64;
int sum_w65;
int sum_w66;
int sum_w67;
int sum_w68;
int sum_w69;
int sum_w7;
int sum_w70;
int sum_w71;
int sum_w72;
int sum_w73;
int sum_w74;
int sum_w75;
int sum_w76;
int sum_w77;
int sum_w78;
int sum_w79;
int sum_w8;
int sum_w80;
int sum_w81;
int sum_w82;
int sum_w83;
int sum_w84;
int sum_w85;
int sum_w86;
int sum_w87;
int sum_w88;
int sum_w89;
int sum_w9;
int sum_w90;
int sum_w91;
int sum_w92;
int sum_w93;
int sum_w94;
int sum_w95;
int sum_w96;
int sum_w97;
int sum_w98;
int sum_w99;
int temp_l83_i10_w1;
int temp_l83_i11_w1;
int temp_l83_i12_w1;
int temp_l83_i13_w1;
int temp_l83_i1998_w1;
int temp_l83_i1999_w1;
int temp_l83_i1_w1;
int temp_l83_i2000_w1;
int temp_l83_i2_w1;
int temp_l83_i3_w1;
int temp_l83_i4_w1;
int temp_l83_i5_w1;
int temp_l83_i6_w1;
int temp_l83_i7_w1;
int temp_l83_i8_w1;
int temp_l83_i9_w1;
// Initialization done
sum_w137 = sum_array_0[0] + sum_array_0[1];
sum_w114 = sum_array_0[2] + sum_array_0[3];
sum_w115 = sum_array_0[4] + sum_array_0[5];
sum_w69 = sum_array_0[6] + sum_array_0[7];
sum_w70 = sum_array_0[8] + sum_array_0[9];
sum_w130 = sum_array_0[10] + sum_array_0[11];
sum_w131 = sum_array_0[12] + sum_array_0[13];
sum_w61 = sum_array_0[14] + sum_array_0[15];
sum_w62 = sum_array_0[16] + sum_array_0[17];
sum_w21 = sum_array_0[18] + sum_array_0[19];
sum_w22 = sum_array_0[20] + sum_array_0[21];
sum_w29 = sum_array_0[22] + sum_array_0[23];
sum_w30 = sum_array_0[24] + sum_array_0[25];
sum_w92 = sum_array_0[26] + sum_array_0[27];
sum_w93 = sum_array_0[28] + sum_array_0[29];
sum_w37 = sum_array_0[30] + sum_array_0[31];
sum_w38 = sum_array_0[32] + sum_array_0[33];
sum_w3 = sum_array_0[34] + sum_array_0[35];
sum_w4 = sum_array_0[36] + sum_array_0[37];
sum_w118 = sum_array_0[38] + sum_array_0[39];
sum_w119 = sum_array_0[40] + sum_array_0[41];
sum_w132 = sum_array_0[42] + sum_array_0[43];
sum_w133 = sum_array_0[44] + sum_array_0[45];
sum_w106 = sum_array_0[46] + sum_array_0[47];
sum_w107 = sum_array_0[48] + sum_array_0[49];
sum_w82 = sum_array_0[50] + sum_array_0[51];
sum_w83 = sum_array_0[52] + sum_array_0[53];
sum_w100 = sum_array_0[54] + sum_array_0[55];
sum_w101 = sum_array_0[56] + sum_array_0[57];
sum_w27 = sum_array_0[58] + sum_array_0[59];
sum_w28 = sum_array_0[60] + sum_array_0[61];
sum_w84 = sum_array_1[0] + sum_array_1[1];
sum_w85 = sum_array_1[2] + sum_array_1[3];
sum_w41 = sum_array_1[4] + sum_array_1[5];
sum_w42 = sum_array_1[6] + sum_array_1[7];
sum_w39 = sum_array_1[8] + sum_array_1[9];
sum_w40 = sum_array_1[10] + sum_array_1[11];
sum_w35 = sum_array_1[12] + sum_array_1[13];
sum_w36 = sum_array_1[14] + sum_array_1[15];
sum_w49 = sum_array_1[16] + sum_array_1[17];
sum_w50 = sum_array_1[18] + sum_array_1[19];
sum_w112 = sum_array_1[20] + sum_array_1[21];
sum_w113 = sum_array_1[22] + sum_array_1[23];
sum_w63 = sum_array_1[24] + sum_array_1[25];
sum_w64 = sum_array_1[26] + sum_array_1[27];
sum_w55 = sum_array_1[28] + sum_array_1[29];
sum_w56 = sum_array_1[30] + sum_array_1[31];
sum_w77 = sum_array_1[32] + sum_array_1[33];
sum_w78 = sum_array_1[34] + sum_array_1[35];
sum_w53 = sum_array_1[36] + sum_array_1[37];
sum_w54 = sum_array_1[38] + sum_array_1[39];
sum_w88 = sum_array_1[40] + sum_array_1[41];
sum_w89 = sum_array_1[42] + sum_array_1[43];
sum_w5 = sum_array_1[44] + sum_array_1[45];
sum_w6 = sum_array_1[46] + sum_array_1[47];
sum_w17 = sum_array_1[48] + sum_array_1[49];
sum_w18 = sum_array_1[50] + sum_array_1[51];
sum_w13 = sum_array_1[52] + sum_array_1[53];
sum_w14 = sum_array_1[54] + sum_array_1[55];
sum_w45 = sum_array_1[56] + sum_array_1[57];
sum_w46 = sum_array_1[58] + sum_array_1[59];
sum_w16 = sum_array_1[60] + sum_array_1[61];
temp_l83_i1_w1 = x_0[0] * y_0[0];
temp_l83_i2_w1 = x_0[1] * y_0[1];
temp_l83_i3_w1 = x_0[2] * y_0[2];
temp_l83_i4_w1 = x_0[3] * y_0[3];
temp_l83_i5_w1 = x_0[4] * y_0[4];
temp_l83_i6_w1 = x_0[5] * y_0[5];
temp_l83_i7_w1 = x_0[6] * y_0[6];
temp_l83_i8_w1 = x_0[7] * y_0[7];
temp_l83_i9_w1 = x_0[8] * y_0[8];
temp_l83_i10_w1 = x_0[9] * y_0[9];
temp_l83_i11_w1 = x_0[10] * y_0[10];
temp_l83_i12_w1 = x_0[11] * y_0[11];
temp_l83_i13_w1 = x_0[12] * y_0[12];
temp_l83_i1998_w1 = x_3[0] * y_3[0];
temp_l83_i1999_w1 = x_3[1] * y_3[1];
temp_l83_i2000_w1 = x_3[2] * y_3[2];

sum_w123 = sum_w100 + sum_w101;
sum_w125 = sum_w106 + sum_w107;
sum_w47 = sum_w112 + sum_w113;
sum_w9 = sum_w114 + sum_w115;
sum_w32 = sum_w118 + sum_w119;
sum_w59 = sum_w13 + sum_w14;
sum_w110 = sum_w130 + sum_w131;
sum_w124 = sum_w132 + sum_w133;
sum_w103 = sum_w17 + sum_w18;
sum_w80 = sum_w21 + sum_w22;
sum_w7 = sum_w27 + sum_w28;
sum_w81 = sum_w29 + sum_w30;
sum_w31 = sum_w3 + sum_w4;
sum_w71 = sum_w35 + sum_w36;
sum_w44 = sum_w37 + sum_w38;
sum_w139 = sum_w39 + sum_w40;
sum_w138 = sum_w41 + sum_w42;
sum_w60 = sum_w45 + sum_w46;
sum_w72 = sum_w49 + sum_w50;
sum_w102 = sum_w5 + sum_w6;
sum_w90 = sum_w53 + sum_w54;
sum_w33 = sum_w55 + sum_w56;
sum_w111 = sum_w61 + sum_w62;
sum_w48 = sum_w63 + sum_w64;
sum_w10 = sum_w69 + sum_w70;
sum_w34 = sum_w77 + sum_w78;
sum_w122 = sum_w82 + sum_w83;
sum_w8 = sum_w84 + sum_w85;
sum_w91 = sum_w88 + sum_w89;
sum_w43 = sum_w92 + sum_w93;
sum_w128 = temp_l83_i10_w1 + temp_l83_i11_w1;
sum_w129 = temp_l83_i12_w1 + temp_l83_i13_w1;
sum_w15 = temp_l83_i1998_w1 + temp_l83_i1999_w1;
sum_w94 = 0 + temp_l83_i1_w1;
sum_w134 = temp_l83_i2_w1 + temp_l83_i3_w1;
sum_w135 = temp_l83_i4_w1 + temp_l83_i5_w1;
sum_w86 = temp_l83_i6_w1 + temp_l83_i7_w1;
sum_w87 = temp_l83_i8_w1 + temp_l83_i9_w1;

sum_w20 = sum_w102 + sum_w103;
sum_w117 = sum_w110 + sum_w111;
sum_w67 = sum_w122 + sum_w123;
sum_w74 = sum_w124 + sum_w125;
sum_w99 = sum_w128 + sum_w129;
sum_w95 = sum_w134 + sum_w135;
sum_w1 = sum_w138 + sum_w139;
sum_w11 = sum_w16 + sum_w15;
sum_w73 = sum_w31 + sum_w32;
sum_w97 = sum_w33 + sum_w34;
sum_w109 = sum_w43 + sum_w44;
sum_w96 = sum_w47 + sum_w48;
sum_w12 = sum_w59 + sum_w60;
sum_w68 = sum_w7 + sum_w8;
sum_w2 = sum_w71 + sum_w72;
sum_w108 = sum_w80 + sum_w81;
sum_w98 = sum_w86 + sum_w87;
sum_w116 = sum_w9 + sum_w10;
sum_w19 = sum_w90 + sum_w91;

sum_w23 = sum_w1 + sum_w2;
sum_w58 = sum_w108 + sum_w109;
sum_w76 = sum_w12 + sum_w11;
sum_w57 = sum_w116 + sum_w117;
sum_w75 = sum_w19 + sum_w20;
sum_w127 = sum_w67 + sum_w68;
sum_w126 = sum_w73 + sum_w74;
sum_w51 = sum_w94 + sum_w95;
sum_w24 = sum_w96 + sum_w97;
sum_w52 = sum_w98 + sum_w99;

sum_w121 = sum_w126 + sum_w127;
sum_w65 = sum_w23 + sum_w24;
sum_w136 = sum_w51 + sum_w52;
sum_w120 = sum_w57 + sum_w58;
sum_w66 = sum_w75 + sum_w76;

sum_w25 = sum_w120 + sum_w121;
sum_w104 = sum_w136 + sum_w137;
sum_w26 = sum_w65 + sum_w66;

sum_w105 = sum_w25 + sum_w26;

sum_w79 = sum_w104 + sum_w105;

*out = sum_w79 + temp_l83_i2000_w1;

}

void dotprod_parallel2(short y_0[13], short y_1[992], short y_2[992], short y_3[3], short x_0[13], short x_1[992], short x_2[992], short x_3[3], int *out) {
// Step 2: Initialize local variables
int sum_array_0[62];
int sum_array_1[62];
#pragma HLS ARRAY_PARTITION variable=y_0 cyclic factor=13 dim=0
#pragma HLS ARRAY_PARTITION variable=y_1 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=y_2 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=y_3 cyclic factor=3 dim=0
#pragma HLS ARRAY_PARTITION variable=x_0 cyclic factor=13 dim=0
#pragma HLS ARRAY_PARTITION variable=x_1 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=x_2 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=x_3 cyclic factor=3 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_0 cyclic factor=2 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_1 cyclic factor=2 dim=0
// Initialization done
#pragma HLS dataflow

parallel_0(x_1,y_1,sum_array_0);
parallel_0(x_2,y_2,sum_array_1);


epilogue(x_0,y_0,y_3,x_3,sum_array_1,sum_array_0,out);

}
