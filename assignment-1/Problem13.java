package com.anup.interviewprep;

public class Problem13 {

	public static void main(String[] args) {
		// 13: Write a Java program to find the largest element in an array.

		int[] nums = {43, 2, 4, 10, 1, 22};
		int largest = nums[0];
		
		for(int i = 0; i < nums.length; i++) {
			if(nums[i] > largest) largest = nums[i];
		}

        System.out.println("Largest element: " + largest);
	}

}
