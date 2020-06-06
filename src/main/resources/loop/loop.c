#include<stdio.h>

#define SIZE 100

int sumArray(int a[SIZE]) {
    FILE *f = fopen("loop.dot", "w");
	fprintf(f, "Digraph G{\n");

    int n_const = 0;
    int ne = 0;
    int n_sum = 0;
    int n_op = 0;
    int n_a[SIZE] = {0};

    n_const++;
    fprintf(f, "const%d [label=0, att1=const];\n", n_const);
    fprintf(f, "sum_%d [label=sum, att1=var, att2=loc, att3=int ];\n", ++n_sum);
    ne++;
    fprintf(f, "const%d->sum_%d [label=%d, ord=%d];\n", n_const, n_sum, ne, ne);
    int sum = 0;

    for (int i = 0; i < SIZE; i++) {
        fprintf(f, "\"a[%d]_%d\" [label=\"a[%d]\", att1=var, att2=inte, att3=int ];\n", i, n_a[i], i);
        n_op++;
        fprintf(f, "op_%d [label=\"+\", att1=op ];\n", n_op);
        ne++;
        fprintf(f, "sum_%d->op_%d [label=%d, pos=l, ord=%d];\n", n_sum, n_op, ne, ne);
        ne++;
        fprintf(f, "\"a[%d]_%d\"->op_%d [label=%d, pos=r, ord=%d];\n",i, n_a[i], n_op, ne, ne);
        n_sum++;
        fprintf(f, "sum_%d [label=sum, att1=var, att2=loc, att3=int ];\n", n_sum);
        ne++;
        fprintf(f, "op_%d->sum_%d [label=%d, ord=%d];\n", n_op, n_sum, ne, ne);
        sum = sum + a[i];
    }
    fprintf(f, "}");
    fclose(f);
    return sum;
}

int main() {
    int a[SIZE] = {};
    for (int i = 0; i < SIZE; i++) {
        a[i] = i % 7;
    };
    int sum = sumArray(a);
    printf("sum=%d", sum);
    return 0;
}