#include<stdio.h>

void ternary(int a, int *b) {
    FILE *f = fopen("ternary.dot", "w");
    fprintf(f, "Digraph G{\n");
    // Declare constants and variables
    fprintf(f, "const1 [label=\"10\", att1=const];\n");
    fprintf(f, "const2 [label=\"0\", att1=const];\n");
    fprintf(f, "const3 [label=\"1\", att1=const];\n");
    fprintf(f, "b_1 [label=\"*b\", att1=var, att2=inte, att3=int ];\n");
    fprintf(f, "a_0 [label=a, att1=var, att2=inte, att3=int];\n");
    // Declare operations
    fprintf(f, "op1 [label=\">\" , att1=op ];\n");
    fprintf(f, "mux1 [label=mux1, att1=mux];\n");
    // Declare edges
    fprintf(f, "a_0->op1 [label=\"1\", pos=l];\n");
    fprintf(f, "const1->op1 [label=\"2\", pos=r];\n");
    fprintf(f, "op1->mux1 [label=\"3\",pos=sel];\n");
    fprintf(f, "const2->mux1 [label=\"4\",pos=t];\n");
    fprintf(f, "const3->mux1 [label=\"5\",pos=f];\n");
    fprintf(f, "mux1->b_1 [label=\"6\"];\n");

   *b = a > 10 ? 0 : 1;

   fprintf(f, "}");
   fclose(f);
}


int main() {
    int b = 20;
    ternary(20, &b);
    printf("b=%d", b);
    return 0;
}