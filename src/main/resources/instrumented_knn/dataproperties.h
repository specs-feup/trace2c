/**
	k-NN
	
	Version by Jo�o MP Cardoso
	Email: jmpc@fe.up.pt
	
	April 2020
	FEUP, Porto, Portugal
*/

#ifndef DATAPROPERTIES_H
#define DATAPROPERTIES_H

/* BEGIN: parameters according to the use case. */
#define NUM_FEATURES 32     //5 // number of features used
#define K 3                    // value of k in kNN
#define NUM_CLASSES 2//8          // number of classes considered
#define NUM_KNOWN_POINTS 10000 //10000 //8 //instances of the model used after training




#define MAX_VALUE_OF_FEATURE 1

#define ftype float // type used for features
#define dtype float // type used for distance calculation
#define ctype char   // type used for class ID, an integer

#if dtype == double
#define MAXDISTANCE DBL_MAX
#else
#define MAXDISTANCE FLT_MAX
#endif

/* END: parameters according to the use case. */

#endif
