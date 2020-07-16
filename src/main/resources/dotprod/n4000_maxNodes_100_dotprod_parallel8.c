
void parallel_0(short x[512], short y[512], int sum_array[32]) {
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
for( int i = 0; i < 32;i=i+1){
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

void parallel_1(short x[400], short y[400], int sum_array[25]) {
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
int temp_l83_i3598_w1;
int temp_l83_i3599_w1;
int temp_l83_i3600_w1;
int temp_l83_i3601_w1;
int temp_l83_i3602_w1;
int temp_l83_i3603_w1;
int temp_l83_i3604_w1;
int temp_l83_i3605_w1;
int temp_l83_i3606_w1;
int temp_l83_i3607_w1;
int temp_l83_i3608_w1;
int temp_l83_i3609_w1;
int temp_l83_i3610_w1;
int temp_l83_i3611_w1;
int temp_l83_i3612_w1;
int temp_l83_i3613_w1;
// Initialization done
// starting Loop
for( int i = 0; i < 25;i=i+1){
#pragma HLS pipeline


temp_l83_i3598_w1 = x[(16)*i] * y[(16)*i];
temp_l83_i3599_w1 = x[(16)*i+1] * y[(16)*i+1];
temp_l83_i3600_w1 = x[(16)*i+2] * y[(16)*i+2];
temp_l83_i3601_w1 = x[(16)*i+3] * y[(16)*i+3];
temp_l83_i3602_w1 = x[(16)*i+4] * y[(16)*i+4];
temp_l83_i3603_w1 = x[(16)*i+5] * y[(16)*i+5];
temp_l83_i3604_w1 = x[(16)*i+6] * y[(16)*i+6];
temp_l83_i3605_w1 = x[(16)*i+7] * y[(16)*i+7];
temp_l83_i3606_w1 = x[(16)*i+8] * y[(16)*i+8];
temp_l83_i3607_w1 = x[(16)*i+9] * y[(16)*i+9];
temp_l83_i3608_w1 = x[(16)*i+10] * y[(16)*i+10];
temp_l83_i3609_w1 = x[(16)*i+11] * y[(16)*i+11];
temp_l83_i3610_w1 = x[(16)*i+12] * y[(16)*i+12];
temp_l83_i3611_w1 = x[(16)*i+13] * y[(16)*i+13];
temp_l83_i3612_w1 = x[(16)*i+14] * y[(16)*i+14];
temp_l83_i3613_w1 = x[(16)*i+15] * y[(16)*i+15];

sum_w1 = temp_l83_i3598_w1 + temp_l83_i3599_w1;
sum_w2 = temp_l83_i3600_w1 + temp_l83_i3601_w1;
sum_w7 = temp_l83_i3602_w1 + temp_l83_i3603_w1;
sum_w8 = temp_l83_i3604_w1 + temp_l83_i3605_w1;
sum_w11 = temp_l83_i3606_w1 + temp_l83_i3607_w1;
sum_w12 = temp_l83_i3608_w1 + temp_l83_i3609_w1;
sum_w13 = temp_l83_i3610_w1 + temp_l83_i3611_w1;
sum_w14 = temp_l83_i3612_w1 + temp_l83_i3613_w1;

sum_w3 = sum_w1 + sum_w2;
sum_w9 = sum_w11 + sum_w12;
sum_w10 = sum_w13 + sum_w14;
sum_w4 = sum_w7 + sum_w8;

sum_w5 = sum_w3 + sum_w4;
sum_w6 = sum_w9 + sum_w10;

sum_array[i] = sum_w5 + sum_w6;

}
}

void epilogue(int sum_array_3[32], int sum_array_2[32], int sum_array_5[32], short y_9[3], int sum_array_4[32], short x_9[3], int sum_array_7[25], int sum_array_6[32], short x_0[13], short y_0[13], int sum_array_1[32], int sum_array_0[32], int *out) {
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
int sum_w140;
int sum_w141;
int sum_w142;
int sum_w143;
int sum_w144;
int sum_w145;
int sum_w146;
int sum_w147;
int sum_w148;
int sum_w149;
int sum_w15;
int sum_w150;
int sum_w151;
int sum_w152;
int sum_w153;
int sum_w154;
int sum_w155;
int sum_w156;
int sum_w157;
int sum_w158;
int sum_w159;
int sum_w16;
int sum_w160;
int sum_w161;
int sum_w162;
int sum_w163;
int sum_w164;
int sum_w165;
int sum_w166;
int sum_w167;
int sum_w168;
int sum_w169;
int sum_w17;
int sum_w170;
int sum_w171;
int sum_w172;
int sum_w173;
int sum_w174;
int sum_w175;
int sum_w176;
int sum_w177;
int sum_w178;
int sum_w179;
int sum_w18;
int sum_w180;
int sum_w181;
int sum_w182;
int sum_w183;
int sum_w184;
int sum_w185;
int sum_w186;
int sum_w187;
int sum_w188;
int sum_w189;
int sum_w19;
int sum_w190;
int sum_w191;
int sum_w192;
int sum_w193;
int sum_w194;
int sum_w195;
int sum_w196;
int sum_w197;
int sum_w198;
int sum_w199;
int sum_w2;
int sum_w20;
int sum_w200;
int sum_w201;
int sum_w202;
int sum_w203;
int sum_w204;
int sum_w205;
int sum_w206;
int sum_w207;
int sum_w208;
int sum_w209;
int sum_w21;
int sum_w210;
int sum_w211;
int sum_w212;
int sum_w213;
int sum_w214;
int sum_w215;
int sum_w216;
int sum_w217;
int sum_w218;
int sum_w219;
int sum_w22;
int sum_w220;
int sum_w221;
int sum_w222;
int sum_w223;
int sum_w224;
int sum_w225;
int sum_w226;
int sum_w227;
int sum_w228;
int sum_w229;
int sum_w23;
int sum_w230;
int sum_w231;
int sum_w232;
int sum_w233;
int sum_w234;
int sum_w235;
int sum_w236;
int sum_w237;
int sum_w238;
int sum_w239;
int sum_w24;
int sum_w240;
int sum_w241;
int sum_w242;
int sum_w243;
int sum_w244;
int sum_w245;
int sum_w246;
int sum_w247;
int sum_w248;
int sum_w249;
int sum_w25;
int sum_w250;
int sum_w251;
int sum_w252;
int sum_w253;
int sum_w254;
int sum_w255;
int sum_w256;
int sum_w257;
int sum_w258;
int sum_w259;
int sum_w26;
int sum_w260;
int sum_w261;
int sum_w262;
int sum_w263;
int sum_w264;
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
int temp_l83_i1_w1;
int temp_l83_i2_w1;
int temp_l83_i3998_w1;
int temp_l83_i3999_w1;
int temp_l83_i3_w1;
int temp_l83_i4000_w1;
int temp_l83_i4_w1;
int temp_l83_i5_w1;
int temp_l83_i6_w1;
int temp_l83_i7_w1;
int temp_l83_i8_w1;
int temp_l83_i9_w1;
// Initialization done
sum_w112 = sum_array_0[0] + sum_array_0[1];
sum_w93 = sum_array_0[2] + sum_array_0[3];
sum_w94 = sum_array_0[4] + sum_array_0[5];
sum_w187 = sum_array_0[6] + sum_array_0[7];
sum_w188 = sum_array_0[8] + sum_array_0[9];
sum_w255 = sum_array_0[10] + sum_array_0[11];
sum_w256 = sum_array_0[12] + sum_array_0[13];
sum_w181 = sum_array_0[14] + sum_array_0[15];
sum_w182 = sum_array_0[16] + sum_array_0[17];
sum_w133 = sum_array_0[18] + sum_array_0[19];
sum_w134 = sum_array_0[20] + sum_array_0[21];
sum_w19 = sum_array_0[22] + sum_array_0[23];
sum_w20 = sum_array_0[24] + sum_array_0[25];
sum_w55 = sum_array_0[26] + sum_array_0[27];
sum_w56 = sum_array_0[28] + sum_array_0[29];
sum_w153 = sum_array_0[30] + sum_array_0[31];
sum_w154 = sum_array_1[0] + sum_array_1[1];
sum_w3 = sum_array_1[2] + sum_array_1[3];
sum_w4 = sum_array_1[4] + sum_array_1[5];
sum_w243 = sum_array_1[6] + sum_array_1[7];
sum_w244 = sum_array_1[8] + sum_array_1[9];
sum_w257 = sum_array_1[10] + sum_array_1[11];
sum_w258 = sum_array_1[12] + sum_array_1[13];
sum_w71 = sum_array_1[14] + sum_array_1[15];
sum_w72 = sum_array_1[16] + sum_array_1[17];
sum_w208 = sum_array_1[18] + sum_array_1[19];
sum_w209 = sum_array_1[20] + sum_array_1[21];
sum_w61 = sum_array_1[22] + sum_array_1[23];
sum_w62 = sum_array_1[24] + sum_array_1[25];
sum_w141 = sum_array_1[26] + sum_array_1[27];
sum_w142 = sum_array_1[28] + sum_array_1[29];
sum_w210 = sum_array_1[30] + sum_array_1[31];
sum_w211 = sum_array_2[0] + sum_array_2[1];
sum_w155 = sum_array_2[2] + sum_array_2[3];
sum_w156 = sum_array_2[4] + sum_array_2[5];
sum_w29 = sum_array_2[6] + sum_array_2[7];
sum_w30 = sum_array_2[8] + sum_array_2[9];
sum_w149 = sum_array_2[10] + sum_array_2[11];
sum_w150 = sum_array_2[12] + sum_array_2[13];
sum_w163 = sum_array_2[14] + sum_array_2[15];
sum_w164 = sum_array_2[16] + sum_array_2[17];
sum_w83 = sum_array_2[18] + sum_array_2[19];
sum_w84 = sum_array_2[20] + sum_array_2[21];
sum_w47 = sum_array_2[22] + sum_array_2[23];
sum_w48 = sum_array_2[24] + sum_array_2[25];
sum_w173 = sum_array_2[26] + sum_array_2[27];
sum_w174 = sum_array_2[28] + sum_array_2[29];
sum_w199 = sum_array_2[30] + sum_array_2[31];
sum_w200 = sum_array_3[0] + sum_array_3[1];
sum_w171 = sum_array_3[2] + sum_array_3[3];
sum_w172 = sum_array_3[4] + sum_array_3[5];
sum_w214 = sum_array_3[6] + sum_array_3[7];
sum_w215 = sum_array_3[8] + sum_array_3[9];
sum_w5 = sum_array_3[10] + sum_array_3[11];
sum_w6 = sum_array_3[12] + sum_array_3[13];
sum_w129 = sum_array_3[14] + sum_array_3[15];
sum_w130 = sum_array_3[16] + sum_array_3[17];
sum_w127 = sum_array_3[18] + sum_array_3[19];
sum_w128 = sum_array_3[20] + sum_array_3[21];
sum_w157 = sum_array_3[22] + sum_array_3[23];
sum_w158 = sum_array_3[24] + sum_array_3[25];
sum_w131 = sum_array_3[26] + sum_array_3[27];
sum_w132 = sum_array_3[28] + sum_array_3[29];
sum_w101 = sum_array_3[30] + sum_array_3[31];
sum_w102 = sum_array_4[0] + sum_array_4[1];
sum_w17 = sum_array_4[2] + sum_array_4[3];
sum_w18 = sum_array_4[4] + sum_array_4[5];
sum_w201 = sum_array_4[6] + sum_array_4[7];
sum_w202 = sum_array_4[8] + sum_array_4[9];
sum_w145 = sum_array_4[10] + sum_array_4[11];
sum_w146 = sum_array_4[12] + sum_array_4[13];
sum_w203 = sum_array_4[14] + sum_array_4[15];
sum_w204 = sum_array_4[16] + sum_array_4[17];
sum_w35 = sum_array_4[18] + sum_array_4[19];
sum_w36 = sum_array_4[20] + sum_array_4[21];
sum_w261 = sum_array_4[22] + sum_array_4[23];
sum_w262 = sum_array_4[24] + sum_array_4[25];
sum_w239 = sum_array_4[26] + sum_array_4[27];
sum_w240 = sum_array_4[28] + sum_array_4[29];
sum_w189 = sum_array_4[30] + sum_array_4[31];
sum_w190 = sum_array_5[0] + sum_array_5[1];
sum_w218 = sum_array_5[2] + sum_array_5[3];
sum_w219 = sum_array_5[4] + sum_array_5[5];
sum_w67 = sum_array_5[6] + sum_array_5[7];
sum_w68 = sum_array_5[8] + sum_array_5[9];
sum_w259 = sum_array_5[10] + sum_array_5[11];
sum_w260 = sum_array_5[12] + sum_array_5[13];
sum_w85 = sum_array_5[14] + sum_array_5[15];
sum_w86 = sum_array_5[16] + sum_array_5[17];
sum_w167 = sum_array_5[18] + sum_array_5[19];
sum_w168 = sum_array_5[20] + sum_array_5[21];
sum_w87 = sum_array_5[22] + sum_array_5[23];
sum_w88 = sum_array_5[24] + sum_array_5[25];
sum_w237 = sum_array_5[26] + sum_array_5[27];
sum_w238 = sum_array_5[28] + sum_array_5[29];
sum_w117 = sum_array_5[30] + sum_array_5[31];
sum_w118 = sum_array_6[0] + sum_array_6[1];
sum_w81 = sum_array_6[2] + sum_array_6[3];
sum_w82 = sum_array_6[4] + sum_array_6[5];
sum_w109 = sum_array_6[6] + sum_array_6[7];
sum_w110 = sum_array_6[8] + sum_array_6[9];
sum_w75 = sum_array_6[10] + sum_array_6[11];
sum_w76 = sum_array_6[12] + sum_array_6[13];
sum_w11 = sum_array_6[14] + sum_array_6[15];
sum_w12 = sum_array_6[16] + sum_array_6[17];
sum_w137 = sum_array_6[18] + sum_array_6[19];
sum_w138 = sum_array_6[20] + sum_array_6[21];
sum_w37 = sum_array_6[22] + sum_array_6[23];
sum_w38 = sum_array_6[24] + sum_array_6[25];
sum_w253 = sum_array_6[26] + sum_array_6[27];
sum_w254 = sum_array_6[28] + sum_array_6[29];
sum_w183 = sum_array_6[30] + sum_array_6[31];
sum_w184 = sum_array_7[0] + sum_array_7[1];
sum_w89 = sum_array_7[2] + sum_array_7[3];
sum_w90 = sum_array_7[4] + sum_array_7[5];
sum_w73 = sum_array_7[6] + sum_array_7[7];
sum_w74 = sum_array_7[8] + sum_array_7[9];
sum_w39 = sum_array_7[10] + sum_array_7[11];
sum_w40 = sum_array_7[12] + sum_array_7[13];
sum_w161 = sum_array_7[14] + sum_array_7[15];
sum_w162 = sum_array_7[16] + sum_array_7[17];
sum_w165 = sum_array_7[18] + sum_array_7[19];
sum_w166 = sum_array_7[20] + sum_array_7[21];
sum_w241 = sum_array_7[22] + sum_array_7[23];
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
temp_l83_i3998_w1 = x_9[0] * y_9[0];
temp_l83_i3999_w1 = x_9[1] * y_9[1];
temp_l83_i4000_w1 = x_9[2] * y_9[2];

sum_w116 = sum_w101 + sum_w102;
sum_w234 = sum_w109 + sum_w110;
sum_w92 = sum_w11 + sum_w12;
sum_w106 = sum_w117 + sum_w118;
sum_w177 = sum_w127 + sum_w128;
sum_w64 = sum_w129 + sum_w130;
sum_w115 = sum_w131 + sum_w132;
sum_w205 = sum_w133 + sum_w134;
sum_w9 = sum_w137 + sum_w138;
sum_w121 = sum_w141 + sum_w142;
sum_w195 = sum_w145 + sum_w146;
sum_w191 = sum_w149 + sum_w150;
sum_w34 = sum_w153 + sum_w154;
sum_w113 = sum_w155 + sum_w156;
sum_w178 = sum_w157 + sum_w158;
sum_w44 = sum_w161 + sum_w162;
sum_w192 = sum_w163 + sum_w164;
sum_w79 = sum_w165 + sum_w166;
sum_w226 = sum_w167 + sum_w168;
sum_w193 = sum_w17 + sum_w18;
sum_w53 = sum_w171 + sum_w172;
sum_w147 = sum_w173 + sum_w174;
sum_w78 = sum_w181 + sum_w182;
sum_w26 = sum_w183 + sum_w184;
sum_w124 = sum_w187 + sum_w188;
sum_w2 = sum_w189 + sum_w190;
sum_w206 = sum_w19 + sum_w20;
sum_w148 = sum_w199 + sum_w200;
sum_w194 = sum_w201 + sum_w202;
sum_w196 = sum_w203 + sum_w204;
sum_w99 = sum_w208 + sum_w209;
sum_w122 = sum_w210 + sum_w211;
sum_w54 = sum_w214 + sum_w215;
sum_w31 = sum_w218 + sum_w219;
sum_w105 = sum_w237 + sum_w238;
sum_w1 = sum_w239 + sum_w240;
sum_w22 = sum_w243 + sum_w244;
sum_w25 = sum_w253 + sum_w254;
sum_w77 = sum_w255 + sum_w256;
sum_w247 = sum_w257 + sum_w258;
sum_w143 = sum_w259 + sum_w260;
sum_w16 = sum_w261 + sum_w262;
sum_w114 = sum_w29 + sum_w30;
sum_w21 = sum_w3 + sum_w4;
sum_w15 = sum_w35 + sum_w36;
sum_w10 = sum_w37 + sum_w38;
sum_w43 = sum_w39 + sum_w40;
sum_w160 = sum_w47 + sum_w48;
sum_w63 = sum_w5 + sum_w6;
sum_w33 = sum_w55 + sum_w56;
sum_w100 = sum_w61 + sum_w62;
sum_w32 = sum_w67 + sum_w68;
sum_w248 = sum_w71 + sum_w72;
sum_w264 = sum_w73 + sum_w74;
sum_w91 = sum_w75 + sum_w76;
sum_w233 = sum_w81 + sum_w82;
sum_w159 = sum_w83 + sum_w84;
sum_w144 = sum_w85 + sum_w86;
sum_w227 = sum_w87 + sum_w88;
sum_w263 = sum_w89 + sum_w90;
sum_w123 = sum_w93 + sum_w94;
sum_w251 = temp_l83_i10_w1 + temp_l83_i11_w1;
sum_w252 = temp_l83_i12_w1 + temp_l83_i13_w1;
sum_w222 = 0 + temp_l83_i1_w1;
sum_w232 = temp_l83_i3998_w1 + temp_l83_i3999_w1;
sum_w107 = temp_l83_i2_w1 + temp_l83_i3_w1;
sum_w108 = temp_l83_i4_w1 + temp_l83_i5_w1;
sum_w212 = temp_l83_i6_w1 + temp_l83_i7_w1;
sum_w213 = temp_l83_i8_w1 + temp_l83_i9_w1;

sum_w46 = sum_w1 + sum_w2;
sum_w217 = sum_w105 + sum_w106;
sum_w223 = sum_w107 + sum_w108;
sum_w119 = sum_w113 + sum_w114;
sum_w126 = sum_w115 + sum_w116;
sum_w186 = sum_w121 + sum_w122;
sum_w95 = sum_w123 + sum_w124;
sum_w221 = sum_w143 + sum_w144;
sum_w60 = sum_w147 + sum_w148;
sum_w45 = sum_w15 + sum_w16;
sum_w59 = sum_w159 + sum_w160;
sum_w125 = sum_w177 + sum_w178;
sum_w120 = sum_w191 + sum_w192;
sum_w57 = sum_w193 + sum_w194;
sum_w58 = sum_w195 + sum_w196;
sum_w230 = sum_w205 + sum_w206;
sum_w51 = sum_w21 + sum_w22;
sum_w224 = sum_w212 + sum_w213;
sum_w216 = sum_w226 + sum_w227;
sum_w242 = sum_array_7[24] + sum_w232;
sum_w228 = sum_w233 + sum_w234;
sum_w52 = sum_w247 + sum_w248;
sum_w180 = sum_w25 + sum_w26;
sum_w225 = sum_w251 + sum_w252;
sum_w41 = sum_w263 + sum_w264;
sum_w220 = sum_w31 + sum_w32;
sum_w231 = sum_w33 + sum_w34;
sum_w42 = sum_w43 + sum_w44;
sum_w13 = sum_w53 + sum_w54;
sum_w14 = sum_w63 + sum_w64;
sum_w96 = sum_w77 + sum_w78;
sum_w179 = sum_w9 + sum_w10;
sum_w229 = sum_w91 + sum_w92;
sum_w185 = sum_w99 + sum_w100;

sum_w135 = sum_w119 + sum_w120;
sum_w198 = sum_w125 + sum_w126;
sum_w197 = sum_w13 + sum_w14;
sum_w152 = sum_w179 + sum_w180;
sum_w250 = sum_w185 + sum_w186;
sum_w98 = sum_w216 + sum_w217;
sum_w97 = sum_w220 + sum_w221;
sum_w169 = sum_w222 + sum_w223;
sum_w170 = sum_w224 + sum_w225;
sum_w151 = sum_w228 + sum_w229;
sum_w176 = sum_w230 + sum_w231;
sum_w80 = sum_w241 + sum_w242;
sum_w104 = sum_w41 + sum_w42;
sum_w236 = sum_w45 + sum_w46;
sum_w249 = sum_w51 + sum_w52;
sum_w235 = sum_w57 + sum_w58;
sum_w136 = sum_w59 + sum_w60;
sum_w175 = sum_w95 + sum_w96;

sum_w49 = sum_w135 + sum_w136;
sum_w23 = sum_w151 + sum_w152;
sum_w111 = sum_w169 + sum_w170;
sum_w245 = sum_w175 + sum_w176;
sum_w50 = sum_w197 + sum_w198;
sum_w7 = sum_w235 + sum_w236;
sum_w246 = sum_w249 + sum_w250;
sum_w103 = sum_w79 + sum_w80;
sum_w8 = sum_w97 + sum_w98;

sum_w24 = sum_w104 + sum_w103;
sum_w69 = sum_w111 + sum_w112;
sum_w139 = sum_w245 + sum_w246;
sum_w140 = sum_w49 + sum_w50;
sum_w65 = sum_w7 + sum_w8;

sum_w27 = sum_w139 + sum_w140;
sum_w66 = sum_w23 + sum_w24;

sum_w28 = sum_w65 + sum_w66;

sum_w70 = sum_w27 + sum_w28;

sum_w207 = sum_w69 + sum_w70;

*out = sum_w207 + temp_l83_i4000_w1;

}

void n4000_maxNodes_100_dotprod_parallel8(short x_0[13], short x_1[512], short x_2[512], short x_3[512], short x_4[512], short x_5[512], short x_6[512], short x_7[512], short x_8[400], short x_9[3], short y_0[13], short y_1[512], short y_2[512], short y_3[512], short y_4[512], short y_5[512], short y_6[512], short y_7[512], short y_8[400], short y_9[3], int *out) {
// Step 2: Initialize local variables
int sum_array_0[32];
int sum_array_1[32];
int sum_array_2[32];
int sum_array_3[32];
int sum_array_4[32];
int sum_array_5[32];
int sum_array_6[32];
int sum_array_7[25];
#pragma HLS ARRAY_PARTITION variable=x_0 cyclic factor=13 dim=0
#pragma HLS ARRAY_PARTITION variable=x_1 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=x_2 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=x_3 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=x_4 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=x_5 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=x_6 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=x_7 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=x_8 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=x_9 cyclic factor=3 dim=0
#pragma HLS ARRAY_PARTITION variable=y_0 cyclic factor=13 dim=0
#pragma HLS ARRAY_PARTITION variable=y_1 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=y_2 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=y_3 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=y_4 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=y_5 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=y_6 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=y_7 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=y_8 cyclic factor=16 dim=0
#pragma HLS ARRAY_PARTITION variable=y_9 cyclic factor=3 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_0 cyclic factor=2 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_1 cyclic factor=2 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_2 cyclic factor=2 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_3 cyclic factor=2 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_4 cyclic factor=2 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_5 cyclic factor=2 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_6 cyclic factor=2 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_7 cyclic factor=2 dim=0
// Initialization done
#pragma HLS dataflow

parallel_0(x_1,y_1,sum_array_0);
parallel_0(x_2,y_2,sum_array_1);
parallel_0(x_3,y_3,sum_array_2);
parallel_0(x_4,y_4,sum_array_3);
parallel_0(x_5,y_5,sum_array_4);
parallel_0(x_6,y_6,sum_array_5);
parallel_0(x_7,y_7,sum_array_6);
parallel_1(x_8,y_8,sum_array_7);


epilogue(sum_array_3,sum_array_2,sum_array_5,y_9,sum_array_4,x_9,sum_array_7,sum_array_6,x_0,y_0,sum_array_1,sum_array_0,out);

}
