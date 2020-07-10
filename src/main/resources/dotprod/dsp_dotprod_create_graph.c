#include <stdio.h>
#include "dotprod_viv.h"

int main(){

	
	int nx=NX;
	short x[NX];
	short y[NX];
	
	for(int i=0; i<nx;i++){
		x[i]=i%100+1;
		y[i]=2*i%107;
	}
	
	int dotprodResult = DSP_dotprod_graph(x,y,NX);
	printf("dotprod result= %d", dotprodResult);
	return 0;
	
}


int DSP_dotprod_graph(const short *x, const short *y, int nx)
{
    
	

	short n_x[NX]={0};
	short n_y[NX]={0};
	short n_sum=0;
	int n_const=0;
	int n_temp=0;
	int n_op=0;
	int n_out=0;
	int ne=0;
	FILE *f=fopen("dotprod_graph.dot","w");
	fprintf(f,"Digraph G{\n");
	
	int sum = 0, i;
	
     n_const++;
	 fprintf(f,"const%d [label=\"0\", att1=const];\n",n_const);
	 n_sum++;
	 fprintf(f,"\"sum_%d\" [label=\"sum\", att1=var, att2=loc, att3=int ];\n",n_sum);
	 ne++;
	 fprintf(f,"const%d->\"sum_%d\" [label=\"%d\", ord=\"%d\"];\n",n_const,n_sum,ne,ne);
	 

    for (i = 0; i < nx; i++){
        sum += x[i] * y[i];
		
	  	fprintf(f,"\"x[%d]_%d_l\" [label=\"x[%d]\", att1=var, att2=inte, att3=short ];\n" ,i, n_x[i] ,i);//nc-5
		fprintf(f,"\"y[%d]_%d_r\" [label=\"y[%d]\", att1=var, att2=inte, att3=short ];\n" ,i, n_y[i] ,i);//nc-5
		n_op++;
		fprintf(f,"op%d [label=\"*\", att1=op];\n",n_op);//nc-3
		n_temp++;
		fprintf(f,"temp%d [label=\"temp_l83_i%d\", att1=var, att2=loc, att3=int ];\n",n_temp,n_temp);//nc-2
		   
		ne++;
		fprintf(f,"\"x[%d]_%d_l\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n",i, n_x[i],n_op,ne,ne);
		ne++;
		fprintf(f,"\"y[%d]_%d_r\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n",i,n_y[i],n_op,ne,ne);
		ne++;
		fprintf(f,"op%d->temp%d [label=\"%d\", ord=\"%d\"];\n",n_op,n_temp,ne,ne);
		   
		fprintf(f,"temp%d [label=\"temp_l83_i%d\", att1=var, att2=loc, att3=int ];\n",n_temp,n_temp);//nc-2
		n_op++;
		fprintf(f,"op%d [label=\"+\", att1=op ];\n",n_op);//nc-1
		fprintf(f,"\"sum_%d\" [label=sum, att1=var, att2=loc, att3=int ];\n",n_sum+1);
		   
		ne++;		   
		fprintf(f,"temp%d->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n",n_temp,n_op,ne,ne);
		ne++;
		fprintf(f,"\"sum_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n",n_sum,n_op,ne,ne);
		ne++;
		fprintf(f,"op%d->\"sum_%d\" [label=\"%d\", ord=\"%d\"];\n",n_op,n_sum+1,ne,ne);
	    n_sum++;

	}
	
	fprintf(f,"\"sum_%d\" [label=sum, att1=var, att2=loc, att3=int ];\n",n_sum);
	fprintf(f,"\"out_%d\" [label=\"*out\", att1=var, att2=inte, att3=int ];\n",n_out);
	fprintf(f,"\"sum_%d\"->out_%d [label=\"%d\", ord=\"%d\"];\n",n_sum,n_out,ne,ne);
	ne++;
	
	
	fprintf(f,"}\n");	
	fclose(f);
    return sum;

}
