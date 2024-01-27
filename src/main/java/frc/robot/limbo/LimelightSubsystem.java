// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.limbo;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import lombok.Getter;

import edu.wpi.first.wpilutil.net.PortForwarder;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * A demonstration and testing subsystem that extends SubsystemBase.
 */

 @SuppressWarnings("PMD.CommentSize") public class LimelightSubsystem extends SubsystemBase {

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");

  //private @Getter ADAM adam = new ADAM(null);

  /** 
   * Creates a new ExampleSubsystem. 
   */
  public LimelightSubsystem() {
    super();
  }

  @Override public void periodic() {
    runTest(() -> {
      double x = tx.getDouble(0.0);
      double y = ty.getDouble(0.0);
      double area = ta.getDouble(0.0);

      SmartDashboard.putNumber("LimelightX", x);
      SmartDashboard.putNumber("LimelightX", y);
      SmartDashboard.putNumber("LimelightArea", area);
    });
  }

  @Override
    public void robotInit() 
    {
        // Make sure you only configure port forwarding once in your robot code.
        // Do not place these function calls in any periodic functions
        for (int port = 5800; port <= 5807; port++) {
            PortForwarder.add(port, "limelight.local", port);
        }
    }

  /**
   * Executes a custom method, running it within a testing environment.
   * This method is intended for testing and validation of specific functionality.
   *
   * @see #runTest(Runnable)
   */
  public void myMethod() {
    runTest(() -> {
      // Code for method
    });
  }


  /**
   * Executes custom testing and validation methods within a controlled testing environment.
   * Any exceptions thrown during execution are caught and logged.
   *
   * @see #runTest(Runnable)
   */
  public void debugSubsystem() {
    runTest(() -> periodic());
    runTest(() -> myMethod());
  }

  /**
   * Runs the provided code as a runnable task. If the code throws an exception, it is caught, and an uncaught exception is passed to the default uncaught exception handler for the current thread.
   *
   * @param code The runnable task to be executed.
   */
  public void runTest(final Runnable code) {
    try {
      code.run();
    } catch (Exception e) {
      adam.uncaughtException(Thread.currentThread(), e);
    }
  }
}
