#include<stdio.h>

#define SIZE 11


void parallelFunction0(int a[1000], int offset, int size, int sum_array[15]) {
// Step 2: Initialize local variables
int sum_w1;
int sum_w2;
int sum_w3;
int sum_w4;
int sum_w5;
int sum_w6;
int sum_w7;
int sum_w8;
int sum_w9;
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
int sum_w60;
int sum_w61;
int sum_w62;
// Initialization done
// starting Loop
for( int i = offset; i < offset + size;i=i+1){



sum_w26 = a[(64)*i+61] + a[(64)*i+62];
sum_w1 = a[(64)*i+71] + a[(64)*i+72];
sum_w32 = a[(64)*i+69] + a[(64)*i+70];
sum_w48 = a[(64)*i+65] + a[(64)*i+66];
sum_w3 = a[(64)*i+35] + a[(64)*i+36];
sum_w19 = a[(64)*i+23] + a[(64)*i+24];
sum_w31 = a[(64)*i+67] + a[(64)*i+68];
sum_w16 = a[(64)*i+53] + a[(64)*i+54];
sum_w30 = a[(64)*i+41] + a[(64)*i+42];
sum_w60 = a[(64)*i+33] + a[(64)*i+34];
sum_w58 = a[(64)*i+29] + a[(64)*i+30];
sum_w57 = a[(64)*i+27] + a[(64)*i+28];
sum_w18 = a[(64)*i+21] + a[(64)*i+22];
sum_w54 = a[(64)*i+17] + a[(64)*i+18];
sum_w13 = a[(64)*i+75] + a[(64)*i+76];
sum_w20 = a[(64)*i+25] + a[(64)*i+26];
sum_w59 = a[(64)*i+31] + a[(64)*i+32];
sum_w25 = a[(64)*i+59] + a[(64)*i+60];
sum_w17 = a[(64)*i+19] + a[(64)*i+20];
sum_w21 = a[(64)*i+55] + a[(64)*i+56];
sum_w46 = a[(64)*i+49] + a[(64)*i+50];
sum_w2 = a[(64)*i+73] + a[(64)*i+74];
sum_w29 = a[(64)*i+39] + a[(64)*i+40];
sum_w14 = a[(64)*i+77] + a[(64)*i+78];
sum_w15 = a[(64)*i+51] + a[(64)*i+52];
sum_w45 = a[(64)*i+47] + a[(64)*i+48];
sum_w4 = a[(64)*i+37] + a[(64)*i+38];
sum_w22 = a[(64)*i+57] + a[(64)*i+58];
sum_w36 = a[(64)*i+45] + a[(64)*i+46];
sum_w53 = a[(64)*i+15] + a[(64)*i+16];
sum_w47 = a[(64)*i+63] + a[(64)*i+64];
sum_w35 = a[(64)*i+43] + a[(64)*i+44];

sum_w49 = sum_w1 + sum_w2;
sum_w27 = sum_w45 + sum_w46;
sum_w37 = sum_w19 + sum_w20;
sum_w12 = sum_w25 + sum_w26;
sum_w10 = sum_w3 + sum_w4;
sum_w61 = sum_w29 + sum_w30;
sum_w42 = sum_w17 + sum_w18;
sum_w28 = sum_w15 + sum_w16;
sum_w50 = sum_w13 + sum_w14;
sum_w9 = sum_w59 + sum_w60;
sum_w62 = sum_w35 + sum_w36;
sum_w55 = sum_w47 + sum_w48;
sum_w56 = sum_w31 + sum_w32;
sum_w41 = sum_w53 + sum_w54;
sum_w38 = sum_w57 + sum_w58;
sum_w11 = sum_w21 + sum_w22;

sum_w7 = sum_w55 + sum_w56;
sum_w24 = sum_w11 + sum_w12;
sum_w5 = sum_w41 + sum_w42;
sum_w8 = sum_w49 + sum_w50;
sum_w23 = sum_w27 + sum_w28;
sum_w51 = sum_w9 + sum_w10;
sum_w52 = sum_w61 + sum_w62;
sum_w6 = sum_w37 + sum_w38;

sum_w34 = sum_w7 + sum_w8;
sum_w40 = sum_w51 + sum_w52;
sum_w39 = sum_w5 + sum_w6;
sum_w33 = sum_w23 + sum_w24;

sum_w44 = sum_w33 + sum_w34;
sum_w43 = sum_w39 + sum_w40;

sum_array[i] = sum_w43 + sum_w44;

}
}


void loopResult(int a[1000], int *sum) {
#pragma HLS dataflow
// Step 2: Initialize local variables
int sum_array[15];
int sum_w1;
int sum_w2;
int sum_w3;
int sum_w4;
int sum_w5;
int sum_w6;
int sum_w7;
int sum_w8;
int sum_w9;
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
int sum_w50;
int sum_w51;
int sum_w52;
int sum_w53;
int sum_w54;
// Initialization done
sum_w1 = 0.0 + a[0];
sum_w16 = a[975] + a[976];
sum_w51 = a[993] + a[994];
sum_w13 = a[979] + a[980];
sum_w37 = a[997] + a[998];
sum_w4 = a[991] + a[992];
sum_w38 = a[989] + a[990];
sum_w20 = a[13] + a[14];
parallelFunction0(a,0,8,sum_array);
parallelFunction0(a,8,7,sum_array);
sum_w48 = a[3] + a[4];
sum_w45 = a[981] + a[982];
sum_w9 = a[5] + a[6];
sum_w32 = a[7] + a[8];
sum_w15 = a[9] + a[10];
sum_w7 = a[987] + a[988];
sum_w25 = a[11] + a[12];
sum_w42 = a[985] + a[986];
sum_w3 = a[1] + a[2];
sum_w49 = a[977] + a[978];
sum_w10 = a[983] + a[984];
sum_w6 = a[995] + a[996];

sum_w2 = sum_w6 + sum_w37;
sum_w23 = sum_w25 + sum_w20;
sum_w26 = sum_w4 + sum_w51;
sum_w50 = sum_array[3] + sum_array[4];
sum_w41 = sum_array[7] + sum_array[8];
sum_w46 = sum_w10 + sum_w42;
sum_w21 = sum_array[11] + sum_array[12];
sum_w17 = sum_w1 + sum_w3;
sum_w53 = sum_w16 + sum_w49;
sum_w39 = sum_array[1] + sum_array[2];
sum_w54 = sum_array[13] + sum_array[14];
sum_w14 = sum_w13 + sum_w45;
sum_w34 = sum_array[9] + sum_array[10];
sum_w19 = sum_w48 + sum_w9;
sum_w8 = sum_w7 + sum_w38;
sum_w31 = sum_array[5] + sum_array[6];
sum_w24 = sum_w32 + sum_w15;

sum_w47 = sum_w31 + sum_w41;
sum_w43 = sum_w26 + sum_w2;
sum_w18 = sum_w24 + sum_w23;
sum_w12 = sum_w17 + sum_w19;
sum_w52 = sum_w46 + sum_w8;
sum_w28 = sum_w39 + sum_w50;
sum_w5 = sum_w53 + sum_w14;
sum_w22 = sum_w34 + sum_w21;

sum_w36 = sum_w12 + sum_w18;
sum_w35 = sum_w28 + sum_w47;
sum_w29 = sum_w5 + sum_w52;
sum_w30 = sum_w43 + a[999];

sum_w40 = sum_w29 + sum_w30;
sum_w33 = sum_w36 + sum_array[0];

sum_w44 = sum_w54 + sum_w40;

sum_w11 = sum_w22 + sum_w44;

sum_w27 = sum_w35 + sum_w11;

*sum = sum_w33 + sum_w27;

}








int main() {
    int a[SIZE] = {};
    for (int i = 0; i < SIZE; i++) {
        a[i] = i % 7;
    };
    int sum;
    loopResult(a, &sum);
    printf("sum=%d", sum);
    return 0;
}