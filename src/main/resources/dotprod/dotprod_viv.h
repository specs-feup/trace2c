
#include <stdio.h>
#define NX 10

int DSP_dotprod_golden_c(const short *x, const short *y, int nx);
void DSP_dotprod_hard_c( short x[NX], short y[NX], int *out);
int DSP_dotprod_graph(const short x[NX], const short y[NX], int nx);
