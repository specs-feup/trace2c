
void hipotenuses_lvl2(unsigned int first_array[10], unsigned int second_array[10], unsigned int hypotenuse_array[10])
// Step 2: Initialize local variables
// Initialization done
{
// starting Loop
for( int i =0; i < 10; i=i+1){



hypotenuse_array[i] = sqrt(square(first_array[i]) + square(second_array[i]));

}

return hypotenuse_array;
}
