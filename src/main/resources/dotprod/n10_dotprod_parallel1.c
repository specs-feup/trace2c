
void parallel_0(short x[8], short y[8], int sum_array[4]) {
// Step 2: Initialize local variables
int temp_l83_i2_w1;
int temp_l83_i3_w1;
// Initialization done
// starting Loop
for( int i = 0; i < 4;i=i+1){
#pragma HLS pipeline


temp_l83_i2_w1 = x[(2)*i] * y[(2)*i];
temp_l83_i3_w1 = x[(2)*i+1] * y[(2)*i+1];

sum_array[i] = temp_l83_i2_w1 + temp_l83_i3_w1;

}
}

void epilogue(short x_0[1], short x_2[1], short y_0[1], short y_2[1], int sum_array_0[4], int *out) {
// Step 2: Initialize local variables
int sum_w1;
int sum_w2;
int sum_w3;
int sum_w4;
int sum_w5;
int temp_l83_i10_w1;
int temp_l83_i1_w1;
// Initialization done
sum_w5 = sum_array_0[0] + sum_array_0[1];
sum_w3 = sum_array_0[2] + sum_array_0[3];
temp_l83_i1_w1 = x_0[0] * y_0[0];
temp_l83_i10_w1 = x_2[0] * y_2[0];

sum_w4 = 0 + temp_l83_i1_w1;

sum_w2 = sum_w4 + sum_w5;

sum_w1 = sum_w2 + sum_w3;

*out = sum_w1 + temp_l83_i10_w1;

}

void n10_dotprod_parallel1(short x_0[1], short x_1[8], short x_2[1], short y_0[1], short y_1[8], short y_2[1], int *out) {
// Step 2: Initialize local variables
int sum_array_0[4];
#pragma HLS ARRAY_PARTITION variable=x_0 cyclic factor=1 dim=0
#pragma HLS ARRAY_PARTITION variable=x_1 cyclic factor=2 dim=0
#pragma HLS ARRAY_PARTITION variable=x_2 cyclic factor=1 dim=0
#pragma HLS ARRAY_PARTITION variable=y_0 cyclic factor=1 dim=0
#pragma HLS ARRAY_PARTITION variable=y_1 cyclic factor=2 dim=0
#pragma HLS ARRAY_PARTITION variable=y_2 cyclic factor=1 dim=0
#pragma HLS ARRAY_PARTITION variable=sum_array_0 cyclic factor=2 dim=0
// Initialization done
#pragma HLS dataflow

parallel_0(x_1,y_1,sum_array_0);


epilogue(x_0,x_2,y_0,y_2,sum_array_0,out);

}
