#include <stdio.h>

void array_access( int a[5], int input, int *out) {
    FILE *log_file_0 = fopen("arrayaccess.dot", "w");
    fprintf(log_file_0, "Digraph G{\n");

    fprintf(log_file_0, "input_0 [label=\"input\", att1=var, att2=param, att3=int];\n");
    fprintf(log_file_0, "out_0 [label=\"*out\", att1=var, att2=param, att3=int];\n");
    fprintf(log_file_0, "AA_0 [label=\"AA_0\", att1=arrayAccess, att2=a];\n");

    fprintf(log_file_0, "input_0->AA_0  [label=\"1\", ord=1, dim=\"0\"];\n");
    fprintf(log_file_0, "AA_0->out_0 [label=\"2\", ord=2];\n");    

    *out = a[input];

    fprintf(log_file_0, "}\n");
}


int main() {
    int a[5] = {1, 2, 10, 4, 6};
    int index = 0;
    int out = 0;
    array_access(a, index, &out);
    printf("out: %d" , out);
    return 0;
}