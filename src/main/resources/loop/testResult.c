#include<stdio.h>

#define SIZE 1000

void parallelFunction0(int a[21], int offset, int size, int sum_array[4]) {
// Step 2: Initialize local variables
int sum_w1;
int sum_w2;
// Initialization done
// starting Loop
    for( int i = offset; i < offset + size;i=i+1){
        sum_w1 = a[(4)*i+3] + a[(4)*i+4];
        sum_w2 = a[(4)*i+5] + a[(4)*i+6];
        sum_array[i] = sum_w1 + sum_w2;
    }
}

void loopResult(int a[21], int *sum) {
#pragma HLS dataflow
// Step 2: Initialize local variables
int sum_array[4];
int sum_w1;
int sum_w2;
int sum_w3;
int sum_w4;
int sum_w5;
int sum_w6;
int sum_w7;
int sum_w8;
// Initialization done
parallelFunction0(a,0,2,sum_array);
parallelFunction0(a,2,2,sum_array);
sum_w2 = 0.0 + a[0];
sum_w5 = a[19] + a[20];
sum_w4 = a[1] + a[2];

sum_w8 = sum_array[3] + sum_w5;
sum_w6 = sum_w2 + sum_w4;
sum_w1 = sum_array[1] + sum_array[2];

sum_w7 = sum_w6 + sum_array[0];

sum_w3 = sum_w7 + sum_w1;

*sum = sum_w3 + sum_w8;

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