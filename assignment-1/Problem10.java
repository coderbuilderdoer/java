package com.anup.interviewprep;

public class Problem10 {

	public static void main(String[] args) {
		// 10: Write a Java program to reverse a number.

		int num = 12345, reversed = 0, original = num;
		
		while(num != 0) {
			int digit = num % 10;
			reversed = reversed * 10 + digit;
			num /= 10;
		}
		System.out.println("Original: " + original + ", Reversed: " + reversed);
	}

}
