/* This program enhances a 256-gray-level, 128x128 pixel image by applying
   global histogram equalization.

   This program is based on the routines and algorithms found in the book
   "C Language Algorithms for Disgital Signal Processing" by P. M. Embree
   and B. Kimble.

   Copyright (c) 1992 -- Mazen A.R. Saghir -- University of Toronto */
/* Modified to use arrays - SMP */

#include <stdio.h>
#include "traps.h"

#define N 128
#define L 256

void hist(int image[N][N], int histogram[L], int glm[L])
{
    //---------------------
    FILE *f = fopen("histogram.dot", "w");
    int n_const = 0;
    int n_temp = 0;
    int ne = 0;
    int n_assign = 0;
    int n_op = 0;
    int n_ca = 0;
    int n_aa = 0;
    int n_cdf = 0;
    int n_pixels = 0;

    int n_image[N][N] = {0};
    int n_histogram[L] = {0};
    int n_glm[L] = {0};
    int n_histogramAtImage[N][N] = {0};
    int n_glmAtImage[N][N] = {0};
    fprintf(f, "Digraph G{\n");
    for (int i = 0; i < L; i++)
    {
        n_histogram[i]++;
        fprintf(f, "\"histogram[%d]_%d_l\" [label=\"histogram[%d]\", att1=var, att2=param, att3=float ];\n", i, n_histogram[i], i);
        n_glm[i]++;
        fprintf(f, "\"glm[%d]_%d_l\" [label=\"glm[%d]\", att1=var, att2=param, att3=float ];\n", i, n_glm[i], i);
    }
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N; j++)
        {
            n_image[i][j]++;
            fprintf(f, "\"image[%d][%d]_%d\" [label=\"image[%d][%d]\", att1=var, att2=param, att3=float ];\n", i, j, n_image[i][j], i, j);
        }
    }
    //---------------------
    float cdf;
    float pixels;

    /* Compute the image's histogram */

    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N; ++j)
        {
            //---------------------
            n_const++;
            fprintf(f, "const%d [label=\"1\", att1=const];\n", n_const);
            n_op++;
            fprintf(f, "op%d [label=\"+\", att1=op];\n", n_op);
            ne++;
            fprintf(f, "\"const%d\"->\"op%d\" [label=\"%d\", ord=\"%d\"];\n", n_const, n_op, ne, ne);
            n_ca++;
            fprintf(f, "CA_%d [label=CA_%d, att1=complexAssignment];\n", n_ca, n_ca);
            n_aa++;
            fprintf(f, "AA_%d [label=AA_%d, att1=nop];\n", n_aa, n_aa);
            ne++;
            fprintf(f, "\"image[%d][%d]_%d\"->AA_%d [label=%d];\n", i, j, n_image[i][j], n_aa, ne);
            n_histogramAtImage[i][j]++;
            fprintf(f, "\"histogram[image[%d][%d]]_%d\" [label=\"histogram[image[%d][%d]]\", att1=var, att2=param, att3=int];\n", i,j,n_histogramAtImage[i][j], i, j);
            ne++;
            fprintf(f, "AA_%d->\"histogram[image[%d][%d]]_%d\" [label=%d];\n", n_aa, i, j, n_histogramAtImage[i][j], ne);
            
            ne++;
            fprintf(f, "\"histogram[image[%d][%d]]_%d\"->\"op%d\" [label=\"%d\"];\n", i,j,n_histogramAtImage[i][j], n_op, ne);
            n_histogramAtImage[i][j]++;
            fprintf(f, "\"histogram[image[%d][%d]]_%d\" [label=\"histogram[image[%d][%d]]\", att1=var, att2=param, att3=int];\n", i,j,n_histogramAtImage[i][j],i,j);
            ne++;
            fprintf(f, "\"op%d\"->CA_%d [label=\"%d\", pos=r];\n", n_op, n_ca, ne);
            ne++;
            fprintf(f, "\"image[%d][%d]_%d\"->CA_%d [label=%d, pos=l];\n", i, j, n_image[i][j], n_ca, ne);
            ne++;
            fprintf(f, "CA_%d->\"histogram[image[%d][%d]]_%d\" [label=%d];\n", n_ca,i,j, n_histogramAtImage[i][j], ne);
            //---------------------
            histogram[image[i][j]] += 1;
        }
    }

    /* Compute the mapping from the old to the new gray levels */

    //---------------------
    n_const++;
    fprintf(f, "const%d [label=\"0.0\", att1=const];\n", n_const);
    n_cdf++;
    fprintf(f, "cdf_%d [label=\"cdf\", att1=var, att2=loc, att3=float];\n", n_cdf);
    ne++;
    fprintf(f, "\"const%d\"->\"cdf_%d\" [label=\"%d\", ord=\"%d\"];\n", n_const, n_cdf, ne, ne);
    //---------------------
    cdf = 0.0;

    //---------------------
    n_const++;
    fprintf(f, "const%d [label=\"%d\", att1=const];\n", n_const, N * N);
    n_pixels++;
    fprintf(f, "pixels_%d [label=\"pixels\", att1=var, att2=loc, att3=float];\n", n_pixels);
    ne++;
    fprintf(f, "\"const%d\"->\"pixels_%d\" [label=\"%d\", ord=\"%d\"];\n", n_const, n_pixels, ne, ne);
    //---------------------
    pixels = (float)(N * N);

    for (int i = 0; i < L; i++)
    {
        //---------------------
        n_op++;
        fprintf(f, "op%d [label=\"/\", att1=op];\n", n_op);
        ne++;
        fprintf(f, "\"histogram[%d]_%d_l\"->\"op%d\" [label=\"%d\", pos=l];\n", i, n_histogram[i], n_op, ne);
        ne++;
        fprintf(f, "\"pixels_%d\"->\"op%d\" [label=\"%d\", pos=r];\n", n_pixels, n_op, ne);
        n_temp++;
        fprintf(f, "temp%d [label=\"temp_l70_i%d\", att1=var, att2=loc, att3=float ];\n", n_temp, n_temp);
        ne++;
        fprintf(f, "\"op%d\"->\"temp_%d\" [label=\"%d\", ord=\"%d\"];\n", n_op, n_temp, ne, ne);
        n_op++;
        fprintf(f, "op%d [label=\"+\", att1=op];\n", n_op);
        ne++;
        fprintf(f, "\"temp_%d\"->\"op%d\" [label=\"%d\", ord=\"%d\"];\n", n_temp, n_op, ne, ne);
        ne++;
        fprintf(f, "\"cdf_%d\"->\"op%d\" [label=\"%d\", ord=\"%d\"];\n", n_cdf, n_op, ne, ne);
        n_cdf++;
        fprintf(f, "\"cdf_%d\" [label=\"cdf\", att1=var, att2=loc, att3=float ];\n", n_cdf);
        ne++;
        fprintf(f, "\"op%d\"->\"cdf_%d\" [label=\"%d\", ord=\"%d\"];\n", n_op, n_cdf, ne, ne);
        //---------------------
        cdf += ((float)(histogram[i])) / pixels;

        //---------------------
        n_op++;
        fprintf(f, "op%d [label=\"*\", att1=op];\n", n_op);
        n_const++;
        fprintf(f, "const%d [label=\"%.1f\", att1=const];\n", n_const, 255.0f);
        ne++;
        fprintf(f, "\"cdf_%d\"->\"op%d\" [label=\"%d\", ord=\"%d\"];\n", n_cdf, n_op, ne, ne);
        ne++;
        fprintf(f, "\"const_%d\"->\"op%d\" [label=\"%d\", ord=\"%d\"];\n", n_const, n_op, ne, ne);
        n_temp++;
        fprintf(f, "temp%d [label=\"temp_l70_i%d\", att1=var, att2=loc, att3=float ];\n", n_temp, n_temp);
        ne++;
        fprintf(f, "\"op%d\"->\"temp_%d\" [label=\"%d\", ord=\"%d\"];\n", n_op, n_temp, ne, ne);
        n_glm[i]++;
        fprintf(f, "\"glm[%d]_%d_l\" [label=\"glm[%d]\", att1=var, att2=param, att3=int ];\n", i, n_glm[i], i);
        ne++;
        fprintf(f, "\"temp%d\"->\"glm[%d]_%d_l\" [label=\"%d\", ord=\"%d\"];\n", n_temp, i, n_glm[i], ne, ne);
        //---------------------
        glm[i] = (int)(255.0 * cdf);
    }

    /* Map the old gray levels in the original image to the new gray levels. */

    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N; ++j)
        {
            //---------------------
            n_glmAtImage[i][j]++;
            fprintf(f, "\"glm[image[%d][%d]]_%d\" [label=\"glm[image[%d][%d]]\", att1=var, att2=param, att3=int];\n",i,j,n_glmAtImage[i][j],i,j);
            n_aa++;
            fprintf(f, "AA_%d [label=AA_%d, att1=nop];\n", n_aa, n_aa);
            ne++;
            fprintf(f, "\"image[%d][%d]_%d\"->AA_%d [label=%d];\n", i, j, n_image[i][j], n_aa, ne);
            ne++;
            fprintf(f, "AA_%d->\"glm[image[%d][%d]]_%d\" [label=%d];\n", n_aa,i,j,n_glmAtImage[i][j], ne);
            n_image[i][j]++;
            fprintf(f, "\"image[%d][%d]_%d\" [label=\"image[%d][%d]\", att1=var, att2=param, att3=int ];\n", i, j, n_image[i][j], i, j);
            n_assign++;
            fprintf(f, "Assign_%d [label=Assign_%d, att1=assignment];\n", n_assign, n_assign);
            ne++;
            fprintf(f, "\"glm[image[%d][%d]]_%d\"->Assign_%d [label=\"%d\"];\n",i,j,n_glmAtImage[i][j],n_assign, ne);
            ne++;
            fprintf(f, "Assign_%d->\"image[%d][%d]_%d\" [label=%d]", n_assign, i, j, n_image[i][j], ne);
            //---------------------
            image[i][j] = glm[image[i][j]];
        }
    }
    //---------------------
    fprintf(f, "}\n");
    fclose(f);
    //---------------------
}

int main()
{
    /* Get the original image */
    int image[N][N] = {0};
    int histogram[L] = {0};
    int glm[L] = {0};

    input_dsp(image, N * N, 1);

    hist(image, histogram, glm);

    /* Return the histogram equalized image. */

    output_dsp(image, N * N, 1);
    output_dsp(histogram, L, 1);
    output_dsp(glm, L, 1);
}
