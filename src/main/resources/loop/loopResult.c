
void loopResult(int a[11], int sum)
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
// Initialization done
{
sum_w3 = 0.0 + a[0];
sum_w8 = a[4] + a[3];
sum_w10 = a[8] + a[7];
sum_w5 = a[2] + a[1];
sum_w1 = a[10] + a[9];
sum_w9 = a[6] + a[5];

sum_w7 = sum_w3 + sum_w5;

sum_w6 = sum_w7 + sum_w8;

sum_w4 = sum_w6 + sum_w9;

sum_w2 = sum_w4 + sum_w10;

sum = sum_w2 + sum_w1;

}
