#include<stdio.h>
#include<math.h>
#include"svm.h"


void svm_predict(float test_vector[D_sv], int *y){
	float diff;
	float norma;
	float sum = 0;
	for(int i=0; i< N_sv;i++){
		norma=0;
	#pragma HLS pipeline
		for(int j=0; j<D_sv;j++){
		diff=test_vector[j] - sup_vectors[j][i];
		diff=diff*diff;
		norma=norma + diff;
		}
    sum = sum + (exp(-gamma*norma)*sv_coeff[i]);
	}
    sum= sum-b;
    *y = sum < 0 ? -1 : 1;
}