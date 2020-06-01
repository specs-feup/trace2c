#include<stdio.h>


void main(){
FILE *f1=fopen("sup_v.txt","w+");
FILE *f2=fopen("coeff_v.txt","w+");
for(int i=0; i<1248; i++){
	if(i%2==0){
	//sv_coeff[i]=3;
	fprintf(f2,"3 ,");
	}
	else{
	//sv_coeff[i]=2;
	fprintf(f2,"2 ,");
	}
}
	for(int j=0; j<18;j++){
		fprintf(f1,"{");
		for(int k=0; k<1248;k++){
		//sup_vectors[j][k]=j+0,01;
		fprintf(f1," %f,",j+0.01);
		}
		fprintf(f1,"}\n");
	}
	return;
}