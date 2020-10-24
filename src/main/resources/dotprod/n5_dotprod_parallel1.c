
void parallel_0(short x[5], short y[5], int temp_l83_i1_array[5]) {
// Step 2: Initialize local variables
// Initialization done
// starting Loop
for( int i = 0; i < 5;i=i+1){
#pragma HLS pipeline


temp_l83_i1_array[i] = x[i] * y[i];

}
}

void epilogue(int temp_l83_i1_array_0[5], int *out) {
// Step 2: Initialize local variables
int sum_w1;
int sum_w2;
int sum_w3;
int sum_w4;
// Initialization done
sum_w3 = 0 + temp_l83_i1_array_0[0];
sum_w4 = temp_l83_i1_array_0[1] + temp_l83_i1_array_0[2];

sum_w2 = sum_w3 + sum_w4;

sum_w1 = sum_w2 + temp_l83_i1_array_0[3];

*out = sum_w1 + temp_l83_i1_array_0[4];

}

void n5_dotprod_parallel1(short x_0[5], short y_0[5], int *out) {
// Step 2: Initialize local variables
int temp_l83_i1_array_0[5];
// Initialization done
#pragma HLS dataflow

parallel_0(x_0,y_0,temp_l83_i1_array_0);


epilogue(temp_l83_i1_array_0,out);

}
