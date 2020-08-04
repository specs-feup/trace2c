#include <stdio.h>

void array_access( int a[5], int input) {
    a[input] = a[input] + 3;
}


int main() {
    int a[5] = {1, 2, 10, 4, 6};
    int index = 0;
    array_access(a, index);
    printf("%d" , a[index]);
    return 0;
}