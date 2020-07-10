#include<stdio.h>

void ternary(int a, int *b) {
    FILE *f = fopen("ternary.dot", "w");
    fprintf(f, "Digraph G{\n");

    fprintf(f, "const1 [label=10, att1=const];\n");
    fprintf(f, "const2 [label=0, att1=const];\n");
    fprintf(f, "const3 [label=1, att1=const];\n");
    fprintf(f, "b_1 [label=\"*b\", att1=var, att2=inte, att3=int ];\n");
    fprintf(f, "a_0 [label=a, att1=var, att2=inte, att3=int];\n");

    fprintf(f, "op1 [label=\">\" , att1=op ];\n");

    fprintf(f, "a_0->op1 [label=1, ord=1, pos=l];\n");
    fprintf(f, "const1->op1 [label=2, ord=2, pos=r];\n");

    fprintf(f, "mux1 [label=mux1, att1=mux];\n"); //att2 tells where is the selector
    fprintf(f, "op1->mux1 [label=3, ord=3, pos=sel];\n"); //pos=selector

    fprintf(f, "const2->mux1 [label=4, ord=4, pos=t];\n"); //pos=t means pos=true
    fprintf(f, "const3->mux1 [label=5, ord=5, pos=f];\n"); //pos=f means pos=false

    fprintf(f, "mux1->b_1 [label=6, ord=6];\n");

   *b = a > 10 ? 0 : 1;

   fprintf(f, "}");
   fclose(f);
}


int main() {
    int b = 20;
    ternary(20, &b);
    return 0;
}