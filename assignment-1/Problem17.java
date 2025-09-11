package com.anup.interviewprep;

import java.util.Arrays;

public class Problem17 {
	
	public static void main(String[] args) {
		// 17: Write a Java program to sort an array in ascending order.

		int[] arr = {5, 2, 8, 1, 9};
        
        System.out.println("Original array: " + Arrays.toString(arr));
        
        // Using built-in sort
        Arrays.sort(arr);
        
        System.out.println("Sorted array: " + Arrays.toString(arr));
	}
}
