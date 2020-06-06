#define SIZE 11

int sumArray(int a[SIZE]) {
    int sum = 0;
    for (int i = 0; i < SIZE; i++) {
        sum = sum + a[i];
    }
    return sum;
}