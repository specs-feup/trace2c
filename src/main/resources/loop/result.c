// Step by step description of process
// Step 1: create function header

int main_func(int a[11]){
// Step 2: Initialize local variables
int sum;
int sum_w0;
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
// Initialization done




// Step 3: write code by level
// Currently we write attributions and simple operations between two variables

sum_w0 = 0.0;
sum_w2=a[2] + a[1];
sum_w5=a[4] + a[3];
sum_w7=a[6] + a[5];
sum_w11=a[8] + a[7];
sum_w9=a[10] + a[9];

sum_w1=sum_w0 + a[0];

sum_w4=sum_w1 + sum_w2;

sum_w6=sum_w4 + sum_w5;

sum_w10=sum_w6 + sum_w7;

sum_w8=sum_w10 + sum_w11;

sum_w3=sum_w8 + sum_w9;

sum = sum_w8 + sum_w9;

return sum;
}
