// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;

public class ExampleSubsystem extends SubsystemBase {
 
  // Create an instance of ADAM to handle exception logging
  private final ADAM adam;

  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    // Initialize the ADAM instance with a null exception (null indicates no exception initially)
    adam = new ADAM(null);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void testException() {
    try {
      int[] arr = new int[5];
      int value = arr[10]; // This will throw ArrayIndexOutOfBoundsException
    } catch (ArrayIndexOutOfBoundsException e) {
      // Use ADAM to log the exception
      adam.uncaughtException(Thread.currentThread(), e);
      throw e;
    }
  }
}
