package com.anup.interviewprep;

public class Problem5 {

	public static void main(String[] args) {
		// 5: Write a Java program to find the largest of two numbers.

		int num1 = 7, num2 = 10, greatest = 0;
		
		if(num1 > num2) greatest = num1;
		else greatest = num2;
		
		System.out.println("Greatest number between " + num1 + " and " + num2 + " is " + greatest);
		
	}

}
