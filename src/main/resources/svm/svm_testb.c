#include <stdio.h>
#include "svm.h"
#include <math.h>

void svm_predict(float test_vector[D_sv], float sup_vectors[18][1248], float sv_coeff[1248], float *sum)
{
	float diff;
	float norma;
	for (int i = 0; i < N_sv; i++)
	{

		norma = 0;
		for (int j = 0; j < D_sv; j++)
		{

			diff = test_vector[j] - sup_vectors[j][i];
			diff = diff * diff;
			norma = norma + diff;
		}

		(*sum) = (*sum) + (exp(-gamma * norma) * sv_coeff[i]);
	}

	*sum = (*sum) - b;
}

int main()
{

	float sv_coeff[N_sv];
	float sup_vectors[D_sv][N_sv];

	float test_vector[18];
	float sum = 0;
	float sum_hard = 0;
	for (int i = 0; i < N_sv; i++)
	{
		if (i % 2 == 0)
			sv_coeff[i] = 3;
		else
			sv_coeff[i] = 2;
		for (int j = 0; j < D_sv; j++)
		{
			sup_vectors[j][i] = j + 0.01;
		}
	}

	for (int k = 0; k < D_sv; k++)
	{
		test_vector[k] = k;
	}

	svm_predict(test_vector, sup_vectors, sv_coeff, &sum);
	result(&sum_hard, test_vector, sup_vectors, sv_coeff);

	printf("sum : %f\nsum_hard %f\n", sum, sum_hard);
}
