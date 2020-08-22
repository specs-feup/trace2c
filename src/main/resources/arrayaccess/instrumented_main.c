#include <stdio.h>

void array_access_param( int a[5], int input, int *out) {
    FILE *log_file_0 = fopen("arrayaccess_param.dot", "w");
    fprintf(log_file_0, "Digraph G{\n");

    fprintf(log_file_0, "input_0 [label=\"input\", att1=var, att2=param, att3=int];\n");
    fprintf(log_file_0, "out_0 [label=\"*out\", att1=var, att2=param, att3=int];\n");
    fprintf(log_file_0, "AA_0 [label=\"AA_0\", att1=nop];\n");
    fprintf(log_file_0, "\"a[input]_0\" [label=\"a[input]\", att1=var, att2=param, att3=int];\n");
    fprintf(log_file_0, "Assign_0 [label=\"Assign_0\", att1=assignment];\n");
    fprintf(log_file_0, "input_0->AA_0  [label=\"1\", ord=1];\n");
    fprintf(log_file_0, "AA_0->\"a[input]_0\"  [label=\"2\", ord=2];\n");
    fprintf(log_file_0, "\"a[input]_0\"->Assign_0  [label=\"3\", ord=3];\n");
    fprintf(log_file_0, "Assign_0->out_0 [label=\"4\", ord=4];\n");    

    *out = a[input];

    fprintf(log_file_0, "}\n");
}


void array_access_local(int input, int *out) {
    int a[2];
    FILE *log_file_0 = fopen("arrayaccess_local.dot", "w");
    fprintf(log_file_0, "Digraph G{\n");
    fprintf(log_file_0, "\"a[0]_1\" [label=\"a[0]\", att1=var, att2=loc, att3=int];\n");
    fprintf(log_file_0, "\"a[1]_1\" [label=\"a[1]\", att1=var, att2=loc, att3=int];\n");
    fprintf(log_file_0, "const_0 [label=\"5\", att1=const];\n");
    fprintf(log_file_0, "const_1 [label=\"2\", att1=const];\n");
    fprintf(log_file_0, "Assign_0 [label=\"Assign\", att1=assignment];\n");
    fprintf(log_file_0, "Assign_1 [label=\"Assign\", att1=assignment];\n");
    fprintf(log_file_0, "const_0->Assign_0;\n");
    fprintf(log_file_0, "const_1->Assign_1;\n");
    fprintf(log_file_0, "Assign_0->\"a[0]_1\";\n");
    fprintf(log_file_0, "Assign_1->\"a[1]_1\";\n");
    a[0] = 5;
    a[1] = 2;

    fprintf(log_file_0, "\"a[0]_1\"->AA_0;\n");
    fprintf(log_file_0, "\"a[1]_1\"->AA_0;\n");

    fprintf(log_file_0, "input_0 [label=\"input\", att1=var, att2=param, att3=int];\n");
    fprintf(log_file_0, "out_0 [label=\"*out\", att1=var, att2=param, att3=int];\n");
    fprintf(log_file_0, "AA_0 [label=\"AA_0\", att1=nop];\n");
    fprintf(log_file_0, "\"a[input]_1\" [label=\"a[input]\", att1=var, att2=loc, att3=int];\n");
    fprintf(log_file_0, "Assign_2 [label=\"Assign\", att1=assignment];\n");
    fprintf(log_file_0, "input_0->AA_0;\n");
    fprintf(log_file_0, "AA_0->\"a[input]_1\";\n");
    fprintf(log_file_0, "\"a[input]_1\"->Assign_2;\n");
    fprintf(log_file_0, "Assign_2->out_0;\n");    

    *out = a[input];

    fprintf(log_file_0, "}\n");
}



int main() {
    int a[5] = {1, 2, 10, 4, 6};
    int index = 0;
    int out = 0;
    array_access_param(a, index, &out);
    array_access_local(index, &out);
    printf("out: %d" , out);
    return 0;
}