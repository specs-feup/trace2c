#include<stdio.h>

int main() {
    FILE *f = fopen("3statements.dot", "w");
	fprintf(f, "Digraph G{\n");
    int n_const = 0;
    int ne = 0;
    int n_op = 0;
    int n_a = 0;
    int n_b = 0;
    int n_c = 0;

	fprintf(f, "const%d [label=10, att1=const];\n", n_const);
	fprintf(f, "a_%d [label=a, att1=var, att2=loc, att3=int ];\n", ++n_a);
	ne++;
	fprintf(f, "const%d->a_%d [label=%d, ord=%d];\n", n_const, n_a, ne, ne);
	

    int a = 10;
    
    n_const++;
    fprintf(f, "const%d [label=5, att1=const];\n", n_const);
	fprintf(f, "b_%d [label=b, att1=var, att2=loc, att3=int ];\n", ++n_b);
    n_op++;
	fprintf(f, "op%d [label=\"*\", att1=op ];\n", n_op);
	ne++;
	fprintf(f, "const%d->op%d [label=%d, ord=%d, pos=l];\n", n_const, n_op, ne, ne);
	ne++;
    fprintf(f, "a_%d->op%d [label=%d, ord=%d, pos=r];\n", n_a, n_op, ne, ne);
    ne++;
    fprintf(f, "op%d->b_%d [label=%d, ord=%d];\n", n_op, n_b, ne, ne);
    
    int b = 5 * a;

    fprintf(f, "c_%d [label=c, att1=var, att2=loc, att3=int ];\n", ++n_c);
    n_op++;
	fprintf(f, "op%d [label=\"*\", att1=op ];\n", n_op);
	ne++;
	fprintf(f, "b_%d->op%d [label=%d, ord=%d, pos=l];\n", n_b, n_op, ne, ne);
	ne++;
    fprintf(f, "a_%d->op%d [label=%d, ord=%d, pos=r];\n", n_a, n_op, ne, ne);
    ne++;
    fprintf(f, "op%d->c_%d [label=%d, ord=%d];\n", n_op, n_b, ne, ne);
    int c = b * a;
    fprintf(f, "}");
	fclose(f);
    return c;
}