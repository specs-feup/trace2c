
#include <math.h>
#include <stdio.h>

#define NUMBER_OF_TRIANGLES 10000

unsigned int square(unsigned int value) {
  return value*value;
}

void calc_hypotenuse(unsigned int first_array[], unsigned int second_array[], unsigned int hypotenuse_array[]) {
      unsigned int x = 0;
      for(x=0; x<NUMBER_OF_TRIANGLES; x++) {
              hypotenuse_array[x] = sqrt(square(first_array[x]) +  square(second_array[x]));
     }
}

/**
 * Generates the input and output files for the hypothenuse benchmark
 * */
int main() {
    FILE *inputFile = fopen("input.txt", "w");
    FILE *outputFile = fopen("output.txt", "w");


    unsigned int x = 0;
    unsigned int triangle_side_a[NUMBER_OF_TRIANGLES];
    unsigned int triangle_side_b[NUMBER_OF_TRIANGLES];
    unsigned int hypotenuse[NUMBER_OF_TRIANGLES];

    for(x=0; x<NUMBER_OF_TRIANGLES; x++) {
        triangle_side_a[x] = (x*2)+5;
        while(triangle_side_a[x] > 50000) {
            triangle_side_a[x] -= 1000;
        }
    }

    for(x=0; x<NUMBER_OF_TRIANGLES; x++) {
        triangle_side_b[x] = ((x+3)*3)+3;
        while(triangle_side_b[x] > 50000) {
            triangle_side_b[x] -= 787;
        }
    }

    for (int x=0; x < NUMBER_OF_TRIANGLES; x++) {
        fprintf(inputFile, "%d %d\n", triangle_side_a[x], triangle_side_b[x]);
    }

    calc_hypotenuse(triangle_side_a, triangle_side_b, hypotenuse);

    for (int x=0; x < NUMBER_OF_TRIANGLES; x++) {
        fprintf(outputFile, "%d\n", hypotenuse[x]);
    }
}


