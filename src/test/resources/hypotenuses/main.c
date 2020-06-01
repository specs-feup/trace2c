#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include "hypotenuses_lvl1.c"

#define NUMBER_OF_TRIANGLES 10000

void readInput(FILE *fp, unsigned int triangle_side_a[NUMBER_OF_TRIANGLES], unsigned int triangle_side_b[NUMBER_OF_TRIANGLES]) {
    // open file
    size_t len = 255;
    // need malloc memory for line, if not, segmentation fault error will occurred.
    char *line = malloc(sizeof(char) * len);
    // check if file exist (and you can open it) or not
    if (fp == NULL) {
        printf("can open file input.txt!");
        return;
    }
    int i=0;
    while(fgets(line, len, fp) != NULL) {
        char *s = line;
        sscanf(s, "%d %d", &triangle_side_a[i], &triangle_side_b[i]);
        i++;
    }
    free(line);
}

void produceOutput(FILE* fp, unsigned int hypotenuse[NUMBER_OF_TRIANGLES]) {
    for (int i = 0; i < NUMBER_OF_TRIANGLES; i++) {
        fprintf(fp, "%d\n", hypotenuse[i]);
    }
}


int main() {
    FILE *inputFile = fopen("input.txt", "r");
    FILE *outputFile = fopen("outputLvl1.txt", "w");

    unsigned int triangle_side_a[NUMBER_OF_TRIANGLES];
    unsigned int triangle_side_b[NUMBER_OF_TRIANGLES];
    unsigned int hypotenuse[NUMBER_OF_TRIANGLES];

    readInput(inputFile, triangle_side_a, triangle_side_b);

    hypotenuseLvl1(triangle_side_a, triangle_side_b, hypotenuse);
    
    produceOutput(outputFile, hypotenuse);

    return 0;
}