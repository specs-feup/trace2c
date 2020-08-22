#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <float.h>

#define NUM_CLASSES 2
#define MAXDISTANCE DBL_MAX
#define sqr(x) ((x)*(x))

void parallel_0(double knownFeatures[8][512], double xFeatures[512], double distance_array[8]) {
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
float distance_w158;
float distance_w159;
float distance_w16;
float distance_w160;
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
float distance_w195;
float distance_w196;
float distance_w197;
float distance_w198;
float distance_w199;
float distance_w2;
float distance_w20;
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
float distance_w227;
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
float distance_w252;
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
float distance_w265;
float distance_w266;
float distance_w267;
float distance_w268;
float distance_w269;
float distance_w27;
float distance_w270;
float distance_w271;
float distance_w272;
float distance_w273;
float distance_w274;
float distance_w275;
float distance_w276;
float distance_w277;
float distance_w278;
float distance_w279;
float distance_w28;
float distance_w280;
float distance_w281;
float distance_w282;
float distance_w283;
float distance_w284;
float distance_w285;
float distance_w286;
float distance_w287;
float distance_w288;
float distance_w289;
float distance_w29;
float distance_w290;
float distance_w291;
float distance_w292;
float distance_w293;
float distance_w294;
float distance_w295;
float distance_w296;
float distance_w297;
float distance_w298;
float distance_w299;
float distance_w3;
float distance_w30;
float distance_w300;
float distance_w301;
float distance_w302;
float distance_w303;
float distance_w304;
float distance_w305;
float distance_w306;
float distance_w307;
float distance_w308;
float distance_w309;
float distance_w31;
float distance_w310;
float distance_w311;
float distance_w312;
float distance_w313;
float distance_w314;
float distance_w315;
float distance_w316;
float distance_w317;
float distance_w318;
float distance_w319;
float distance_w32;
float distance_w320;
float distance_w321;
float distance_w322;
float distance_w323;
float distance_w324;
float distance_w325;
float distance_w326;
float distance_w327;
float distance_w328;
float distance_w329;
float distance_w33;
float distance_w330;
float distance_w331;
float distance_w332;
float distance_w333;
float distance_w334;
float distance_w335;
float distance_w336;
float distance_w337;
float distance_w338;
float distance_w339;
float distance_w34;
float distance_w340;
float distance_w341;
float distance_w342;
float distance_w343;
float distance_w344;
float distance_w345;
float distance_w346;
float distance_w347;
float distance_w348;
float distance_w349;
float distance_w35;
float distance_w350;
float distance_w351;
float distance_w352;
float distance_w353;
float distance_w354;
float distance_w355;
float distance_w356;
float distance_w357;
float distance_w358;
float distance_w359;
float distance_w36;
float distance_w360;
float distance_w361;
float distance_w362;
float distance_w363;
float distance_w364;
float distance_w365;
float distance_w366;
float distance_w367;
float distance_w368;
float distance_w369;
float distance_w37;
float distance_w370;
float distance_w371;
float distance_w372;
float distance_w373;
float distance_w374;
float distance_w375;
float distance_w376;
float distance_w377;
float distance_w378;
float distance_w379;
float distance_w38;
float distance_w380;
float distance_w381;
float distance_w382;
float distance_w383;
float distance_w384;
float distance_w385;
float distance_w386;
float distance_w387;
float distance_w388;
float distance_w389;
float distance_w39;
float distance_w390;
float distance_w391;
float distance_w392;
float distance_w393;
float distance_w394;
float distance_w395;
float distance_w396;
float distance_w397;
float distance_w398;
float distance_w399;
float distance_w4;
float distance_w40;
float distance_w400;
float distance_w401;
float distance_w402;
float distance_w403;
float distance_w404;
float distance_w405;
float distance_w406;
float distance_w407;
float distance_w408;
float distance_w409;
float distance_w41;
float distance_w410;
float distance_w411;
float distance_w412;
float distance_w413;
float distance_w414;
float distance_w415;
float distance_w416;
float distance_w417;
float distance_w418;
float distance_w419;
float distance_w42;
float distance_w420;
float distance_w421;
float distance_w422;
float distance_w423;
float distance_w424;
float distance_w425;
float distance_w426;
float distance_w427;
float distance_w428;
float distance_w429;
float distance_w43;
float distance_w430;
float distance_w431;
float distance_w432;
float distance_w433;
float distance_w434;
float distance_w435;
float distance_w436;
float distance_w437;
float distance_w438;
float distance_w439;
float distance_w44;
float distance_w440;
float distance_w441;
float distance_w442;
float distance_w443;
float distance_w444;
float distance_w445;
float distance_w446;
float distance_w447;
float distance_w448;
float distance_w449;
float distance_w45;
float distance_w450;
float distance_w451;
float distance_w452;
float distance_w453;
float distance_w454;
float distance_w455;
float distance_w456;
float distance_w457;
float distance_w458;
float distance_w459;
float distance_w46;
float distance_w460;
float distance_w461;
float distance_w462;
float distance_w463;
float distance_w464;
float distance_w465;
float distance_w466;
float distance_w467;
float distance_w468;
float distance_w469;
float distance_w47;
float distance_w470;
float distance_w471;
float distance_w472;
float distance_w473;
float distance_w474;
float distance_w475;
float distance_w476;
float distance_w477;
float distance_w478;
float distance_w479;
float distance_w48;
float distance_w480;
float distance_w481;
float distance_w482;
float distance_w483;
float distance_w484;
float distance_w485;
float distance_w486;
float distance_w487;
float distance_w488;
float distance_w489;
float distance_w49;
float distance_w490;
float distance_w491;
float distance_w492;
float distance_w493;
float distance_w494;
float distance_w495;
float distance_w496;
float distance_w497;
float distance_w498;
float distance_w499;
float distance_w5;
float distance_w50;
float distance_w500;
float distance_w501;
float distance_w502;
float distance_w503;
float distance_w504;
float distance_w505;
float distance_w506;
float distance_w507;
float distance_w508;
float distance_w509;
float distance_w51;
float distance_w510;
float distance_w511;
float distance_w512;
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
float temp_l77_i257_w1;
float temp_l77_i258_w1;
float temp_l77_i259_w1;
float temp_l77_i25_w1;
float temp_l77_i260_w1;
float temp_l77_i261_w1;
float temp_l77_i262_w1;
float temp_l77_i263_w1;
float temp_l77_i264_w1;
float temp_l77_i265_w1;
float temp_l77_i266_w1;
float temp_l77_i267_w1;
float temp_l77_i268_w1;
float temp_l77_i269_w1;
float temp_l77_i26_w1;
float temp_l77_i270_w1;
float temp_l77_i271_w1;
float temp_l77_i272_w1;
float temp_l77_i273_w1;
float temp_l77_i274_w1;
float temp_l77_i275_w1;
float temp_l77_i276_w1;
float temp_l77_i277_w1;
float temp_l77_i278_w1;
float temp_l77_i279_w1;
float temp_l77_i27_w1;
float temp_l77_i280_w1;
float temp_l77_i281_w1;
float temp_l77_i282_w1;
float temp_l77_i283_w1;
float temp_l77_i284_w1;
float temp_l77_i285_w1;
float temp_l77_i286_w1;
float temp_l77_i287_w1;
float temp_l77_i288_w1;
float temp_l77_i289_w1;
float temp_l77_i28_w1;
float temp_l77_i290_w1;
float temp_l77_i291_w1;
float temp_l77_i292_w1;
float temp_l77_i293_w1;
float temp_l77_i294_w1;
float temp_l77_i295_w1;
float temp_l77_i296_w1;
float temp_l77_i297_w1;
float temp_l77_i298_w1;
float temp_l77_i299_w1;
float temp_l77_i29_w1;
float temp_l77_i2_w1;
float temp_l77_i300_w1;
float temp_l77_i301_w1;
float temp_l77_i302_w1;
float temp_l77_i303_w1;
float temp_l77_i304_w1;
float temp_l77_i305_w1;
float temp_l77_i306_w1;
float temp_l77_i307_w1;
float temp_l77_i308_w1;
float temp_l77_i309_w1;
float temp_l77_i30_w1;
float temp_l77_i310_w1;
float temp_l77_i311_w1;
float temp_l77_i312_w1;
float temp_l77_i313_w1;
float temp_l77_i314_w1;
float temp_l77_i315_w1;
float temp_l77_i316_w1;
float temp_l77_i317_w1;
float temp_l77_i318_w1;
float temp_l77_i319_w1;
float temp_l77_i31_w1;
float temp_l77_i320_w1;
float temp_l77_i321_w1;
float temp_l77_i322_w1;
float temp_l77_i323_w1;
float temp_l77_i324_w1;
float temp_l77_i325_w1;
float temp_l77_i326_w1;
float temp_l77_i327_w1;
float temp_l77_i328_w1;
float temp_l77_i329_w1;
float temp_l77_i32_w1;
float temp_l77_i330_w1;
float temp_l77_i331_w1;
float temp_l77_i332_w1;
float temp_l77_i333_w1;
float temp_l77_i334_w1;
float temp_l77_i335_w1;
float temp_l77_i336_w1;
float temp_l77_i337_w1;
float temp_l77_i338_w1;
float temp_l77_i339_w1;
float temp_l77_i33_w1;
float temp_l77_i340_w1;
float temp_l77_i341_w1;
float temp_l77_i342_w1;
float temp_l77_i343_w1;
float temp_l77_i344_w1;
float temp_l77_i345_w1;
float temp_l77_i346_w1;
float temp_l77_i347_w1;
float temp_l77_i348_w1;
float temp_l77_i349_w1;
float temp_l77_i34_w1;
float temp_l77_i350_w1;
float temp_l77_i351_w1;
float temp_l77_i352_w1;
float temp_l77_i353_w1;
float temp_l77_i354_w1;
float temp_l77_i355_w1;
float temp_l77_i356_w1;
float temp_l77_i357_w1;
float temp_l77_i358_w1;
float temp_l77_i359_w1;
float temp_l77_i35_w1;
float temp_l77_i360_w1;
float temp_l77_i361_w1;
float temp_l77_i362_w1;
float temp_l77_i363_w1;
float temp_l77_i364_w1;
float temp_l77_i365_w1;
float temp_l77_i366_w1;
float temp_l77_i367_w1;
float temp_l77_i368_w1;
float temp_l77_i369_w1;
float temp_l77_i36_w1;
float temp_l77_i370_w1;
float temp_l77_i371_w1;
float temp_l77_i372_w1;
float temp_l77_i373_w1;
float temp_l77_i374_w1;
float temp_l77_i375_w1;
float temp_l77_i376_w1;
float temp_l77_i377_w1;
float temp_l77_i378_w1;
float temp_l77_i379_w1;
float temp_l77_i37_w1;
float temp_l77_i380_w1;
float temp_l77_i381_w1;
float temp_l77_i382_w1;
float temp_l77_i383_w1;
float temp_l77_i384_w1;
float temp_l77_i385_w1;
float temp_l77_i386_w1;
float temp_l77_i387_w1;
float temp_l77_i388_w1;
float temp_l77_i389_w1;
float temp_l77_i38_w1;
float temp_l77_i390_w1;
float temp_l77_i391_w1;
float temp_l77_i392_w1;
float temp_l77_i393_w1;
float temp_l77_i394_w1;
float temp_l77_i395_w1;
float temp_l77_i396_w1;
float temp_l77_i397_w1;
float temp_l77_i398_w1;
float temp_l77_i399_w1;
float temp_l77_i39_w1;
float temp_l77_i3_w1;
float temp_l77_i400_w1;
float temp_l77_i401_w1;
float temp_l77_i402_w1;
float temp_l77_i403_w1;
float temp_l77_i404_w1;
float temp_l77_i405_w1;
float temp_l77_i406_w1;
float temp_l77_i407_w1;
float temp_l77_i408_w1;
float temp_l77_i409_w1;
float temp_l77_i40_w1;
float temp_l77_i410_w1;
float temp_l77_i411_w1;
float temp_l77_i412_w1;
float temp_l77_i413_w1;
float temp_l77_i414_w1;
float temp_l77_i415_w1;
float temp_l77_i416_w1;
float temp_l77_i417_w1;
float temp_l77_i418_w1;
float temp_l77_i419_w1;
float temp_l77_i41_w1;
float temp_l77_i420_w1;
float temp_l77_i421_w1;
float temp_l77_i422_w1;
float temp_l77_i423_w1;
float temp_l77_i424_w1;
float temp_l77_i425_w1;
float temp_l77_i426_w1;
float temp_l77_i427_w1;
float temp_l77_i428_w1;
float temp_l77_i429_w1;
float temp_l77_i42_w1;
float temp_l77_i430_w1;
float temp_l77_i431_w1;
float temp_l77_i432_w1;
float temp_l77_i433_w1;
float temp_l77_i434_w1;
float temp_l77_i435_w1;
float temp_l77_i436_w1;
float temp_l77_i437_w1;
float temp_l77_i438_w1;
float temp_l77_i439_w1;
float temp_l77_i43_w1;
float temp_l77_i440_w1;
float temp_l77_i441_w1;
float temp_l77_i442_w1;
float temp_l77_i443_w1;
float temp_l77_i444_w1;
float temp_l77_i445_w1;
float temp_l77_i446_w1;
float temp_l77_i447_w1;
float temp_l77_i448_w1;
float temp_l77_i449_w1;
float temp_l77_i44_w1;
float temp_l77_i450_w1;
float temp_l77_i451_w1;
float temp_l77_i452_w1;
float temp_l77_i453_w1;
float temp_l77_i454_w1;
float temp_l77_i455_w1;
float temp_l77_i456_w1;
float temp_l77_i457_w1;
float temp_l77_i458_w1;
float temp_l77_i459_w1;
float temp_l77_i45_w1;
float temp_l77_i460_w1;
float temp_l77_i461_w1;
float temp_l77_i462_w1;
float temp_l77_i463_w1;
float temp_l77_i464_w1;
float temp_l77_i465_w1;
float temp_l77_i466_w1;
float temp_l77_i467_w1;
float temp_l77_i468_w1;
float temp_l77_i469_w1;
float temp_l77_i46_w1;
float temp_l77_i470_w1;
float temp_l77_i471_w1;
float temp_l77_i472_w1;
float temp_l77_i473_w1;
float temp_l77_i474_w1;
float temp_l77_i475_w1;
float temp_l77_i476_w1;
float temp_l77_i477_w1;
float temp_l77_i478_w1;
float temp_l77_i479_w1;
float temp_l77_i47_w1;
float temp_l77_i480_w1;
float temp_l77_i481_w1;
float temp_l77_i482_w1;
float temp_l77_i483_w1;
float temp_l77_i484_w1;
float temp_l77_i485_w1;
float temp_l77_i486_w1;
float temp_l77_i487_w1;
float temp_l77_i488_w1;
float temp_l77_i489_w1;
float temp_l77_i48_w1;
float temp_l77_i490_w1;
float temp_l77_i491_w1;
float temp_l77_i492_w1;
float temp_l77_i493_w1;
float temp_l77_i494_w1;
float temp_l77_i495_w1;
float temp_l77_i496_w1;
float temp_l77_i497_w1;
float temp_l77_i498_w1;
float temp_l77_i499_w1;
float temp_l77_i49_w1;
float temp_l77_i4_w1;
float temp_l77_i500_w1;
float temp_l77_i501_w1;
float temp_l77_i502_w1;
float temp_l77_i503_w1;
float temp_l77_i504_w1;
float temp_l77_i505_w1;
float temp_l77_i506_w1;
float temp_l77_i507_w1;
float temp_l77_i508_w1;
float temp_l77_i509_w1;
float temp_l77_i50_w1;
float temp_l77_i510_w1;
float temp_l77_i511_w1;
float temp_l77_i512_w1;
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
for( int i = 0; i < 8;i=i+1){
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
temp_l77_i129_w1 = xFeatures[128] - knownFeatures[i][128];
temp_l77_i130_w1 = xFeatures[129] - knownFeatures[i][129];
temp_l77_i131_w1 = xFeatures[130] - knownFeatures[i][130];
temp_l77_i132_w1 = xFeatures[131] - knownFeatures[i][131];
temp_l77_i133_w1 = xFeatures[132] - knownFeatures[i][132];
temp_l77_i134_w1 = xFeatures[133] - knownFeatures[i][133];
temp_l77_i135_w1 = xFeatures[134] - knownFeatures[i][134];
temp_l77_i136_w1 = xFeatures[135] - knownFeatures[i][135];
temp_l77_i137_w1 = xFeatures[136] - knownFeatures[i][136];
temp_l77_i138_w1 = xFeatures[137] - knownFeatures[i][137];
temp_l77_i139_w1 = xFeatures[138] - knownFeatures[i][138];
temp_l77_i140_w1 = xFeatures[139] - knownFeatures[i][139];
temp_l77_i141_w1 = xFeatures[140] - knownFeatures[i][140];
temp_l77_i142_w1 = xFeatures[141] - knownFeatures[i][141];
temp_l77_i143_w1 = xFeatures[142] - knownFeatures[i][142];
temp_l77_i144_w1 = xFeatures[143] - knownFeatures[i][143];
temp_l77_i145_w1 = xFeatures[144] - knownFeatures[i][144];
temp_l77_i146_w1 = xFeatures[145] - knownFeatures[i][145];
temp_l77_i147_w1 = xFeatures[146] - knownFeatures[i][146];
temp_l77_i148_w1 = xFeatures[147] - knownFeatures[i][147];
temp_l77_i149_w1 = xFeatures[148] - knownFeatures[i][148];
temp_l77_i150_w1 = xFeatures[149] - knownFeatures[i][149];
temp_l77_i151_w1 = xFeatures[150] - knownFeatures[i][150];
temp_l77_i152_w1 = xFeatures[151] - knownFeatures[i][151];
temp_l77_i153_w1 = xFeatures[152] - knownFeatures[i][152];
temp_l77_i154_w1 = xFeatures[153] - knownFeatures[i][153];
temp_l77_i155_w1 = xFeatures[154] - knownFeatures[i][154];
temp_l77_i156_w1 = xFeatures[155] - knownFeatures[i][155];
temp_l77_i157_w1 = xFeatures[156] - knownFeatures[i][156];
temp_l77_i158_w1 = xFeatures[157] - knownFeatures[i][157];
temp_l77_i159_w1 = xFeatures[158] - knownFeatures[i][158];
temp_l77_i160_w1 = xFeatures[159] - knownFeatures[i][159];
temp_l77_i161_w1 = xFeatures[160] - knownFeatures[i][160];
temp_l77_i162_w1 = xFeatures[161] - knownFeatures[i][161];
temp_l77_i163_w1 = xFeatures[162] - knownFeatures[i][162];
temp_l77_i164_w1 = xFeatures[163] - knownFeatures[i][163];
temp_l77_i165_w1 = xFeatures[164] - knownFeatures[i][164];
temp_l77_i166_w1 = xFeatures[165] - knownFeatures[i][165];
temp_l77_i167_w1 = xFeatures[166] - knownFeatures[i][166];
temp_l77_i168_w1 = xFeatures[167] - knownFeatures[i][167];
temp_l77_i169_w1 = xFeatures[168] - knownFeatures[i][168];
temp_l77_i170_w1 = xFeatures[169] - knownFeatures[i][169];
temp_l77_i171_w1 = xFeatures[170] - knownFeatures[i][170];
temp_l77_i172_w1 = xFeatures[171] - knownFeatures[i][171];
temp_l77_i173_w1 = xFeatures[172] - knownFeatures[i][172];
temp_l77_i174_w1 = xFeatures[173] - knownFeatures[i][173];
temp_l77_i175_w1 = xFeatures[174] - knownFeatures[i][174];
temp_l77_i176_w1 = xFeatures[175] - knownFeatures[i][175];
temp_l77_i177_w1 = xFeatures[176] - knownFeatures[i][176];
temp_l77_i178_w1 = xFeatures[177] - knownFeatures[i][177];
temp_l77_i179_w1 = xFeatures[178] - knownFeatures[i][178];
temp_l77_i180_w1 = xFeatures[179] - knownFeatures[i][179];
temp_l77_i181_w1 = xFeatures[180] - knownFeatures[i][180];
temp_l77_i182_w1 = xFeatures[181] - knownFeatures[i][181];
temp_l77_i183_w1 = xFeatures[182] - knownFeatures[i][182];
temp_l77_i184_w1 = xFeatures[183] - knownFeatures[i][183];
temp_l77_i185_w1 = xFeatures[184] - knownFeatures[i][184];
temp_l77_i186_w1 = xFeatures[185] - knownFeatures[i][185];
temp_l77_i187_w1 = xFeatures[186] - knownFeatures[i][186];
temp_l77_i188_w1 = xFeatures[187] - knownFeatures[i][187];
temp_l77_i189_w1 = xFeatures[188] - knownFeatures[i][188];
temp_l77_i190_w1 = xFeatures[189] - knownFeatures[i][189];
temp_l77_i191_w1 = xFeatures[190] - knownFeatures[i][190];
temp_l77_i192_w1 = xFeatures[191] - knownFeatures[i][191];
temp_l77_i193_w1 = xFeatures[192] - knownFeatures[i][192];
temp_l77_i194_w1 = xFeatures[193] - knownFeatures[i][193];
temp_l77_i195_w1 = xFeatures[194] - knownFeatures[i][194];
temp_l77_i196_w1 = xFeatures[195] - knownFeatures[i][195];
temp_l77_i197_w1 = xFeatures[196] - knownFeatures[i][196];
temp_l77_i198_w1 = xFeatures[197] - knownFeatures[i][197];
temp_l77_i199_w1 = xFeatures[198] - knownFeatures[i][198];
temp_l77_i200_w1 = xFeatures[199] - knownFeatures[i][199];
temp_l77_i201_w1 = xFeatures[200] - knownFeatures[i][200];
temp_l77_i202_w1 = xFeatures[201] - knownFeatures[i][201];
temp_l77_i203_w1 = xFeatures[202] - knownFeatures[i][202];
temp_l77_i204_w1 = xFeatures[203] - knownFeatures[i][203];
temp_l77_i205_w1 = xFeatures[204] - knownFeatures[i][204];
temp_l77_i206_w1 = xFeatures[205] - knownFeatures[i][205];
temp_l77_i207_w1 = xFeatures[206] - knownFeatures[i][206];
temp_l77_i208_w1 = xFeatures[207] - knownFeatures[i][207];
temp_l77_i209_w1 = xFeatures[208] - knownFeatures[i][208];
temp_l77_i210_w1 = xFeatures[209] - knownFeatures[i][209];
temp_l77_i211_w1 = xFeatures[210] - knownFeatures[i][210];
temp_l77_i212_w1 = xFeatures[211] - knownFeatures[i][211];
temp_l77_i213_w1 = xFeatures[212] - knownFeatures[i][212];
temp_l77_i214_w1 = xFeatures[213] - knownFeatures[i][213];
temp_l77_i215_w1 = xFeatures[214] - knownFeatures[i][214];
temp_l77_i216_w1 = xFeatures[215] - knownFeatures[i][215];
temp_l77_i217_w1 = xFeatures[216] - knownFeatures[i][216];
temp_l77_i218_w1 = xFeatures[217] - knownFeatures[i][217];
temp_l77_i219_w1 = xFeatures[218] - knownFeatures[i][218];
temp_l77_i220_w1 = xFeatures[219] - knownFeatures[i][219];
temp_l77_i221_w1 = xFeatures[220] - knownFeatures[i][220];
temp_l77_i222_w1 = xFeatures[221] - knownFeatures[i][221];
temp_l77_i223_w1 = xFeatures[222] - knownFeatures[i][222];
temp_l77_i224_w1 = xFeatures[223] - knownFeatures[i][223];
temp_l77_i225_w1 = xFeatures[224] - knownFeatures[i][224];
temp_l77_i226_w1 = xFeatures[225] - knownFeatures[i][225];
temp_l77_i227_w1 = xFeatures[226] - knownFeatures[i][226];
temp_l77_i228_w1 = xFeatures[227] - knownFeatures[i][227];
temp_l77_i229_w1 = xFeatures[228] - knownFeatures[i][228];
temp_l77_i230_w1 = xFeatures[229] - knownFeatures[i][229];
temp_l77_i231_w1 = xFeatures[230] - knownFeatures[i][230];
temp_l77_i232_w1 = xFeatures[231] - knownFeatures[i][231];
temp_l77_i233_w1 = xFeatures[232] - knownFeatures[i][232];
temp_l77_i234_w1 = xFeatures[233] - knownFeatures[i][233];
temp_l77_i235_w1 = xFeatures[234] - knownFeatures[i][234];
temp_l77_i236_w1 = xFeatures[235] - knownFeatures[i][235];
temp_l77_i237_w1 = xFeatures[236] - knownFeatures[i][236];
temp_l77_i238_w1 = xFeatures[237] - knownFeatures[i][237];
temp_l77_i239_w1 = xFeatures[238] - knownFeatures[i][238];
temp_l77_i240_w1 = xFeatures[239] - knownFeatures[i][239];
temp_l77_i241_w1 = xFeatures[240] - knownFeatures[i][240];
temp_l77_i242_w1 = xFeatures[241] - knownFeatures[i][241];
temp_l77_i243_w1 = xFeatures[242] - knownFeatures[i][242];
temp_l77_i244_w1 = xFeatures[243] - knownFeatures[i][243];
temp_l77_i245_w1 = xFeatures[244] - knownFeatures[i][244];
temp_l77_i246_w1 = xFeatures[245] - knownFeatures[i][245];
temp_l77_i247_w1 = xFeatures[246] - knownFeatures[i][246];
temp_l77_i248_w1 = xFeatures[247] - knownFeatures[i][247];
temp_l77_i249_w1 = xFeatures[248] - knownFeatures[i][248];
temp_l77_i250_w1 = xFeatures[249] - knownFeatures[i][249];
temp_l77_i251_w1 = xFeatures[250] - knownFeatures[i][250];
temp_l77_i252_w1 = xFeatures[251] - knownFeatures[i][251];
temp_l77_i253_w1 = xFeatures[252] - knownFeatures[i][252];
temp_l77_i254_w1 = xFeatures[253] - knownFeatures[i][253];
temp_l77_i255_w1 = xFeatures[254] - knownFeatures[i][254];
temp_l77_i256_w1 = xFeatures[255] - knownFeatures[i][255];
temp_l77_i257_w1 = xFeatures[256] - knownFeatures[i][256];
temp_l77_i258_w1 = xFeatures[257] - knownFeatures[i][257];
temp_l77_i259_w1 = xFeatures[258] - knownFeatures[i][258];
temp_l77_i260_w1 = xFeatures[259] - knownFeatures[i][259];
temp_l77_i261_w1 = xFeatures[260] - knownFeatures[i][260];
temp_l77_i262_w1 = xFeatures[261] - knownFeatures[i][261];
temp_l77_i263_w1 = xFeatures[262] - knownFeatures[i][262];
temp_l77_i264_w1 = xFeatures[263] - knownFeatures[i][263];
temp_l77_i265_w1 = xFeatures[264] - knownFeatures[i][264];
temp_l77_i266_w1 = xFeatures[265] - knownFeatures[i][265];
temp_l77_i267_w1 = xFeatures[266] - knownFeatures[i][266];
temp_l77_i268_w1 = xFeatures[267] - knownFeatures[i][267];
temp_l77_i269_w1 = xFeatures[268] - knownFeatures[i][268];
temp_l77_i270_w1 = xFeatures[269] - knownFeatures[i][269];
temp_l77_i271_w1 = xFeatures[270] - knownFeatures[i][270];
temp_l77_i272_w1 = xFeatures[271] - knownFeatures[i][271];
temp_l77_i273_w1 = xFeatures[272] - knownFeatures[i][272];
temp_l77_i274_w1 = xFeatures[273] - knownFeatures[i][273];
temp_l77_i275_w1 = xFeatures[274] - knownFeatures[i][274];
temp_l77_i276_w1 = xFeatures[275] - knownFeatures[i][275];
temp_l77_i277_w1 = xFeatures[276] - knownFeatures[i][276];
temp_l77_i278_w1 = xFeatures[277] - knownFeatures[i][277];
temp_l77_i279_w1 = xFeatures[278] - knownFeatures[i][278];
temp_l77_i280_w1 = xFeatures[279] - knownFeatures[i][279];
temp_l77_i281_w1 = xFeatures[280] - knownFeatures[i][280];
temp_l77_i282_w1 = xFeatures[281] - knownFeatures[i][281];
temp_l77_i283_w1 = xFeatures[282] - knownFeatures[i][282];
temp_l77_i284_w1 = xFeatures[283] - knownFeatures[i][283];
temp_l77_i285_w1 = xFeatures[284] - knownFeatures[i][284];
temp_l77_i286_w1 = xFeatures[285] - knownFeatures[i][285];
temp_l77_i287_w1 = xFeatures[286] - knownFeatures[i][286];
temp_l77_i288_w1 = xFeatures[287] - knownFeatures[i][287];
temp_l77_i289_w1 = xFeatures[288] - knownFeatures[i][288];
temp_l77_i290_w1 = xFeatures[289] - knownFeatures[i][289];
temp_l77_i291_w1 = xFeatures[290] - knownFeatures[i][290];
temp_l77_i292_w1 = xFeatures[291] - knownFeatures[i][291];
temp_l77_i293_w1 = xFeatures[292] - knownFeatures[i][292];
temp_l77_i294_w1 = xFeatures[293] - knownFeatures[i][293];
temp_l77_i295_w1 = xFeatures[294] - knownFeatures[i][294];
temp_l77_i296_w1 = xFeatures[295] - knownFeatures[i][295];
temp_l77_i297_w1 = xFeatures[296] - knownFeatures[i][296];
temp_l77_i298_w1 = xFeatures[297] - knownFeatures[i][297];
temp_l77_i299_w1 = xFeatures[298] - knownFeatures[i][298];
temp_l77_i300_w1 = xFeatures[299] - knownFeatures[i][299];
temp_l77_i301_w1 = xFeatures[300] - knownFeatures[i][300];
temp_l77_i302_w1 = xFeatures[301] - knownFeatures[i][301];
temp_l77_i303_w1 = xFeatures[302] - knownFeatures[i][302];
temp_l77_i304_w1 = xFeatures[303] - knownFeatures[i][303];
temp_l77_i305_w1 = xFeatures[304] - knownFeatures[i][304];
temp_l77_i306_w1 = xFeatures[305] - knownFeatures[i][305];
temp_l77_i307_w1 = xFeatures[306] - knownFeatures[i][306];
temp_l77_i308_w1 = xFeatures[307] - knownFeatures[i][307];
temp_l77_i309_w1 = xFeatures[308] - knownFeatures[i][308];
temp_l77_i310_w1 = xFeatures[309] - knownFeatures[i][309];
temp_l77_i311_w1 = xFeatures[310] - knownFeatures[i][310];
temp_l77_i312_w1 = xFeatures[311] - knownFeatures[i][311];
temp_l77_i313_w1 = xFeatures[312] - knownFeatures[i][312];
temp_l77_i314_w1 = xFeatures[313] - knownFeatures[i][313];
temp_l77_i315_w1 = xFeatures[314] - knownFeatures[i][314];
temp_l77_i316_w1 = xFeatures[315] - knownFeatures[i][315];
temp_l77_i317_w1 = xFeatures[316] - knownFeatures[i][316];
temp_l77_i318_w1 = xFeatures[317] - knownFeatures[i][317];
temp_l77_i319_w1 = xFeatures[318] - knownFeatures[i][318];
temp_l77_i320_w1 = xFeatures[319] - knownFeatures[i][319];
temp_l77_i321_w1 = xFeatures[320] - knownFeatures[i][320];
temp_l77_i322_w1 = xFeatures[321] - knownFeatures[i][321];
temp_l77_i323_w1 = xFeatures[322] - knownFeatures[i][322];
temp_l77_i324_w1 = xFeatures[323] - knownFeatures[i][323];
temp_l77_i325_w1 = xFeatures[324] - knownFeatures[i][324];
temp_l77_i326_w1 = xFeatures[325] - knownFeatures[i][325];
temp_l77_i327_w1 = xFeatures[326] - knownFeatures[i][326];
temp_l77_i328_w1 = xFeatures[327] - knownFeatures[i][327];
temp_l77_i329_w1 = xFeatures[328] - knownFeatures[i][328];
temp_l77_i330_w1 = xFeatures[329] - knownFeatures[i][329];
temp_l77_i331_w1 = xFeatures[330] - knownFeatures[i][330];
temp_l77_i332_w1 = xFeatures[331] - knownFeatures[i][331];
temp_l77_i333_w1 = xFeatures[332] - knownFeatures[i][332];
temp_l77_i334_w1 = xFeatures[333] - knownFeatures[i][333];
temp_l77_i335_w1 = xFeatures[334] - knownFeatures[i][334];
temp_l77_i336_w1 = xFeatures[335] - knownFeatures[i][335];
temp_l77_i337_w1 = xFeatures[336] - knownFeatures[i][336];
temp_l77_i338_w1 = xFeatures[337] - knownFeatures[i][337];
temp_l77_i339_w1 = xFeatures[338] - knownFeatures[i][338];
temp_l77_i340_w1 = xFeatures[339] - knownFeatures[i][339];
temp_l77_i341_w1 = xFeatures[340] - knownFeatures[i][340];
temp_l77_i342_w1 = xFeatures[341] - knownFeatures[i][341];
temp_l77_i343_w1 = xFeatures[342] - knownFeatures[i][342];
temp_l77_i344_w1 = xFeatures[343] - knownFeatures[i][343];
temp_l77_i345_w1 = xFeatures[344] - knownFeatures[i][344];
temp_l77_i346_w1 = xFeatures[345] - knownFeatures[i][345];
temp_l77_i347_w1 = xFeatures[346] - knownFeatures[i][346];
temp_l77_i348_w1 = xFeatures[347] - knownFeatures[i][347];
temp_l77_i349_w1 = xFeatures[348] - knownFeatures[i][348];
temp_l77_i350_w1 = xFeatures[349] - knownFeatures[i][349];
temp_l77_i351_w1 = xFeatures[350] - knownFeatures[i][350];
temp_l77_i352_w1 = xFeatures[351] - knownFeatures[i][351];
temp_l77_i353_w1 = xFeatures[352] - knownFeatures[i][352];
temp_l77_i354_w1 = xFeatures[353] - knownFeatures[i][353];
temp_l77_i355_w1 = xFeatures[354] - knownFeatures[i][354];
temp_l77_i356_w1 = xFeatures[355] - knownFeatures[i][355];
temp_l77_i357_w1 = xFeatures[356] - knownFeatures[i][356];
temp_l77_i358_w1 = xFeatures[357] - knownFeatures[i][357];
temp_l77_i359_w1 = xFeatures[358] - knownFeatures[i][358];
temp_l77_i360_w1 = xFeatures[359] - knownFeatures[i][359];
temp_l77_i361_w1 = xFeatures[360] - knownFeatures[i][360];
temp_l77_i362_w1 = xFeatures[361] - knownFeatures[i][361];
temp_l77_i363_w1 = xFeatures[362] - knownFeatures[i][362];
temp_l77_i364_w1 = xFeatures[363] - knownFeatures[i][363];
temp_l77_i365_w1 = xFeatures[364] - knownFeatures[i][364];
temp_l77_i366_w1 = xFeatures[365] - knownFeatures[i][365];
temp_l77_i367_w1 = xFeatures[366] - knownFeatures[i][366];
temp_l77_i368_w1 = xFeatures[367] - knownFeatures[i][367];
temp_l77_i369_w1 = xFeatures[368] - knownFeatures[i][368];
temp_l77_i370_w1 = xFeatures[369] - knownFeatures[i][369];
temp_l77_i371_w1 = xFeatures[370] - knownFeatures[i][370];
temp_l77_i372_w1 = xFeatures[371] - knownFeatures[i][371];
temp_l77_i373_w1 = xFeatures[372] - knownFeatures[i][372];
temp_l77_i374_w1 = xFeatures[373] - knownFeatures[i][373];
temp_l77_i375_w1 = xFeatures[374] - knownFeatures[i][374];
temp_l77_i376_w1 = xFeatures[375] - knownFeatures[i][375];
temp_l77_i377_w1 = xFeatures[376] - knownFeatures[i][376];
temp_l77_i378_w1 = xFeatures[377] - knownFeatures[i][377];
temp_l77_i379_w1 = xFeatures[378] - knownFeatures[i][378];
temp_l77_i380_w1 = xFeatures[379] - knownFeatures[i][379];
temp_l77_i381_w1 = xFeatures[380] - knownFeatures[i][380];
temp_l77_i382_w1 = xFeatures[381] - knownFeatures[i][381];
temp_l77_i383_w1 = xFeatures[382] - knownFeatures[i][382];
temp_l77_i384_w1 = xFeatures[383] - knownFeatures[i][383];
temp_l77_i385_w1 = xFeatures[384] - knownFeatures[i][384];
temp_l77_i386_w1 = xFeatures[385] - knownFeatures[i][385];
temp_l77_i387_w1 = xFeatures[386] - knownFeatures[i][386];
temp_l77_i388_w1 = xFeatures[387] - knownFeatures[i][387];
temp_l77_i389_w1 = xFeatures[388] - knownFeatures[i][388];
temp_l77_i390_w1 = xFeatures[389] - knownFeatures[i][389];
temp_l77_i391_w1 = xFeatures[390] - knownFeatures[i][390];
temp_l77_i392_w1 = xFeatures[391] - knownFeatures[i][391];
temp_l77_i393_w1 = xFeatures[392] - knownFeatures[i][392];
temp_l77_i394_w1 = xFeatures[393] - knownFeatures[i][393];
temp_l77_i395_w1 = xFeatures[394] - knownFeatures[i][394];
temp_l77_i396_w1 = xFeatures[395] - knownFeatures[i][395];
temp_l77_i397_w1 = xFeatures[396] - knownFeatures[i][396];
temp_l77_i398_w1 = xFeatures[397] - knownFeatures[i][397];
temp_l77_i399_w1 = xFeatures[398] - knownFeatures[i][398];
temp_l77_i400_w1 = xFeatures[399] - knownFeatures[i][399];
temp_l77_i401_w1 = xFeatures[400] - knownFeatures[i][400];
temp_l77_i402_w1 = xFeatures[401] - knownFeatures[i][401];
temp_l77_i403_w1 = xFeatures[402] - knownFeatures[i][402];
temp_l77_i404_w1 = xFeatures[403] - knownFeatures[i][403];
temp_l77_i405_w1 = xFeatures[404] - knownFeatures[i][404];
temp_l77_i406_w1 = xFeatures[405] - knownFeatures[i][405];
temp_l77_i407_w1 = xFeatures[406] - knownFeatures[i][406];
temp_l77_i408_w1 = xFeatures[407] - knownFeatures[i][407];
temp_l77_i409_w1 = xFeatures[408] - knownFeatures[i][408];
temp_l77_i410_w1 = xFeatures[409] - knownFeatures[i][409];
temp_l77_i411_w1 = xFeatures[410] - knownFeatures[i][410];
temp_l77_i412_w1 = xFeatures[411] - knownFeatures[i][411];
temp_l77_i413_w1 = xFeatures[412] - knownFeatures[i][412];
temp_l77_i414_w1 = xFeatures[413] - knownFeatures[i][413];
temp_l77_i415_w1 = xFeatures[414] - knownFeatures[i][414];
temp_l77_i416_w1 = xFeatures[415] - knownFeatures[i][415];
temp_l77_i417_w1 = xFeatures[416] - knownFeatures[i][416];
temp_l77_i418_w1 = xFeatures[417] - knownFeatures[i][417];
temp_l77_i419_w1 = xFeatures[418] - knownFeatures[i][418];
temp_l77_i420_w1 = xFeatures[419] - knownFeatures[i][419];
temp_l77_i421_w1 = xFeatures[420] - knownFeatures[i][420];
temp_l77_i422_w1 = xFeatures[421] - knownFeatures[i][421];
temp_l77_i423_w1 = xFeatures[422] - knownFeatures[i][422];
temp_l77_i424_w1 = xFeatures[423] - knownFeatures[i][423];
temp_l77_i425_w1 = xFeatures[424] - knownFeatures[i][424];
temp_l77_i426_w1 = xFeatures[425] - knownFeatures[i][425];
temp_l77_i427_w1 = xFeatures[426] - knownFeatures[i][426];
temp_l77_i428_w1 = xFeatures[427] - knownFeatures[i][427];
temp_l77_i429_w1 = xFeatures[428] - knownFeatures[i][428];
temp_l77_i430_w1 = xFeatures[429] - knownFeatures[i][429];
temp_l77_i431_w1 = xFeatures[430] - knownFeatures[i][430];
temp_l77_i432_w1 = xFeatures[431] - knownFeatures[i][431];
temp_l77_i433_w1 = xFeatures[432] - knownFeatures[i][432];
temp_l77_i434_w1 = xFeatures[433] - knownFeatures[i][433];
temp_l77_i435_w1 = xFeatures[434] - knownFeatures[i][434];
temp_l77_i436_w1 = xFeatures[435] - knownFeatures[i][435];
temp_l77_i437_w1 = xFeatures[436] - knownFeatures[i][436];
temp_l77_i438_w1 = xFeatures[437] - knownFeatures[i][437];
temp_l77_i439_w1 = xFeatures[438] - knownFeatures[i][438];
temp_l77_i440_w1 = xFeatures[439] - knownFeatures[i][439];
temp_l77_i441_w1 = xFeatures[440] - knownFeatures[i][440];
temp_l77_i442_w1 = xFeatures[441] - knownFeatures[i][441];
temp_l77_i443_w1 = xFeatures[442] - knownFeatures[i][442];
temp_l77_i444_w1 = xFeatures[443] - knownFeatures[i][443];
temp_l77_i445_w1 = xFeatures[444] - knownFeatures[i][444];
temp_l77_i446_w1 = xFeatures[445] - knownFeatures[i][445];
temp_l77_i447_w1 = xFeatures[446] - knownFeatures[i][446];
temp_l77_i448_w1 = xFeatures[447] - knownFeatures[i][447];
temp_l77_i449_w1 = xFeatures[448] - knownFeatures[i][448];
temp_l77_i450_w1 = xFeatures[449] - knownFeatures[i][449];
temp_l77_i451_w1 = xFeatures[450] - knownFeatures[i][450];
temp_l77_i452_w1 = xFeatures[451] - knownFeatures[i][451];
temp_l77_i453_w1 = xFeatures[452] - knownFeatures[i][452];
temp_l77_i454_w1 = xFeatures[453] - knownFeatures[i][453];
temp_l77_i455_w1 = xFeatures[454] - knownFeatures[i][454];
temp_l77_i456_w1 = xFeatures[455] - knownFeatures[i][455];
temp_l77_i457_w1 = xFeatures[456] - knownFeatures[i][456];
temp_l77_i458_w1 = xFeatures[457] - knownFeatures[i][457];
temp_l77_i459_w1 = xFeatures[458] - knownFeatures[i][458];
temp_l77_i460_w1 = xFeatures[459] - knownFeatures[i][459];
temp_l77_i461_w1 = xFeatures[460] - knownFeatures[i][460];
temp_l77_i462_w1 = xFeatures[461] - knownFeatures[i][461];
temp_l77_i463_w1 = xFeatures[462] - knownFeatures[i][462];
temp_l77_i464_w1 = xFeatures[463] - knownFeatures[i][463];
temp_l77_i465_w1 = xFeatures[464] - knownFeatures[i][464];
temp_l77_i466_w1 = xFeatures[465] - knownFeatures[i][465];
temp_l77_i467_w1 = xFeatures[466] - knownFeatures[i][466];
temp_l77_i468_w1 = xFeatures[467] - knownFeatures[i][467];
temp_l77_i469_w1 = xFeatures[468] - knownFeatures[i][468];
temp_l77_i470_w1 = xFeatures[469] - knownFeatures[i][469];
temp_l77_i471_w1 = xFeatures[470] - knownFeatures[i][470];
temp_l77_i472_w1 = xFeatures[471] - knownFeatures[i][471];
temp_l77_i473_w1 = xFeatures[472] - knownFeatures[i][472];
temp_l77_i474_w1 = xFeatures[473] - knownFeatures[i][473];
temp_l77_i475_w1 = xFeatures[474] - knownFeatures[i][474];
temp_l77_i476_w1 = xFeatures[475] - knownFeatures[i][475];
temp_l77_i477_w1 = xFeatures[476] - knownFeatures[i][476];
temp_l77_i478_w1 = xFeatures[477] - knownFeatures[i][477];
temp_l77_i479_w1 = xFeatures[478] - knownFeatures[i][478];
temp_l77_i480_w1 = xFeatures[479] - knownFeatures[i][479];
temp_l77_i481_w1 = xFeatures[480] - knownFeatures[i][480];
temp_l77_i482_w1 = xFeatures[481] - knownFeatures[i][481];
temp_l77_i483_w1 = xFeatures[482] - knownFeatures[i][482];
temp_l77_i484_w1 = xFeatures[483] - knownFeatures[i][483];
temp_l77_i485_w1 = xFeatures[484] - knownFeatures[i][484];
temp_l77_i486_w1 = xFeatures[485] - knownFeatures[i][485];
temp_l77_i487_w1 = xFeatures[486] - knownFeatures[i][486];
temp_l77_i488_w1 = xFeatures[487] - knownFeatures[i][487];
temp_l77_i489_w1 = xFeatures[488] - knownFeatures[i][488];
temp_l77_i490_w1 = xFeatures[489] - knownFeatures[i][489];
temp_l77_i491_w1 = xFeatures[490] - knownFeatures[i][490];
temp_l77_i492_w1 = xFeatures[491] - knownFeatures[i][491];
temp_l77_i493_w1 = xFeatures[492] - knownFeatures[i][492];
temp_l77_i494_w1 = xFeatures[493] - knownFeatures[i][493];
temp_l77_i495_w1 = xFeatures[494] - knownFeatures[i][494];
temp_l77_i496_w1 = xFeatures[495] - knownFeatures[i][495];
temp_l77_i497_w1 = xFeatures[496] - knownFeatures[i][496];
temp_l77_i498_w1 = xFeatures[497] - knownFeatures[i][497];
temp_l77_i499_w1 = xFeatures[498] - knownFeatures[i][498];
temp_l77_i500_w1 = xFeatures[499] - knownFeatures[i][499];
temp_l77_i501_w1 = xFeatures[500] - knownFeatures[i][500];
temp_l77_i502_w1 = xFeatures[501] - knownFeatures[i][501];
temp_l77_i503_w1 = xFeatures[502] - knownFeatures[i][502];
temp_l77_i504_w1 = xFeatures[503] - knownFeatures[i][503];
temp_l77_i505_w1 = xFeatures[504] - knownFeatures[i][504];
temp_l77_i506_w1 = xFeatures[505] - knownFeatures[i][505];
temp_l77_i507_w1 = xFeatures[506] - knownFeatures[i][506];
temp_l77_i508_w1 = xFeatures[507] - knownFeatures[i][507];
temp_l77_i509_w1 = xFeatures[508] - knownFeatures[i][508];
temp_l77_i510_w1 = xFeatures[509] - knownFeatures[i][509];
temp_l77_i511_w1 = xFeatures[510] - knownFeatures[i][510];
temp_l77_i512_w1 = xFeatures[511] - knownFeatures[i][511];

distance_w69 = sqr(temp_l77_i447_w1) + sqr(temp_l77_i448_w1);
distance_w203 = sqr(temp_l77_i487_w1) + sqr(temp_l77_i488_w1);
distance_w121 = sqr(temp_l77_i269_w1) + sqr(temp_l77_i270_w1);
distance_w146 = sqr(temp_l77_i467_w1) + sqr(temp_l77_i468_w1);
distance_w127 = sqr(temp_l77_i497_w1) + sqr(temp_l77_i498_w1);
distance_w90 = sqr(temp_l77_i305_w1) + sqr(temp_l77_i306_w1);
distance_w198 = sqr(temp_l77_i261_w1) + sqr(temp_l77_i262_w1);
distance_w33 = sqr(temp_l77_i435_w1) + sqr(temp_l77_i436_w1);
distance_w123 = sqr(temp_l77_i499_w1) + sqr(temp_l77_i500_w1);
distance_w447 = sqr(temp_l77_i245_w1) + sqr(temp_l77_i246_w1);
distance_w157 = sqr(temp_l77_i311_w1) + sqr(temp_l77_i312_w1);
distance_w30 = sqr(temp_l77_i439_w1) + sqr(temp_l77_i440_w1);
distance_w459 = sqr(temp_l77_i237_w1) + sqr(temp_l77_i238_w1);
distance_w31 = sqr(temp_l77_i263_w1) + sqr(temp_l77_i264_w1);
distance_w376 = sqr(temp_l77_i295_w1) + sqr(temp_l77_i296_w1);
distance_w467 = sqr(temp_l77_i299_w1) + sqr(temp_l77_i300_w1);
distance_w184 = sqr(temp_l77_i297_w1) + sqr(temp_l77_i298_w1);
distance_w144 = sqr(temp_l77_i247_w1) + sqr(temp_l77_i248_w1);
distance_w49 = sqr(temp_l77_i343_w1) + sqr(temp_l77_i344_w1);
distance_w42 = sqr(temp_l77_i397_w1) + sqr(temp_l77_i398_w1);
distance_w84 = sqr(temp_l77_i289_w1) + sqr(temp_l77_i290_w1);
distance_w175 = sqr(temp_l77_i425_w1) + sqr(temp_l77_i426_w1);
distance_w307 = sqr(temp_l77_i429_w1) + sqr(temp_l77_i430_w1);
distance_w337 = sqr(temp_l77_i451_w1) + sqr(temp_l77_i452_w1);
distance_w201 = sqr(temp_l77_i395_w1) + sqr(temp_l77_i396_w1);
distance_w63 = sqr(temp_l77_i457_w1) + sqr(temp_l77_i458_w1);
distance_w435 = sqr(temp_l77_i243_w1) + sqr(temp_l77_i244_w1);
distance_w398 = sqr(temp_l77_i431_w1) + sqr(temp_l77_i432_w1);
distance_w428 = sqr(temp_l77_i327_w1) + sqr(temp_l77_i328_w1);
distance_w310 = sqr(temp_l77_i489_w1) + sqr(temp_l77_i490_w1);
distance_w295 = sqr(temp_l77_i365_w1) + sqr(temp_l77_i366_w1);
distance_w348 = sqr(temp_l77_i381_w1) + sqr(temp_l77_i382_w1);
distance_w387 = sqr(temp_l77_i477_w1) + sqr(temp_l77_i478_w1);
distance_w85 = sqr(temp_l77_i469_w1) + sqr(temp_l77_i470_w1);
distance_w241 = sqr(temp_l77_i463_w1) + sqr(temp_l77_i464_w1);
distance_w21 = sqr(temp_l77_i411_w1) + sqr(temp_l77_i412_w1);
distance_w104 = sqr(temp_l77_i377_w1) + sqr(temp_l77_i378_w1);
distance_w455 = sqr(temp_l77_i399_w1) + sqr(temp_l77_i400_w1);
distance_w114 = sqr(temp_l77_i473_w1) + sqr(temp_l77_i474_w1);
distance_w480 = sqr(temp_l77_i301_w1) + sqr(temp_l77_i302_w1);
distance_w338 = sqr(temp_l77_i503_w1) + sqr(temp_l77_i504_w1);
distance_w192 = sqr(temp_l77_i341_w1) + sqr(temp_l77_i342_w1);
distance_w444 = sqr(temp_l77_i345_w1) + sqr(temp_l77_i346_w1);
distance_w278 = sqr(temp_l77_i393_w1) + sqr(temp_l77_i394_w1);
distance_w254 = sqr(temp_l77_i275_w1) + sqr(temp_l77_i276_w1);
distance_w133 = sqr(temp_l77_i361_w1) + sqr(temp_l77_i362_w1);
distance_w468 = sqr(temp_l77_i445_w1) + sqr(temp_l77_i446_w1);
distance_w283 = sqr(temp_l77_i255_w1) + sqr(temp_l77_i256_w1);
distance_w364 = sqr(temp_l77_i465_w1) + sqr(temp_l77_i466_w1);
distance_w160 = sqr(temp_l77_i257_w1) + sqr(temp_l77_i258_w1);
distance_w487 = sqr(temp_l77_i443_w1) + sqr(temp_l77_i444_w1);
distance_w186 = sqr(temp_l77_i459_w1) + sqr(temp_l77_i460_w1);
distance_w110 = sqr(temp_l77_i507_w1) + sqr(temp_l77_i508_w1);
distance_w225 = sqr(temp_l77_i387_w1) + sqr(temp_l77_i388_w1);
distance_w239 = sqr(temp_l77_i317_w1) + sqr(temp_l77_i318_w1);
distance_w111 = sqr(temp_l77_i433_w1) + sqr(temp_l77_i434_w1);
distance_w221 = sqr(temp_l77_i303_w1) + sqr(temp_l77_i304_w1);
distance_w222 = sqr(temp_l77_i265_w1) + sqr(temp_l77_i266_w1);
distance_w96 = sqr(temp_l77_i375_w1) + sqr(temp_l77_i376_w1);
distance_w445 = sqr(temp_l77_i271_w1) + sqr(temp_l77_i272_w1);
distance_w406 = 0 + sqr(temp_l77_i1_w1);
distance_w251 = sqr(temp_l77_i349_w1) + sqr(temp_l77_i350_w1);
distance_w71 = sqr(temp_l77_i385_w1) + sqr(temp_l77_i386_w1);
distance_w321 = sqr(temp_l77_i239_w1) + sqr(temp_l77_i240_w1);
distance_w50 = sqr(temp_l77_i481_w1) + sqr(temp_l77_i482_w1);
distance_w158 = sqr(temp_l77_i371_w1) + sqr(temp_l77_i372_w1);
distance_w503 = sqr(temp_l77_i449_w1) + sqr(temp_l77_i450_w1);
distance_w256 = sqr(temp_l77_i421_w1) + sqr(temp_l77_i422_w1);
distance_w36 = sqr(temp_l77_i453_w1) + sqr(temp_l77_i454_w1);
distance_w309 = sqr(temp_l77_i363_w1) + sqr(temp_l77_i364_w1);
distance_w149 = sqr(temp_l77_i505_w1) + sqr(temp_l77_i506_w1);
distance_w395 = sqr(temp_l77_i259_w1) + sqr(temp_l77_i260_w1);
distance_w392 = sqr(temp_l77_i475_w1) + sqr(temp_l77_i476_w1);
distance_w246 = sqr(temp_l77_i319_w1) + sqr(temp_l77_i320_w1);
distance_w386 = sqr(temp_l77_i373_w1) + sqr(temp_l77_i374_w1);
distance_w404 = sqr(temp_l77_i509_w1) + sqr(temp_l77_i510_w1);
distance_w320 = sqr(temp_l77_i479_w1) + sqr(temp_l77_i480_w1);
distance_w99 = sqr(temp_l77_i251_w1) + sqr(temp_l77_i252_w1);
distance_w61 = sqr(temp_l77_i389_w1) + sqr(temp_l77_i390_w1);
distance_w297 = sqr(temp_l77_i391_w1) + sqr(temp_l77_i392_w1);
distance_w105 = sqr(temp_l77_i313_w1) + sqr(temp_l77_i314_w1);
distance_w12 = sqr(temp_l77_i355_w1) + sqr(temp_l77_i356_w1);
distance_w299 = sqr(temp_l77_i455_w1) + sqr(temp_l77_i456_w1);
distance_w16 = sqr(temp_l77_i323_w1) + sqr(temp_l77_i324_w1);
distance_w172 = sqr(temp_l77_i495_w1) + sqr(temp_l77_i496_w1);
distance_w234 = sqr(temp_l77_i461_w1) + sqr(temp_l77_i462_w1);
distance_w372 = sqr(temp_l77_i401_w1) + sqr(temp_l77_i402_w1);
distance_w463 = sqr(temp_l77_i331_w1) + sqr(temp_l77_i332_w1);
distance_w450 = sqr(temp_l77_i321_w1) + sqr(temp_l77_i322_w1);
distance_w347 = sqr(temp_l77_i357_w1) + sqr(temp_l77_i358_w1);
distance_w199 = sqr(temp_l77_i241_w1) + sqr(temp_l77_i242_w1);
distance_w215 = sqr(temp_l77_i485_w1) + sqr(temp_l77_i486_w1);
distance_w352 = sqr(temp_l77_i413_w1) + sqr(temp_l77_i414_w1);
distance_w453 = sqr(temp_l77_i273_w1) + sqr(temp_l77_i274_w1);
distance_w74 = sqr(temp_l77_i359_w1) + sqr(temp_l77_i360_w1);
distance_w88 = sqr(temp_l77_i437_w1) + sqr(temp_l77_i438_w1);
distance_w20 = sqr(temp_l77_i417_w1) + sqr(temp_l77_i418_w1);
distance_w125 = sqr(temp_l77_i427_w1) + sqr(temp_l77_i428_w1);
distance_w407 = sqr(temp_l77_i419_w1) + sqr(temp_l77_i420_w1);
distance_w274 = sqr(temp_l77_i415_w1) + sqr(temp_l77_i416_w1);
distance_w214 = sqr(temp_l77_i369_w1) + sqr(temp_l77_i370_w1);
distance_w412 = sqr(temp_l77_i315_w1) + sqr(temp_l77_i316_w1);
distance_w491 = sqr(temp_l77_i291_w1) + sqr(temp_l77_i292_w1);
distance_w501 = sqr(temp_l77_i287_w1) + sqr(temp_l77_i288_w1);
distance_w360 = sqr(temp_l77_i409_w1) + sqr(temp_l77_i410_w1);
distance_w188 = sqr(temp_l77_i405_w1) + sqr(temp_l77_i406_w1);
distance_w261 = sqr(temp_l77_i441_w1) + sqr(temp_l77_i442_w1);
distance_w108 = sqr(temp_l77_i423_w1) + sqr(temp_l77_i424_w1);
distance_w177 = sqr(temp_l77_i333_w1) + sqr(temp_l77_i334_w1);
distance_w420 = sqr(temp_l77_i293_w1) + sqr(temp_l77_i294_w1);
distance_w336 = sqr(temp_l77_i491_w1) + sqr(temp_l77_i492_w1);
distance_w229 = sqr(temp_l77_i407_w1) + sqr(temp_l77_i408_w1);
distance_w296 = sqr(temp_l77_i367_w1) + sqr(temp_l77_i368_w1);
distance_w206 = sqr(temp_l77_i501_w1) + sqr(temp_l77_i502_w1);
distance_w493 = sqr(temp_l77_i285_w1) + sqr(temp_l77_i286_w1);
distance_w324 = sqr(temp_l77_i471_w1) + sqr(temp_l77_i472_w1);
distance_w457 = sqr(temp_l77_i339_w1) + sqr(temp_l77_i340_w1);
distance_w495 = sqr(temp_l77_i351_w1) + sqr(temp_l77_i352_w1);
distance_w484 = sqr(temp_l77_i335_w1) + sqr(temp_l77_i336_w1);
distance_w379 = sqr(temp_l77_i483_w1) + sqr(temp_l77_i484_w1);
distance_w327 = sqr(temp_l77_i347_w1) + sqr(temp_l77_i348_w1);
distance_w399 = sqr(temp_l77_i283_w1) + sqr(temp_l77_i284_w1);
distance_w440 = sqr(temp_l77_i309_w1) + sqr(temp_l77_i310_w1);
distance_w28 = sqr(temp_l77_i253_w1) + sqr(temp_l77_i254_w1);
distance_w152 = sqr(temp_l77_i329_w1) + sqr(temp_l77_i330_w1);
distance_w249 = sqr(temp_l77_i383_w1) + sqr(temp_l77_i384_w1);
distance_w301 = sqr(temp_l77_i353_w1) + sqr(temp_l77_i354_w1);
distance_w470 = sqr(temp_l77_i249_w1) + sqr(temp_l77_i250_w1);
distance_w371 = sqr(temp_l77_i277_w1) + sqr(temp_l77_i278_w1);
distance_w218 = sqr(temp_l77_i379_w1) + sqr(temp_l77_i380_w1);
distance_w262 = sqr(temp_l77_i279_w1) + sqr(temp_l77_i280_w1);
distance_w427 = sqr(temp_l77_i325_w1) + sqr(temp_l77_i326_w1);
distance_w442 = sqr(temp_l77_i403_w1) + sqr(temp_l77_i404_w1);
distance_w113 = sqr(temp_l77_i337_w1) + sqr(temp_l77_i338_w1);
distance_w202 = sqr(temp_l77_i307_w1) + sqr(temp_l77_i308_w1);
distance_w342 = sqr(temp_l77_i493_w1) + sqr(temp_l77_i494_w1);
distance_w131 = sqr(temp_l77_i281_w1) + sqr(temp_l77_i282_w1);
distance_w3 = sqr(temp_l77_i267_w1) + sqr(temp_l77_i268_w1);

distance_w81 = distance_w404 + sqr(temp_l77_i511_w1);
distance_w370 = distance_w406 + sqr(temp_l77_i2_w1);
distance_w19 = distance_w20 + distance_w407;
distance_w27 = distance_w28 + distance_w283;
distance_w35 = distance_w36 + distance_w299;
distance_w323 = distance_w42 + distance_w455;
distance_w389 = distance_w50 + distance_w379;
distance_w60 = distance_w61 + distance_w297;
distance_w62 = distance_w63 + distance_w186;
distance_w70 = distance_w71 + distance_w225;
distance_w83 = distance_w84 + distance_w491;
distance_w408 = distance_w85 + distance_w324;
distance_w87 = distance_w88 + distance_w30;
distance_w89 = distance_w90 + distance_w202;
distance_w103 = distance_w104 + distance_w218;
distance_w498 = distance_w105 + distance_w412;
distance_w34 = distance_w111 + distance_w33;
distance_w112 = distance_w113 + distance_w457;
distance_w508 = distance_w114 + distance_w392;
distance_w120 = distance_w121 + distance_w445;
distance_w391 = distance_w127 + distance_w123;
distance_w400 = distance_w131 + distance_w399;
distance_w226 = distance_w133 + distance_w309;
distance_w454 = distance_w149 + distance_w110;
distance_w151 = distance_w152 + distance_w463;
distance_w396 = distance_w160 + distance_w395;
distance_w174 = distance_w175 + distance_w125;
distance_w176 = distance_w177 + distance_w484;
distance_w394 = distance_w184 + distance_w467;
distance_w187 = distance_w188 + distance_w229;
distance_w191 = distance_w192 + distance_w49;
distance_w32 = distance_w198 + distance_w31;
distance_w436 = distance_w199 + distance_w435;
distance_w205 = distance_w206 + distance_w338;
distance_w159 = distance_w214 + distance_w158;
distance_w204 = distance_w215 + distance_w203;
distance_w4 = distance_w222 + distance_w3;
distance_w260 = distance_w234 + distance_w241;
distance_w238 = distance_w239 + distance_w246;
distance_w415 = distance_w251 + distance_w495;
distance_w328 = distance_w256 + distance_w108;
distance_w266 = distance_w261 + distance_w487;
distance_w497 = distance_w278 + distance_w201;
distance_w294 = distance_w295 + distance_w296;
distance_w300 = distance_w301 + distance_w12;
distance_w306 = distance_w307 + distance_w398;
distance_w438 = distance_w310 + distance_w336;
distance_w173 = distance_w342 + distance_w172;
distance_w75 = distance_w347 + distance_w74;
distance_w250 = distance_w348 + distance_w249;
distance_w380 = distance_w352 + distance_w274;
distance_w22 = distance_w360 + distance_w21;
distance_w362 = distance_w364 + distance_w146;
distance_w430 = distance_w371 + distance_w262;
distance_w414 = distance_w372 + distance_w442;
distance_w97 = distance_w386 + distance_w96;
distance_w413 = distance_w387 + distance_w320;
distance_w377 = distance_w420 + distance_w376;
distance_w429 = distance_w427 + distance_w428;
distance_w439 = distance_w440 + distance_w157;
distance_w443 = distance_w444 + distance_w327;
distance_w145 = distance_w447 + distance_w144;
distance_w507 = distance_w450 + distance_w16;
distance_w452 = distance_w453 + distance_w254;
distance_w322 = distance_w459 + distance_w321;
distance_w473 = distance_w468 + distance_w69;
distance_w381 = distance_w470 + distance_w99;
distance_w293 = distance_w480 + distance_w221;
distance_w510 = distance_w493 + distance_w501;
distance_w384 = distance_w503 + distance_w337;

distance_w171 = distance_w370 + sqr(temp_l77_i3_w1);
distance_w161 = distance_w27 + distance_w396;
distance_w223 = distance_w32 + distance_w4;
distance_w478 = distance_w35 + distance_w62;
distance_w279 = distance_w60 + distance_w497;
distance_w134 = distance_w75 + distance_w226;
distance_w29 = distance_w87 + distance_w266;
distance_w422 = distance_w97 + distance_w103;
distance_w477 = distance_w120 + distance_w452;
distance_w471 = distance_w145 + distance_w381;
distance_w128 = distance_w173 + distance_w391;
distance_w421 = distance_w176 + distance_w112;
distance_w228 = distance_w187 + distance_w22;
distance_w48 = distance_w191 + distance_w443;
distance_w311 = distance_w204 + distance_w438;
distance_w150 = distance_w205 + distance_w454;
distance_w245 = distance_w238 + distance_w507;
distance_w461 = distance_w250 + distance_w70;
distance_w240 = distance_w260 + distance_w362;
distance_w220 = distance_w293 + distance_w89;
distance_w403 = distance_w294 + distance_w159;
distance_w365 = distance_w306 + distance_w34;
distance_w200 = distance_w322 + distance_w436;
distance_w373 = distance_w323 + distance_w414;
distance_w107 = distance_w328 + distance_w174;
distance_w185 = distance_w377 + distance_w394;
distance_w273 = distance_w380 + distance_w19;
distance_w115 = distance_w408 + distance_w508;
distance_w51 = distance_w413 + distance_w389;
distance_w496 = distance_w415 + distance_w300;
distance_w213 = distance_w429 + distance_w151;
distance_w132 = distance_w430 + distance_w400;
distance_w106 = distance_w439 + distance_w498;
distance_w68 = distance_w473 + distance_w384;
distance_w343 = distance_w510 + distance_w83;

distance_w170 = distance_w171 + sqr(temp_l77_i4_w1);
distance_w378 = distance_w51 + distance_w311;
distance_w270 = distance_w68 + distance_w478;
distance_w122 = distance_w128 + distance_w150;
distance_w479 = distance_w161 + distance_w223;
distance_w441 = distance_w200 + distance_w471;
distance_w433 = distance_w220 + distance_w106;
distance_w86 = distance_w240 + distance_w115;
distance_w15 = distance_w245 + distance_w213;
distance_w257 = distance_w273 + distance_w107;
distance_w449 = distance_w343 + distance_w185;
distance_w332 = distance_w365 + distance_w29;
distance_w409 = distance_w373 + distance_w228;
distance_w356 = distance_w403 + distance_w422;
distance_w481 = distance_w421 + distance_w48;
distance_w224 = distance_w461 + distance_w279;
distance_w253 = distance_w477 + distance_w132;
distance_w11 = distance_w496 + distance_w134;

distance_w298 = distance_w170 + sqr(temp_l77_i5_w1);
distance_w109 = distance_w122 + distance_w81;
distance_w388 = distance_w86 + distance_w378;
distance_w432 = distance_w253 + distance_w449;
distance_w469 = distance_w332 + distance_w270;
distance_w506 = distance_w356 + distance_w224;
distance_w353 = distance_w409 + distance_w257;
distance_w339 = distance_w433 + distance_w15;
distance_w98 = distance_w441 + distance_w479;
distance_w252 = distance_w481 + distance_w11;

distance_w316 = distance_w298 + sqr(temp_l77_i6_w1);
distance_w280 = distance_w98 + distance_w432;
distance_w462 = distance_w339 + distance_w252;
distance_w235 = distance_w469 + distance_w388;
distance_w43 = distance_w506 + distance_w353;

distance_w375 = distance_w316 + sqr(temp_l77_i7_w1);
distance_w124 = distance_w43 + distance_w235;
distance_w500 = distance_w280 + distance_w462;

distance_w45 = distance_w375 + sqr(temp_l77_i8_w1);
distance_w308 = distance_w500 + distance_w124;

distance_w44 = distance_w45 + sqr(temp_l77_i9_w1);
distance_w335 = distance_w308 + distance_w109;

distance_w416 = distance_w44 + sqr(temp_l77_i10_w1);

distance_w475 = distance_w416 + sqr(temp_l77_i11_w1);

distance_w474 = distance_w475 + sqr(temp_l77_i12_w1);

distance_w8 = distance_w474 + sqr(temp_l77_i13_w1);

distance_w7 = distance_w8 + sqr(temp_l77_i14_w1);

distance_w511 = distance_w7 + sqr(temp_l77_i15_w1);

distance_w361 = distance_w511 + sqr(temp_l77_i16_w1);

distance_w315 = distance_w361 + sqr(temp_l77_i17_w1);

distance_w275 = distance_w315 + sqr(temp_l77_i18_w1);

distance_w243 = distance_w275 + sqr(temp_l77_i19_w1);

distance_w242 = distance_w243 + sqr(temp_l77_i20_w1);

distance_w140 = distance_w242 + sqr(temp_l77_i21_w1);

distance_w139 = distance_w140 + sqr(temp_l77_i22_w1);

distance_w181 = distance_w139 + sqr(temp_l77_i23_w1);

distance_w180 = distance_w181 + sqr(temp_l77_i24_w1);

distance_w195 = distance_w180 + sqr(temp_l77_i25_w1);

distance_w194 = distance_w195 + sqr(temp_l77_i26_w1);

distance_w476 = distance_w194 + sqr(temp_l77_i27_w1);

distance_w219 = distance_w476 + sqr(temp_l77_i28_w1);

distance_w55 = distance_w219 + sqr(temp_l77_i29_w1);

distance_w54 = distance_w55 + sqr(temp_l77_i30_w1);

distance_w505 = distance_w54 + sqr(temp_l77_i31_w1);

distance_w504 = distance_w505 + sqr(temp_l77_i32_w1);

distance_w319 = distance_w504 + sqr(temp_l77_i33_w1);

distance_w231 = distance_w319 + sqr(temp_l77_i34_w1);

distance_w230 = distance_w231 + sqr(temp_l77_i35_w1);

distance_w210 = distance_w230 + sqr(temp_l77_i36_w1);

distance_w209 = distance_w210 + sqr(temp_l77_i37_w1);

distance_w26 = distance_w209 + sqr(temp_l77_i38_w1);

distance_w25 = distance_w26 + sqr(temp_l77_i39_w1);

distance_w37 = distance_w25 + sqr(temp_l77_i40_w1);

distance_w486 = distance_w37 + sqr(temp_l77_i41_w1);

distance_w292 = distance_w486 + sqr(temp_l77_i42_w1);

distance_w291 = distance_w292 + sqr(temp_l77_i43_w1);

distance_w382 = distance_w291 + sqr(temp_l77_i44_w1);

distance_w494 = distance_w382 + sqr(temp_l77_i45_w1);

distance_w117 = distance_w494 + sqr(temp_l77_i46_w1);

distance_w116 = distance_w117 + sqr(temp_l77_i47_w1);

distance_w402 = distance_w116 + sqr(temp_l77_i48_w1);

distance_w350 = distance_w402 + sqr(temp_l77_i49_w1);

distance_w346 = distance_w350 + sqr(temp_l77_i50_w1);

distance_w169 = distance_w346 + sqr(temp_l77_i51_w1);

distance_w168 = distance_w169 + sqr(temp_l77_i52_w1);

distance_w163 = distance_w168 + sqr(temp_l77_i53_w1);

distance_w162 = distance_w163 + sqr(temp_l77_i54_w1);

distance_w354 = distance_w162 + sqr(temp_l77_i55_w1);

distance_w67 = distance_w354 + sqr(temp_l77_i56_w1);

distance_w66 = distance_w67 + sqr(temp_l77_i57_w1);

distance_w82 = distance_w66 + sqr(temp_l77_i58_w1);

distance_w437 = distance_w82 + sqr(temp_l77_i59_w1);

distance_w464 = distance_w437 + sqr(temp_l77_i60_w1);

distance_w212 = distance_w464 + sqr(temp_l77_i61_w1);

distance_w211 = distance_w212 + sqr(temp_l77_i62_w1);

distance_w401 = distance_w211 + sqr(temp_l77_i63_w1);

distance_w509 = distance_w401 + sqr(temp_l77_i64_w1);

distance_w277 = distance_w509 + sqr(temp_l77_i65_w1);

distance_w276 = distance_w277 + sqr(temp_l77_i66_w1);

distance_w345 = distance_w276 + sqr(temp_l77_i67_w1);

distance_w512 = distance_w345 + sqr(temp_l77_i68_w1);

distance_w359 = distance_w512 + sqr(temp_l77_i69_w1);

distance_w358 = distance_w359 + sqr(temp_l77_i70_w1);

distance_w483 = distance_w358 + sqr(temp_l77_i71_w1);

distance_w482 = distance_w483 + sqr(temp_l77_i72_w1);

distance_w248 = distance_w482 + sqr(temp_l77_i73_w1);

distance_w247 = distance_w248 + sqr(temp_l77_i74_w1);

distance_w489 = distance_w247 + sqr(temp_l77_i75_w1);

distance_w488 = distance_w489 + sqr(temp_l77_i76_w1);

distance_w502 = distance_w488 + sqr(temp_l77_i77_w1);

distance_w14 = distance_w502 + sqr(temp_l77_i78_w1);

distance_w13 = distance_w14 + sqr(temp_l77_i79_w1);

distance_w287 = distance_w13 + sqr(temp_l77_i80_w1);

distance_w259 = distance_w287 + sqr(temp_l77_i81_w1);

distance_w258 = distance_w259 + sqr(temp_l77_i82_w1);

distance_w351 = distance_w258 + sqr(temp_l77_i83_w1);

distance_w303 = distance_w351 + sqr(temp_l77_i84_w1);

distance_w302 = distance_w303 + sqr(temp_l77_i85_w1);

distance_w137 = distance_w302 + sqr(temp_l77_i86_w1);

distance_w24 = distance_w137 + sqr(temp_l77_i87_w1);

distance_w23 = distance_w24 + sqr(temp_l77_i88_w1);

distance_w418 = distance_w23 + sqr(temp_l77_i89_w1);

distance_w417 = distance_w418 + sqr(temp_l77_i90_w1);

distance_w485 = distance_w417 + sqr(temp_l77_i91_w1);

distance_w165 = distance_w485 + sqr(temp_l77_i92_w1);

distance_w164 = distance_w165 + sqr(temp_l77_i93_w1);

distance_w93 = distance_w164 + sqr(temp_l77_i94_w1);

distance_w92 = distance_w93 + sqr(temp_l77_i95_w1);

distance_w325 = distance_w92 + sqr(temp_l77_i96_w1);

distance_w424 = distance_w325 + sqr(temp_l77_i97_w1);

distance_w423 = distance_w424 + sqr(temp_l77_i98_w1);

distance_w374 = distance_w423 + sqr(temp_l77_i99_w1);

distance_w65 = distance_w374 + sqr(temp_l77_i100_w1);

distance_w64 = distance_w65 + sqr(temp_l77_i101_w1);

distance_w79 = distance_w64 + sqr(temp_l77_i102_w1);

distance_w78 = distance_w79 + sqr(temp_l77_i103_w1);

distance_w265 = distance_w78 + sqr(temp_l77_i104_w1);

distance_w288 = distance_w265 + sqr(temp_l77_i105_w1);

distance_w312 = distance_w288 + sqr(temp_l77_i106_w1);

distance_w156 = distance_w312 + sqr(temp_l77_i107_w1);

distance_w73 = distance_w156 + sqr(temp_l77_i108_w1);

distance_w72 = distance_w73 + sqr(temp_l77_i109_w1);

distance_w101 = distance_w72 + sqr(temp_l77_i110_w1);

distance_w100 = distance_w101 + sqr(temp_l77_i111_w1);

distance_w434 = distance_w100 + sqr(temp_l77_i112_w1);

distance_w466 = distance_w434 + sqr(temp_l77_i113_w1);

distance_w264 = distance_w466 + sqr(temp_l77_i114_w1);

distance_w263 = distance_w264 + sqr(temp_l77_i115_w1);

distance_w411 = distance_w263 + sqr(temp_l77_i116_w1);

distance_w410 = distance_w411 + sqr(temp_l77_i117_w1);

distance_w344 = distance_w410 + sqr(temp_l77_i118_w1);

distance_w47 = distance_w344 + sqr(temp_l77_i119_w1);

distance_w46 = distance_w47 + sqr(temp_l77_i120_w1);

distance_w91 = distance_w46 + sqr(temp_l77_i121_w1);

distance_w390 = distance_w91 + sqr(temp_l77_i122_w1);

distance_w330 = distance_w390 + sqr(temp_l77_i123_w1);

distance_w59 = distance_w330 + sqr(temp_l77_i124_w1);

distance_w58 = distance_w59 + sqr(temp_l77_i125_w1);

distance_w492 = distance_w58 + sqr(temp_l77_i126_w1);

distance_w57 = distance_w492 + sqr(temp_l77_i127_w1);

distance_w56 = distance_w57 + sqr(temp_l77_i128_w1);

distance_w141 = distance_w56 + sqr(temp_l77_i129_w1);

distance_w95 = distance_w141 + sqr(temp_l77_i130_w1);

distance_w94 = distance_w95 + sqr(temp_l77_i131_w1);

distance_w102 = distance_w94 + sqr(temp_l77_i132_w1);

distance_w458 = distance_w102 + sqr(temp_l77_i133_w1);

distance_w233 = distance_w458 + sqr(temp_l77_i134_w1);

distance_w232 = distance_w233 + sqr(temp_l77_i135_w1);

distance_w53 = distance_w232 + sqr(temp_l77_i136_w1);

distance_w52 = distance_w53 + sqr(temp_l77_i137_w1);

distance_w355 = distance_w52 + sqr(temp_l77_i138_w1);

distance_w237 = distance_w355 + sqr(temp_l77_i139_w1);

distance_w236 = distance_w237 + sqr(temp_l77_i140_w1);

distance_w368 = distance_w236 + sqr(temp_l77_i141_w1);

distance_w367 = distance_w368 + sqr(temp_l77_i142_w1);

distance_w446 = distance_w367 + sqr(temp_l77_i143_w1);

distance_w341 = distance_w446 + sqr(temp_l77_i144_w1);

distance_w340 = distance_w341 + sqr(temp_l77_i145_w1);

distance_w2 = distance_w340 + sqr(temp_l77_i146_w1);

distance_w1 = distance_w2 + sqr(temp_l77_i147_w1);

distance_w167 = distance_w1 + sqr(temp_l77_i148_w1);

distance_w166 = distance_w167 + sqr(temp_l77_i149_w1);

distance_w119 = distance_w166 + sqr(temp_l77_i150_w1);

distance_w118 = distance_w119 + sqr(temp_l77_i151_w1);

distance_w155 = distance_w118 + sqr(temp_l77_i152_w1);

distance_w419 = distance_w155 + sqr(temp_l77_i153_w1);

distance_w197 = distance_w419 + sqr(temp_l77_i154_w1);

distance_w196 = distance_w197 + sqr(temp_l77_i155_w1);

distance_w431 = distance_w196 + sqr(temp_l77_i156_w1);

distance_w227 = distance_w431 + sqr(temp_l77_i157_w1);

distance_w39 = distance_w227 + sqr(temp_l77_i158_w1);

distance_w38 = distance_w39 + sqr(temp_l77_i159_w1);

distance_w143 = distance_w38 + sqr(temp_l77_i160_w1);

distance_w142 = distance_w143 + sqr(temp_l77_i161_w1);

distance_w193 = distance_w142 + sqr(temp_l77_i162_w1);

distance_w305 = distance_w193 + sqr(temp_l77_i163_w1);

distance_w304 = distance_w305 + sqr(temp_l77_i164_w1);

distance_w333 = distance_w304 + sqr(temp_l77_i165_w1);

distance_w363 = distance_w333 + sqr(temp_l77_i166_w1);

distance_w357 = distance_w363 + sqr(temp_l77_i167_w1);

distance_w268 = distance_w357 + sqr(temp_l77_i168_w1);

distance_w267 = distance_w268 + sqr(temp_l77_i169_w1);

distance_w136 = distance_w267 + sqr(temp_l77_i170_w1);

distance_w135 = distance_w136 + sqr(temp_l77_i171_w1);

distance_w405 = distance_w135 + sqr(temp_l77_i172_w1);

distance_w77 = distance_w405 + sqr(temp_l77_i173_w1);

distance_w76 = distance_w77 + sqr(temp_l77_i174_w1);

distance_w179 = distance_w76 + sqr(temp_l77_i175_w1);

distance_w178 = distance_w179 + sqr(temp_l77_i176_w1);

distance_w451 = distance_w178 + sqr(temp_l77_i177_w1);

distance_w269 = distance_w451 + sqr(temp_l77_i178_w1);

distance_w190 = distance_w269 + sqr(temp_l77_i179_w1);

distance_w189 = distance_w190 + sqr(temp_l77_i180_w1);

distance_w456 = distance_w189 + sqr(temp_l77_i181_w1);

distance_w448 = distance_w456 + sqr(temp_l77_i182_w1);

distance_w285 = distance_w448 + sqr(temp_l77_i183_w1);

distance_w284 = distance_w285 + sqr(temp_l77_i184_w1);

distance_w314 = distance_w284 + sqr(temp_l77_i185_w1);

distance_w313 = distance_w314 + sqr(temp_l77_i186_w1);

distance_w183 = distance_w313 + sqr(temp_l77_i187_w1);

distance_w182 = distance_w183 + sqr(temp_l77_i188_w1);

distance_w255 = distance_w182 + sqr(temp_l77_i189_w1);

distance_w286 = distance_w255 + sqr(temp_l77_i190_w1);

distance_w393 = distance_w286 + sqr(temp_l77_i191_w1);

distance_w490 = distance_w393 + sqr(temp_l77_i192_w1);

distance_w154 = distance_w490 + sqr(temp_l77_i193_w1);

distance_w153 = distance_w154 + sqr(temp_l77_i194_w1);

distance_w366 = distance_w153 + sqr(temp_l77_i195_w1);

distance_w369 = distance_w366 + sqr(temp_l77_i196_w1);

distance_w282 = distance_w369 + sqr(temp_l77_i197_w1);

distance_w281 = distance_w282 + sqr(temp_l77_i198_w1);

distance_w425 = distance_w281 + sqr(temp_l77_i199_w1);

distance_w385 = distance_w425 + sqr(temp_l77_i200_w1);

distance_w130 = distance_w385 + sqr(temp_l77_i201_w1);

distance_w129 = distance_w130 + sqr(temp_l77_i202_w1);

distance_w217 = distance_w129 + sqr(temp_l77_i203_w1);

distance_w216 = distance_w217 + sqr(temp_l77_i204_w1);

distance_w472 = distance_w216 + sqr(temp_l77_i205_w1);

distance_w329 = distance_w472 + sqr(temp_l77_i206_w1);

distance_w244 = distance_w329 + sqr(temp_l77_i207_w1);

distance_w41 = distance_w244 + sqr(temp_l77_i208_w1);

distance_w40 = distance_w41 + sqr(temp_l77_i209_w1);

distance_w334 = distance_w40 + sqr(temp_l77_i210_w1);

distance_w148 = distance_w334 + sqr(temp_l77_i211_w1);

distance_w147 = distance_w148 + sqr(temp_l77_i212_w1);

distance_w208 = distance_w147 + sqr(temp_l77_i213_w1);

distance_w207 = distance_w208 + sqr(temp_l77_i214_w1);

distance_w10 = distance_w207 + sqr(temp_l77_i215_w1);

distance_w9 = distance_w10 + sqr(temp_l77_i216_w1);

distance_w465 = distance_w9 + sqr(temp_l77_i217_w1);

distance_w331 = distance_w465 + sqr(temp_l77_i218_w1);

distance_w318 = distance_w331 + sqr(temp_l77_i219_w1);

distance_w317 = distance_w318 + sqr(temp_l77_i220_w1);

distance_w138 = distance_w317 + sqr(temp_l77_i221_w1);

distance_w6 = distance_w138 + sqr(temp_l77_i222_w1);

distance_w5 = distance_w6 + sqr(temp_l77_i223_w1);

distance_w349 = distance_w5 + sqr(temp_l77_i224_w1);

distance_w290 = distance_w349 + sqr(temp_l77_i225_w1);

distance_w289 = distance_w290 + sqr(temp_l77_i226_w1);

distance_w499 = distance_w289 + sqr(temp_l77_i227_w1);

distance_w272 = distance_w499 + sqr(temp_l77_i228_w1);

distance_w271 = distance_w272 + sqr(temp_l77_i229_w1);

distance_w383 = distance_w271 + sqr(temp_l77_i230_w1);

distance_w126 = distance_w383 + sqr(temp_l77_i231_w1);

distance_w18 = distance_w126 + sqr(temp_l77_i232_w1);

distance_w17 = distance_w18 + sqr(temp_l77_i233_w1);

distance_w326 = distance_w17 + sqr(temp_l77_i234_w1);

distance_w397 = distance_w326 + sqr(temp_l77_i235_w1);

distance_w426 = distance_w397 + sqr(temp_l77_i236_w1);

distance_w460 = distance_w426 + distance_w335;

distance_w80 = distance_w460 + sqr(temp_l77_i512_w1);

distance_array[i] = sqrtf(distance_w80);

}
}

void epilogue(char knownClasses[8], double distance_array_0[8], char *out) {
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
float dbest_w12;
float dbest_w13;
float dbest_w14;
float dbest_w15;
float dbest_w16;
float dbest_w17;
double dbest_w18;
double dbest_w19;
double dbest_w2;
float dbest_w20;
double dbest_w21;
double dbest_w22;
float dbest_w23;
double dbest_w24;
float dbest_w25;
float dbest_w26;
float dbest_w27;
float dbest_w28;
double dbest_w29;
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
// Initialization done
max_tmp_w8 = 0;
max_tmp_w21 = 0;
max_tmp_w20 = 0;
max_tmp_w13 = 0;
max_tmp_w5 = 0;
max_tmp_w3 = 0;
max_tmp_w23 = 0;
max_tmp_w6 = 0;
BestPointsDistances[0] = MAXDISTANCE;
BestPointsDistances[1] = MAXDISTANCE;
BestPointsDistances[2] = MAXDISTANCE;
BestPointsClasses[0] = NUM_CLASSES;
BestPointsClasses[1] = NUM_CLASSES;
BestPointsClasses[2] = NUM_CLASSES;

dbest_w32 = BestPointsDistances[0];
dbest_w20 = BestPointsDistances[1];
dbest_w4 = BestPointsDistances[2];

operationOutput_w35 = dbest_w32 > max_tmp_w23;

max_w5 = operationOutput_w35 ? dbest_w32:0;
index_w19 = operationOutput_w35 ? 0:0;

max_tmp_w2 = max_w5;

operationOutput_w20 = dbest_w20 > max_tmp_w2;

max_w14 = operationOutput_w20 ? dbest_w20:max_w5;
index_w18 = operationOutput_w20 ? 1:index_w19;

max_tmp_w15 = max_w14;

operationOutput_w32 = dbest_w4 > max_tmp_w15;

index_w7 = operationOutput_w32 ? 2:index_w18;
max_w23 = operationOutput_w32 ? dbest_w4:max_w14;

operationOutput_w4 = distance_array_0[0] < max_w23;

cbest_w1 = BestPointsClasses[index_w7];
dbest_w19 = BestPointsDistances[index_w7];

muxOutput_w7 = operationOutput_w4 ? distance_array_0[0]:dbest_w19;
muxOutput_w2 = operationOutput_w4 ? knownClasses[0]:cbest_w1;

BestPointsClasses[index_w7] = muxOutput_w2;
BestPointsDistances[index_w7] = muxOutput_w7;


dbest_w31 = BestPointsDistances[0];
dbest_w16 = BestPointsDistances[1];
dbest_w27 = BestPointsDistances[2];

operationOutput_w23 = dbest_w31 > max_tmp_w8;

max_w12 = operationOutput_w23 ? dbest_w31:0;
index_w9 = operationOutput_w23 ? 0:0;

max_tmp_w12 = max_w12;

operationOutput_w18 = dbest_w16 > max_tmp_w12;

max_w11 = operationOutput_w18 ? dbest_w16:max_w12;
index_w13 = operationOutput_w18 ? 1:index_w9;

max_tmp_w24 = max_w11;

operationOutput_w34 = dbest_w27 > max_tmp_w24;

max_w21 = operationOutput_w34 ? dbest_w27:max_w11;
index_w8 = operationOutput_w34 ? 2:index_w13;

operationOutput_w14 = distance_array_0[1] < max_w21;

cbest_w3 = BestPointsClasses[index_w8];
dbest_w18 = BestPointsDistances[index_w8];

muxOutput_w14 = operationOutput_w14 ? knownClasses[1]:cbest_w3;
muxOutput_w6 = operationOutput_w14 ? distance_array_0[1]:dbest_w18;

BestPointsDistances[index_w8] = muxOutput_w6;
BestPointsClasses[index_w8] = muxOutput_w14;


dbest_w30 = BestPointsDistances[0];
dbest_w8 = BestPointsDistances[1];
dbest_w28 = BestPointsDistances[2];

operationOutput_w33 = dbest_w30 > max_tmp_w20;

max_w18 = operationOutput_w33 ? dbest_w30:0;
index_w12 = operationOutput_w33 ? 0:0;

max_tmp_w17 = max_w18;

operationOutput_w26 = dbest_w8 > max_tmp_w17;

max_w17 = operationOutput_w26 ? dbest_w8:max_w18;
index_w4 = operationOutput_w26 ? 1:index_w12;

max_tmp_w16 = max_w17;

operationOutput_w16 = dbest_w28 > max_tmp_w16;

max_w8 = operationOutput_w16 ? dbest_w28:max_w17;
index_w3 = operationOutput_w16 ? 2:index_w4;

operationOutput_w12 = distance_array_0[2] < max_w8;

cbest_w5 = BestPointsClasses[index_w3];
dbest_w21 = BestPointsDistances[index_w3];

muxOutput_w8 = operationOutput_w12 ? knownClasses[2]:cbest_w5;
muxOutput_w10 = operationOutput_w12 ? distance_array_0[2]:dbest_w21;

BestPointsDistances[index_w3] = muxOutput_w10;
BestPointsClasses[index_w3] = muxOutput_w8;


dbest_w15 = BestPointsDistances[0];
dbest_w9 = BestPointsDistances[1];
dbest_w26 = BestPointsDistances[2];

operationOutput_w11 = dbest_w15 > max_tmp_w21;

max_w7 = operationOutput_w11 ? dbest_w15:0;
index_w6 = operationOutput_w11 ? 0:0;

max_tmp_w1 = max_w7;

operationOutput_w7 = dbest_w9 > max_tmp_w1;

max_w16 = operationOutput_w7 ? dbest_w9:max_w7;
index_w23 = operationOutput_w7 ? 1:index_w6;

max_tmp_w14 = max_w16;

operationOutput_w28 = dbest_w26 > max_tmp_w14;

index_w5 = operationOutput_w28 ? 2:index_w23;
max_w1 = operationOutput_w28 ? dbest_w26:max_w16;

operationOutput_w3 = distance_array_0[3] < max_w1;

cbest_w2 = BestPointsClasses[index_w5];
dbest_w11 = BestPointsDistances[index_w5];

muxOutput_w4 = operationOutput_w3 ? knownClasses[3]:cbest_w2;
muxOutput_w3 = operationOutput_w3 ? distance_array_0[3]:dbest_w11;

BestPointsClasses[index_w5] = muxOutput_w4;
BestPointsDistances[index_w5] = muxOutput_w3;


dbest_w13 = BestPointsDistances[0];
dbest_w3 = BestPointsDistances[1];
dbest_w7 = BestPointsDistances[2];

operationOutput_w9 = dbest_w13 > max_tmp_w5;

index_w15 = operationOutput_w9 ? 0:0;
max_w15 = operationOutput_w9 ? dbest_w13:0;

max_tmp_w18 = max_w15;

operationOutput_w29 = dbest_w3 > max_tmp_w18;

index_w14 = operationOutput_w29 ? 1:index_w15;
max_w4 = operationOutput_w29 ? dbest_w3:max_w15;

max_tmp_w7 = max_w4;

operationOutput_w6 = dbest_w7 > max_tmp_w7;

max_w3 = operationOutput_w6 ? dbest_w7:max_w4;
index_w10 = operationOutput_w6 ? 2:index_w14;

operationOutput_w24 = distance_array_0[4] < max_w3;

cbest_w8 = BestPointsClasses[index_w10];
dbest_w22 = BestPointsDistances[index_w10];

muxOutput_w15 = operationOutput_w24 ? knownClasses[4]:cbest_w8;
muxOutput_w11 = operationOutput_w24 ? distance_array_0[4]:dbest_w22;

BestPointsClasses[index_w10] = muxOutput_w15;
BestPointsDistances[index_w10] = muxOutput_w11;


dbest_w25 = BestPointsDistances[0];
dbest_w10 = BestPointsDistances[1];
dbest_w12 = BestPointsDistances[2];

operationOutput_w27 = dbest_w25 > max_tmp_w3;

max_w19 = operationOutput_w27 ? dbest_w25:0;
index_w24 = operationOutput_w27 ? 0:0;

max_tmp_w19 = max_w19;

operationOutput_w36 = dbest_w10 > max_tmp_w19;

max_w10 = operationOutput_w36 ? dbest_w10:max_w19;
index_w22 = operationOutput_w36 ? 1:index_w24;

max_tmp_w4 = max_w10;

operationOutput_w8 = dbest_w12 > max_tmp_w4;

max_w13 = operationOutput_w8 ? dbest_w12:max_w10;
index_w2 = operationOutput_w8 ? 2:index_w22;

operationOutput_w19 = distance_array_0[5] < max_w13;

cbest_w4 = BestPointsClasses[index_w2];
dbest_w29 = BestPointsDistances[index_w2];

muxOutput_w9 = operationOutput_w19 ? knownClasses[5]:cbest_w4;
muxOutput_w5 = operationOutput_w19 ? distance_array_0[5]:dbest_w29;

BestPointsDistances[index_w2] = muxOutput_w5;
BestPointsClasses[index_w2] = muxOutput_w9;


dbest_w6 = BestPointsDistances[0];
dbest_w17 = BestPointsDistances[1];
dbest_w23 = BestPointsDistances[2];

operationOutput_w5 = dbest_w6 > max_tmp_w6;

index_w17 = operationOutput_w5 ? 0:0;
max_w2 = operationOutput_w5 ? dbest_w6:0;

max_tmp_w10 = max_w2;

operationOutput_w13 = dbest_w17 > max_tmp_w10;

index_w16 = operationOutput_w13 ? 1:index_w17;
max_w9 = operationOutput_w13 ? dbest_w17:max_w2;

max_tmp_w22 = max_w9;

operationOutput_w37 = dbest_w23 > max_tmp_w22;

index_w11 = operationOutput_w37 ? 2:index_w16;
max_w24 = operationOutput_w37 ? dbest_w23:max_w9;

operationOutput_w25 = distance_array_0[6] < max_w24;

cbest_w7 = BestPointsClasses[index_w11];
dbest_w24 = BestPointsDistances[index_w11];

muxOutput_w12 = operationOutput_w25 ? distance_array_0[6]:dbest_w24;
muxOutput_w13 = operationOutput_w25 ? knownClasses[6]:cbest_w7;

BestPointsDistances[index_w11] = muxOutput_w12;
BestPointsClasses[index_w11] = muxOutput_w13;


dbest_w14 = BestPointsDistances[0];
dbest_w5 = BestPointsDistances[1];
dbest_w1 = BestPointsDistances[2];

operationOutput_w10 = dbest_w14 > max_tmp_w13;

index_w21 = operationOutput_w10 ? 0:0;
max_w6 = operationOutput_w10 ? dbest_w14:0;

max_tmp_w9 = max_w6;

operationOutput_w21 = dbest_w5 > max_tmp_w9;

max_w20 = operationOutput_w21 ? dbest_w5:max_w6;
index_w20 = operationOutput_w21 ? 1:index_w21;

max_tmp_w11 = max_w20;

operationOutput_w22 = dbest_w1 > max_tmp_w11;

index_w1 = operationOutput_w22 ? 2:index_w20;
max_w22 = operationOutput_w22 ? dbest_w1:max_w20;

operationOutput_w1 = distance_array_0[7] < max_w22;

cbest_w6 = BestPointsClasses[index_w1];
dbest_w2 = BestPointsDistances[index_w1];

muxOutput_w16 = operationOutput_w1 ? knownClasses[7]:cbest_w6;
muxOutput_w1 = operationOutput_w1 ? distance_array_0[7]:dbest_w2;

BestPointsClasses[index_w1] = muxOutput_w16;
BestPointsDistances[index_w1] = muxOutput_w1;


c1_w1 = BestPointsClasses[0];
c2_w1 = BestPointsClasses[1];
c3_w1 = BestPointsClasses[2];
d1_w1 = BestPointsDistances[0];
d2_w1 = BestPointsDistances[1];
d3_w1 = BestPointsDistances[2];

operationOutput_w2 = c1_w1 == c2_w1;
mindist_w2 = d1_w1;
operationOutput_w17 = c1_w1 == c3_w1;
operationOutput_w38 = c2_w1 == c3_w1;

operationOutput_w15 = mindist_w2 > d2_w1;
operationOutput_w31 = mindist_w2 > d2_w1;

mindist_w1 = operationOutput_w15 ? d2_w1:d1_w1;
classID_w3 = operationOutput_w31 ? c2_w1:c1_w1;

operationOutput_w30 = mindist_w1 > d3_w1;

classID_w4 = operationOutput_w30 ? c3_w1:classID_w3;

classID_w2 = operationOutput_w38 ? c2_w1:classID_w4;

classID_w1 = operationOutput_w17 ? c1_w1:classID_w2;

*out = operationOutput_w2 ? c1_w1:classID_w1;

}

void knn_8p512f_1parallel_saveEnergy(double xFeatures[512], char knownClasses[8], double knownFeatures_0[8][512], char *out) {
// Step 2: Initialize local variables
double distance_array_0[8];
#pragma HLS ARRAY_PARTITION variable=xFeatures cyclic factor=512 dim=1
#pragma HLS ARRAY_PARTITION variable=knownFeatures_0 cyclic factor=2 dim=2
// Initialization done
#pragma HLS dataflow

parallel_0(knownFeatures_0,xFeatures,distance_array_0);


epilogue(knownClasses,distance_array_0,out);

}
