package com.anup.interviewprep;

public class Problem18 {

	public static void main(String[] args) {
		// 18: Write a Java program to search for an element in an array.

		int[] arr = {10, 20, 30, 40, 50};
        int target = 30;
        boolean found = false;
        int index = -1;
        
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == target) {
                found = true;
                index = i;
                break;
            }
        }
        
        if(found) {
            System.out.println("Element " + target + " found at index: " + index);
        } else {
            System.out.println("Element " + target + " not found in the array");
        }

	}

}
