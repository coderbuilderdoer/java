package com.anup.interviewprep;

public class Classwork {
	/* Sep 11, Thu
	 * Create a program which will create array of size 5. try to initialize 
	 * sixth element to that array. see which exception is coming
	 * if exception comes handle that exception using try and catch.
	 * */

	public static void main(String[] args) {
        
        // create an array of size 5
        int[] numbers = new int[5];
        
        // initialize the first five elements with some values
       for(int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1; // Assign values 1, 2, 3, 4, 5
        }
        
        // try to access and initialize the sixth element (index 5)
        // this will cause an ArrayIndexOutOfBoundsException because
        // arrays are zero indexed and our array only has indices 0 - 4
        try {
            System.out.println("Attempting to initialize sixth element...");
            numbers[5] = 6; // This line will throw the exception
            System.out.println("Sixth element initialized successfully: " + numbers[5]);
        } 
        // catch the specific exception that occurs when trying to access
        // an array index that doesn't exist
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Exception caught: " + e.getClass().getSimpleName());
            System.out.println("Error message: " + e.getMessage());
            System.out.println("You tried to access index 5, but the array only has indices 0 through 4");
        }
        
        // display the valid array elements
        System.out.println("\nValid array elements (indices 0-4):");
        for(int i = 0; i < numbers.length; i++) {
            System.out.println("numbers[" + i + "] = " + numbers[i]);
        }
    }

}
