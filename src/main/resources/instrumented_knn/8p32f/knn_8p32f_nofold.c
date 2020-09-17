#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <float.h>

#define NUM_CLASSES 2
#define MAXDISTANCE DBL_MAX
#define sqr(x) ((x)*(x))

void knn_8p32f_nofold(double xFeatures[32], double knownFeatures[8][32], char knownClasses[8], char *out) {
// Step 2: Initialize local variables
char BestPointsClasses[3];
double BestPointsDistances[3];
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
double d1_w1;
double d2_w1;
double d3_w1;
float dbest_w1;
float dbest_w10;
double dbest_w11;
double dbest_w12;
double dbest_w13;
float dbest_w14;
float dbest_w15;
double dbest_w16;
float dbest_w17;
float dbest_w18;
float dbest_w19;
double dbest_w2;
float dbest_w20;
float dbest_w21;
float dbest_w22;
float dbest_w23;
double dbest_w24;
double dbest_w25;
float dbest_w26;
float dbest_w27;
float dbest_w28;
float dbest_w29;
float dbest_w3;
float dbest_w30;
float dbest_w31;
double dbest_w32;
float dbest_w4;
float dbest_w5;
float dbest_w6;
float dbest_w7;
float dbest_w8;
float dbest_w9;
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
float distance_w128;
float distance_w129;
float distance_w13;
float distance_w130;
float distance_w131;
float distance_w132;
float distance_w133;
float distance_w134;
float distance_w135;
float distance_w136;
float distance_w137;
float distance_w138;
float distance_w139;
float distance_w14;
float distance_w140;
float distance_w141;
float distance_w142;
float distance_w143;
float distance_w144;
float distance_w145;
float distance_w146;
float distance_w147;
float distance_w148;
float distance_w149;
float distance_w15;
float distance_w150;
float distance_w151;
float distance_w152;
float distance_w153;
float distance_w154;
float distance_w155;
float distance_w156;
float distance_w157;
double distance_w158;
float distance_w159;
float distance_w16;
double distance_w160;
float distance_w161;
float distance_w162;
float distance_w163;
float distance_w164;
float distance_w165;
float distance_w166;
float distance_w167;
float distance_w168;
float distance_w169;
float distance_w17;
float distance_w170;
float distance_w171;
float distance_w172;
float distance_w173;
float distance_w174;
float distance_w175;
float distance_w176;
float distance_w177;
float distance_w178;
float distance_w179;
float distance_w18;
float distance_w180;
float distance_w181;
float distance_w182;
float distance_w183;
float distance_w184;
float distance_w185;
float distance_w186;
float distance_w187;
float distance_w188;
float distance_w189;
float distance_w19;
float distance_w190;
float distance_w191;
float distance_w192;
float distance_w193;
float distance_w194;
double distance_w195;
float distance_w196;
float distance_w197;
float distance_w198;
float distance_w199;
float distance_w2;
double distance_w20;
float distance_w200;
float distance_w201;
float distance_w202;
float distance_w203;
float distance_w204;
float distance_w205;
float distance_w206;
float distance_w207;
float distance_w208;
float distance_w209;
float distance_w21;
float distance_w210;
float distance_w211;
float distance_w212;
float distance_w213;
float distance_w214;
float distance_w215;
float distance_w216;
float distance_w217;
float distance_w218;
float distance_w219;
float distance_w22;
float distance_w220;
float distance_w221;
float distance_w222;
float distance_w223;
float distance_w224;
float distance_w225;
float distance_w226;
double distance_w227;
float distance_w228;
float distance_w229;
float distance_w23;
float distance_w230;
float distance_w231;
float distance_w232;
float distance_w233;
float distance_w234;
float distance_w235;
float distance_w236;
float distance_w237;
float distance_w238;
float distance_w239;
float distance_w24;
float distance_w240;
float distance_w241;
float distance_w242;
float distance_w243;
float distance_w244;
float distance_w245;
float distance_w246;
float distance_w247;
float distance_w248;
float distance_w249;
float distance_w25;
float distance_w250;
float distance_w251;
double distance_w252;
float distance_w253;
float distance_w254;
float distance_w255;
float distance_w256;
float distance_w257;
float distance_w258;
float distance_w259;
float distance_w26;
float distance_w260;
float distance_w261;
float distance_w262;
float distance_w263;
float distance_w264;
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
double distance_w56;
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
double distance_w75;
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
double max_tmp_w1;
double max_tmp_w10;
double max_tmp_w11;
double max_tmp_w12;
double max_tmp_w13;
double max_tmp_w14;
double max_tmp_w15;
double max_tmp_w16;
double max_tmp_w17;
double max_tmp_w18;
double max_tmp_w19;
double max_tmp_w2;
double max_tmp_w20;
double max_tmp_w21;
double max_tmp_w22;
double max_tmp_w23;
double max_tmp_w24;
double max_tmp_w3;
double max_tmp_w4;
double max_tmp_w5;
double max_tmp_w6;
double max_tmp_w7;
double max_tmp_w8;
double max_tmp_w9;
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
double mindist_w1;
double mindist_w2;
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
float temp_l77_i129_w1;
float temp_l77_i12_w1;
float temp_l77_i130_w1;
float temp_l77_i131_w1;
float temp_l77_i132_w1;
float temp_l77_i133_w1;
float temp_l77_i134_w1;
float temp_l77_i135_w1;
float temp_l77_i136_w1;
float temp_l77_i137_w1;
float temp_l77_i138_w1;
float temp_l77_i139_w1;
float temp_l77_i13_w1;
float temp_l77_i140_w1;
float temp_l77_i141_w1;
float temp_l77_i142_w1;
float temp_l77_i143_w1;
float temp_l77_i144_w1;
float temp_l77_i145_w1;
float temp_l77_i146_w1;
float temp_l77_i147_w1;
float temp_l77_i148_w1;
float temp_l77_i149_w1;
float temp_l77_i14_w1;
float temp_l77_i150_w1;
float temp_l77_i151_w1;
float temp_l77_i152_w1;
float temp_l77_i153_w1;
float temp_l77_i154_w1;
float temp_l77_i155_w1;
float temp_l77_i156_w1;
float temp_l77_i157_w1;
float temp_l77_i158_w1;
float temp_l77_i159_w1;
float temp_l77_i15_w1;
float temp_l77_i160_w1;
float temp_l77_i161_w1;
float temp_l77_i162_w1;
float temp_l77_i163_w1;
float temp_l77_i164_w1;
float temp_l77_i165_w1;
float temp_l77_i166_w1;
float temp_l77_i167_w1;
float temp_l77_i168_w1;
float temp_l77_i169_w1;
float temp_l77_i16_w1;
float temp_l77_i170_w1;
float temp_l77_i171_w1;
float temp_l77_i172_w1;
float temp_l77_i173_w1;
float temp_l77_i174_w1;
float temp_l77_i175_w1;
float temp_l77_i176_w1;
float temp_l77_i177_w1;
float temp_l77_i178_w1;
float temp_l77_i179_w1;
float temp_l77_i17_w1;
float temp_l77_i180_w1;
float temp_l77_i181_w1;
float temp_l77_i182_w1;
float temp_l77_i183_w1;
float temp_l77_i184_w1;
float temp_l77_i185_w1;
float temp_l77_i186_w1;
float temp_l77_i187_w1;
float temp_l77_i188_w1;
float temp_l77_i189_w1;
float temp_l77_i18_w1;
float temp_l77_i190_w1;
float temp_l77_i191_w1;
float temp_l77_i192_w1;
float temp_l77_i193_w1;
float temp_l77_i194_w1;
float temp_l77_i195_w1;
float temp_l77_i196_w1;
float temp_l77_i197_w1;
float temp_l77_i198_w1;
float temp_l77_i199_w1;
float temp_l77_i19_w1;
float temp_l77_i1_w1;
float temp_l77_i200_w1;
float temp_l77_i201_w1;
float temp_l77_i202_w1;
float temp_l77_i203_w1;
float temp_l77_i204_w1;
float temp_l77_i205_w1;
float temp_l77_i206_w1;
float temp_l77_i207_w1;
float temp_l77_i208_w1;
float temp_l77_i209_w1;
float temp_l77_i20_w1;
float temp_l77_i210_w1;
float temp_l77_i211_w1;
float temp_l77_i212_w1;
float temp_l77_i213_w1;
float temp_l77_i214_w1;
float temp_l77_i215_w1;
float temp_l77_i216_w1;
float temp_l77_i217_w1;
float temp_l77_i218_w1;
float temp_l77_i219_w1;
float temp_l77_i21_w1;
float temp_l77_i220_w1;
float temp_l77_i221_w1;
float temp_l77_i222_w1;
float temp_l77_i223_w1;
float temp_l77_i224_w1;
float temp_l77_i225_w1;
float temp_l77_i226_w1;
float temp_l77_i227_w1;
float temp_l77_i228_w1;
float temp_l77_i229_w1;
float temp_l77_i22_w1;
float temp_l77_i230_w1;
float temp_l77_i231_w1;
float temp_l77_i232_w1;
float temp_l77_i233_w1;
float temp_l77_i234_w1;
float temp_l77_i235_w1;
float temp_l77_i236_w1;
float temp_l77_i237_w1;
float temp_l77_i238_w1;
float temp_l77_i239_w1;
float temp_l77_i23_w1;
float temp_l77_i240_w1;
float temp_l77_i241_w1;
float temp_l77_i242_w1;
float temp_l77_i243_w1;
float temp_l77_i244_w1;
float temp_l77_i245_w1;
float temp_l77_i246_w1;
float temp_l77_i247_w1;
float temp_l77_i248_w1;
float temp_l77_i249_w1;
float temp_l77_i24_w1;
float temp_l77_i250_w1;
float temp_l77_i251_w1;
float temp_l77_i252_w1;
float temp_l77_i253_w1;
float temp_l77_i254_w1;
float temp_l77_i255_w1;
float temp_l77_i256_w1;
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
temp_l77_i225_w1 = xFeatures[0] - knownFeatures[7][0];
temp_l77_i33_w1 = xFeatures[0] - knownFeatures[1][0];
temp_l77_i65_w1 = xFeatures[0] - knownFeatures[2][0];
temp_l77_i1_w1 = xFeatures[0] - knownFeatures[0][0];
temp_l77_i193_w1 = xFeatures[0] - knownFeatures[6][0];
temp_l77_i129_w1 = xFeatures[0] - knownFeatures[4][0];
temp_l77_i97_w1 = xFeatures[0] - knownFeatures[3][0];
temp_l77_i161_w1 = xFeatures[0] - knownFeatures[5][0];
temp_l77_i2_w1 = xFeatures[1] - knownFeatures[0][1];
temp_l77_i34_w1 = xFeatures[1] - knownFeatures[1][1];
temp_l77_i194_w1 = xFeatures[1] - knownFeatures[6][1];
temp_l77_i66_w1 = xFeatures[1] - knownFeatures[2][1];
temp_l77_i162_w1 = xFeatures[1] - knownFeatures[5][1];
temp_l77_i130_w1 = xFeatures[1] - knownFeatures[4][1];
temp_l77_i98_w1 = xFeatures[1] - knownFeatures[3][1];
temp_l77_i226_w1 = xFeatures[1] - knownFeatures[7][1];
temp_l77_i35_w1 = xFeatures[2] - knownFeatures[1][2];
temp_l77_i227_w1 = xFeatures[2] - knownFeatures[7][2];
temp_l77_i131_w1 = xFeatures[2] - knownFeatures[4][2];
temp_l77_i163_w1 = xFeatures[2] - knownFeatures[5][2];
temp_l77_i3_w1 = xFeatures[2] - knownFeatures[0][2];
temp_l77_i67_w1 = xFeatures[2] - knownFeatures[2][2];
temp_l77_i195_w1 = xFeatures[2] - knownFeatures[6][2];
temp_l77_i99_w1 = xFeatures[2] - knownFeatures[3][2];
temp_l77_i68_w1 = xFeatures[3] - knownFeatures[2][3];
temp_l77_i4_w1 = xFeatures[3] - knownFeatures[0][3];
temp_l77_i228_w1 = xFeatures[3] - knownFeatures[7][3];
temp_l77_i36_w1 = xFeatures[3] - knownFeatures[1][3];
temp_l77_i164_w1 = xFeatures[3] - knownFeatures[5][3];
temp_l77_i196_w1 = xFeatures[3] - knownFeatures[6][3];
temp_l77_i100_w1 = xFeatures[3] - knownFeatures[3][3];
temp_l77_i132_w1 = xFeatures[3] - knownFeatures[4][3];
temp_l77_i133_w1 = xFeatures[4] - knownFeatures[4][4];
temp_l77_i101_w1 = xFeatures[4] - knownFeatures[3][4];
temp_l77_i37_w1 = xFeatures[4] - knownFeatures[1][4];
temp_l77_i69_w1 = xFeatures[4] - knownFeatures[2][4];
temp_l77_i229_w1 = xFeatures[4] - knownFeatures[7][4];
temp_l77_i5_w1 = xFeatures[4] - knownFeatures[0][4];
temp_l77_i165_w1 = xFeatures[4] - knownFeatures[5][4];
temp_l77_i197_w1 = xFeatures[4] - knownFeatures[6][4];
temp_l77_i70_w1 = xFeatures[5] - knownFeatures[2][5];
temp_l77_i198_w1 = xFeatures[5] - knownFeatures[6][5];
temp_l77_i166_w1 = xFeatures[5] - knownFeatures[5][5];
temp_l77_i102_w1 = xFeatures[5] - knownFeatures[3][5];
temp_l77_i6_w1 = xFeatures[5] - knownFeatures[0][5];
temp_l77_i134_w1 = xFeatures[5] - knownFeatures[4][5];
temp_l77_i230_w1 = xFeatures[5] - knownFeatures[7][5];
temp_l77_i38_w1 = xFeatures[5] - knownFeatures[1][5];
temp_l77_i199_w1 = xFeatures[6] - knownFeatures[6][6];
temp_l77_i103_w1 = xFeatures[6] - knownFeatures[3][6];
temp_l77_i39_w1 = xFeatures[6] - knownFeatures[1][6];
temp_l77_i71_w1 = xFeatures[6] - knownFeatures[2][6];
temp_l77_i135_w1 = xFeatures[6] - knownFeatures[4][6];
temp_l77_i7_w1 = xFeatures[6] - knownFeatures[0][6];
temp_l77_i167_w1 = xFeatures[6] - knownFeatures[5][6];
temp_l77_i231_w1 = xFeatures[6] - knownFeatures[7][6];
temp_l77_i72_w1 = xFeatures[7] - knownFeatures[2][7];
temp_l77_i104_w1 = xFeatures[7] - knownFeatures[3][7];
temp_l77_i232_w1 = xFeatures[7] - knownFeatures[7][7];
temp_l77_i200_w1 = xFeatures[7] - knownFeatures[6][7];
temp_l77_i136_w1 = xFeatures[7] - knownFeatures[4][7];
temp_l77_i8_w1 = xFeatures[7] - knownFeatures[0][7];
temp_l77_i168_w1 = xFeatures[7] - knownFeatures[5][7];
temp_l77_i40_w1 = xFeatures[7] - knownFeatures[1][7];
temp_l77_i9_w1 = xFeatures[8] - knownFeatures[0][8];
temp_l77_i137_w1 = xFeatures[8] - knownFeatures[4][8];
temp_l77_i73_w1 = xFeatures[8] - knownFeatures[2][8];
temp_l77_i233_w1 = xFeatures[8] - knownFeatures[7][8];
temp_l77_i169_w1 = xFeatures[8] - knownFeatures[5][8];
temp_l77_i201_w1 = xFeatures[8] - knownFeatures[6][8];
temp_l77_i105_w1 = xFeatures[8] - knownFeatures[3][8];
temp_l77_i41_w1 = xFeatures[8] - knownFeatures[1][8];
temp_l77_i202_w1 = xFeatures[9] - knownFeatures[6][9];
temp_l77_i74_w1 = xFeatures[9] - knownFeatures[2][9];
temp_l77_i138_w1 = xFeatures[9] - knownFeatures[4][9];
temp_l77_i170_w1 = xFeatures[9] - knownFeatures[5][9];
temp_l77_i106_w1 = xFeatures[9] - knownFeatures[3][9];
temp_l77_i42_w1 = xFeatures[9] - knownFeatures[1][9];
temp_l77_i10_w1 = xFeatures[9] - knownFeatures[0][9];
temp_l77_i234_w1 = xFeatures[9] - knownFeatures[7][9];
temp_l77_i171_w1 = xFeatures[10] - knownFeatures[5][10];
temp_l77_i43_w1 = xFeatures[10] - knownFeatures[1][10];
temp_l77_i75_w1 = xFeatures[10] - knownFeatures[2][10];
temp_l77_i139_w1 = xFeatures[10] - knownFeatures[4][10];
temp_l77_i203_w1 = xFeatures[10] - knownFeatures[6][10];
temp_l77_i235_w1 = xFeatures[10] - knownFeatures[7][10];
temp_l77_i11_w1 = xFeatures[10] - knownFeatures[0][10];
temp_l77_i107_w1 = xFeatures[10] - knownFeatures[3][10];
temp_l77_i12_w1 = xFeatures[11] - knownFeatures[0][11];
temp_l77_i236_w1 = xFeatures[11] - knownFeatures[7][11];
temp_l77_i76_w1 = xFeatures[11] - knownFeatures[2][11];
temp_l77_i140_w1 = xFeatures[11] - knownFeatures[4][11];
temp_l77_i44_w1 = xFeatures[11] - knownFeatures[1][11];
temp_l77_i172_w1 = xFeatures[11] - knownFeatures[5][11];
temp_l77_i204_w1 = xFeatures[11] - knownFeatures[6][11];
temp_l77_i108_w1 = xFeatures[11] - knownFeatures[3][11];
temp_l77_i237_w1 = xFeatures[12] - knownFeatures[7][12];
temp_l77_i173_w1 = xFeatures[12] - knownFeatures[5][12];
temp_l77_i205_w1 = xFeatures[12] - knownFeatures[6][12];
temp_l77_i141_w1 = xFeatures[12] - knownFeatures[4][12];
temp_l77_i45_w1 = xFeatures[12] - knownFeatures[1][12];
temp_l77_i77_w1 = xFeatures[12] - knownFeatures[2][12];
temp_l77_i109_w1 = xFeatures[12] - knownFeatures[3][12];
temp_l77_i13_w1 = xFeatures[12] - knownFeatures[0][12];
temp_l77_i78_w1 = xFeatures[13] - knownFeatures[2][13];
temp_l77_i110_w1 = xFeatures[13] - knownFeatures[3][13];
temp_l77_i14_w1 = xFeatures[13] - knownFeatures[0][13];
temp_l77_i174_w1 = xFeatures[13] - knownFeatures[5][13];
temp_l77_i238_w1 = xFeatures[13] - knownFeatures[7][13];
temp_l77_i46_w1 = xFeatures[13] - knownFeatures[1][13];
temp_l77_i142_w1 = xFeatures[13] - knownFeatures[4][13];
temp_l77_i206_w1 = xFeatures[13] - knownFeatures[6][13];
temp_l77_i207_w1 = xFeatures[14] - knownFeatures[6][14];
temp_l77_i239_w1 = xFeatures[14] - knownFeatures[7][14];
temp_l77_i47_w1 = xFeatures[14] - knownFeatures[1][14];
temp_l77_i15_w1 = xFeatures[14] - knownFeatures[0][14];
temp_l77_i79_w1 = xFeatures[14] - knownFeatures[2][14];
temp_l77_i175_w1 = xFeatures[14] - knownFeatures[5][14];
temp_l77_i143_w1 = xFeatures[14] - knownFeatures[4][14];
temp_l77_i111_w1 = xFeatures[14] - knownFeatures[3][14];
temp_l77_i144_w1 = xFeatures[15] - knownFeatures[4][15];
temp_l77_i16_w1 = xFeatures[15] - knownFeatures[0][15];
temp_l77_i240_w1 = xFeatures[15] - knownFeatures[7][15];
temp_l77_i176_w1 = xFeatures[15] - knownFeatures[5][15];
temp_l77_i112_w1 = xFeatures[15] - knownFeatures[3][15];
temp_l77_i48_w1 = xFeatures[15] - knownFeatures[1][15];
temp_l77_i80_w1 = xFeatures[15] - knownFeatures[2][15];
temp_l77_i208_w1 = xFeatures[15] - knownFeatures[6][15];
temp_l77_i17_w1 = xFeatures[16] - knownFeatures[0][16];
temp_l77_i177_w1 = xFeatures[16] - knownFeatures[5][16];
temp_l77_i113_w1 = xFeatures[16] - knownFeatures[3][16];
temp_l77_i241_w1 = xFeatures[16] - knownFeatures[7][16];
temp_l77_i145_w1 = xFeatures[16] - knownFeatures[4][16];
temp_l77_i81_w1 = xFeatures[16] - knownFeatures[2][16];
temp_l77_i49_w1 = xFeatures[16] - knownFeatures[1][16];
temp_l77_i209_w1 = xFeatures[16] - knownFeatures[6][16];
temp_l77_i18_w1 = xFeatures[17] - knownFeatures[0][17];
temp_l77_i114_w1 = xFeatures[17] - knownFeatures[3][17];
temp_l77_i242_w1 = xFeatures[17] - knownFeatures[7][17];
temp_l77_i146_w1 = xFeatures[17] - knownFeatures[4][17];
temp_l77_i82_w1 = xFeatures[17] - knownFeatures[2][17];
temp_l77_i210_w1 = xFeatures[17] - knownFeatures[6][17];
temp_l77_i178_w1 = xFeatures[17] - knownFeatures[5][17];
temp_l77_i50_w1 = xFeatures[17] - knownFeatures[1][17];
temp_l77_i19_w1 = xFeatures[18] - knownFeatures[0][18];
temp_l77_i51_w1 = xFeatures[18] - knownFeatures[1][18];
temp_l77_i211_w1 = xFeatures[18] - knownFeatures[6][18];
temp_l77_i83_w1 = xFeatures[18] - knownFeatures[2][18];
temp_l77_i115_w1 = xFeatures[18] - knownFeatures[3][18];
temp_l77_i179_w1 = xFeatures[18] - knownFeatures[5][18];
temp_l77_i243_w1 = xFeatures[18] - knownFeatures[7][18];
temp_l77_i147_w1 = xFeatures[18] - knownFeatures[4][18];
temp_l77_i20_w1 = xFeatures[19] - knownFeatures[0][19];
temp_l77_i52_w1 = xFeatures[19] - knownFeatures[1][19];
temp_l77_i116_w1 = xFeatures[19] - knownFeatures[3][19];
temp_l77_i212_w1 = xFeatures[19] - knownFeatures[6][19];
temp_l77_i244_w1 = xFeatures[19] - knownFeatures[7][19];
temp_l77_i148_w1 = xFeatures[19] - knownFeatures[4][19];
temp_l77_i84_w1 = xFeatures[19] - knownFeatures[2][19];
temp_l77_i180_w1 = xFeatures[19] - knownFeatures[5][19];
temp_l77_i213_w1 = xFeatures[20] - knownFeatures[6][20];
temp_l77_i53_w1 = xFeatures[20] - knownFeatures[1][20];
temp_l77_i149_w1 = xFeatures[20] - knownFeatures[4][20];
temp_l77_i85_w1 = xFeatures[20] - knownFeatures[2][20];
temp_l77_i245_w1 = xFeatures[20] - knownFeatures[7][20];
temp_l77_i21_w1 = xFeatures[20] - knownFeatures[0][20];
temp_l77_i117_w1 = xFeatures[20] - knownFeatures[3][20];
temp_l77_i181_w1 = xFeatures[20] - knownFeatures[5][20];
temp_l77_i150_w1 = xFeatures[21] - knownFeatures[4][21];
temp_l77_i182_w1 = xFeatures[21] - knownFeatures[5][21];
temp_l77_i118_w1 = xFeatures[21] - knownFeatures[3][21];
temp_l77_i214_w1 = xFeatures[21] - knownFeatures[6][21];
temp_l77_i54_w1 = xFeatures[21] - knownFeatures[1][21];
temp_l77_i22_w1 = xFeatures[21] - knownFeatures[0][21];
temp_l77_i246_w1 = xFeatures[21] - knownFeatures[7][21];
temp_l77_i86_w1 = xFeatures[21] - knownFeatures[2][21];
temp_l77_i183_w1 = xFeatures[22] - knownFeatures[5][22];
temp_l77_i55_w1 = xFeatures[22] - knownFeatures[1][22];
temp_l77_i87_w1 = xFeatures[22] - knownFeatures[2][22];
temp_l77_i119_w1 = xFeatures[22] - knownFeatures[3][22];
temp_l77_i23_w1 = xFeatures[22] - knownFeatures[0][22];
temp_l77_i215_w1 = xFeatures[22] - knownFeatures[6][22];
temp_l77_i151_w1 = xFeatures[22] - knownFeatures[4][22];
temp_l77_i247_w1 = xFeatures[22] - knownFeatures[7][22];
temp_l77_i152_w1 = xFeatures[23] - knownFeatures[4][23];
temp_l77_i88_w1 = xFeatures[23] - knownFeatures[2][23];
temp_l77_i56_w1 = xFeatures[23] - knownFeatures[1][23];
temp_l77_i120_w1 = xFeatures[23] - knownFeatures[3][23];
temp_l77_i248_w1 = xFeatures[23] - knownFeatures[7][23];
temp_l77_i184_w1 = xFeatures[23] - knownFeatures[5][23];
temp_l77_i24_w1 = xFeatures[23] - knownFeatures[0][23];
temp_l77_i216_w1 = xFeatures[23] - knownFeatures[6][23];
temp_l77_i217_w1 = xFeatures[24] - knownFeatures[6][24];
temp_l77_i249_w1 = xFeatures[24] - knownFeatures[7][24];
temp_l77_i153_w1 = xFeatures[24] - knownFeatures[4][24];
temp_l77_i89_w1 = xFeatures[24] - knownFeatures[2][24];
temp_l77_i25_w1 = xFeatures[24] - knownFeatures[0][24];
temp_l77_i57_w1 = xFeatures[24] - knownFeatures[1][24];
temp_l77_i121_w1 = xFeatures[24] - knownFeatures[3][24];
temp_l77_i185_w1 = xFeatures[24] - knownFeatures[5][24];
temp_l77_i122_w1 = xFeatures[25] - knownFeatures[3][25];
temp_l77_i90_w1 = xFeatures[25] - knownFeatures[2][25];
temp_l77_i250_w1 = xFeatures[25] - knownFeatures[7][25];
temp_l77_i154_w1 = xFeatures[25] - knownFeatures[4][25];
temp_l77_i218_w1 = xFeatures[25] - knownFeatures[6][25];
temp_l77_i186_w1 = xFeatures[25] - knownFeatures[5][25];
temp_l77_i26_w1 = xFeatures[25] - knownFeatures[0][25];
temp_l77_i58_w1 = xFeatures[25] - knownFeatures[1][25];
temp_l77_i155_w1 = xFeatures[26] - knownFeatures[4][26];
temp_l77_i187_w1 = xFeatures[26] - knownFeatures[5][26];
temp_l77_i59_w1 = xFeatures[26] - knownFeatures[1][26];
temp_l77_i251_w1 = xFeatures[26] - knownFeatures[7][26];
temp_l77_i27_w1 = xFeatures[26] - knownFeatures[0][26];
temp_l77_i123_w1 = xFeatures[26] - knownFeatures[3][26];
temp_l77_i91_w1 = xFeatures[26] - knownFeatures[2][26];
temp_l77_i219_w1 = xFeatures[26] - knownFeatures[6][26];
temp_l77_i60_w1 = xFeatures[27] - knownFeatures[1][27];
temp_l77_i220_w1 = xFeatures[27] - knownFeatures[6][27];
temp_l77_i92_w1 = xFeatures[27] - knownFeatures[2][27];
temp_l77_i28_w1 = xFeatures[27] - knownFeatures[0][27];
temp_l77_i156_w1 = xFeatures[27] - knownFeatures[4][27];
temp_l77_i252_w1 = xFeatures[27] - knownFeatures[7][27];
temp_l77_i124_w1 = xFeatures[27] - knownFeatures[3][27];
temp_l77_i188_w1 = xFeatures[27] - knownFeatures[5][27];
temp_l77_i253_w1 = xFeatures[28] - knownFeatures[7][28];
temp_l77_i157_w1 = xFeatures[28] - knownFeatures[4][28];
temp_l77_i125_w1 = xFeatures[28] - knownFeatures[3][28];
temp_l77_i221_w1 = xFeatures[28] - knownFeatures[6][28];
temp_l77_i93_w1 = xFeatures[28] - knownFeatures[2][28];
temp_l77_i189_w1 = xFeatures[28] - knownFeatures[5][28];
temp_l77_i29_w1 = xFeatures[28] - knownFeatures[0][28];
temp_l77_i61_w1 = xFeatures[28] - knownFeatures[1][28];
temp_l77_i62_w1 = xFeatures[29] - knownFeatures[1][29];
temp_l77_i222_w1 = xFeatures[29] - knownFeatures[6][29];
temp_l77_i30_w1 = xFeatures[29] - knownFeatures[0][29];
temp_l77_i94_w1 = xFeatures[29] - knownFeatures[2][29];
temp_l77_i158_w1 = xFeatures[29] - knownFeatures[4][29];
temp_l77_i254_w1 = xFeatures[29] - knownFeatures[7][29];
temp_l77_i190_w1 = xFeatures[29] - knownFeatures[5][29];
temp_l77_i126_w1 = xFeatures[29] - knownFeatures[3][29];
temp_l77_i191_w1 = xFeatures[30] - knownFeatures[5][30];
temp_l77_i95_w1 = xFeatures[30] - knownFeatures[2][30];
temp_l77_i223_w1 = xFeatures[30] - knownFeatures[6][30];
temp_l77_i255_w1 = xFeatures[30] - knownFeatures[7][30];
temp_l77_i63_w1 = xFeatures[30] - knownFeatures[1][30];
temp_l77_i159_w1 = xFeatures[30] - knownFeatures[4][30];
temp_l77_i127_w1 = xFeatures[30] - knownFeatures[3][30];
temp_l77_i31_w1 = xFeatures[30] - knownFeatures[0][30];
temp_l77_i32_w1 = xFeatures[31] - knownFeatures[0][31];
temp_l77_i224_w1 = xFeatures[31] - knownFeatures[6][31];
temp_l77_i192_w1 = xFeatures[31] - knownFeatures[5][31];
temp_l77_i160_w1 = xFeatures[31] - knownFeatures[4][31];
temp_l77_i64_w1 = xFeatures[31] - knownFeatures[1][31];
temp_l77_i96_w1 = xFeatures[31] - knownFeatures[2][31];
temp_l77_i128_w1 = xFeatures[31] - knownFeatures[3][31];
temp_l77_i256_w1 = xFeatures[31] - knownFeatures[7][31];
max_tmp_w16 = 0;
max_tmp_w15 = 0;
max_tmp_w7 = 0;
max_tmp_w12 = 0;
max_tmp_w4 = 0;
max_tmp_w22 = 0;
max_tmp_w21 = 0;
max_tmp_w1 = 0;
BestPointsDistances[2] = MAXDISTANCE;
BestPointsDistances[1] = MAXDISTANCE;
BestPointsDistances[0] = MAXDISTANCE;
BestPointsClasses[2] = NUM_CLASSES;
BestPointsClasses[0] = NUM_CLASSES;
BestPointsClasses[1] = NUM_CLASSES;

dbest_w21 = BestPointsDistances[0];
dbest_w22 = BestPointsDistances[1];
dbest_w23 = BestPointsDistances[2];
distance_w76 = 0 + sqr(temp_l77_i97_w1);
distance_w243 = 0 + sqr(temp_l77_i65_w1);
distance_w170 = 0 + sqr(temp_l77_i33_w1);
distance_w98 = 0 + sqr(temp_l77_i1_w1);
distance_w147 = 0 + sqr(temp_l77_i129_w1);
distance_w60 = 0 + sqr(temp_l77_i161_w1);
distance_w72 = 0 + sqr(temp_l77_i225_w1);
distance_w162 = 0 + sqr(temp_l77_i193_w1);

distance_w55 = distance_w60 + sqr(temp_l77_i162_w1);
distance_w100 = distance_w98 + sqr(temp_l77_i2_w1);
distance_w171 = distance_w170 + sqr(temp_l77_i34_w1);
distance_w244 = distance_w243 + sqr(temp_l77_i66_w1);
distance_w161 = distance_w162 + sqr(temp_l77_i194_w1);
distance_w79 = distance_w76 + sqr(temp_l77_i98_w1);
distance_w167 = distance_w147 + sqr(temp_l77_i130_w1);
distance_w71 = distance_w72 + sqr(temp_l77_i226_w1);
operationOutput_w27 = dbest_w21 > max_tmp_w16;

distance_w155 = distance_w161 + sqr(temp_l77_i195_w1);
distance_w247 = distance_w244 + sqr(temp_l77_i67_w1);
distance_w47 = distance_w55 + sqr(temp_l77_i163_w1);
distance_w190 = distance_w167 + sqr(temp_l77_i131_w1);
distance_w70 = distance_w71 + sqr(temp_l77_i227_w1);
distance_w172 = distance_w171 + sqr(temp_l77_i35_w1);
distance_w82 = distance_w79 + sqr(temp_l77_i99_w1);
distance_w101 = distance_w100 + sqr(temp_l77_i3_w1);
index_w16 = operationOutput_w27 ? 0:0;
max_w16 = operationOutput_w27 ? dbest_w21:0;

distance_w39 = distance_w47 + sqr(temp_l77_i164_w1);
distance_w218 = distance_w190 + sqr(temp_l77_i132_w1);
distance_w173 = distance_w172 + sqr(temp_l77_i36_w1);
distance_w85 = distance_w82 + sqr(temp_l77_i100_w1);
distance_w103 = distance_w101 + sqr(temp_l77_i4_w1);
distance_w249 = distance_w247 + sqr(temp_l77_i68_w1);
distance_w151 = distance_w155 + sqr(temp_l77_i196_w1);
distance_w69 = distance_w70 + sqr(temp_l77_i228_w1);
max_tmp_w17 = max_w16;

distance_w251 = distance_w249 + sqr(temp_l77_i69_w1);
distance_w105 = distance_w103 + sqr(temp_l77_i5_w1);
distance_w175 = distance_w173 + sqr(temp_l77_i37_w1);
distance_w146 = distance_w151 + sqr(temp_l77_i197_w1);
distance_w88 = distance_w85 + sqr(temp_l77_i101_w1);
distance_w239 = distance_w218 + sqr(temp_l77_i133_w1);
distance_w31 = distance_w39 + sqr(temp_l77_i165_w1);
distance_w68 = distance_w69 + sqr(temp_l77_i229_w1);
operationOutput_w28 = dbest_w22 > max_tmp_w17;

distance_w91 = distance_w88 + sqr(temp_l77_i102_w1);
distance_w260 = distance_w239 + sqr(temp_l77_i134_w1);
distance_w108 = distance_w105 + sqr(temp_l77_i6_w1);
distance_w176 = distance_w175 + sqr(temp_l77_i38_w1);
distance_w253 = distance_w251 + sqr(temp_l77_i70_w1);
distance_w67 = distance_w68 + sqr(temp_l77_i230_w1);
distance_w24 = distance_w31 + sqr(temp_l77_i166_w1);
distance_w142 = distance_w146 + sqr(temp_l77_i198_w1);
max_w17 = operationOutput_w28 ? dbest_w22:max_w16;
index_w17 = operationOutput_w28 ? 1:index_w16;

distance_w36 = distance_w260 + sqr(temp_l77_i135_w1);
distance_w177 = distance_w176 + sqr(temp_l77_i39_w1);
distance_w94 = distance_w91 + sqr(temp_l77_i103_w1);
distance_w18 = distance_w24 + sqr(temp_l77_i167_w1);
distance_w255 = distance_w253 + sqr(temp_l77_i71_w1);
distance_w138 = distance_w142 + sqr(temp_l77_i199_w1);
distance_w109 = distance_w108 + sqr(temp_l77_i7_w1);
distance_w65 = distance_w67 + sqr(temp_l77_i231_w1);
max_tmp_w18 = max_w17;

distance_w256 = distance_w255 + sqr(temp_l77_i72_w1);
distance_w14 = distance_w18 + sqr(temp_l77_i168_w1);
distance_w97 = distance_w94 + sqr(temp_l77_i104_w1);
distance_w111 = distance_w109 + sqr(temp_l77_i8_w1);
distance_w73 = distance_w36 + sqr(temp_l77_i136_w1);
distance_w178 = distance_w177 + sqr(temp_l77_i40_w1);
distance_w64 = distance_w65 + sqr(temp_l77_i232_w1);
distance_w135 = distance_w138 + sqr(temp_l77_i200_w1);
operationOutput_w29 = dbest_w23 > max_tmp_w18;

distance_w180 = distance_w178 + sqr(temp_l77_i41_w1);
distance_w11 = distance_w14 + sqr(temp_l77_i169_w1);
distance_w113 = distance_w111 + sqr(temp_l77_i9_w1);
distance_w152 = distance_w73 + sqr(temp_l77_i137_w1);
distance_w63 = distance_w64 + sqr(temp_l77_i233_w1);
distance_w104 = distance_w97 + sqr(temp_l77_i105_w1);
distance_w259 = distance_w256 + sqr(temp_l77_i73_w1);
distance_w131 = distance_w135 + sqr(temp_l77_i201_w1);
max_w18 = operationOutput_w29 ? dbest_w23:max_w17;
index_w18 = operationOutput_w29 ? 2:index_w17;

distance_w62 = distance_w63 + sqr(temp_l77_i234_w1);
distance_w128 = distance_w131 + sqr(temp_l77_i202_w1);
distance_w235 = distance_w152 + sqr(temp_l77_i138_w1);
distance_w115 = distance_w113 + sqr(temp_l77_i10_w1);
distance_w182 = distance_w180 + sqr(temp_l77_i42_w1);
distance_w112 = distance_w104 + sqr(temp_l77_i106_w1);
distance_w261 = distance_w259 + sqr(temp_l77_i74_w1);
distance_w8 = distance_w11 + sqr(temp_l77_i170_w1);

cbest_w7 = BestPointsClasses[index_w18];
dbest_w24 = BestPointsDistances[index_w18];
distance_w183 = distance_w182 + sqr(temp_l77_i43_w1);
distance_w124 = distance_w128 + sqr(temp_l77_i203_w1);
distance_w116 = distance_w115 + sqr(temp_l77_i11_w1);
distance_w263 = distance_w261 + sqr(temp_l77_i75_w1);
distance_w61 = distance_w62 + sqr(temp_l77_i235_w1);
distance_w119 = distance_w112 + sqr(temp_l77_i107_w1);
distance_w5 = distance_w8 + sqr(temp_l77_i171_w1);
distance_w143 = distance_w235 + sqr(temp_l77_i139_w1);

distance_w59 = distance_w61 + sqr(temp_l77_i236_w1);
distance_w1 = distance_w263 + sqr(temp_l77_i76_w1);
distance_w121 = distance_w124 + sqr(temp_l77_i204_w1);
distance_w185 = distance_w183 + sqr(temp_l77_i44_w1);
distance_w210 = distance_w143 + sqr(temp_l77_i140_w1);
distance_w126 = distance_w119 + sqr(temp_l77_i108_w1);
distance_w2 = distance_w5 + sqr(temp_l77_i172_w1);
distance_w118 = distance_w116 + sqr(temp_l77_i12_w1);

distance_w133 = distance_w126 + sqr(temp_l77_i109_w1);
distance_w262 = distance_w2 + sqr(temp_l77_i173_w1);
distance_w165 = distance_w210 + sqr(temp_l77_i141_w1);
distance_w58 = distance_w59 + sqr(temp_l77_i237_w1);
distance_w3 = distance_w1 + sqr(temp_l77_i77_w1);
distance_w117 = distance_w121 + sqr(temp_l77_i205_w1);
distance_w120 = distance_w118 + sqr(temp_l77_i13_w1);
distance_w186 = distance_w185 + sqr(temp_l77_i45_w1);

distance_w122 = distance_w120 + sqr(temp_l77_i14_w1);
distance_w114 = distance_w117 + sqr(temp_l77_i206_w1);
distance_w257 = distance_w262 + sqr(temp_l77_i174_w1);
distance_w187 = distance_w186 + sqr(temp_l77_i46_w1);
distance_w49 = distance_w165 + sqr(temp_l77_i142_w1);
distance_w57 = distance_w58 + sqr(temp_l77_i238_w1);
distance_w4 = distance_w3 + sqr(temp_l77_i78_w1);
distance_w140 = distance_w133 + sqr(temp_l77_i110_w1);

distance_w6 = distance_w4 + sqr(temp_l77_i79_w1);
distance_w110 = distance_w114 + sqr(temp_l77_i207_w1);
distance_w54 = distance_w57 + sqr(temp_l77_i239_w1);
distance_w254 = distance_w257 + sqr(temp_l77_i175_w1);
distance_w123 = distance_w122 + sqr(temp_l77_i15_w1);
distance_w149 = distance_w140 + sqr(temp_l77_i111_w1);
distance_w189 = distance_w187 + sqr(temp_l77_i47_w1);
distance_w246 = distance_w49 + sqr(temp_l77_i143_w1);

distance_w250 = distance_w254 + sqr(temp_l77_i176_w1);
distance_w106 = distance_w110 + sqr(temp_l77_i208_w1);
distance_w193 = distance_w246 + sqr(temp_l77_i144_w1);
distance_w7 = distance_w6 + sqr(temp_l77_i80_w1);
distance_w159 = distance_w149 + sqr(temp_l77_i112_w1);
distance_w125 = distance_w123 + sqr(temp_l77_i16_w1);
distance_w191 = distance_w189 + sqr(temp_l77_i48_w1);
distance_w52 = distance_w54 + sqr(temp_l77_i240_w1);

distance_w9 = distance_w7 + sqr(temp_l77_i81_w1);
distance_w245 = distance_w250 + sqr(temp_l77_i177_w1);
distance_w102 = distance_w106 + sqr(temp_l77_i209_w1);
distance_w164 = distance_w159 + sqr(temp_l77_i113_w1);
distance_w127 = distance_w125 + sqr(temp_l77_i17_w1);
distance_w194 = distance_w191 + sqr(temp_l77_i49_w1);
distance_w51 = distance_w52 + sqr(temp_l77_i241_w1);
distance_w156 = distance_w193 + sqr(temp_l77_i145_w1);

distance_w166 = distance_w164 + sqr(temp_l77_i114_w1);
distance_w197 = distance_w194 + sqr(temp_l77_i50_w1);
distance_w129 = distance_w127 + sqr(temp_l77_i18_w1);
distance_w240 = distance_w245 + sqr(temp_l77_i178_w1);
distance_w107 = distance_w156 + sqr(temp_l77_i146_w1);
distance_w10 = distance_w9 + sqr(temp_l77_i82_w1);
distance_w48 = distance_w51 + sqr(temp_l77_i242_w1);
distance_w99 = distance_w102 + sqr(temp_l77_i210_w1);

distance_w74 = distance_w107 + sqr(temp_l77_i147_w1);
distance_w130 = distance_w129 + sqr(temp_l77_i19_w1);
distance_w12 = distance_w10 + sqr(temp_l77_i83_w1);
distance_w238 = distance_w240 + sqr(temp_l77_i179_w1);
distance_w96 = distance_w99 + sqr(temp_l77_i211_w1);
distance_w169 = distance_w166 + sqr(temp_l77_i115_w1);
distance_w199 = distance_w197 + sqr(temp_l77_i51_w1);
distance_w46 = distance_w48 + sqr(temp_l77_i243_w1);

distance_w132 = distance_w130 + sqr(temp_l77_i20_w1);
distance_w237 = distance_w238 + sqr(temp_l77_i180_w1);
distance_w95 = distance_w96 + sqr(temp_l77_i212_w1);
distance_w174 = distance_w169 + sqr(temp_l77_i116_w1);
distance_w66 = distance_w74 + sqr(temp_l77_i148_w1);
distance_w43 = distance_w46 + sqr(temp_l77_i244_w1);
distance_w200 = distance_w199 + sqr(temp_l77_i52_w1);
distance_w13 = distance_w12 + sqr(temp_l77_i84_w1);

distance_w93 = distance_w95 + sqr(temp_l77_i213_w1);
distance_w42 = distance_w43 + sqr(temp_l77_i245_w1);
distance_w16 = distance_w13 + sqr(temp_l77_i85_w1);
distance_w44 = distance_w66 + sqr(temp_l77_i149_w1);
distance_w234 = distance_w237 + sqr(temp_l77_i181_w1);
distance_w202 = distance_w200 + sqr(temp_l77_i53_w1);
distance_w134 = distance_w132 + sqr(temp_l77_i21_w1);
distance_w179 = distance_w174 + sqr(temp_l77_i117_w1);

distance_w15 = distance_w44 + sqr(temp_l77_i150_w1);
distance_w17 = distance_w16 + sqr(temp_l77_i86_w1);
distance_w92 = distance_w93 + sqr(temp_l77_i214_w1);
distance_w136 = distance_w134 + sqr(temp_l77_i22_w1);
distance_w232 = distance_w234 + sqr(temp_l77_i182_w1);
distance_w184 = distance_w179 + sqr(temp_l77_i118_w1);
distance_w204 = distance_w202 + sqr(temp_l77_i54_w1);
distance_w40 = distance_w42 + sqr(temp_l77_i246_w1);

distance_w137 = distance_w136 + sqr(temp_l77_i23_w1);
distance_w19 = distance_w17 + sqr(temp_l77_i87_w1);
distance_w90 = distance_w92 + sqr(temp_l77_i215_w1);
distance_w264 = distance_w15 + sqr(temp_l77_i151_w1);
distance_w38 = distance_w40 + sqr(temp_l77_i247_w1);
distance_w188 = distance_w184 + sqr(temp_l77_i119_w1);
distance_w207 = distance_w204 + sqr(temp_l77_i55_w1);
distance_w230 = distance_w232 + sqr(temp_l77_i183_w1);

distance_w89 = distance_w90 + sqr(temp_l77_i216_w1);
distance_w22 = distance_w19 + sqr(temp_l77_i88_w1);
distance_w258 = distance_w264 + sqr(temp_l77_i152_w1);
distance_w229 = distance_w230 + sqr(temp_l77_i184_w1);
distance_w139 = distance_w137 + sqr(temp_l77_i24_w1);
distance_w196 = distance_w188 + sqr(temp_l77_i120_w1);
distance_w208 = distance_w207 + sqr(temp_l77_i56_w1);
distance_w35 = distance_w38 + sqr(temp_l77_i248_w1);

distance_w141 = distance_w139 + sqr(temp_l77_i25_w1);
distance_w34 = distance_w35 + sqr(temp_l77_i249_w1);
distance_w211 = distance_w208 + sqr(temp_l77_i57_w1);
distance_w242 = distance_w258 + sqr(temp_l77_i153_w1);
distance_w226 = distance_w229 + sqr(temp_l77_i185_w1);
distance_w203 = distance_w196 + sqr(temp_l77_i121_w1);
distance_w87 = distance_w89 + sqr(temp_l77_i217_w1);
distance_w26 = distance_w22 + sqr(temp_l77_i89_w1);

distance_w212 = distance_w203 + sqr(temp_l77_i122_w1);
distance_w222 = distance_w226 + sqr(temp_l77_i186_w1);
distance_w32 = distance_w34 + sqr(temp_l77_i250_w1);
distance_w213 = distance_w211 + sqr(temp_l77_i58_w1);
distance_w86 = distance_w87 + sqr(temp_l77_i218_w1);
distance_w29 = distance_w26 + sqr(temp_l77_i90_w1);
distance_w144 = distance_w141 + sqr(temp_l77_i26_w1);
distance_w233 = distance_w242 + sqr(temp_l77_i154_w1);

distance_w84 = distance_w86 + sqr(temp_l77_i219_w1);
distance_w215 = distance_w213 + sqr(temp_l77_i59_w1);
distance_w33 = distance_w29 + sqr(temp_l77_i91_w1);
distance_w220 = distance_w212 + sqr(temp_l77_i123_w1);
distance_w217 = distance_w222 + sqr(temp_l77_i187_w1);
distance_w30 = distance_w32 + sqr(temp_l77_i251_w1);
distance_w145 = distance_w144 + sqr(temp_l77_i27_w1);
distance_w223 = distance_w233 + sqr(temp_l77_i155_w1);

distance_w37 = distance_w33 + sqr(temp_l77_i92_w1);
distance_w214 = distance_w217 + sqr(temp_l77_i188_w1);
distance_w28 = distance_w30 + sqr(temp_l77_i252_w1);
distance_w206 = distance_w223 + sqr(temp_l77_i156_w1);
distance_w148 = distance_w145 + sqr(temp_l77_i28_w1);
distance_w228 = distance_w220 + sqr(temp_l77_i124_w1);
distance_w216 = distance_w215 + sqr(temp_l77_i60_w1);
distance_w83 = distance_w84 + sqr(temp_l77_i220_w1);

distance_w192 = distance_w206 + sqr(temp_l77_i157_w1);
distance_w209 = distance_w214 + sqr(temp_l77_i189_w1);
distance_w81 = distance_w83 + sqr(temp_l77_i221_w1);
distance_w41 = distance_w37 + sqr(temp_l77_i93_w1);
distance_w219 = distance_w216 + sqr(temp_l77_i61_w1);
distance_w27 = distance_w28 + sqr(temp_l77_i253_w1);
distance_w231 = distance_w228 + sqr(temp_l77_i125_w1);
distance_w150 = distance_w148 + sqr(temp_l77_i29_w1);

distance_w80 = distance_w81 + sqr(temp_l77_i222_w1);
distance_w153 = distance_w150 + sqr(temp_l77_i30_w1);
distance_w45 = distance_w41 + sqr(temp_l77_i94_w1);
distance_w181 = distance_w192 + sqr(temp_l77_i158_w1);
distance_w205 = distance_w209 + sqr(temp_l77_i190_w1);
distance_w236 = distance_w231 + sqr(temp_l77_i126_w1);
distance_w221 = distance_w219 + sqr(temp_l77_i62_w1);
distance_w25 = distance_w27 + sqr(temp_l77_i254_w1);

distance_w224 = distance_w221 + sqr(temp_l77_i63_w1);
distance_w168 = distance_w181 + sqr(temp_l77_i159_w1);
distance_w23 = distance_w25 + sqr(temp_l77_i255_w1);
distance_w241 = distance_w236 + sqr(temp_l77_i127_w1);
distance_w78 = distance_w80 + sqr(temp_l77_i223_w1);
distance_w50 = distance_w45 + sqr(temp_l77_i95_w1);
distance_w201 = distance_w205 + sqr(temp_l77_i191_w1);
distance_w154 = distance_w153 + sqr(temp_l77_i31_w1);

distance_w248 = distance_w241 + sqr(temp_l77_i128_w1);
distance_w157 = distance_w154 + sqr(temp_l77_i32_w1);
distance_w53 = distance_w50 + sqr(temp_l77_i96_w1);
distance_w21 = distance_w23 + sqr(temp_l77_i256_w1);
distance_w225 = distance_w224 + sqr(temp_l77_i64_w1);
distance_w163 = distance_w168 + sqr(temp_l77_i160_w1);
distance_w77 = distance_w78 + sqr(temp_l77_i224_w1);
distance_w198 = distance_w201 + sqr(temp_l77_i192_w1);

distance_w20 = sqrtf(distance_w21);
distance_w56 = sqrtf(distance_w53);
distance_w75 = sqrtf(distance_w77);
distance_w158 = sqrtf(distance_w157);
distance_w160 = sqrtf(distance_w163);
distance_w195 = sqrtf(distance_w198);
distance_w227 = sqrtf(distance_w225);
distance_w252 = sqrtf(distance_w248);

operationOutput_w31 = distance_w158 < max_w18;

muxOutput_w13 = operationOutput_w31 ? distance_w158:dbest_w24;
muxOutput_w14 = operationOutput_w31 ? knownClasses[0]:cbest_w7;

BestPointsDistances[index_w18] = muxOutput_w13;
BestPointsClasses[index_w18] = muxOutput_w14;


dbest_w29 = BestPointsDistances[0];
dbest_w30 = BestPointsDistances[1];
dbest_w31 = BestPointsDistances[2];

operationOutput_w35 = dbest_w29 > max_tmp_w22;

max_w22 = operationOutput_w35 ? dbest_w29:0;
index_w22 = operationOutput_w35 ? 0:0;

max_tmp_w23 = max_w22;

operationOutput_w36 = dbest_w30 > max_tmp_w23;

index_w23 = operationOutput_w36 ? 1:index_w22;
max_w23 = operationOutput_w36 ? dbest_w30:max_w22;

max_tmp_w24 = max_w23;

operationOutput_w37 = dbest_w31 > max_tmp_w24;

max_w24 = operationOutput_w37 ? dbest_w31:max_w23;
index_w24 = operationOutput_w37 ? 2:index_w23;

operationOutput_w38 = distance_w227 < max_w24;

cbest_w8 = BestPointsClasses[index_w24];
dbest_w32 = BestPointsDistances[index_w24];

muxOutput_w16 = operationOutput_w38 ? knownClasses[1]:cbest_w8;
muxOutput_w15 = operationOutput_w38 ? distance_w227:dbest_w32;

BestPointsDistances[index_w24] = muxOutput_w15;
BestPointsClasses[index_w24] = muxOutput_w16;


dbest_w8 = BestPointsDistances[0];
dbest_w9 = BestPointsDistances[1];
dbest_w10 = BestPointsDistances[2];

operationOutput_w13 = dbest_w8 > max_tmp_w7;

max_w7 = operationOutput_w13 ? dbest_w8:0;
index_w6 = operationOutput_w13 ? 0:0;

max_tmp_w8 = max_w7;

operationOutput_w15 = dbest_w9 > max_tmp_w8;

index_w7 = operationOutput_w15 ? 1:index_w6;
max_w8 = operationOutput_w15 ? dbest_w9:max_w7;

max_tmp_w9 = max_w8;

operationOutput_w17 = dbest_w10 > max_tmp_w9;

max_w9 = operationOutput_w17 ? dbest_w10:max_w8;
index_w9 = operationOutput_w17 ? 2:index_w7;

operationOutput_w21 = distance_w56 < max_w9;

cbest_w4 = BestPointsClasses[index_w9];
dbest_w13 = BestPointsDistances[index_w9];

muxOutput_w8 = operationOutput_w21 ? knownClasses[2]:cbest_w4;
muxOutput_w7 = operationOutput_w21 ? distance_w56:dbest_w13;

BestPointsDistances[index_w9] = muxOutput_w7;
BestPointsClasses[index_w9] = muxOutput_w8;


dbest_w1 = BestPointsDistances[0];
dbest_w6 = BestPointsDistances[1];
dbest_w7 = BestPointsDistances[2];

operationOutput_w7 = dbest_w1 > max_tmp_w1;

max_w1 = operationOutput_w7 ? dbest_w1:0;
index_w1 = operationOutput_w7 ? 0:0;

max_tmp_w5 = max_w1;

operationOutput_w12 = dbest_w6 > max_tmp_w5;

index_w5 = operationOutput_w12 ? 1:index_w1;
max_w5 = operationOutput_w12 ? dbest_w6:max_w1;

max_tmp_w6 = max_w5;

operationOutput_w14 = dbest_w7 > max_tmp_w6;

index_w8 = operationOutput_w14 ? 2:index_w5;
max_w6 = operationOutput_w14 ? dbest_w7:max_w5;

operationOutput_w24 = distance_w252 < max_w6;

cbest_w5 = BestPointsClasses[index_w8];
dbest_w16 = BestPointsDistances[index_w8];

muxOutput_w10 = operationOutput_w24 ? knownClasses[3]:cbest_w5;
muxOutput_w9 = operationOutput_w24 ? distance_w252:dbest_w16;

BestPointsClasses[index_w8] = muxOutput_w10;
BestPointsDistances[index_w8] = muxOutput_w9;


dbest_w20 = BestPointsDistances[0];
dbest_w19 = BestPointsDistances[1];
dbest_w18 = BestPointsDistances[2];

operationOutput_w26 = dbest_w20 > max_tmp_w15;

index_w15 = operationOutput_w26 ? 0:0;
max_w15 = operationOutput_w26 ? dbest_w20:0;

max_tmp_w14 = max_w15;

operationOutput_w25 = dbest_w19 > max_tmp_w14;

index_w14 = operationOutput_w25 ? 1:index_w15;
max_w14 = operationOutput_w25 ? dbest_w19:max_w15;

max_tmp_w13 = max_w14;

operationOutput_w23 = dbest_w18 > max_tmp_w13;

max_w13 = operationOutput_w23 ? dbest_w18:max_w14;
index_w13 = operationOutput_w23 ? 2:index_w14;

operationOutput_w16 = distance_w160 < max_w13;

cbest_w2 = BestPointsClasses[index_w13];
dbest_w12 = BestPointsDistances[index_w13];

muxOutput_w4 = operationOutput_w16 ? distance_w160:dbest_w12;
muxOutput_w3 = operationOutput_w16 ? knownClasses[4]:cbest_w2;

BestPointsClasses[index_w13] = muxOutput_w3;
BestPointsDistances[index_w13] = muxOutput_w4;


dbest_w28 = BestPointsDistances[0];
dbest_w27 = BestPointsDistances[1];
dbest_w26 = BestPointsDistances[2];

operationOutput_w34 = dbest_w28 > max_tmp_w21;

max_w21 = operationOutput_w34 ? dbest_w28:0;
index_w21 = operationOutput_w34 ? 0:0;

max_tmp_w20 = max_w21;

operationOutput_w33 = dbest_w27 > max_tmp_w20;

max_w20 = operationOutput_w33 ? dbest_w27:max_w21;
index_w20 = operationOutput_w33 ? 1:index_w21;

max_tmp_w19 = max_w20;

operationOutput_w32 = dbest_w26 > max_tmp_w19;

max_w19 = operationOutput_w32 ? dbest_w26:max_w20;
index_w19 = operationOutput_w32 ? 2:index_w20;

operationOutput_w30 = distance_w195 < max_w19;

cbest_w6 = BestPointsClasses[index_w19];
dbest_w25 = BestPointsDistances[index_w19];

muxOutput_w11 = operationOutput_w30 ? knownClasses[5]:cbest_w6;
muxOutput_w12 = operationOutput_w30 ? distance_w195:dbest_w25;

BestPointsDistances[index_w19] = muxOutput_w12;
BestPointsClasses[index_w19] = muxOutput_w11;


dbest_w17 = BestPointsDistances[0];
dbest_w15 = BestPointsDistances[1];
dbest_w14 = BestPointsDistances[2];

operationOutput_w22 = dbest_w17 > max_tmp_w12;

max_w12 = operationOutput_w22 ? dbest_w17:0;
index_w12 = operationOutput_w22 ? 0:0;

max_tmp_w11 = max_w12;

operationOutput_w20 = dbest_w15 > max_tmp_w11;

index_w11 = operationOutput_w20 ? 1:index_w12;
max_w11 = operationOutput_w20 ? dbest_w15:max_w12;

max_tmp_w10 = max_w11;

operationOutput_w19 = dbest_w14 > max_tmp_w10;

max_w10 = operationOutput_w19 ? dbest_w14:max_w11;
index_w10 = operationOutput_w19 ? 2:index_w11;

operationOutput_w18 = distance_w75 < max_w10;

cbest_w3 = BestPointsClasses[index_w10];
dbest_w11 = BestPointsDistances[index_w10];

muxOutput_w5 = operationOutput_w18 ? knownClasses[6]:cbest_w3;
muxOutput_w6 = operationOutput_w18 ? distance_w75:dbest_w11;

BestPointsDistances[index_w10] = muxOutput_w6;
BestPointsClasses[index_w10] = muxOutput_w5;


dbest_w5 = BestPointsDistances[0];
dbest_w4 = BestPointsDistances[1];
dbest_w3 = BestPointsDistances[2];

operationOutput_w11 = dbest_w5 > max_tmp_w4;

max_w4 = operationOutput_w11 ? dbest_w5:0;
index_w4 = operationOutput_w11 ? 0:0;

max_tmp_w3 = max_w4;

operationOutput_w10 = dbest_w4 > max_tmp_w3;

index_w3 = operationOutput_w10 ? 1:index_w4;
max_w3 = operationOutput_w10 ? dbest_w4:max_w4;

max_tmp_w2 = max_w3;

operationOutput_w9 = dbest_w3 > max_tmp_w2;

index_w2 = operationOutput_w9 ? 2:index_w3;
max_w2 = operationOutput_w9 ? dbest_w3:max_w3;

operationOutput_w8 = distance_w20 < max_w2;

cbest_w1 = BestPointsClasses[index_w2];
dbest_w2 = BestPointsDistances[index_w2];

muxOutput_w2 = operationOutput_w8 ? distance_w20:dbest_w2;
muxOutput_w1 = operationOutput_w8 ? knownClasses[7]:cbest_w1;

BestPointsClasses[index_w2] = muxOutput_w1;
BestPointsDistances[index_w2] = muxOutput_w2;


c1_w1 = BestPointsClasses[0];
c2_w1 = BestPointsClasses[1];
c3_w1 = BestPointsClasses[2];
d1_w1 = BestPointsDistances[0];
d2_w1 = BestPointsDistances[1];
d3_w1 = BestPointsDistances[2];

operationOutput_w2 = c1_w1 == c3_w1;
operationOutput_w1 = c1_w1 == c2_w1;
operationOutput_w3 = c2_w1 == c3_w1;
mindist_w2 = d1_w1;

operationOutput_w6 = mindist_w2 > d2_w1;
operationOutput_w5 = mindist_w2 > d2_w1;

mindist_w1 = operationOutput_w5 ? d2_w1:d1_w1;
classID_w4 = operationOutput_w6 ? c2_w1:c1_w1;

operationOutput_w4 = mindist_w1 > d3_w1;

classID_w3 = operationOutput_w4 ? c3_w1:classID_w4;

classID_w2 = operationOutput_w3 ? c2_w1:classID_w3;

classID_w1 = operationOutput_w2 ? c1_w1:classID_w2;

*out = operationOutput_w1 ? c1_w1:classID_w1;

}
