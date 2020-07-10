#include <stdio.h>
#include "math.h"
#include "svm.h"

void svm_predict_graph(float test_vector[D_sv], int *y);

int main()
{

	float test_vector[D_sv];
	int y = 0;

	for (int i = 0; i < N_sv; i++)
	{
		sv_coeff[i] = 3;
		for (int j = 0; j < D_sv; j++)
		{
			sup_vectors[j][i] = j + 0.01f;
		}
	}

	for (int k = 0; k < D_sv; k++)
	{
		test_vector[k] = k;
	}
	svm_predict_graph(test_vector, &y);

	printf("y : %d", y);
}

float square(float x) {
    return x*x;
}

void svm_predict_graph(float test_vector[D_sv], int *y)
{

	FILE *f = fopen("svm.dot", "w");
	fprintf(f, "Digraph G{\n");

	short n_test_vector[D_sv] = {0};
	short n_sup_vectors[D_sv][N_sv] = {0};
	short n_sv_coeff[N_sv] = {0};
	short n_sum = 0;
	short n_y = 0;
	int n_diff = 0;
	//int n_diff_extra = 0;
	int n_norma = 0;
	int n_const = 0;
	int n_temp = 0;
	int n_op = 0;
	int n_mux = 0;
	//int n_out = 0;
	int ne = 0;

	float diff;
	//float diff_extra;
	float norma;

    n_sum++;
    fprintf(f, "\"sum_%d\" [label=\"sum\", att1=var, att2=loc, att3=float ];\n", n_sum);
    n_const++;
    fprintf(f, "const%d [label=\"0\", att1=const];\n", n_const);
    ne++;
    fprintf(f, "const%d->\"sum_%d\" [label=\"%d\", ord=\"%d\"];\n", n_const, n_sum, ne, ne);
	float sum = 0.0f;

	for (int i = 0; i < N_sv; i++)
	{
		n_const++;
		fprintf(f, "const%d [label=\"0\", att1=const];\n", n_const);
		n_norma++;
		fprintf(f, "\"norma_%d\" [label=\"norma\", att1=var, att2=loc, att3=float ];\n", n_norma);
		ne++;
		fprintf(f, "const%d->\"norma_%d\" [label=\"%d\", ord=\"%d\"];\n", n_const, n_norma, ne, ne);
		

		norma = 0;

		for (int j = 0; j < D_sv; j++)
		{
			fprintf(f, "\"test_vector[%d]_%d\" [label=\"test_vector[%d]\", att1=var, att2=inte, att3=float ];\n", j, n_test_vector[j], j);					//nc-2
			fprintf(f, "\"sup_vectors[%d][%d]_%d\" [label=\"sup_vectors[%d][%d]\", att1=var, att2=inte, att3=float ];\n", j, i, n_sup_vectors[j][i], j, i); //nc-2
			n_op++;
			fprintf(f, "op%d [label=\"-\", att1=op ];\n", n_op); //nc-1
			fprintf(f, "diff_%d [label=\"diff\", att1=var, att2=loc, att3=float ];\n", n_diff + 1);

			ne++;
			fprintf(f, "\"sup_vectors[%d][%d]_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", j, i, n_sup_vectors[j][i], n_op, ne, ne);
			ne++;
			fprintf(f, "\"test_vector[%d]_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", j, n_test_vector[j], n_op, ne, ne);
			ne++;
			fprintf(f, "op%d->diff_%d [label=\"%d\", ord=\"%d\"];\n", n_op, n_diff + 1, ne, ne);
			n_diff++;
			diff = test_vector[j] - sup_vectors[j][i];

			n_op++;
			fprintf(f, "op%d [label=\"+\", att1=op ];\n", n_op); //nc-1
			

			ne++;
			fprintf(f, "diff_%d->op%d [label=\"%d\", ord=\"%d\", pos=\"r\",  mod=\"square(\"];\n", n_diff, n_op, ne, ne);
			ne++;
			fprintf(f, "\"norma_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_norma, n_op, ne, ne);
			
			n_norma++;
			fprintf(f, "\"norma_%d\" [label=\"norma\", att1=var, att2=loc, att3=float ];\n", n_norma);
			ne++;
			fprintf(f, "op%d->\"norma_%d\" [label=\"%d\", ord=\"%d\"];\n", n_op, n_norma, ne, ne);

			norma = norma + square(diff);
		}

		

		fprintf(f, "gamma_0 [label=\"gamma\", att1=var, att2=global, att3=int , mod=\"(-\"];\n"); //nc-2
		n_op++;
		fprintf(f, "op%d [label=\"*\", att1=op ];\n", n_op); //nc-1
		n_temp++;
		fprintf(f, "temp%d [label=\"temp_l111_1_i%d\", att1=var, att2=loc, att3=float, mod=\"exp(\" ];\n", n_temp, n_temp); //nc-2

		ne++;
		fprintf(f, "gamma_0->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_op, ne, ne);
		ne++;
		fprintf(f, "\"norma_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_norma, n_op, ne, ne);
		ne++;
		fprintf(f, "op%d->temp%d [label=\"%d\", ord=\"%d\"];\n", n_op, n_temp, ne, ne);

		fprintf(f, "\"sv_coeff[%d]_%d\" [label=\"sv_coeff[%d]\", att1=var, att2=inte, att3=float ];\n", i, n_sv_coeff[i], i);
		n_op++;
		fprintf(f, "op%d [label=\"*\", att1=op ];\n", n_op); //nc-1
		n_temp++;
		fprintf(f, "temp%d [label=\"temp_l111_2_i%d\", att1=var, att2=loc, att3=float ];\n", n_temp, n_temp); //nc-2

		ne++;
		fprintf(f, "temp%d->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_temp - 1, n_op, ne, ne);
		ne++;
		fprintf(f, "\"sv_coeff[%d]_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", i, n_sv_coeff[i], n_op, ne, ne);
		ne++;
		fprintf(f, "op%d->temp%d [label=\"%d\", ord=\"%d\"];\n", n_op, n_temp, ne, ne);

		fprintf(f, "\"sum_%d\" [label=\"sum\", att1=var, att2=loc, att3=float ];\n", n_sum);
		n_op++;
		fprintf(f, "op%d [label=\"+\", att1=op ];\n", n_op); //nc-1
		fprintf(f, "\"sum_%d\" [label=\"sum\", att1=var, att2=loc, att3=float ];\n", n_sum + 1);

		ne++;
		fprintf(f, "temp%d->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_temp, n_op, ne, ne);
		ne++;
		fprintf(f, "\"sum_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_sum, n_op, ne, ne);
		ne++;
		fprintf(f, "op%d->\"sum_%d\" [label=\"%d\", ord=\"%d\"];\n", n_op, n_sum + 1, ne, ne);
		n_sum++;

		sum = sum + (exp(-gamma * norma) * sv_coeff[i]);
	}

	fprintf(f, "b_0 [label=\"b\", att1=var, att2=global, att3=int ];\n"); //nc-2
	n_op++;
	fprintf(f, "op%d [label=\"-\", att1=op ];\n", n_op); //nc-1
	n_sum++;
	fprintf(f, "\"sum_%d\" [label=\"sum\", att1=var, att2=loc, att3=float ];\n", n_sum);

	ne++;
	fprintf(f, "b_0->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_op, ne, ne);
	ne++;
	fprintf(f, "\"sum_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_sum-1, n_op, ne, ne);
	ne++;
	fprintf(f, "op%d->\"sum_%d\" [label=\"%d\", ord=\"%d\"];\n", n_op, n_sum, ne, ne);

	sum = sum - b;

	n_mux++;
	fprintf(f, "mux_%d [label=\"mux_%d\", att1=mux];\n", n_mux, n_mux);
	n_const++;
    fprintf(f, "const%d [label=\"0\", att1=const];\n", n_const);
    n_op++;
    fprintf(f, "op%d [label=\"<\", att1=op ];\n", n_op); //
    ne++;
    fprintf(f, "\"sum_%d\"->op%d [label=\"%d\", ord=\"%d\", pos=\"l\"];\n", n_sum, n_op, ne, ne);
    ne++;
    fprintf(f, "const%d->op%d [label=\"%d\", ord=\"%d\", pos=\"r\"];\n", n_const, n_op, ne, ne);
    ne++;
    fprintf(f, "op%d->mux_%d [label=\"%d\", ord=\"%d\", pos=\"sel\"];\n", n_op, n_mux, ne, ne);

    n_const++;
    fprintf(f, "const%d [label=\"-1\", att1=const];\n", n_const);
    ne++;
    fprintf(f, "const%d->mux_%d [label=\"%d\", ord=\"%d\", pos=\"t\"];\n", n_const, n_mux, ne, ne);

    n_const++;
    fprintf(f, "const%d [label=\"1\", att1=const];\n", n_const);
    ne++;
    fprintf(f, "const%d->mux_%d [label=\"%d\", ord=\"%d\", pos=\"f\"];\n", n_const, n_mux, ne, ne);

    n_y++;
    fprintf(f, "\"*y_%d\" [label=\"*y\", att1=var, att2=inte, att3=int ];\n", n_y);
    ne++;
    fprintf(f, "mux_%d->\"*y_%d\" [label=\"%d\", ord=\"%d\"];\n", n_mux, n_y, ne, ne);
	*y = sum < 0 ? -1 : 1;

	fprintf(f, "}");
	fclose(f);
	printf("sum : %f\n", sum);
}