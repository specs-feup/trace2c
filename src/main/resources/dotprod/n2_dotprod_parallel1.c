
void n2_dotprod_parallel1(short x[2], short y[2], int *out) {
// Step 2: Initialize local variables
int sum_w1;
int temp_l83_i1_w1;
int temp_l83_i2_w1;
// Initialization done
temp_l83_i1_w1 = x[0] * y[0];
temp_l83_i2_w1 = x[1] * y[1];

sum_w1 = 0 + temp_l83_i1_w1;

*out = sum_w1 + temp_l83_i2_w1;

}
