package com.anup.interviewprep;

public class Problem6 {

	public static void main(String[] args) {
		// 6: Write a Java program to calculate the factorial of a number.

		int num = 6;
		long factorial = 1;
		
		// factorial of 6 = 1x2x3x4x5x6
		for(int i = 1; i <= num; i++) {
			factorial *= i;
		}
		
		System.out.println("Factorial of " + num + " is: " + factorial);
	}

}
