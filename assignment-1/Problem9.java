package com.anup.interviewprep;

public class Problem9 {

	public static void main(String[] args) {
		// 9: Write a Java program to find the sum of digits of a given number.

		int num = 12345, sum = 0, original = num;
		
		while(num != 0) {
			sum += num % 10;
			num /= 10;
		}
		System.out.println("Sum of digits of " + original + " is: " + sum);
	}

}
