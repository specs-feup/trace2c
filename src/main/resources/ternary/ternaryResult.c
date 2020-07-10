#include <stdio.h>

void ternaryResult(int a, int *b) {
// Step 2: Initialize local variables
double operationOutput_w1;
// Initialization done
operationOutput_w1 = a > 10;

*b = operationOutput_w1 ? 0 :1;

}

int main() {
    int a = 11;
    int b = 0;
    ternaryResult(a, &b);
    printf("%d\n",b);
    return 0;
}