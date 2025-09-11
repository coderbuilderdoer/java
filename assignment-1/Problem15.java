package com.anup.interviewprep;

public class Problem15 {

	public static void main(String[] args) {
		// 15: Write a Java program to find the second largest element in an array.

		int[] arr = {10, 5, 20, 8, 15};
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        
        for(int num : arr) {
            if(num > largest) {
                secondLargest = largest;
                largest = num;
            } else if(num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }
        
        System.out.println("Second largest element: " + secondLargest);
	}

}
