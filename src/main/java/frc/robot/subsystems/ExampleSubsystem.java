// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;

public class ExampleSubsystem extends SubsystemBase {

  // Create an instance of ADAM to handle exception logging
  private final ADAM adam = new ADAM(null);

  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    runTest(() -> {
      debugSubsystem();
    });
  }

  @Override
  public void periodic() {
    runTest(() -> {
      // This method will be called once per scheduler run
    });
  }

  public void myMethod() {
    runTest(() -> {
      // Code for method
    });
  }

  public void debugSubsystem() {
    runTest(() -> periodic());
    runTest(() -> myMethod());
  }

  public void runTest(Runnable code) {
    try {
      code.run();
    } catch (Exception e) {
      adam.uncaughtException(Thread.currentThread(), e);
    }
  }
}
