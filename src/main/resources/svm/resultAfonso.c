/Func created from graph [First implementation]// Step by step description of process
// Step 1: create function header

void result( float *sum, float test_vector[18], float sup_vectors[18][1248], float sv_coeff[1248]){
#pragma HLS array_partition variable=test_vector cyclic factor=9 dim= 1
#pragma HLS array_partition variable=sup_vectors cyclic factor=9 dim= 1
#pragma HLS array_partition variable=sup_vectors cyclic factor=2 dim= 2
#pragma HLS array_partition variable=sv_coeff cyclic factor=1 dim= 1
// Step 2: Initialize local variables
float temp_l111_1_i1;
float sv_coeff_0_b0;
float sv_coeff_0_b0_w0;
float partial_2_0_a10_w1;
float partial_2_1_a10;
float partial_2_1_a10_w2;
float norma;
float norma_w3;
float diff_w4;
float sup_vectors_17_0_b0_w5;
float test_vector_17_b1;
float diff_w6;
float sup_vectors_2_0_b0_w7;
float test_vector_2_b1;
float sup_vectors_16_0_b0_w8;
float test_vector_16_b1;
float diff_w9;
float diff_w10;
float sup_vectors_15_0_b0_w11;
float test_vector_15_b1;
float diff_w12;
float partial_3_1_a10_w13;
float partial_3_0_a10;
float partial_3_0_a10_w14;
float sup_vectors_14_0_b0_w15;
float test_vector_14_b1;
float diff_w16;
float sup_vectors_13_0_b0_w17;
float test_vector_13_b1;
float diff_w18;
float sup_vectors_12_0_b0_w19;
float test_vector_12_b1;
float diff_w20;
float sup_vectors_1_0_b0_w21;
float test_vector_1_b1;
float sup_vectors_11_0_b0_w22;
float test_vector_11_b1;
float diff_w23;
float diff_w24;
float sup_vectors_10_0_b0_w25;
float test_vector_10_b1;
float diff_w26;
float sup_vectors_9_0_b0_w27;
float test_vector_9_b1;
float diff_w28;
float sup_vectors_8_0_b0_w29;
float test_vector_8_b1;
float diff_w30;
float sup_vectors_7_0_b0_w31;
float test_vector_7_b1;
float diff_w32;
float sup_vectors_0_0_b0_w33;
float test_vector_0_b1;
float sup_vectors_6_0_b0_w34;
float test_vector_6_b1;
float diff_w35;
float sup_vectors_5_0_b0_w36;
float test_vector_5_b1;
float diff_w37;
float sup_vectors_4_0_b0_w38;
float test_vector_4_b1;
float diff_w39;
float sup_vectors_3_0_b0_w40;
float test_vector_3_b1;
float diff_w41;
float diff_w42;
float diff;
float diff_w43;
float diff_w44;
float diff_w45;
float diff_w46;
float diff_w47;
float diff_w48;
float diff_w49;
float diff_w50;
float diff_w51;
float diff_w52;
float diff_w53;
float diff_w54;
float diff_w55;
float diff_w56;
float diff_w57;
float diff_w58;
float diff_w59;
float partial_1_0_w60;
float partial_1_1;
float partial_1_1_w61;
float partial_1_2_w62;
float partial_1_3;
float partial_1_3_w63;
float partial_1_4_w64;
float partial_1_5;
float partial_1_5_w65;
float partial_1_6_w66;
float partial_1_7;
float partial_1_7_w67;
float partial_2_0_w68;
float partial_2_1;
float partial_2_1_w69;
float partial_2_2_w70;
float partial_2_3;
float partial_2_3_w71;
float partial_3_0_w72;
float partial_3_1;
float partial_3_1_w73;
float partial_4_0_w74;
float partial_4_1;
float partial_4_1_w75;
float final_partial_v_w76;
float norma_w77;
float sup_vectors_17_0_b0_a10_w78;
float sup_vectors_16_0_b0_a10_w79;
float sup_vectors_15_0_b0_a10_w80;
float sup_vectors_14_0_b0_a10_w81;
float sup_vectors_13_0_b0_a10_w82;
float sup_vectors_12_0_b0_a10_w83;
float sup_vectors_11_0_b0_a10_w84;
float sup_vectors_10_0_b0_a10_w85;
float sup_vectors_9_0_b0_a10_w86;
float sup_vectors_8_0_b0_a10_w87;
float sup_vectors_7_0_b0_a10_w88;
float sup_vectors_6_0_b0_a10_w89;
float sup_vectors_5_0_b0_a10_w90;
float sup_vectors_4_0_b0_a10_w91;
float sup_vectors_3_0_b0_a10_w92;
float sup_vectors_2_0_b0_a10_w93;
float sup_vectors_1_0_b0_a10_w94;
float sup_vectors_0_0_b0_a10_w95;
float partial_2_3_a10_w96;
float partial_2_2_a10;
float partial_2_2_a10_w97;
float norma_a10;
float norma_a10_w98;
float diff_a10_w99;
float diff_a10_w100;
float diff_a10_w101;
float diff_a10_w102;
float diff_a10_w103;
float diff_a10_w104;
float diff_a10_w105;
float diff_a10_w106;
float diff_a10_w107;
float diff_a10_w108;
float diff_a10_w109;
float diff_a10_w110;
float diff_a10_w111;
float diff_a10_w112;
float diff_a10_w113;
float diff_a10_w114;
float diff_a10_w115;
float final_partial_v_a10_w116;
float norma_a10_w117;
float diff_a10_w118;
float temp_l111_1_i1_a10;
float sv_coeff_0_b0_a10;
float sv_coeff_0_b0_a10_w119;
float diff_a10_w120;
float diff_a10;
float diff_a10_w121;
float diff_a10_w122;
float diff_a10_w123;
float diff_a10_w124;
float diff_a10_w125;
float diff_a10_w126;
float diff_a10_w127;
float diff_a10_w128;
float diff_a10_w129;
float diff_a10_w130;
float diff_a10_w131;
float diff_a10_w132;
float diff_a10_w133;
float diff_a10_w134;
float diff_a10_w135;
float diff_a10_w136;
float diff_a10_w137;
float partial_4_0_a10_w138;
float partial_4_1_a10;
float partial_4_1_a10_w139;
float partial_1_0_a10_w140;
float partial_1_1_a10;
float partial_1_1_a10_w141;
float partial_1_2_a10_w142;
float partial_1_3_a10;
float partial_1_3_a10_w143;
float partial_1_4_a10_w144;
float partial_1_5_a10;
float partial_1_5_a10_w145;
float partial_1_6_a10_w146;
float partial_1_7_a10;
float partial_1_7_a10_w147;
float temp_l111_2_i2_a10;
float temp_l111_2_i2;
float final_partial_v_w148;
// Initialization done




// Step 3: write code by level
// Currently we write attributions and simple operations between two variables


test_vector_17_b1=test_vector[17];
test_vector_16_b1=test_vector[16];
test_vector_15_b1=test_vector[15];
test_vector_14_b1=test_vector[14];
test_vector_13_b1=test_vector[13];
test_vector_12_b1=test_vector[12];
test_vector_11_b1=test_vector[11];
test_vector_10_b1=test_vector[10];
test_vector_9_b1=test_vector[9];
test_vector_8_b1=test_vector[8];
test_vector_7_b1=test_vector[7];
test_vector_6_b1=test_vector[6];
test_vector_5_b1=test_vector[5];
test_vector_4_b1=test_vector[4];
test_vector_3_b1=test_vector[3];
test_vector_2_b1=test_vector[2];
test_vector_1_b1=test_vector[1];
test_vector_0_b1=test_vector[0];



 // Creating Loop
// starting Loop
for( int i =0; i < 1248; i=i+2){
#pragma HLS pipeline


// Currently we write attributions and simple operations between two variables and unrolling with directive
// Start of Level 0:
// Start of Level 1:
// Start of Level 2:
sup_vectors_17_0_b0_w5=sup_vectors[17][i];
sup_vectors_16_0_b0_w8=sup_vectors[16][i];
sup_vectors_15_0_b0_w11=sup_vectors[15][i];
sup_vectors_14_0_b0_w15=sup_vectors[14][i];
sup_vectors_13_0_b0_w17=sup_vectors[13][i];
sup_vectors_12_0_b0_w19=sup_vectors[12][i];
sup_vectors_11_0_b0_w22=sup_vectors[11][i];
sup_vectors_10_0_b0_w25=sup_vectors[10][i];
sup_vectors_9_0_b0_w27=sup_vectors[9][i];
sup_vectors_8_0_b0_w29=sup_vectors[8][i];
sup_vectors_7_0_b0_w31=sup_vectors[7][i];
sup_vectors_6_0_b0_w34=sup_vectors[6][i];
sup_vectors_5_0_b0_w36=sup_vectors[5][i];
sup_vectors_4_0_b0_w38=sup_vectors[4][i];
sup_vectors_3_0_b0_w40=sup_vectors[3][i];
sup_vectors_2_0_b0_w7=sup_vectors[2][i];
sup_vectors_1_0_b0_w21=sup_vectors[1][i];
norma_w77=0;
sup_vectors_0_0_b0_w33=sup_vectors[0][i];
sv_coeff_0_b0_w0=sv_coeff[i];
sup_vectors_17_0_b0_a10_w78=sup_vectors[17][i+1];
sup_vectors_16_0_b0_a10_w79=sup_vectors[16][i+1];
sup_vectors_15_0_b0_a10_w80=sup_vectors[15][i+1];
sup_vectors_14_0_b0_a10_w81=sup_vectors[14][i+1];
sup_vectors_13_0_b0_a10_w82=sup_vectors[13][i+1];
sup_vectors_12_0_b0_a10_w83=sup_vectors[12][i+1];
sup_vectors_11_0_b0_a10_w84=sup_vectors[11][i+1];
sup_vectors_10_0_b0_a10_w85=sup_vectors[10][i+1];
sup_vectors_9_0_b0_a10_w86=sup_vectors[9][i+1];
sup_vectors_8_0_b0_a10_w87=sup_vectors[8][i+1];
sup_vectors_7_0_b0_a10_w88=sup_vectors[7][i+1];
sup_vectors_6_0_b0_a10_w89=sup_vectors[6][i+1];
sup_vectors_5_0_b0_a10_w90=sup_vectors[5][i+1];
sup_vectors_4_0_b0_a10_w91=sup_vectors[4][i+1];
sup_vectors_3_0_b0_a10_w92=sup_vectors[3][i+1];
sup_vectors_2_0_b0_a10_w93=sup_vectors[2][i+1];
sup_vectors_1_0_b0_a10_w94=sup_vectors[1][i+1];
norma_a10_w117=0;
sup_vectors_0_0_b0_a10_w95=sup_vectors[0][i+1];
sv_coeff_0_b0_a10_w119=sv_coeff[i+1];
// Start of Level 3:
diff_w4=test_vector_17_b1 - sup_vectors_17_0_b0_w5;
diff_w6=test_vector_16_b1 - sup_vectors_16_0_b0_w8;
diff_w9=test_vector_15_b1 - sup_vectors_15_0_b0_w11;
diff_w12=test_vector_14_b1 - sup_vectors_14_0_b0_w15;
diff_w16=test_vector_13_b1 - sup_vectors_13_0_b0_w17;
diff_w18=test_vector_12_b1 - sup_vectors_12_0_b0_w19;
diff_w20=test_vector_11_b1 - sup_vectors_11_0_b0_w22;
diff_w23=test_vector_10_b1 - sup_vectors_10_0_b0_w25;
diff_w26=test_vector_9_b1 - sup_vectors_9_0_b0_w27;
diff_w28=test_vector_8_b1 - sup_vectors_8_0_b0_w29;
diff_w30=test_vector_7_b1 - sup_vectors_7_0_b0_w31;
diff_w32=test_vector_6_b1 - sup_vectors_6_0_b0_w34;
diff_w35=test_vector_5_b1 - sup_vectors_5_0_b0_w36;
diff_w37=test_vector_4_b1 - sup_vectors_4_0_b0_w38;
diff_w39=test_vector_3_b1 - sup_vectors_3_0_b0_w40;
diff_w41=test_vector_2_b1 - sup_vectors_2_0_b0_w7;
diff_w10=test_vector_1_b1 - sup_vectors_1_0_b0_w21;
diff_w24=test_vector_0_b1 - sup_vectors_0_0_b0_w33;
diff_a10_w99=test_vector_17_b1 - sup_vectors_17_0_b0_a10_w78;
diff_a10_w100=test_vector_16_b1 - sup_vectors_16_0_b0_a10_w79;
diff_a10_w101=test_vector_15_b1 - sup_vectors_15_0_b0_a10_w80;
diff_a10_w102=test_vector_14_b1 - sup_vectors_14_0_b0_a10_w81;
diff_a10_w103=test_vector_13_b1 - sup_vectors_13_0_b0_a10_w82;
diff_a10_w104=test_vector_12_b1 - sup_vectors_12_0_b0_a10_w83;
diff_a10_w105=test_vector_11_b1 - sup_vectors_11_0_b0_a10_w84;
diff_a10_w106=test_vector_10_b1 - sup_vectors_10_0_b0_a10_w85;
diff_a10_w107=test_vector_9_b1 - sup_vectors_9_0_b0_a10_w86;
diff_a10_w108=test_vector_8_b1 - sup_vectors_8_0_b0_a10_w87;
diff_a10_w109=test_vector_7_b1 - sup_vectors_7_0_b0_a10_w88;
diff_a10_w110=test_vector_6_b1 - sup_vectors_6_0_b0_a10_w89;
diff_a10_w111=test_vector_5_b1 - sup_vectors_5_0_b0_a10_w90;
diff_a10_w112=test_vector_4_b1 - sup_vectors_4_0_b0_a10_w91;
diff_a10_w113=test_vector_3_b1 - sup_vectors_3_0_b0_a10_w92;
diff_a10_w114=test_vector_2_b1 - sup_vectors_2_0_b0_a10_w93;
diff_a10_w115=test_vector_1_b1 - sup_vectors_1_0_b0_a10_w94;
diff_a10_w118=test_vector_0_b1 - sup_vectors_0_0_b0_a10_w95;
// Start of Level 4:
diff_w42=diff_w4*diff_w4;
diff_w43=diff_w6*diff_w6;
diff_w44=diff_w9*diff_w9;
diff_w45=diff_w12*diff_w12;
diff_w46=diff_w16*diff_w16;
diff_w47=diff_w18*diff_w18;
diff_w48=diff_w20*diff_w20;
diff_w49=diff_w23*diff_w23;
diff_w50=diff_w26*diff_w26;
diff_w51=diff_w28*diff_w28;
diff_w52=diff_w30*diff_w30;
diff_w53=diff_w32*diff_w32;
diff_w54=diff_w35*diff_w35;
diff_w55=diff_w37*diff_w37;
diff_w56=diff_w39*diff_w39;
diff_w57=diff_w41*diff_w41;
diff_w58=diff_w10*diff_w10;
diff_w59=diff_w24*diff_w24;
diff_a10_w120=diff_a10_w99*diff_a10_w99;
diff_a10_w121=diff_a10_w100*diff_a10_w100;
diff_a10_w122=diff_a10_w101*diff_a10_w101;
diff_a10_w123=diff_a10_w102*diff_a10_w102;
diff_a10_w124=diff_a10_w103*diff_a10_w103;
diff_a10_w125=diff_a10_w104*diff_a10_w104;
diff_a10_w126=diff_a10_w105*diff_a10_w105;
diff_a10_w127=diff_a10_w106*diff_a10_w106;
diff_a10_w128=diff_a10_w107*diff_a10_w107;
diff_a10_w129=diff_a10_w108*diff_a10_w108;
diff_a10_w130=diff_a10_w109*diff_a10_w109;
diff_a10_w131=diff_a10_w110*diff_a10_w110;
diff_a10_w132=diff_a10_w111*diff_a10_w111;
diff_a10_w133=diff_a10_w112*diff_a10_w112;
diff_a10_w134=diff_a10_w113*diff_a10_w113;
diff_a10_w135=diff_a10_w114*diff_a10_w114;
diff_a10_w136=diff_a10_w115*diff_a10_w115;
diff_a10_w137=diff_a10_w118*diff_a10_w118;
// Start of Level 5:
partial_1_0_w60=diff_w42 + diff_w43;
partial_1_1_w61=diff_w44 + diff_w45;
partial_1_2_w62=diff_w46 + diff_w47;
partial_1_3_w63=diff_w48 + diff_w49;
partial_1_4_w64=diff_w50 + diff_w51;
partial_1_5_w65=diff_w52 + diff_w53;
partial_1_6_w66=diff_w54 + diff_w55;
partial_1_7_w67=diff_w56 + diff_w57;
partial_4_1_w75=diff_w58 + diff_w59;
partial_1_0_a10_w140=diff_a10_w120 + diff_a10_w121;
partial_1_1_a10_w141=diff_a10_w122 + diff_a10_w123;
partial_1_2_a10_w142=diff_a10_w124 + diff_a10_w125;
partial_1_3_a10_w143=diff_a10_w126 + diff_a10_w127;
partial_1_4_a10_w144=diff_a10_w128 + diff_a10_w129;
partial_1_5_a10_w145=diff_a10_w130 + diff_a10_w131;
partial_1_6_a10_w146=diff_a10_w132 + diff_a10_w133;
partial_1_7_a10_w147=diff_a10_w134 + diff_a10_w135;
partial_4_1_a10_w139=diff_a10_w136 + diff_a10_w137;
// Start of Level 6:
partial_2_0_w68=partial_1_0_w60 + partial_1_1_w61;
partial_2_1_w69=partial_1_2_w62 + partial_1_3_w63;
partial_2_2_w70=partial_1_4_w64 + partial_1_5_w65;
partial_2_3_w71=partial_1_6_w66 + partial_1_7_w67;
partial_2_0_a10_w1=partial_1_0_a10_w140 + partial_1_1_a10_w141;
partial_2_1_a10_w2=partial_1_2_a10_w142 + partial_1_3_a10_w143;
partial_2_2_a10_w97=partial_1_4_a10_w144 + partial_1_5_a10_w145;
partial_2_3_a10_w96=partial_1_6_a10_w146 + partial_1_7_a10_w147;
// Start of Level 7:
partial_3_0_w72=partial_2_0_w68 + partial_2_1_w69;
partial_3_1_w73=partial_2_2_w70 + partial_2_3_w71;
partial_3_0_a10_w14=partial_2_0_a10_w1 + partial_2_1_a10_w2;
partial_3_1_a10_w13=partial_2_2_a10_w97 + partial_2_3_a10_w96;
// Start of Level 8:
partial_4_0_w74=partial_3_0_w72 + partial_3_1_w73;
partial_4_0_a10_w138=partial_3_0_a10_w14 + partial_3_1_a10_w13;
// Start of Level 9:
final_partial_v_w76=partial_4_0_w74 + partial_4_1_w75;
final_partial_v_a10_w116=partial_4_0_a10_w138 + partial_4_1_a10_w139;
// Start of Level 10:
norma_w3=norma_w77 + final_partial_v_w76;
norma_a10_w98=norma_a10_w117 + final_partial_v_a10_w116;
// Start of Level 11:
temp_l111_1_i1=norma_w3 * (-gamma);
temp_l111_1_i1_a10=norma_a10_w98 * (-gamma);
// Start of Level 12:
temp_l111_2_i2=sv_coeff_0_b0_w0 * exp(temp_l111_1_i1);
temp_l111_2_i2_a10=sv_coeff_0_b0_a10_w119 * exp(temp_l111_1_i1_a10);
// Start of Level 13:
final_partial_v_w148=temp_l111_2_i2_a10 + temp_l111_2_i2;
// Start of Level 14:
*sum=*sum + final_partial_v_w148;
// Start of Level 15:
}

*sum=*sum - b;



}
