package com.anup.interviewprep;

public class Problem7 {

	public static void main(String[] args) {
		// 7: Write a Java program to check whether a number is prime or not.

		int num = 29;
        boolean isPrime = true;
        
        if(num <= 1) {
            isPrime = false;
        } else {
            for(int i = 2; i <= Math.sqrt(num); i++) {
                if(num % i == 0) {
                    isPrime = false;
                    break;
                }
            }
        }
        
        System.out.println(num + " is prime? " + isPrime);
	}

}
