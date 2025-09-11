package com.anup.interviewprep;

public class Problem14 {

	public static void main(String[] args) {
		// 14: Write a Java program to reverse an array.

		int[] arr = {1, 2, 3, 4, 5};
		
        System.out.println("Original array:");
        for(int num : arr) {
            System.out.print(num + " ");
        }

        // Reverse the array
        for(int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
        
        System.out.println("\nReversed array:");
        for(int num : arr) {
            System.out.print(num + " ");
        }
	}

}
