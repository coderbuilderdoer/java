package com.anup.interviewprep;

public class Problem3 {

	public static void main(String[] args) {
		// 3: Write a Java program to swap two numbers using a temporary variable and without temp var.
		
		// with temp var
		int num1 = 14, num2 = 23, temp = num1;
		System.out.println("Before swapping WITH a temp var:\nnum1 = " + num1 + ", num2 = " + num2);
		
		num1 = num2;
		num2 = temp;
		
		System.out.println("After swapping:\nnum1 = " + num1 + ", num2 = " + num2);
		
		System.out.println();
		
		// without temp var
		num1 = 14; 
		num2 = 23;
		System.out.println("Before swapping WITHOUT a temp var:\nnum1 = " + num1 + ", num2 = " + num2);
		
		num1 = num1 + num2;
		num2 = num1 - num2;
		num1 = num1 - num2;
		
		System.out.println("After swapping:\nnum1 = " + num1 + ", num2 = " + num2);
	}

}
