package com.anup.interviewprep;

public class Problem12 {

	public static void main(String[] args) {
		// 12: Write a Java program to count the number of digits in a number.

		int num = 12345, original = num, count = 0;
		
		while(num != 0) {
			num /= 10;
			count++;
		}
		
		System.out.println("Number of digits in " + original + " is: " + count);
	}

}
