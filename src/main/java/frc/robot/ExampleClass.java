// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public class ExampleClass {

    private final static ADAM adam = new ADAM(null);

    public static void main(String[] args) {
        runTest(() -> {
            throw new RuntimeException("This is a test exception!");
        });
    }

    public static void myMethod() {
        runTest(() -> {
            int[] arr = new int[5];
            int value = arr[10];
        });
    }

    public void debugClass() {
        runTest(() -> main(null));
        runTest(() -> myMethod());
    }

    public static void runTest(Runnable code) {
        try {
            code.run();
        } catch (Exception e) {
            adam.uncaughtException(Thread.currentThread(), e);
        }
    }
}
