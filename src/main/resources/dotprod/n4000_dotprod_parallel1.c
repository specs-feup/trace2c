
void parallel_0(short x[3904], short y[3904], int sum_array[61]) {
// Step 2: Initialize local variables
int sum_w1;
int sum_w10;
int sum_w11;
int sum_w12;
int sum_w13;
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
int sum_w7;
int sum_w8;
int sum_w9;
int temp_l83_i100_w1;
int temp_l83_i101_w1;
int temp_l83_i102_w1;
int temp_l83_i103_w1;
int temp_l83_i104_w1;
int temp_l83_i105_w1;
int temp_l83_i106_w1;
int temp_l83_i107_w1;
int temp_l83_i108_w1;
int temp_l83_i109_w1;
int temp_l83_i46_w1;
int temp_l83_i47_w1;
int temp_l83_i48_w1;
int temp_l83_i49_w1;
int temp_l83_i50_w1;
int temp_l83_i51_w1;
int temp_l83_i52_w1;
int temp_l83_i53_w1;
int temp_l83_i54_w1;
int temp_l83_i55_w1;
int temp_l83_i56_w1;
int temp_l83_i57_w1;
int temp_l83_i58_w1;
int temp_l83_i59_w1;
int temp_l83_i60_w1;
int temp_l83_i61_w1;
int temp_l83_i62_w1;
int temp_l83_i63_w1;
int temp_l83_i64_w1;
int temp_l83_i65_w1;
int temp_l83_i66_w1;
int temp_l83_i67_w1;
int temp_l83_i68_w1;
int temp_l83_i69_w1;
int temp_l83_i70_w1;
int temp_l83_i71_w1;
int temp_l83_i72_w1;
int temp_l83_i73_w1;
int temp_l83_i74_w1;
int temp_l83_i75_w1;
int temp_l83_i76_w1;
int temp_l83_i77_w1;
int temp_l83_i78_w1;
int temp_l83_i79_w1;
int temp_l83_i80_w1;
int temp_l83_i81_w1;
int temp_l83_i82_w1;
int temp_l83_i83_w1;
int temp_l83_i84_w1;
int temp_l83_i85_w1;
int temp_l83_i86_w1;
int temp_l83_i87_w1;
int temp_l83_i88_w1;
int temp_l83_i89_w1;
int temp_l83_i90_w1;
int temp_l83_i91_w1;
int temp_l83_i92_w1;
int temp_l83_i93_w1;
int temp_l83_i94_w1;
int temp_l83_i95_w1;
int temp_l83_i96_w1;
int temp_l83_i97_w1;
int temp_l83_i98_w1;
int temp_l83_i99_w1;
// Initialization done
// starting Loop
for( int i = 0; i < 61;i=i+1){
#pragma HLS pipeline


temp_l83_i46_w1 = x[(64)*i] * y[(64)*i];
temp_l83_i47_w1 = x[(64)*i+1] * y[(64)*i+1];
temp_l83_i48_w1 = x[(64)*i+2] * y[(64)*i+2];
temp_l83_i49_w1 = x[(64)*i+3] * y[(64)*i+3];
temp_l83_i50_w1 = x[(64)*i+4] * y[(64)*i+4];
temp_l83_i51_w1 = x[(64)*i+5] * y[(64)*i+5];
temp_l83_i52_w1 = x[(64)*i+6] * y[(64)*i+6];
temp_l83_i53_w1 = x[(64)*i+7] * y[(64)*i+7];
temp_l83_i54_w1 = x[(64)*i+8] * y[(64)*i+8];
temp_l83_i55_w1 = x[(64)*i+9] * y[(64)*i+9];
temp_l83_i56_w1 = x[(64)*i+10] * y[(64)*i+10];
temp_l83_i57_w1 = x[(64)*i+11] * y[(64)*i+11];
temp_l83_i58_w1 = x[(64)*i+12] * y[(64)*i+12];
temp_l83_i59_w1 = x[(64)*i+13] * y[(64)*i+13];
temp_l83_i60_w1 = x[(64)*i+14] * y[(64)*i+14];
temp_l83_i61_w1 = x[(64)*i+15] * y[(64)*i+15];
temp_l83_i62_w1 = x[(64)*i+16] * y[(64)*i+16];
temp_l83_i63_w1 = x[(64)*i+17] * y[(64)*i+17];
temp_l83_i64_w1 = x[(64)*i+18] * y[(64)*i+18];
temp_l83_i65_w1 = x[(64)*i+19] * y[(64)*i+19];
temp_l83_i66_w1 = x[(64)*i+20] * y[(64)*i+20];
temp_l83_i67_w1 = x[(64)*i+21] * y[(64)*i+21];
temp_l83_i68_w1 = x[(64)*i+22] * y[(64)*i+22];
temp_l83_i69_w1 = x[(64)*i+23] * y[(64)*i+23];
temp_l83_i70_w1 = x[(64)*i+24] * y[(64)*i+24];
temp_l83_i71_w1 = x[(64)*i+25] * y[(64)*i+25];
temp_l83_i72_w1 = x[(64)*i+26] * y[(64)*i+26];
temp_l83_i73_w1 = x[(64)*i+27] * y[(64)*i+27];
temp_l83_i74_w1 = x[(64)*i+28] * y[(64)*i+28];
temp_l83_i75_w1 = x[(64)*i+29] * y[(64)*i+29];
temp_l83_i76_w1 = x[(64)*i+30] * y[(64)*i+30];
temp_l83_i77_w1 = x[(64)*i+31] * y[(64)*i+31];
temp_l83_i78_w1 = x[(64)*i+32] * y[(64)*i+32];
temp_l83_i79_w1 = x[(64)*i+33] * y[(64)*i+33];
temp_l83_i80_w1 = x[(64)*i+34] * y[(64)*i+34];
temp_l83_i81_w1 = x[(64)*i+35] * y[(64)*i+35];
temp_l83_i82_w1 = x[(64)*i+36] * y[(64)*i+36];
temp_l83_i83_w1 = x[(64)*i+37] * y[(64)*i+37];
temp_l83_i84_w1 = x[(64)*i+38] * y[(64)*i+38];
temp_l83_i85_w1 = x[(64)*i+39] * y[(64)*i+39];
temp_l83_i86_w1 = x[(64)*i+40] * y[(64)*i+40];
temp_l83_i87_w1 = x[(64)*i+41] * y[(64)*i+41];
temp_l83_i88_w1 = x[(64)*i+42] * y[(64)*i+42];
temp_l83_i89_w1 = x[(64)*i+43] * y[(64)*i+43];
temp_l83_i90_w1 = x[(64)*i+44] * y[(64)*i+44];
temp_l83_i91_w1 = x[(64)*i+45] * y[(64)*i+45];
temp_l83_i92_w1 = x[(64)*i+46] * y[(64)*i+46];
temp_l83_i93_w1 = x[(64)*i+47] * y[(64)*i+47];
temp_l83_i94_w1 = x[(64)*i+48] * y[(64)*i+48];
temp_l83_i95_w1 = x[(64)*i+49] * y[(64)*i+49];
temp_l83_i96_w1 = x[(64)*i+50] * y[(64)*i+50];
temp_l83_i97_w1 = x[(64)*i+51] * y[(64)*i+51];
temp_l83_i98_w1 = x[(64)*i+52] * y[(64)*i+52];
temp_l83_i99_w1 = x[(64)*i+53] * y[(64)*i+53];
temp_l83_i100_w1 = x[(64)*i+54] * y[(64)*i+54];
temp_l83_i101_w1 = x[(64)*i+55] * y[(64)*i+55];
temp_l83_i102_w1 = x[(64)*i+56] * y[(64)*i+56];
temp_l83_i103_w1 = x[(64)*i+57] * y[(64)*i+57];
temp_l83_i104_w1 = x[(64)*i+58] * y[(64)*i+58];
temp_l83_i105_w1 = x[(64)*i+59] * y[(64)*i+59];
temp_l83_i106_w1 = x[(64)*i+60] * y[(64)*i+60];
temp_l83_i107_w1 = x[(64)*i+61] * y[(64)*i+61];
temp_l83_i108_w1 = x[(64)*i+62] * y[(64)*i+62];
temp_l83_i109_w1 = x[(64)*i+63] * y[(64)*i+63];

sum_w26 = temp_l83_i100_w1 + temp_l83_i101_w1;
sum_w49 = temp_l83_i102_w1 + temp_l83_i103_w1;
sum_w50 = temp_l83_i104_w1 + temp_l83_i105_w1;
sum_w21 = temp_l83_i106_w1 + temp_l83_i107_w1;
sum_w22 = temp_l83_i108_w1 + temp_l83_i109_w1;
sum_w57 = temp_l83_i46_w1 + temp_l83_i47_w1;
sum_w58 = temp_l83_i48_w1 + temp_l83_i49_w1;
sum_w19 = temp_l83_i50_w1 + temp_l83_i51_w1;
sum_w20 = temp_l83_i52_w1 + temp_l83_i53_w1;
sum_w37 = temp_l83_i54_w1 + temp_l83_i55_w1;
sum_w38 = temp_l83_i56_w1 + temp_l83_i57_w1;
sum_w31 = temp_l83_i58_w1 + temp_l83_i59_w1;
sum_w32 = temp_l83_i60_w1 + temp_l83_i61_w1;
sum_w47 = temp_l83_i62_w1 + temp_l83_i63_w1;
sum_w48 = temp_l83_i64_w1 + temp_l83_i65_w1;
sum_w9 = temp_l83_i66_w1 + temp_l83_i67_w1;
sum_w10 = temp_l83_i68_w1 + temp_l83_i69_w1;
sum_w7 = temp_l83_i70_w1 + temp_l83_i71_w1;
sum_w8 = temp_l83_i72_w1 + temp_l83_i73_w1;
sum_w13 = temp_l83_i74_w1 + temp_l83_i75_w1;
sum_w14 = temp_l83_i76_w1 + temp_l83_i77_w1;
sum_w35 = temp_l83_i78_w1 + temp_l83_i79_w1;
sum_w36 = temp_l83_i80_w1 + temp_l83_i81_w1;
sum_w41 = temp_l83_i82_w1 + temp_l83_i83_w1;
sum_w42 = temp_l83_i84_w1 + temp_l83_i85_w1;
sum_w27 = temp_l83_i86_w1 + temp_l83_i87_w1;
sum_w28 = temp_l83_i88_w1 + temp_l83_i89_w1;
sum_w33 = temp_l83_i90_w1 + temp_l83_i91_w1;
sum_w34 = temp_l83_i92_w1 + temp_l83_i93_w1;
sum_w23 = temp_l83_i94_w1 + temp_l83_i95_w1;
sum_w24 = temp_l83_i96_w1 + temp_l83_i97_w1;
sum_w25 = temp_l83_i98_w1 + temp_l83_i99_w1;

sum_w40 = sum_w13 + sum_w14;
sum_w46 = sum_w19 + sum_w20;
sum_w44 = sum_w21 + sum_w22;
sum_w61 = sum_w23 + sum_w24;
sum_w62 = sum_w25 + sum_w26;
sum_w3 = sum_w27 + sum_w28;
sum_w60 = sum_w31 + sum_w32;
sum_w4 = sum_w33 + sum_w34;
sum_w11 = sum_w35 + sum_w36;
sum_w59 = sum_w37 + sum_w38;
sum_w12 = sum_w41 + sum_w42;
sum_w15 = sum_w47 + sum_w48;
sum_w43 = sum_w49 + sum_w50;
sum_w45 = sum_w57 + sum_w58;
sum_w39 = sum_w7 + sum_w8;
sum_w16 = sum_w9 + sum_w10;

sum_w55 = sum_w11 + sum_w12;
sum_w53 = sum_w15 + sum_w16;
sum_w56 = sum_w3 + sum_w4;
sum_w54 = sum_w39 + sum_w40;
sum_w30 = sum_w43 + sum_w44;
sum_w17 = sum_w45 + sum_w46;
sum_w18 = sum_w59 + sum_w60;
sum_w29 = sum_w61 + sum_w62;

sum_w1 = sum_w17 + sum_w18;
sum_w6 = sum_w29 + sum_w30;
sum_w2 = sum_w53 + sum_w54;
sum_w5 = sum_w55 + sum_w56;

sum_w51 = sum_w1 + sum_w2;
sum_w52 = sum_w5 + sum_w6;

sum_array[i] = sum_w51 + sum_w52;

}
}

void epilogue(short x_0[45], short x_2[51], short y_0[45], short y_2[51], int sum_array_0[61], int *out) {
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
int temp_l83_i14_w1;
int temp_l83_i15_w1;
int temp_l83_i16_w1;
int temp_l83_i17_w1;
int temp_l83_i18_w1;
int temp_l83_i19_w1;
int temp_l83_i1_w1;
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
int temp_l83_i2_w1;
int temp_l83_i30_w1;
int temp_l83_i31_w1;
int temp_l83_i32_w1;
int temp_l83_i33_w1;
int temp_l83_i34_w1;
int temp_l83_i35_w1;
int temp_l83_i36_w1;
int temp_l83_i37_w1;
int temp_l83_i38_w1;
int temp_l83_i3950_w1;
int temp_l83_i3951_w1;
int temp_l83_i3952_w1;
int temp_l83_i3953_w1;
int temp_l83_i3954_w1;
int temp_l83_i3955_w1;
int temp_l83_i3956_w1;
int temp_l83_i3957_w1;
int temp_l83_i3958_w1;
int temp_l83_i3959_w1;
int temp_l83_i3960_w1;
int temp_l83_i3961_w1;
int temp_l83_i3962_w1;
int temp_l83_i3963_w1;
int temp_l83_i3964_w1;
int temp_l83_i3965_w1;
int temp_l83_i3966_w1;
int temp_l83_i3967_w1;
int temp_l83_i3968_w1;
int temp_l83_i3969_w1;
int temp_l83_i3970_w1;
int temp_l83_i3971_w1;
int temp_l83_i3972_w1;
int temp_l83_i3973_w1;
int temp_l83_i3974_w1;
int temp_l83_i3975_w1;
int temp_l83_i3976_w1;
int temp_l83_i3977_w1;
int temp_l83_i3978_w1;
int temp_l83_i3979_w1;
int temp_l83_i3980_w1;
int temp_l83_i3981_w1;
int temp_l83_i3982_w1;
int temp_l83_i3983_w1;
int temp_l83_i3984_w1;
int temp_l83_i3985_w1;
int temp_l83_i3986_w1;
int temp_l83_i3987_w1;
int temp_l83_i3988_w1;
int temp_l83_i3989_w1;
int temp_l83_i3990_w1;
int temp_l83_i3991_w1;
int temp_l83_i3992_w1;
int temp_l83_i3993_w1;
int temp_l83_i3994_w1;
int temp_l83_i3995_w1;
int temp_l83_i3996_w1;
int temp_l83_i3997_w1;
int temp_l83_i3998_w1;
int temp_l83_i3999_w1;
int temp_l83_i39_w1;
int temp_l83_i3_w1;
int temp_l83_i4000_w1;
int temp_l83_i40_w1;
int temp_l83_i41_w1;
int temp_l83_i42_w1;
int temp_l83_i43_w1;
int temp_l83_i44_w1;
int temp_l83_i45_w1;
int temp_l83_i4_w1;
int temp_l83_i5_w1;
int temp_l83_i6_w1;
int temp_l83_i7_w1;
int temp_l83_i8_w1;
int temp_l83_i9_w1;
// Initialization done
sum_w64 = sum_array_0[0] + sum_array_0[1];
sum_w65 = sum_array_0[2] + sum_array_0[3];
sum_w127 = sum_array_0[4] + sum_array_0[5];
sum_w128 = sum_array_0[6] + sum_array_0[7];
sum_w35 = sum_array_0[8] + sum_array_0[9];
sum_w36 = sum_array_0[10] + sum_array_0[11];
sum_w106 = sum_array_0[12] + sum_array_0[13];
sum_w107 = sum_array_0[14] + sum_array_0[15];
sum_w80 = sum_array_0[16] + sum_array_0[17];
sum_w81 = sum_array_0[18] + sum_array_0[19];
sum_w43 = sum_array_0[20] + sum_array_0[21];
sum_w44 = sum_array_0[22] + sum_array_0[23];
sum_w15 = sum_array_0[24] + sum_array_0[25];
sum_w16 = sum_array_0[26] + sum_array_0[27];
sum_w82 = sum_array_0[28] + sum_array_0[29];
sum_w83 = sum_array_0[30] + sum_array_0[31];
sum_w41 = sum_array_0[32] + sum_array_0[33];
sum_w42 = sum_array_0[34] + sum_array_0[35];
sum_w31 = sum_array_0[36] + sum_array_0[37];
sum_w32 = sum_array_0[38] + sum_array_0[39];
sum_w115 = sum_array_0[40] + sum_array_0[41];
sum_w116 = sum_array_0[42] + sum_array_0[43];
sum_w113 = sum_array_0[44] + sum_array_0[45];
sum_w114 = sum_array_0[46] + sum_array_0[47];
sum_w125 = sum_array_0[48] + sum_array_0[49];
sum_w126 = sum_array_0[50] + sum_array_0[51];
sum_w104 = sum_array_0[52] + sum_array_0[53];
sum_w105 = sum_array_0[54] + sum_array_0[55];
sum_w29 = sum_array_0[56] + sum_array_0[57];
sum_w30 = sum_array_0[58] + sum_array_0[59];
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
temp_l83_i14_w1 = x_0[13] * y_0[13];
temp_l83_i15_w1 = x_0[14] * y_0[14];
temp_l83_i16_w1 = x_0[15] * y_0[15];
temp_l83_i17_w1 = x_0[16] * y_0[16];
temp_l83_i18_w1 = x_0[17] * y_0[17];
temp_l83_i19_w1 = x_0[18] * y_0[18];
temp_l83_i20_w1 = x_0[19] * y_0[19];
temp_l83_i21_w1 = x_0[20] * y_0[20];
temp_l83_i22_w1 = x_0[21] * y_0[21];
temp_l83_i23_w1 = x_0[22] * y_0[22];
temp_l83_i24_w1 = x_0[23] * y_0[23];
temp_l83_i25_w1 = x_0[24] * y_0[24];
temp_l83_i26_w1 = x_0[25] * y_0[25];
temp_l83_i27_w1 = x_0[26] * y_0[26];
temp_l83_i28_w1 = x_0[27] * y_0[27];
temp_l83_i29_w1 = x_0[28] * y_0[28];
temp_l83_i30_w1 = x_0[29] * y_0[29];
temp_l83_i31_w1 = x_0[30] * y_0[30];
temp_l83_i32_w1 = x_0[31] * y_0[31];
temp_l83_i33_w1 = x_0[32] * y_0[32];
temp_l83_i34_w1 = x_0[33] * y_0[33];
temp_l83_i35_w1 = x_0[34] * y_0[34];
temp_l83_i36_w1 = x_0[35] * y_0[35];
temp_l83_i37_w1 = x_0[36] * y_0[36];
temp_l83_i38_w1 = x_0[37] * y_0[37];
temp_l83_i39_w1 = x_0[38] * y_0[38];
temp_l83_i40_w1 = x_0[39] * y_0[39];
temp_l83_i41_w1 = x_0[40] * y_0[40];
temp_l83_i42_w1 = x_0[41] * y_0[41];
temp_l83_i43_w1 = x_0[42] * y_0[42];
temp_l83_i44_w1 = x_0[43] * y_0[43];
temp_l83_i45_w1 = x_0[44] * y_0[44];
temp_l83_i3950_w1 = x_2[0] * y_2[0];
temp_l83_i3951_w1 = x_2[1] * y_2[1];
temp_l83_i3952_w1 = x_2[2] * y_2[2];
temp_l83_i3953_w1 = x_2[3] * y_2[3];
temp_l83_i3954_w1 = x_2[4] * y_2[4];
temp_l83_i3955_w1 = x_2[5] * y_2[5];
temp_l83_i3956_w1 = x_2[6] * y_2[6];
temp_l83_i3957_w1 = x_2[7] * y_2[7];
temp_l83_i3958_w1 = x_2[8] * y_2[8];
temp_l83_i3959_w1 = x_2[9] * y_2[9];
temp_l83_i3960_w1 = x_2[10] * y_2[10];
temp_l83_i3961_w1 = x_2[11] * y_2[11];
temp_l83_i3962_w1 = x_2[12] * y_2[12];
temp_l83_i3963_w1 = x_2[13] * y_2[13];
temp_l83_i3964_w1 = x_2[14] * y_2[14];
temp_l83_i3965_w1 = x_2[15] * y_2[15];
temp_l83_i3966_w1 = x_2[16] * y_2[16];
temp_l83_i3967_w1 = x_2[17] * y_2[17];
temp_l83_i3968_w1 = x_2[18] * y_2[18];
temp_l83_i3969_w1 = x_2[19] * y_2[19];
temp_l83_i3970_w1 = x_2[20] * y_2[20];
temp_l83_i3971_w1 = x_2[21] * y_2[21];
temp_l83_i3972_w1 = x_2[22] * y_2[22];
temp_l83_i3973_w1 = x_2[23] * y_2[23];
temp_l83_i3974_w1 = x_2[24] * y_2[24];
temp_l83_i3975_w1 = x_2[25] * y_2[25];
temp_l83_i3976_w1 = x_2[26] * y_2[26];
temp_l83_i3977_w1 = x_2[27] * y_2[27];
temp_l83_i3978_w1 = x_2[28] * y_2[28];
temp_l83_i3979_w1 = x_2[29] * y_2[29];
temp_l83_i3980_w1 = x_2[30] * y_2[30];
temp_l83_i3981_w1 = x_2[31] * y_2[31];
temp_l83_i3982_w1 = x_2[32] * y_2[32];
temp_l83_i3983_w1 = x_2[33] * y_2[33];
temp_l83_i3984_w1 = x_2[34] * y_2[34];
temp_l83_i3985_w1 = x_2[35] * y_2[35];
temp_l83_i3986_w1 = x_2[36] * y_2[36];
temp_l83_i3987_w1 = x_2[37] * y_2[37];
temp_l83_i3988_w1 = x_2[38] * y_2[38];
temp_l83_i3989_w1 = x_2[39] * y_2[39];
temp_l83_i3990_w1 = x_2[40] * y_2[40];
temp_l83_i3991_w1 = x_2[41] * y_2[41];
temp_l83_i3992_w1 = x_2[42] * y_2[42];
temp_l83_i3993_w1 = x_2[43] * y_2[43];
temp_l83_i3994_w1 = x_2[44] * y_2[44];
temp_l83_i3995_w1 = x_2[45] * y_2[45];
temp_l83_i3996_w1 = x_2[46] * y_2[46];
temp_l83_i3997_w1 = x_2[47] * y_2[47];
temp_l83_i3998_w1 = x_2[48] * y_2[48];
temp_l83_i3999_w1 = x_2[49] * y_2[49];
temp_l83_i4000_w1 = x_2[50] * y_2[50];

sum_w95 = sum_w104 + sum_w105;
sum_w154 = sum_w106 + sum_w107;
sum_w67 = sum_w113 + sum_w114;
sum_w66 = sum_w115 + sum_w116;
sum_w94 = sum_w125 + sum_w126;
sum_w103 = sum_w127 + sum_w128;
sum_w108 = sum_w15 + sum_w16;
sum_w69 = sum_w29 + sum_w30;
sum_w138 = sum_w31 + sum_w32;
sum_w153 = sum_w35 + sum_w36;
sum_w137 = sum_w41 + sum_w42;
sum_w87 = sum_w43 + sum_w44;
sum_w102 = sum_w64 + sum_w65;
sum_w86 = sum_w80 + sum_w81;
sum_w109 = sum_w82 + sum_w83;
sum_w155 = temp_l83_i10_w1 + temp_l83_i11_w1;
sum_w156 = temp_l83_i12_w1 + temp_l83_i13_w1;
sum_w62 = temp_l83_i14_w1 + temp_l83_i15_w1;
sum_w63 = temp_l83_i16_w1 + temp_l83_i17_w1;
sum_w149 = temp_l83_i18_w1 + temp_l83_i19_w1;
sum_w117 = 0 + temp_l83_i1_w1;
sum_w150 = temp_l83_i20_w1 + temp_l83_i21_w1;
sum_w78 = temp_l83_i22_w1 + temp_l83_i23_w1;
sum_w79 = temp_l83_i24_w1 + temp_l83_i25_w1;
sum_w121 = temp_l83_i26_w1 + temp_l83_i27_w1;
sum_w122 = temp_l83_i28_w1 + temp_l83_i29_w1;
sum_w17 = temp_l83_i30_w1 + temp_l83_i31_w1;
sum_w18 = temp_l83_i32_w1 + temp_l83_i33_w1;
sum_w76 = temp_l83_i34_w1 + temp_l83_i35_w1;
sum_w77 = temp_l83_i36_w1 + temp_l83_i37_w1;
sum_w131 = temp_l83_i3950_w1 + temp_l83_i3951_w1;
sum_w132 = temp_l83_i3952_w1 + temp_l83_i3953_w1;
sum_w37 = temp_l83_i3954_w1 + temp_l83_i3955_w1;
sum_w38 = temp_l83_i3956_w1 + temp_l83_i3957_w1;
sum_w139 = temp_l83_i3958_w1 + temp_l83_i3959_w1;
sum_w140 = temp_l83_i3960_w1 + temp_l83_i3961_w1;
sum_w13 = temp_l83_i3962_w1 + temp_l83_i3963_w1;
sum_w14 = temp_l83_i3964_w1 + temp_l83_i3965_w1;
sum_w96 = temp_l83_i3966_w1 + temp_l83_i3967_w1;
sum_w97 = temp_l83_i3968_w1 + temp_l83_i3969_w1;
sum_w90 = temp_l83_i3970_w1 + temp_l83_i3971_w1;
sum_w91 = temp_l83_i3972_w1 + temp_l83_i3973_w1;
sum_w84 = temp_l83_i3974_w1 + temp_l83_i3975_w1;
sum_w85 = temp_l83_i3976_w1 + temp_l83_i3977_w1;
sum_w56 = temp_l83_i3978_w1 + temp_l83_i3979_w1;
sum_w57 = temp_l83_i3980_w1 + temp_l83_i3981_w1;
sum_w21 = temp_l83_i3982_w1 + temp_l83_i3983_w1;
sum_w22 = temp_l83_i3984_w1 + temp_l83_i3985_w1;
sum_w92 = temp_l83_i3986_w1 + temp_l83_i3987_w1;
sum_w93 = temp_l83_i3988_w1 + temp_l83_i3989_w1;
sum_w47 = temp_l83_i3990_w1 + temp_l83_i3991_w1;
sum_w48 = temp_l83_i3992_w1 + temp_l83_i3993_w1;
sum_w51 = temp_l83_i3994_w1 + temp_l83_i3995_w1;
sum_w52 = temp_l83_i3996_w1 + temp_l83_i3997_w1;
sum_w129 = temp_l83_i3998_w1 + temp_l83_i3999_w1;
sum_w7 = temp_l83_i38_w1 + temp_l83_i39_w1;
sum_w72 = temp_l83_i2_w1 + temp_l83_i3_w1;
sum_w8 = temp_l83_i40_w1 + temp_l83_i41_w1;
sum_w70 = temp_l83_i42_w1 + temp_l83_i43_w1;
sum_w71 = temp_l83_i44_w1 + temp_l83_i45_w1;
sum_w73 = temp_l83_i4_w1 + temp_l83_i5_w1;
sum_w111 = temp_l83_i6_w1 + temp_l83_i7_w1;
sum_w112 = temp_l83_i8_w1 + temp_l83_i9_w1;

sum_w147 = sum_w102 + sum_w103;
sum_w34 = sum_w108 + sum_w109;
sum_w123 = sum_w111 + sum_w112;
sum_w136 = sum_w121 + sum_w122;
sum_w28 = sum_w13 + sum_w14;
sum_w3 = sum_w131 + sum_w132;
sum_w5 = sum_w137 + sum_w138;
sum_w27 = sum_w139 + sum_w140;
sum_w10 = sum_w149 + sum_w150;
sum_w148 = sum_w153 + sum_w154;
sum_w124 = sum_w155 + sum_w156;
sum_w11 = sum_w17 + sum_w18;
sum_w58 = sum_w21 + sum_w22;
sum_w4 = sum_w37 + sum_w38;
sum_w151 = sum_w47 + sum_w48;
sum_w152 = sum_w51 + sum_w52;
sum_w134 = sum_w56 + sum_w57;
sum_w9 = sum_w62 + sum_w63;
sum_w6 = sum_w66 + sum_w67;
sum_w39 = sum_w7 + sum_w8;
sum_w40 = sum_w70 + sum_w71;
sum_w118 = sum_w72 + sum_w73;
sum_w12 = sum_w76 + sum_w77;
sum_w135 = sum_w78 + sum_w79;
sum_w133 = sum_w84 + sum_w85;
sum_w33 = sum_w86 + sum_w87;
sum_w54 = sum_w90 + sum_w91;
sum_w59 = sum_w92 + sum_w93;
sum_w23 = sum_w94 + sum_w95;
sum_w53 = sum_w96 + sum_w97;

sum_w98 = sum_w11 + sum_w12;
sum_w100 = sum_w117 + sum_w118;
sum_w101 = sum_w123 + sum_w124;
sum_w120 = sum_w133 + sum_w134;
sum_w144 = sum_w135 + sum_w136;
sum_w88 = sum_w147 + sum_w148;
sum_w20 = sum_w151 + sum_w152;
sum_w146 = sum_w27 + sum_w28;
sum_w145 = sum_w3 + sum_w4;
sum_w89 = sum_w33 + sum_w34;
sum_w99 = sum_w39 + sum_w40;
sum_w45 = sum_w5 + sum_w6;
sum_w119 = sum_w53 + sum_w54;
sum_w19 = sum_w58 + sum_w59;
sum_w143 = sum_w9 + sum_w10;

sum_w74 = sum_w100 + sum_w101;
sum_w2 = sum_w119 + sum_w120;
sum_w60 = sum_w143 + sum_w144;
sum_w1 = sum_w145 + sum_w146;
sum_w130 = sum_w19 + sum_w20;
sum_w25 = sum_w88 + sum_w89;
sum_w61 = sum_w98 + sum_w99;

sum_w141 = sum_w1 + sum_w2;
sum_w142 = sum_w130 + sum_w129;
sum_w75 = sum_w60 + sum_w61;

sum_w55 = sum_w141 + sum_w142;
sum_w49 = sum_w74 + sum_w75;

sum_w68 = sum_array_0[60] + sum_w55;

sum_w24 = sum_w69 + sum_w68;

sum_w46 = sum_w23 + sum_w24;

sum_w26 = sum_w45 + sum_w46;

sum_w50 = sum_w25 + sum_w26;

sum_w110 = sum_w49 + sum_w50;

*out = sum_w110 + temp_l83_i4000_w1;

}

void n4000_dotprod_parallel1(short y_0[45], short y_1[3904], short y_2[51], short x_0[45], short x_1[3904], short x_2[51], int *out) {
// Step 2: Initialize local variables
int sum_array_0[61];
#pragma HLS ARRAY_PARTITION variable=y_0 cyclic factor=45 dim=0
#pragma HLS ARRAY_PARTITION variable=y_1 cyclic factor=64 dim=0
#pragma HLS ARRAY_PARTITION variable=y_2 cyclic factor=51 dim=0
#pragma HLS ARRAY_PARTITION variable=x_0 cyclic factor=45 dim=0
#pragma HLS ARRAY_PARTITION variable=x_1 cyclic factor=64 dim=0
#pragma HLS ARRAY_PARTITION variable=x_2 cyclic factor=51 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_0 cyclic factor=2 dim=0
// Initialization done
#pragma HLS dataflow

parallel_0(x_1,y_1,sum_array_0);


epilogue(x_0,x_2,y_0,y_2,sum_array_0,out);

}
