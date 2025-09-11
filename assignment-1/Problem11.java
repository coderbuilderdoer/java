package com.anup.interviewprep;

public class Problem11 {

	public static void main(String[] args) {
		// 11: Write a Java program to print the Fibonacci series up to n terms.

		int n = 10;
        int first = 0, second = 1;
        
        System.out.println("Fibonacci series up to " + n + " terms:");
        for(int i = 1; i <= n; i++) {
            System.out.print(first + " ");
            int next = first + second;
            first = second;
            second = next;
        }

	}

}
