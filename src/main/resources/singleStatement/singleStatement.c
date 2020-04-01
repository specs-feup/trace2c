#include<stdio.h>

int singleStatement() {
    FILE *f = fopen("singleStatement.dot", "w");
	fprintf(f, "Digraph G{\n");

    int n_const = 0;
    int ne = 0;
    int n_a = 0;

    fprintf(f, "const%d [label=10, att1=const];\n", n_const);
	fprintf(f, "\"a_%d\" [label=\"a\", att1=var, att2=loc, att3=int ];\n", ++n_a);
	ne++;
	fprintf(f, "const%d->\"a_%d\"   [label=%d, ord=%d];\n", n_const, n_a, ne, ne);
    int a = 10;

    fprintf(f, "}");
    fclose(f);
    return a;
}

int main() {
    int a = singleStatement();
    printf("a=%d",a);
    return 0;
}

