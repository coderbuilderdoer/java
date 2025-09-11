package com.anup.interviewprep;

import java.util.Arrays;

public class Problem16 {

	public static void main(String[] args) {
		// 16: Write a Java program to remove duplicates from an array.

		int[] arr = {1, 2, 2, 3, 4, 4, 5};
        Arrays.sort(arr);
        
        int uniqueCount = 0;
        for(int i = 0; i < arr.length; i++) {
            if(i == 0 || arr[i] != arr[i - 1]) {
                uniqueCount++;
            }
        }
        
        int[] result = new int[uniqueCount];
        int index = 0;
        for(int i = 0; i < arr.length; i++) {
            if(i == 0 || arr[i] != arr[i - 1]) {
                result[index++] = arr[i];
            }
        }
        
        System.out.println("Array without duplicates: " + Arrays.toString(result));

	}

}
