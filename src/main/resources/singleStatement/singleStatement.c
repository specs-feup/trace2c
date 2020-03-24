#include<stdio.h>

void singleStatement(int *a) {
    FILE *f = fopen("singleStatement.dot", "w");
	fprintf(f, "Digraph G{\n");

    int n_const = 0;
    int ne = 0;
    int n_a = 0;

    fprintf(f, "const%d [label=10, att1=const];\n", n_const);
	fprintf(f, "\"*a_%d\" [label=\"*a\", att1=var, att2=inte, att3=int ];\n", ++n_a);
	ne++;
	fprintf(f, "const%d->\"*a_%d\"   [label=%d, ord=%d];\n", n_const, n_a, ne, ne);
    *a = 10;

    fprintf(f, "}");
    fclose(f);
}

int main() {
    int a=0;
    singleStatement(&a);
    printf("a=%d",a);
    return 0;
}

