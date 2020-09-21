#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <float.h>

#define NUM_CLASSES 2
#define MAXDISTANCE DBL_MAX
#define sqr(x) ((x) * (x))

void parallel_0(float knownFeatures[250][128], float xFeatures[128], float distance_array[250])
{
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
    for (int i = 0; i < 250; i = i + 1)
    {
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

        distance_w33 = sqr(temp_l77_i87_w1) + sqr(temp_l77_i88_w1);
        distance_w81 = sqr(temp_l77_i97_w1) + sqr(temp_l77_i98_w1);
        distance_w56 = sqr(temp_l77_i89_w1) + sqr(temp_l77_i90_w1);
        distance_w61 = sqr(temp_l77_i83_w1) + sqr(temp_l77_i84_w1);
        distance_w30 = sqr(temp_l77_i113_w1) + sqr(temp_l77_i114_w1);
        distance_w66 = sqr(temp_l77_i105_w1) + sqr(temp_l77_i106_w1);
        distance_w76 = sqr(temp_l77_i107_w1) + sqr(temp_l77_i108_w1);
        distance_w32 = sqr(temp_l77_i125_w1) + sqr(temp_l77_i126_w1);
        distance_w85 = sqr(temp_l77_i93_w1) + sqr(temp_l77_i94_w1);
        distance_w49 = sqr(temp_l77_i115_w1) + sqr(temp_l77_i116_w1);
        distance_w67 = sqr(temp_l77_i117_w1) + sqr(temp_l77_i118_w1);
        distance_w70 = sqr(temp_l77_i99_w1) + sqr(temp_l77_i100_w1);
        distance_w36 = 0 + sqr(temp_l77_i1_w1);
        distance_w102 = sqr(temp_l77_i85_w1) + sqr(temp_l77_i86_w1);
        distance_w65 = sqr(temp_l77_i123_w1) + sqr(temp_l77_i124_w1);
        distance_w97 = sqr(temp_l77_i111_w1) + sqr(temp_l77_i112_w1);
        distance_w9 = sqr(temp_l77_i91_w1) + sqr(temp_l77_i92_w1);
        distance_w88 = sqr(temp_l77_i109_w1) + sqr(temp_l77_i110_w1);
        distance_w53 = sqr(temp_l77_i119_w1) + sqr(temp_l77_i120_w1);
        distance_w125 = sqr(temp_l77_i121_w1) + sqr(temp_l77_i122_w1);
        distance_w19 = sqr(temp_l77_i81_w1) + sqr(temp_l77_i82_w1);
        distance_w77 = sqr(temp_l77_i101_w1) + sqr(temp_l77_i102_w1);
        distance_w27 = sqr(temp_l77_i103_w1) + sqr(temp_l77_i104_w1);
        distance_w89 = sqr(temp_l77_i95_w1) + sqr(temp_l77_i96_w1);

        distance_w31 = distance_w32 + sqr(temp_l77_i127_w1);
        distance_w35 = distance_w36 + sqr(temp_l77_i2_w1);
        distance_w114 = distance_w19 + distance_w61;
        distance_w29 = distance_w30 + distance_w49;
        distance_w10 = distance_w56 + distance_w9;
        distance_w110 = distance_w66 + distance_w76;
        distance_w54 = distance_w67 + distance_w53;
        distance_w28 = distance_w77 + distance_w27;
        distance_w100 = distance_w81 + distance_w70;
        distance_w84 = distance_w85 + distance_w89;
        distance_w87 = distance_w88 + distance_w97;
        distance_w34 = distance_w102 + distance_w33;
        distance_w98 = distance_w125 + distance_w65;

        distance_w115 = distance_w35 + sqr(temp_l77_i3_w1);
        distance_w113 = distance_w10 + distance_w84;
        distance_w48 = distance_w29 + distance_w54;
        distance_w64 = distance_w98 + distance_w31;
        distance_w69 = distance_w100 + distance_w28;
        distance_w75 = distance_w110 + distance_w87;
        distance_w60 = distance_w114 + distance_w34;

        distance_w58 = distance_w115 + sqr(temp_l77_i4_w1);
        distance_w86 = distance_w48 + distance_w64;
        distance_w68 = distance_w60 + distance_w113;
        distance_w50 = distance_w69 + distance_w75;

        distance_w57 = distance_w58 + sqr(temp_l77_i5_w1);
        distance_w82 = distance_w68 + distance_w50;

        distance_w74 = distance_w57 + sqr(temp_l77_i6_w1);
        distance_w124 = distance_w82 + distance_w86;

        distance_w106 = distance_w74 + sqr(temp_l77_i7_w1);

        distance_w52 = distance_w106 + sqr(temp_l77_i8_w1);

        distance_w51 = distance_w52 + sqr(temp_l77_i9_w1);

        distance_w40 = distance_w51 + sqr(temp_l77_i10_w1);

        distance_w39 = distance_w40 + sqr(temp_l77_i11_w1);

        distance_w126 = distance_w39 + sqr(temp_l77_i12_w1);

        distance_w12 = distance_w126 + sqr(temp_l77_i13_w1);

        distance_w11 = distance_w12 + sqr(temp_l77_i14_w1);

        distance_w104 = distance_w11 + sqr(temp_l77_i15_w1);

        distance_w103 = distance_w104 + sqr(temp_l77_i16_w1);

        distance_w73 = distance_w103 + sqr(temp_l77_i17_w1);

        distance_w44 = distance_w73 + sqr(temp_l77_i18_w1);

        distance_w6 = distance_w44 + sqr(temp_l77_i19_w1);

        distance_w5 = distance_w6 + sqr(temp_l77_i20_w1);

        distance_w105 = distance_w5 + sqr(temp_l77_i21_w1);

        distance_w8 = distance_w105 + sqr(temp_l77_i22_w1);

        distance_w7 = distance_w8 + sqr(temp_l77_i23_w1);

        distance_w2 = distance_w7 + sqr(temp_l77_i24_w1);

        distance_w1 = distance_w2 + sqr(temp_l77_i25_w1);

        distance_w121 = distance_w1 + sqr(temp_l77_i26_w1);

        distance_w93 = distance_w121 + sqr(temp_l77_i27_w1);

        distance_w92 = distance_w93 + sqr(temp_l77_i28_w1);

        distance_w63 = distance_w92 + sqr(temp_l77_i29_w1);

        distance_w62 = distance_w63 + sqr(temp_l77_i30_w1);

        distance_w96 = distance_w62 + sqr(temp_l77_i31_w1);

        distance_w95 = distance_w96 + sqr(temp_l77_i32_w1);

        distance_w79 = distance_w95 + sqr(temp_l77_i33_w1);

        distance_w78 = distance_w79 + sqr(temp_l77_i34_w1);

        distance_w127 = distance_w78 + sqr(temp_l77_i35_w1);

        distance_w123 = distance_w127 + sqr(temp_l77_i36_w1);

        distance_w122 = distance_w123 + sqr(temp_l77_i37_w1);

        distance_w38 = distance_w122 + sqr(temp_l77_i38_w1);

        distance_w37 = distance_w38 + sqr(temp_l77_i39_w1);

        distance_w43 = distance_w37 + sqr(temp_l77_i40_w1);

        distance_w14 = distance_w43 + sqr(temp_l77_i41_w1);

        distance_w13 = distance_w14 + sqr(temp_l77_i42_w1);

        distance_w55 = distance_w13 + sqr(temp_l77_i43_w1);

        distance_w42 = distance_w55 + sqr(temp_l77_i44_w1);

        distance_w41 = distance_w42 + sqr(temp_l77_i45_w1);

        distance_w94 = distance_w41 + sqr(temp_l77_i46_w1);

        distance_w26 = distance_w94 + sqr(temp_l77_i47_w1);

        distance_w25 = distance_w26 + sqr(temp_l77_i48_w1);

        distance_w59 = distance_w25 + sqr(temp_l77_i49_w1);

        distance_w109 = distance_w59 + sqr(temp_l77_i50_w1);

        distance_w108 = distance_w109 + sqr(temp_l77_i51_w1);

        distance_w91 = distance_w108 + sqr(temp_l77_i52_w1);

        distance_w90 = distance_w91 + sqr(temp_l77_i53_w1);

        distance_w112 = distance_w90 + sqr(temp_l77_i54_w1);

        distance_w117 = distance_w112 + sqr(temp_l77_i55_w1);

        distance_w72 = distance_w117 + sqr(temp_l77_i56_w1);

        distance_w71 = distance_w72 + sqr(temp_l77_i57_w1);

        distance_w80 = distance_w71 + sqr(temp_l77_i58_w1);

        distance_w99 = distance_w80 + sqr(temp_l77_i59_w1);

        distance_w120 = distance_w99 + sqr(temp_l77_i60_w1);

        distance_w119 = distance_w120 + sqr(temp_l77_i61_w1);

        distance_w22 = distance_w119 + sqr(temp_l77_i62_w1);

        distance_w21 = distance_w22 + sqr(temp_l77_i63_w1);

        distance_w101 = distance_w21 + sqr(temp_l77_i64_w1);

        distance_w46 = distance_w101 + sqr(temp_l77_i65_w1);

        distance_w45 = distance_w46 + sqr(temp_l77_i66_w1);

        distance_w107 = distance_w45 + sqr(temp_l77_i67_w1);

        distance_w111 = distance_w107 + sqr(temp_l77_i68_w1);

        distance_w118 = distance_w111 + sqr(temp_l77_i69_w1);

        distance_w116 = distance_w118 + sqr(temp_l77_i70_w1);

        distance_w4 = distance_w116 + sqr(temp_l77_i71_w1);

        distance_w3 = distance_w4 + sqr(temp_l77_i72_w1);

        distance_w16 = distance_w3 + sqr(temp_l77_i73_w1);

        distance_w15 = distance_w16 + sqr(temp_l77_i74_w1);

        distance_w24 = distance_w15 + sqr(temp_l77_i75_w1);

        distance_w23 = distance_w24 + sqr(temp_l77_i76_w1);

        distance_w83 = distance_w23 + sqr(temp_l77_i77_w1);

        distance_w18 = distance_w83 + sqr(temp_l77_i78_w1);

        distance_w17 = distance_w18 + sqr(temp_l77_i79_w1);

        distance_w47 = distance_w17 + sqr(temp_l77_i80_w1);

        distance_w20 = distance_w47 + distance_w124;

        distance_array[i] = distance_w20 + sqr(temp_l77_i128_w1);
    }
}


void epilogue_0(char knownClasses[250], float distance_array[250], float BPD[3], char BPC[3])
{
    // Step 2: Initialize local variables
    char BestPointsClasses[3];
    float BestPointsDistances[3];
#pragma HLS ARRAY_PARTITION variable = BestPointsClasses complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BestPointsDistances complete dim = 1

    BestPointsDistances[0] = MAXDISTANCE;
    BestPointsDistances[2] = MAXDISTANCE;
    BestPointsDistances[1] = MAXDISTANCE;
    BestPointsClasses[1] = NUM_CLASSES;
    BestPointsClasses[0] = NUM_CLASSES;
    BestPointsClasses[2] = NUM_CLASSES;
    float max;
    int index;
    float distance;
    float dbest;
    float max_tmp;
    char cbest;
    for (int pi0 = 0; pi0 < 250; pi0++)
    {
#pragma HLS PIPELINE
        max = 0;
        index = 0;
        distance = distance_array[pi0];

        //find the worst point in the BestPoints
        for (int i = 0; i < 3; i++)
        {
            dbest = BestPointsDistances[i];
            max_tmp = max;
            max = (dbest > max_tmp) ? dbest : max;
            index = (dbest > max_tmp) ? i : index;
        }
        // if the point is better (shorter distance) than the worst one (longer distance) in the BestPoints
        // update BestPoints substituting the wrost one

        dbest = BestPointsDistances[index];
        cbest = BestPointsClasses[index];

        BestPointsDistances[index] = (distance < max) ? distance : dbest;
        BestPointsClasses[index] = (distance < max) ? knownClasses[pi0] : cbest;
    }
    BPD[0] = BestPointsDistances[0];
    BPD[1] = BestPointsDistances[1];
    BPD[2] = BestPointsDistances[2];
    BPC[0] = BestPointsClasses[0];
    BPC[1] = BestPointsClasses[1];
    BPC[2] = BestPointsClasses[2];
}

void epilogue(
    float BPD0[3], char BPC0[3], float BPD1[3], char BPC1[3],
    float BPD2[3], char BPC2[3], float BPD3[3], char BPC3[3],
    char *out)
{
    int bestDistances[12];
    int bestClasses[12];
#pragma HLS ARRAY_PARTITION variable = bestDistances complete dim = 1
#pragma HLS ARRAY_PARTITION variable = bestClasses complete dim = 1
    float d1 = MAXDISTANCE, d2 = MAXDISTANCE, d3 = MAXDISTANCE;
    char c1, c2, c3;
    for (int i = 0; i < 3; i++)
    {
        bestDistances[i] = BPD0[i];
        bestClasses[i] = BPC0[i];
        bestDistances[i + 3] = BPD1[i];
        bestClasses[i + 3] = BPC1[i];
        bestDistances[i + 6] = BPD2[i];
        bestClasses[i + 6] = BPC2[i];
        bestDistances[i + 9] = BPD3[i];
        bestClasses[i + 9] = BPC3[i];
    }

    for (int j = 0; j < 12; j++)
    {
        if (bestDistances[j] < d1)
        {
            d3 = d2;
            c3 = c2;
            d2 = d1;
            c2 = c1;
            d1 = bestDistances[j];
            c1 = bestClasses[j];
        }
        else if (bestDistances[j] < d2)
        {
            d3 = d2;
            c3 = c2;
            d2 = bestDistances[j];
            c2 = bestClasses[j];
        }
        else if (bestDistances[j] < d3)
        {
            d3 = bestDistances[j];
            c3 = bestClasses[j];
        }
    }

    char classID = c1;

    classID = (c2 == c3) ? c2 : classID;

    *out = classID;
}


void knnFloatNoSqrt1000p128f_4parallel(
    float xFeatures[128], char knownClasses0[250], char knownClasses1[250], char knownClasses2[250], char knownClasses3[250],
    float knownFeatures_0[250][128], float knownFeatures_1[250][128], float knownFeatures_2[250][128], float knownFeatures_3[250][128], char *out)
{
    // Step 2: Initialize local variables
    float distance_array_0[250];
    float distance_array_1[250];
    float distance_array_2[250];
    float distance_array_3[250];
    float BPD0[3];
    float BPD1[3];
    float BPD2[3];
    float BPD3[3];
    char BPC0[3];
    char BPC1[3];
    char BPC2[3];
    char BPC3[3];
#pragma HLS ARRAY_PARTITION variable = BPD0 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPD1 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPD2 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPD3 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPC0 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPC1 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPC2 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = BPC3 complete dim = 1
#pragma HLS ARRAY_PARTITION variable = xFeatures cyclic factor = 128 dim = 1

// Initialization done
#pragma HLS dataflow

    parallel_0(knownFeatures_0, xFeatures, distance_array_0);
    parallel_0(knownFeatures_1, xFeatures, distance_array_1);
    parallel_0(knownFeatures_2, xFeatures, distance_array_2);
    parallel_0(knownFeatures_3, xFeatures, distance_array_3);

    
    epilogue_0(knownClasses0, distance_array_0, BPD0, BPC0);
    epilogue_0(knownClasses1, distance_array_1, BPD1, BPC1);
    epilogue_0(knownClasses2, distance_array_2, BPD2, BPC2);
    epilogue_0(knownClasses3, distance_array_3, BPD3, BPC3);
    epilogue(BPD0, BPC0, BPD1, BPC1, BPD2, BPC2, BPD3, BPC3, out);
}
