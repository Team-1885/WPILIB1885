// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public class TestADAM {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new ADAM(null));
        // Call the method that will throw the ArrayIndexOutOfBoundsException
        //testArrayOutOfBounds();
        System.out.println("Running in thread: " + Thread.currentThread().getName());
        throw new RuntimeException("This is a test exception!");
    }

    public static void testArrayOutOfBounds() {
        // Create an array with size 5
        int[] arr = new int[5];

        // Attempt to access the element at index 10, which will cause an ArrayIndexOutOfBoundsException
        int value = arr[10];
        throw new RuntimeException("Deliberate exception");
    }
}
