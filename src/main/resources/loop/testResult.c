#include<stdio.h>

#define SIZE 5

// Step by step description of process
// Step 1: create function header

int main_func(int a[SIZE]){
// Step 2: Initialize local variables
int sum;
int sum_w0;
int sum_w1;
int sum_w2;
int sum_w3;
int sum_w4;
int sum_w5;
// Initialization done




// Step 3: write code by level
// Currently we write attributions and simple operations between two variables

sum_w0 = 0.0;
sum_w2=a[2] + a[1];
sum_w5=a[4] + a[3];

sum_w1=sum_w0 + a[0];

sum_w4=sum_w1 + sum_w2;

sum = sum_w4 + sum_w5;

return sum;
}

int main() {
    int a[SIZE] = {};
    for (int i = 0; i < SIZE; i++) {
        a[i] = i % 7;
    };
    int sum = main_func(a);
    printf("sum=%d", sum);
    return 0;
}