#include <stdio.h>

void ternary_out(int a, int *b) {
// Step 2: Initialize local variables
double operationOutput_w1;
// Initialization done
operationOutput_w1 = a > 10;

*b = operationOutput_w1 ? 0:1;

}
