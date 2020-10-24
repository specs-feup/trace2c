#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <float.h>

#define NUM_CLASSES 2
#define MAXDISTANCE DBL_MAX
#define sqr(x) ((x)*(x))

void parallel_0(float knownFeatures[4][128], float xFeatures[128], float distance_array[4]) {
// Step 2: Initialize local variables
float distance_w1;
float distance_w10;
float distance_w100;
float distance_w101;
float distance_w102;
float distance_w103;
float distance_w104;
float distance_w105;
float distance_w106;
float distance_w107;
float distance_w108;
float distance_w109;
float distance_w11;
float distance_w110;
float distance_w111;
float distance_w112;
float distance_w113;
float distance_w114;
float distance_w115;
float distance_w116;
float distance_w117;
float distance_w118;
float distance_w119;
float distance_w12;
float distance_w120;
float distance_w121;
float distance_w122;
float distance_w123;
float distance_w124;
float distance_w125;
float distance_w126;
float distance_w127;
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
float distance_w33;
float distance_w34;
float distance_w35;
float distance_w36;
float distance_w37;
float distance_w38;
float distance_w39;
float distance_w4;
float distance_w40;
float distance_w41;
float distance_w42;
float distance_w43;
float distance_w44;
float distance_w45;
float distance_w46;
float distance_w47;
float distance_w48;
float distance_w49;
float distance_w5;
float distance_w50;
float distance_w51;
float distance_w52;
float distance_w53;
float distance_w54;
float distance_w55;
float distance_w56;
float distance_w57;
float distance_w58;
float distance_w59;
float distance_w6;
float distance_w60;
float distance_w61;
float distance_w62;
float distance_w63;
float distance_w64;
float distance_w65;
float distance_w66;
float distance_w67;
float distance_w68;
float distance_w69;
float distance_w7;
float distance_w70;
float distance_w71;
float distance_w72;
float distance_w73;
float distance_w74;
float distance_w75;
float distance_w76;
float distance_w77;
float distance_w78;
float distance_w79;
float distance_w8;
float distance_w80;
float distance_w81;
float distance_w82;
float distance_w83;
float distance_w84;
float distance_w85;
float distance_w86;
float distance_w87;
float distance_w88;
float distance_w89;
float distance_w9;
float distance_w90;
float distance_w91;
float distance_w92;
float distance_w93;
float distance_w94;
float distance_w95;
float distance_w96;
float distance_w97;
float distance_w98;
float distance_w99;
float temp_l77_i100_w1;
float temp_l77_i101_w1;
float temp_l77_i102_w1;
float temp_l77_i103_w1;
float temp_l77_i104_w1;
float temp_l77_i105_w1;
float temp_l77_i106_w1;
float temp_l77_i107_w1;
float temp_l77_i108_w1;
float temp_l77_i109_w1;
float temp_l77_i10_w1;
float temp_l77_i110_w1;
float temp_l77_i111_w1;
float temp_l77_i112_w1;
float temp_l77_i113_w1;
float temp_l77_i114_w1;
float temp_l77_i115_w1;
float temp_l77_i116_w1;
float temp_l77_i117_w1;
float temp_l77_i118_w1;
float temp_l77_i119_w1;
float temp_l77_i11_w1;
float temp_l77_i120_w1;
float temp_l77_i121_w1;
float temp_l77_i122_w1;
float temp_l77_i123_w1;
float temp_l77_i124_w1;
float temp_l77_i125_w1;
float temp_l77_i126_w1;
float temp_l77_i127_w1;
float temp_l77_i128_w1;
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
float temp_l77_i33_w1;
float temp_l77_i34_w1;
float temp_l77_i35_w1;
float temp_l77_i36_w1;
float temp_l77_i37_w1;
float temp_l77_i38_w1;
float temp_l77_i39_w1;
float temp_l77_i3_w1;
float temp_l77_i40_w1;
float temp_l77_i41_w1;
float temp_l77_i42_w1;
float temp_l77_i43_w1;
float temp_l77_i44_w1;
float temp_l77_i45_w1;
float temp_l77_i46_w1;
float temp_l77_i47_w1;
float temp_l77_i48_w1;
float temp_l77_i49_w1;
float temp_l77_i4_w1;
float temp_l77_i50_w1;
float temp_l77_i51_w1;
float temp_l77_i52_w1;
float temp_l77_i53_w1;
float temp_l77_i54_w1;
float temp_l77_i55_w1;
float temp_l77_i56_w1;
float temp_l77_i57_w1;
float temp_l77_i58_w1;
float temp_l77_i59_w1;
float temp_l77_i5_w1;
float temp_l77_i60_w1;
float temp_l77_i61_w1;
float temp_l77_i62_w1;
float temp_l77_i63_w1;
float temp_l77_i64_w1;
float temp_l77_i65_w1;
float temp_l77_i66_w1;
float temp_l77_i67_w1;
float temp_l77_i68_w1;
float temp_l77_i69_w1;
float temp_l77_i6_w1;
float temp_l77_i70_w1;
float temp_l77_i71_w1;
float temp_l77_i72_w1;
float temp_l77_i73_w1;
float temp_l77_i74_w1;
float temp_l77_i75_w1;
float temp_l77_i76_w1;
float temp_l77_i77_w1;
float temp_l77_i78_w1;
float temp_l77_i79_w1;
float temp_l77_i7_w1;
float temp_l77_i80_w1;
float temp_l77_i81_w1;
float temp_l77_i82_w1;
float temp_l77_i83_w1;
float temp_l77_i84_w1;
float temp_l77_i85_w1;
float temp_l77_i86_w1;
float temp_l77_i87_w1;
float temp_l77_i88_w1;
float temp_l77_i89_w1;
float temp_l77_i8_w1;
float temp_l77_i90_w1;
float temp_l77_i91_w1;
float temp_l77_i92_w1;
float temp_l77_i93_w1;
float temp_l77_i94_w1;
float temp_l77_i95_w1;
float temp_l77_i96_w1;
float temp_l77_i97_w1;
float temp_l77_i98_w1;
float temp_l77_i99_w1;
float temp_l77_i9_w1;
// Initialization done
// starting Loop
for( int i = 0; i < 4;i=i+1){
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
temp_l77_i33_w1 = xFeatures[32] - knownFeatures[i][32];
temp_l77_i34_w1 = xFeatures[33] - knownFeatures[i][33];
temp_l77_i35_w1 = xFeatures[34] - knownFeatures[i][34];
temp_l77_i36_w1 = xFeatures[35] - knownFeatures[i][35];
temp_l77_i37_w1 = xFeatures[36] - knownFeatures[i][36];
temp_l77_i38_w1 = xFeatures[37] - knownFeatures[i][37];
temp_l77_i39_w1 = xFeatures[38] - knownFeatures[i][38];
temp_l77_i40_w1 = xFeatures[39] - knownFeatures[i][39];
temp_l77_i41_w1 = xFeatures[40] - knownFeatures[i][40];
temp_l77_i42_w1 = xFeatures[41] - knownFeatures[i][41];
temp_l77_i43_w1 = xFeatures[42] - knownFeatures[i][42];
temp_l77_i44_w1 = xFeatures[43] - knownFeatures[i][43];
temp_l77_i45_w1 = xFeatures[44] - knownFeatures[i][44];
temp_l77_i46_w1 = xFeatures[45] - knownFeatures[i][45];
temp_l77_i47_w1 = xFeatures[46] - knownFeatures[i][46];
temp_l77_i48_w1 = xFeatures[47] - knownFeatures[i][47];
temp_l77_i49_w1 = xFeatures[48] - knownFeatures[i][48];
temp_l77_i50_w1 = xFeatures[49] - knownFeatures[i][49];
temp_l77_i51_w1 = xFeatures[50] - knownFeatures[i][50];
temp_l77_i52_w1 = xFeatures[51] - knownFeatures[i][51];
temp_l77_i53_w1 = xFeatures[52] - knownFeatures[i][52];
temp_l77_i54_w1 = xFeatures[53] - knownFeatures[i][53];
temp_l77_i55_w1 = xFeatures[54] - knownFeatures[i][54];
temp_l77_i56_w1 = xFeatures[55] - knownFeatures[i][55];
temp_l77_i57_w1 = xFeatures[56] - knownFeatures[i][56];
temp_l77_i58_w1 = xFeatures[57] - knownFeatures[i][57];
temp_l77_i59_w1 = xFeatures[58] - knownFeatures[i][58];
temp_l77_i60_w1 = xFeatures[59] - knownFeatures[i][59];
temp_l77_i61_w1 = xFeatures[60] - knownFeatures[i][60];
temp_l77_i62_w1 = xFeatures[61] - knownFeatures[i][61];
temp_l77_i63_w1 = xFeatures[62] - knownFeatures[i][62];
temp_l77_i64_w1 = xFeatures[63] - knownFeatures[i][63];
temp_l77_i65_w1 = xFeatures[64] - knownFeatures[i][64];
temp_l77_i66_w1 = xFeatures[65] - knownFeatures[i][65];
temp_l77_i67_w1 = xFeatures[66] - knownFeatures[i][66];
temp_l77_i68_w1 = xFeatures[67] - knownFeatures[i][67];
temp_l77_i69_w1 = xFeatures[68] - knownFeatures[i][68];
temp_l77_i70_w1 = xFeatures[69] - knownFeatures[i][69];
temp_l77_i71_w1 = xFeatures[70] - knownFeatures[i][70];
temp_l77_i72_w1 = xFeatures[71] - knownFeatures[i][71];
temp_l77_i73_w1 = xFeatures[72] - knownFeatures[i][72];
temp_l77_i74_w1 = xFeatures[73] - knownFeatures[i][73];
temp_l77_i75_w1 = xFeatures[74] - knownFeatures[i][74];
temp_l77_i76_w1 = xFeatures[75] - knownFeatures[i][75];
temp_l77_i77_w1 = xFeatures[76] - knownFeatures[i][76];
temp_l77_i78_w1 = xFeatures[77] - knownFeatures[i][77];
temp_l77_i79_w1 = xFeatures[78] - knownFeatures[i][78];
temp_l77_i80_w1 = xFeatures[79] - knownFeatures[i][79];
temp_l77_i81_w1 = xFeatures[80] - knownFeatures[i][80];
temp_l77_i82_w1 = xFeatures[81] - knownFeatures[i][81];
temp_l77_i83_w1 = xFeatures[82] - knownFeatures[i][82];
temp_l77_i84_w1 = xFeatures[83] - knownFeatures[i][83];
temp_l77_i85_w1 = xFeatures[84] - knownFeatures[i][84];
temp_l77_i86_w1 = xFeatures[85] - knownFeatures[i][85];
temp_l77_i87_w1 = xFeatures[86] - knownFeatures[i][86];
temp_l77_i88_w1 = xFeatures[87] - knownFeatures[i][87];
temp_l77_i89_w1 = xFeatures[88] - knownFeatures[i][88];
temp_l77_i90_w1 = xFeatures[89] - knownFeatures[i][89];
temp_l77_i91_w1 = xFeatures[90] - knownFeatures[i][90];
temp_l77_i92_w1 = xFeatures[91] - knownFeatures[i][91];
temp_l77_i93_w1 = xFeatures[92] - knownFeatures[i][92];
temp_l77_i94_w1 = xFeatures[93] - knownFeatures[i][93];
temp_l77_i95_w1 = xFeatures[94] - knownFeatures[i][94];
temp_l77_i96_w1 = xFeatures[95] - knownFeatures[i][95];
temp_l77_i97_w1 = xFeatures[96] - knownFeatures[i][96];
temp_l77_i98_w1 = xFeatures[97] - knownFeatures[i][97];
temp_l77_i99_w1 = xFeatures[98] - knownFeatures[i][98];
temp_l77_i100_w1 = xFeatures[99] - knownFeatures[i][99];
temp_l77_i101_w1 = xFeatures[100] - knownFeatures[i][100];
temp_l77_i102_w1 = xFeatures[101] - knownFeatures[i][101];
temp_l77_i103_w1 = xFeatures[102] - knownFeatures[i][102];
temp_l77_i104_w1 = xFeatures[103] - knownFeatures[i][103];
temp_l77_i105_w1 = xFeatures[104] - knownFeatures[i][104];
temp_l77_i106_w1 = xFeatures[105] - knownFeatures[i][105];
temp_l77_i107_w1 = xFeatures[106] - knownFeatures[i][106];
temp_l77_i108_w1 = xFeatures[107] - knownFeatures[i][107];
temp_l77_i109_w1 = xFeatures[108] - knownFeatures[i][108];
temp_l77_i110_w1 = xFeatures[109] - knownFeatures[i][109];
temp_l77_i111_w1 = xFeatures[110] - knownFeatures[i][110];
temp_l77_i112_w1 = xFeatures[111] - knownFeatures[i][111];
temp_l77_i113_w1 = xFeatures[112] - knownFeatures[i][112];
temp_l77_i114_w1 = xFeatures[113] - knownFeatures[i][113];
temp_l77_i115_w1 = xFeatures[114] - knownFeatures[i][114];
temp_l77_i116_w1 = xFeatures[115] - knownFeatures[i][115];
temp_l77_i117_w1 = xFeatures[116] - knownFeatures[i][116];
temp_l77_i118_w1 = xFeatures[117] - knownFeatures[i][117];
temp_l77_i119_w1 = xFeatures[118] - knownFeatures[i][118];
temp_l77_i120_w1 = xFeatures[119] - knownFeatures[i][119];
temp_l77_i121_w1 = xFeatures[120] - knownFeatures[i][120];
temp_l77_i122_w1 = xFeatures[121] - knownFeatures[i][121];
temp_l77_i123_w1 = xFeatures[122] - knownFeatures[i][122];
temp_l77_i124_w1 = xFeatures[123] - knownFeatures[i][123];
temp_l77_i125_w1 = xFeatures[124] - knownFeatures[i][124];
temp_l77_i126_w1 = xFeatures[125] - knownFeatures[i][125];
temp_l77_i127_w1 = xFeatures[126] - knownFeatures[i][126];
temp_l77_i128_w1 = xFeatures[127] - knownFeatures[i][127];

distance_w62 = sqr(temp_l77_i28_w1) + sqr(temp_l77_i29_w1);
distance_w8 = sqr(temp_l77_i22_w1) + sqr(temp_l77_i23_w1);
distance_w107 = sqr(temp_l77_i50_w1) + sqr(temp_l77_i51_w1);
distance_w24 = sqr(temp_l77_i80_w1) + sqr(temp_l77_i81_w1);
distance_w85 = sqr(temp_l77_i96_w1) + sqr(temp_l77_i97_w1);
distance_w125 = sqr(temp_l77_i112_w1) + sqr(temp_l77_i113_w1);
distance_w30 = sqr(temp_l77_i114_w1) + sqr(temp_l77_i115_w1);
distance_w66 = sqr(temp_l77_i106_w1) + sqr(temp_l77_i107_w1);
distance_w48 = sqr(temp_l77_i64_w1) + sqr(temp_l77_i65_w1);
distance_w56 = sqr(temp_l77_i90_w1) + sqr(temp_l77_i91_w1);
distance_w110 = sqr(temp_l77_i54_w1) + sqr(temp_l77_i55_w1);
distance_w50 = sqr(temp_l77_i116_w1) + sqr(temp_l77_i117_w1);
distance_w104 = sqr(temp_l77_i20_w1) + sqr(temp_l77_i21_w1);
distance_w118 = sqr(temp_l77_i60_w1) + sqr(temp_l77_i61_w1);
distance_w14 = sqr(temp_l77_i12_w1) + sqr(temp_l77_i13_w1);
distance_w89 = sqr(temp_l77_i110_w1) + sqr(temp_l77_i111_w1);
distance_w88 = sqr(temp_l77_i120_w1) + sqr(temp_l77_i121_w1);
distance_w34 = sqr(temp_l77_i126_w1) + sqr(temp_l77_i127_w1);
distance_w1 = sqr(temp_l77_i24_w1) + sqr(temp_l77_i25_w1);
distance_w40 = sqr(temp_l77_i38_w1) + sqr(temp_l77_i39_w1);
distance_w70 = sqr(temp_l77_i88_w1) + sqr(temp_l77_i89_w1);
distance_w101 = sqr(temp_l77_i86_w1) + sqr(temp_l77_i87_w1);
distance_w13 = sqr(temp_l77_i14_w1) + sqr(temp_l77_i15_w1);
distance_w73 = sqr(temp_l77_i16_w1) + sqr(temp_l77_i17_w1);
distance_w92 = sqr(temp_l77_i52_w1) + sqr(temp_l77_i53_w1);
distance_w58 = sqr(temp_l77_i48_w1) + sqr(temp_l77_i49_w1);
distance_w41 = sqr(temp_l77_i10_w1) + sqr(temp_l77_i11_w1);
distance_w47 = sqr(temp_l77_i66_w1) + sqr(temp_l77_i67_w1);
distance_w87 = sqr(temp_l77_i94_w1) + sqr(temp_l77_i95_w1);
distance_w71 = sqr(temp_l77_i100_w1) + sqr(temp_l77_i101_w1);
distance_w86 = sqr(temp_l77_i76_w1) + sqr(temp_l77_i77_w1);
distance_w77 = sqr(temp_l77_i102_w1) + sqr(temp_l77_i103_w1);
distance_w16 = sqr(temp_l77_i42_w1) + sqr(temp_l77_i43_w1);
distance_w21 = sqr(temp_l77_i78_w1) + sqr(temp_l77_i79_w1);
distance_w112 = sqr(temp_l77_i92_w1) + sqr(temp_l77_i93_w1);
distance_w122 = sqr(temp_l77_i36_w1) + sqr(temp_l77_i37_w1);
distance_w23 = sqr(temp_l77_i82_w1) + sqr(temp_l77_i83_w1);
distance_w126 = sqr(temp_l77_i122_w1) + sqr(temp_l77_i123_w1);
distance_w57 = sqr(temp_l77_i4_w1) + sqr(temp_l77_i5_w1);
distance_w61 = sqr(temp_l77_i30_w1) + sqr(temp_l77_i31_w1);
distance_w64 = sqr(temp_l77_i124_w1) + sqr(temp_l77_i125_w1);
distance_w75 = sqr(temp_l77_i6_w1) + sqr(temp_l77_i7_w1);
distance_w115 = sqr(temp_l77_i70_w1) + sqr(temp_l77_i71_w1);
distance_w39 = sqr(temp_l77_i2_w1) + sqr(temp_l77_i3_w1);
distance_w59 = sqr(temp_l77_i84_w1) + sqr(temp_l77_i85_w1);
distance_w42 = sqr(temp_l77_i44_w1) + sqr(temp_l77_i45_w1);
distance_w38 = 0 + sqr(temp_l77_i1_w1);
distance_w95 = sqr(temp_l77_i46_w1) + sqr(temp_l77_i47_w1);
distance_w117 = sqr(temp_l77_i68_w1) + sqr(temp_l77_i69_w1);
distance_w84 = sqr(temp_l77_i98_w1) + sqr(temp_l77_i99_w1);
distance_w52 = sqr(temp_l77_i8_w1) + sqr(temp_l77_i9_w1);
distance_w79 = sqr(temp_l77_i34_w1) + sqr(temp_l77_i35_w1);
distance_w68 = sqr(temp_l77_i118_w1) + sqr(temp_l77_i119_w1);
distance_w19 = sqr(temp_l77_i74_w1) + sqr(temp_l77_i75_w1);
distance_w72 = sqr(temp_l77_i56_w1) + sqr(temp_l77_i57_w1);
distance_w119 = sqr(temp_l77_i26_w1) + sqr(temp_l77_i27_w1);
distance_w43 = sqr(temp_l77_i18_w1) + sqr(temp_l77_i19_w1);
distance_w76 = sqr(temp_l77_i108_w1) + sqr(temp_l77_i109_w1);
distance_w20 = sqr(temp_l77_i72_w1) + sqr(temp_l77_i73_w1);
distance_w82 = sqr(temp_l77_i58_w1) + sqr(temp_l77_i59_w1);
distance_w25 = sqr(temp_l77_i62_w1) + sqr(temp_l77_i63_w1);
distance_w17 = sqr(temp_l77_i40_w1) + sqr(temp_l77_i41_w1);
distance_w80 = sqr(temp_l77_i32_w1) + sqr(temp_l77_i33_w1);
distance_w51 = sqr(temp_l77_i104_w1) + sqr(temp_l77_i105_w1);

distance_w6 = distance_w104 + distance_w8;
distance_w12 = distance_w14 + distance_w13;
distance_w15 = distance_w17 + distance_w16;
distance_w18 = distance_w20 + distance_w19;
distance_w26 = distance_w86 + distance_w21;
distance_w22 = distance_w24 + distance_w23;
distance_w123 = distance_w118 + distance_w25;
distance_w120 = distance_w125 + distance_w30;
distance_w99 = distance_w64 + distance_w34;
distance_w37 = distance_w38 + distance_w39;
distance_w121 = distance_w122 + distance_w40;
distance_w69 = distance_w52 + distance_w41;
distance_w7 = distance_w73 + distance_w43;
distance_w46 = distance_w48 + distance_w47;
distance_w11 = distance_w70 + distance_w56;
distance_w60 = distance_w62 + distance_w61;
distance_w65 = distance_w51 + distance_w66;
distance_w67 = distance_w50 + distance_w68;
distance_w74 = distance_w57 + distance_w75;
distance_w114 = distance_w71 + distance_w77;
distance_w78 = distance_w80 + distance_w79;
distance_w81 = distance_w72 + distance_w82;
distance_w83 = distance_w85 + distance_w84;
distance_w10 = distance_w112 + distance_w87;
distance_w98 = distance_w76 + distance_w89;
distance_w127 = distance_w42 + distance_w95;
distance_w124 = distance_w59 + distance_w101;
distance_w111 = distance_w58 + distance_w107;
distance_w109 = distance_w92 + distance_w110;
distance_w108 = distance_w117 + distance_w115;
distance_w94 = distance_w1 + distance_w119;
distance_w100 = distance_w88 + distance_w126;

distance_w5 = distance_w7 + distance_w6;
distance_w9 = distance_w11 + distance_w10;
distance_w106 = distance_w69 + distance_w12;
distance_w3 = distance_w18 + distance_w26;
distance_w105 = distance_w37 + distance_w74;
distance_w93 = distance_w94 + distance_w60;
distance_w55 = distance_w120 + distance_w67;
distance_w32 = distance_w65 + distance_w98;
distance_w54 = distance_w100 + distance_w99;
distance_w4 = distance_w46 + distance_w108;
distance_w113 = distance_w111 + distance_w109;
distance_w33 = distance_w83 + distance_w114;
distance_w45 = distance_w78 + distance_w121;
distance_w116 = distance_w81 + distance_w123;
distance_w36 = distance_w22 + distance_w124;
distance_w44 = distance_w15 + distance_w127;

distance_w2 = distance_w4 + distance_w3;
distance_w35 = distance_w36 + distance_w9;
distance_w31 = distance_w33 + distance_w32;
distance_w29 = distance_w45 + distance_w44;
distance_w53 = distance_w55 + distance_w54;
distance_w103 = distance_w5 + distance_w93;
distance_w102 = distance_w105 + distance_w106;
distance_w28 = distance_w113 + distance_w116;

distance_w27 = distance_w29 + distance_w28;
distance_w49 = distance_w2 + distance_w35;
distance_w91 = distance_w31 + distance_w53;
distance_w97 = distance_w102 + distance_w103;

distance_w90 = distance_w49 + distance_w91;
distance_w96 = distance_w97 + distance_w27;

distance_w63 = distance_w96 + distance_w90;

distance_array[i] = distance_w63 + sqr(temp_l77_i128_w1);

}
}

void epilogue(char knownClasses[8], float distance_array_1[4], float distance_array_0[4], char *out) {
// Step 2: Initialize local variables
char BestPointsClasses[3];
float BestPointsDistances[3];
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
float d1_w1;
float d2_w1;
float d3_w1;
float dbest_w1;
float dbest_w10;
float dbest_w11;
float dbest_w12;
float dbest_w13;
float dbest_w14;
float dbest_w15;
float dbest_w16;
float dbest_w17;
float dbest_w18;
float dbest_w19;
float dbest_w2;
float dbest_w20;
float dbest_w21;
float dbest_w22;
float dbest_w23;
float dbest_w24;
float dbest_w25;
float dbest_w26;
float dbest_w27;
float dbest_w28;
float dbest_w29;
float dbest_w3;
float dbest_w30;
float dbest_w31;
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
float max_tmp_w1;
float max_tmp_w10;
float max_tmp_w11;
float max_tmp_w12;
float max_tmp_w13;
float max_tmp_w14;
float max_tmp_w15;
float max_tmp_w16;
float max_tmp_w17;
float max_tmp_w18;
float max_tmp_w19;
float max_tmp_w2;
float max_tmp_w20;
float max_tmp_w21;
float max_tmp_w22;
float max_tmp_w23;
float max_tmp_w24;
float max_tmp_w3;
float max_tmp_w4;
float max_tmp_w5;
float max_tmp_w6;
float max_tmp_w7;
float max_tmp_w8;
float max_tmp_w9;
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
float mindist_w1;
float mindist_w2;
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
max_tmp_w10 = 0;
max_tmp_w3 = 0;
max_tmp_w24 = 0;
max_tmp_w11 = 0;
max_tmp_w5 = 0;
max_tmp_w7 = 0;
max_tmp_w8 = 0;
max_tmp_w17 = 0;
BestPointsDistances[1] = MAXDISTANCE;
BestPointsDistances[2] = MAXDISTANCE;
BestPointsDistances[0] = MAXDISTANCE;
BestPointsClasses[1] = NUM_CLASSES;
BestPointsClasses[0] = NUM_CLASSES;
BestPointsClasses[2] = NUM_CLASSES;

dbest_w22 = BestPointsDistances[0];
dbest_w12 = BestPointsDistances[1];
dbest_w14 = BestPointsDistances[2];

operationOutput_w25 = dbest_w22 > max_tmp_w5;

max_w12 = operationOutput_w25 ? dbest_w22:0;
index_w22 = operationOutput_w25 ? 0:0;

max_tmp_w15 = max_w12;

operationOutput_w33 = dbest_w12 > max_tmp_w15;

index_w23 = operationOutput_w33 ? 1:index_w22;
max_w23 = operationOutput_w33 ? dbest_w12:max_w12;

max_tmp_w23 = max_w23;

operationOutput_w36 = dbest_w14 > max_tmp_w23;

max_w3 = operationOutput_w36 ? dbest_w14:max_w23;
index_w7 = operationOutput_w36 ? 2:index_w23;

operationOutput_w3 = distance_array_0[0] < max_w3;

cbest_w1 = BestPointsClasses[index_w7];
dbest_w5 = BestPointsDistances[index_w7];

muxOutput_w5 = operationOutput_w3 ? knownClasses[0]:cbest_w1;
muxOutput_w2 = operationOutput_w3 ? distance_array_0[0]:dbest_w5;

BestPointsClasses[index_w7] = muxOutput_w5;
BestPointsDistances[index_w7] = muxOutput_w2;


dbest_w18 = BestPointsDistances[0];
dbest_w25 = BestPointsDistances[1];
dbest_w11 = BestPointsDistances[2];

operationOutput_w2 = dbest_w18 > max_tmp_w17;

index_w2 = operationOutput_w2 ? 0:0;
max_w6 = operationOutput_w2 ? dbest_w18:0;

max_tmp_w9 = max_w6;

operationOutput_w22 = dbest_w25 > max_tmp_w9;

index_w13 = operationOutput_w22 ? 1:index_w2;
max_w14 = operationOutput_w22 ? dbest_w25:max_w6;

max_tmp_w16 = max_w14;

operationOutput_w26 = dbest_w11 > max_tmp_w16;

max_w19 = operationOutput_w26 ? dbest_w11:max_w14;
index_w1 = operationOutput_w26 ? 2:index_w13;

operationOutput_w12 = distance_array_0[1] < max_w19;

cbest_w7 = BestPointsClasses[index_w1];
dbest_w10 = BestPointsDistances[index_w1];

muxOutput_w6 = operationOutput_w12 ? distance_array_0[1]:dbest_w10;
muxOutput_w1 = operationOutput_w12 ? knownClasses[1]:cbest_w7;

BestPointsDistances[index_w1] = muxOutput_w6;
BestPointsClasses[index_w1] = muxOutput_w1;


dbest_w24 = BestPointsDistances[0];
dbest_w32 = BestPointsDistances[1];
dbest_w19 = BestPointsDistances[2];

operationOutput_w27 = dbest_w24 > max_tmp_w3;

max_w24 = operationOutput_w27 ? dbest_w24:0;
index_w20 = operationOutput_w27 ? 0:0;

max_tmp_w22 = max_w24;

operationOutput_w37 = dbest_w32 > max_tmp_w22;

index_w24 = operationOutput_w37 ? 1:index_w20;
max_w17 = operationOutput_w37 ? dbest_w32:max_w24;

max_tmp_w12 = max_w17;

operationOutput_w23 = dbest_w19 > max_tmp_w12;

max_w5 = operationOutput_w23 ? dbest_w19:max_w17;
index_w3 = operationOutput_w23 ? 2:index_w24;

operationOutput_w9 = distance_array_0[2] < max_w5;

cbest_w4 = BestPointsClasses[index_w3];
dbest_w8 = BestPointsDistances[index_w3];

muxOutput_w7 = operationOutput_w9 ? distance_array_0[2]:dbest_w8;
muxOutput_w3 = operationOutput_w9 ? knownClasses[2]:cbest_w4;

BestPointsClasses[index_w3] = muxOutput_w3;
BestPointsDistances[index_w3] = muxOutput_w7;


dbest_w3 = BestPointsDistances[0];
dbest_w27 = BestPointsDistances[1];
dbest_w26 = BestPointsDistances[2];

operationOutput_w15 = dbest_w3 > max_tmp_w7;

index_w16 = operationOutput_w15 ? 0:0;
max_w20 = operationOutput_w15 ? dbest_w3:0;

max_tmp_w20 = max_w20;

operationOutput_w28 = dbest_w27 > max_tmp_w20;

max_w16 = operationOutput_w28 ? dbest_w27:max_w20;
index_w15 = operationOutput_w28 ? 1:index_w16;

max_tmp_w14 = max_w16;

operationOutput_w29 = dbest_w26 > max_tmp_w14;

index_w11 = operationOutput_w29 ? 2:index_w15;
max_w15 = operationOutput_w29 ? dbest_w26:max_w16;

operationOutput_w35 = distance_array_0[3] < max_w15;

cbest_w8 = BestPointsClasses[index_w11];
dbest_w15 = BestPointsDistances[index_w11];

muxOutput_w13 = operationOutput_w35 ? distance_array_0[3]:dbest_w15;
muxOutput_w16 = operationOutput_w35 ? knownClasses[3]:cbest_w8;

BestPointsClasses[index_w11] = muxOutput_w16;
BestPointsDistances[index_w11] = muxOutput_w13;


dbest_w17 = BestPointsDistances[0];
dbest_w9 = BestPointsDistances[1];
dbest_w1 = BestPointsDistances[2];

operationOutput_w17 = dbest_w17 > max_tmp_w24;

max_w9 = operationOutput_w17 ? dbest_w17:0;
index_w8 = operationOutput_w17 ? 0:0;

max_tmp_w2 = max_w9;

operationOutput_w8 = dbest_w9 > max_tmp_w2;

index_w10 = operationOutput_w8 ? 1:index_w8;
max_w22 = operationOutput_w8 ? dbest_w9:max_w9;

max_tmp_w6 = max_w22;

operationOutput_w13 = dbest_w1 > max_tmp_w6;

max_w10 = operationOutput_w13 ? dbest_w1:max_w22;
index_w19 = operationOutput_w13 ? 2:index_w10;

operationOutput_w18 = distance_array_1[0] < max_w10;

cbest_w2 = BestPointsClasses[index_w19];
dbest_w31 = BestPointsDistances[index_w19];

muxOutput_w15 = operationOutput_w18 ? distance_array_1[0]:dbest_w31;
muxOutput_w8 = operationOutput_w18 ? knownClasses[4]:cbest_w2;

BestPointsClasses[index_w19] = muxOutput_w8;
BestPointsDistances[index_w19] = muxOutput_w15;


dbest_w28 = BestPointsDistances[0];
dbest_w30 = BestPointsDistances[1];
dbest_w2 = BestPointsDistances[2];

operationOutput_w11 = dbest_w28 > max_tmp_w11;

max_w21 = operationOutput_w11 ? dbest_w28:0;
index_w5 = operationOutput_w11 ? 0:0;

max_tmp_w18 = max_w21;

operationOutput_w4 = dbest_w30 > max_tmp_w18;

index_w4 = operationOutput_w4 ? 1:index_w5;
max_w8 = operationOutput_w4 ? dbest_w30:max_w21;

max_tmp_w1 = max_w8;

operationOutput_w5 = dbest_w2 > max_tmp_w1;

index_w14 = operationOutput_w5 ? 2:index_w4;
max_w7 = operationOutput_w5 ? dbest_w2:max_w8;

operationOutput_w20 = distance_array_1[1] < max_w7;

cbest_w5 = BestPointsClasses[index_w14];
dbest_w23 = BestPointsDistances[index_w14];

muxOutput_w10 = operationOutput_w20 ? knownClasses[5]:cbest_w5;
muxOutput_w14 = operationOutput_w20 ? distance_array_1[1]:dbest_w23;

BestPointsClasses[index_w14] = muxOutput_w10;
BestPointsDistances[index_w14] = muxOutput_w14;


dbest_w16 = BestPointsDistances[0];
dbest_w21 = BestPointsDistances[1];
dbest_w4 = BestPointsDistances[2];

operationOutput_w19 = dbest_w16 > max_tmp_w10;

index_w9 = operationOutput_w19 ? 0:0;
max_w11 = operationOutput_w19 ? dbest_w16:0;

max_tmp_w19 = max_w11;

operationOutput_w21 = dbest_w21 > max_tmp_w19;

index_w12 = operationOutput_w21 ? 1:index_w9;
max_w2 = operationOutput_w21 ? dbest_w21:max_w11;

max_tmp_w21 = max_w2;

operationOutput_w1 = dbest_w4 > max_tmp_w21;

index_w6 = operationOutput_w1 ? 2:index_w12;
max_w1 = operationOutput_w1 ? dbest_w4:max_w2;

operationOutput_w6 = distance_array_1[2] < max_w1;

cbest_w3 = BestPointsClasses[index_w6];
dbest_w6 = BestPointsDistances[index_w6];

muxOutput_w4 = operationOutput_w6 ? distance_array_1[2]:dbest_w6;
muxOutput_w9 = operationOutput_w6 ? knownClasses[6]:cbest_w3;

BestPointsClasses[index_w6] = muxOutput_w9;
BestPointsDistances[index_w6] = muxOutput_w4;


dbest_w13 = BestPointsDistances[0];
dbest_w20 = BestPointsDistances[1];
dbest_w7 = BestPointsDistances[2];

operationOutput_w16 = dbest_w13 > max_tmp_w8;

max_w13 = operationOutput_w16 ? dbest_w13:0;
index_w21 = operationOutput_w16 ? 0:0;

max_tmp_w13 = max_w13;

operationOutput_w24 = dbest_w20 > max_tmp_w13;

index_w18 = operationOutput_w24 ? 1:index_w21;
max_w4 = operationOutput_w24 ? dbest_w20:max_w13;

max_tmp_w4 = max_w4;

operationOutput_w30 = dbest_w7 > max_tmp_w4;

max_w18 = operationOutput_w30 ? dbest_w7:max_w4;
index_w17 = operationOutput_w30 ? 2:index_w18;

operationOutput_w31 = distance_array_1[3] < max_w18;

cbest_w6 = BestPointsClasses[index_w17];
dbest_w29 = BestPointsDistances[index_w17];

muxOutput_w12 = operationOutput_w31 ? distance_array_1[3]:dbest_w29;
muxOutput_w11 = operationOutput_w31 ? knownClasses[7]:cbest_w6;

BestPointsClasses[index_w17] = muxOutput_w11;
BestPointsDistances[index_w17] = muxOutput_w12;


c1_w1 = BestPointsClasses[0];
c2_w1 = BestPointsClasses[1];
c3_w1 = BestPointsClasses[2];
d1_w1 = BestPointsDistances[0];
d2_w1 = BestPointsDistances[1];
d3_w1 = BestPointsDistances[2];

operationOutput_w14 = c1_w1 == c3_w1;
operationOutput_w34 = c2_w1 == c3_w1;
mindist_w1 = d1_w1;
operationOutput_w10 = c1_w1 == c2_w1;

operationOutput_w32 = mindist_w1 > d2_w1;
operationOutput_w7 = mindist_w1 > d2_w1;

classID_w1 = operationOutput_w7 ? c2_w1:c1_w1;
mindist_w2 = operationOutput_w32 ? d2_w1:d1_w1;

operationOutput_w38 = mindist_w2 > d3_w1;

classID_w4 = operationOutput_w38 ? c3_w1:classID_w1;

classID_w3 = operationOutput_w34 ? c2_w1:classID_w4;

classID_w2 = operationOutput_w14 ? c1_w1:classID_w3;

*out = operationOutput_w10 ? c1_w1:classID_w2;

}

void knnFloatNoSqrt8p128f_2parallel(float xFeatures[128], char knownClasses[8], float knownFeatures_0[4][128], float knownFeatures_1[4][128], char *out) {
// Step 2: Initialize local variables
float distance_array_0[4];
float distance_array_1[4];
#pragma HLS ARRAY_PARTITION variable=xFeatures cyclic factor=128 dim=1
// Initialization done
#pragma HLS dataflow

parallel_0(knownFeatures_0,xFeatures,distance_array_0);
parallel_0(knownFeatures_1,xFeatures,distance_array_1);


epilogue(knownClasses,distance_array_1,distance_array_0,out);

}
