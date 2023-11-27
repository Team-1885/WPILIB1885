// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import lombok.Getter;

/** 
 * Manages controller input and bindings for the Operator Interface (OI). 
 */

 @SuppressWarnings("PMD.CommentSize") public class OperatorInput {

  /**
   * ...
   */
  private @Getter static ADAM adam = new ADAM(null);

  /**
   * ...
   */
  private @Getter XboxController driverXBXCtrlr;
  /**
   * ...
   */
  private @Getter PS4Controller driverPS4Ctrlr;
  /**
   * ...
   */
  private @Getter Joystick driverLGTCtrlr;

  /**
   * ...
   */
  private @Getter XboxController operatorXBXCtrlr;
  /**
   * ...
   */
  private @Getter PS4Controller operatorPS4Ctrlr;
  /**
   * ...
   */
  private @Getter Joystick operatorLGTCtrlr;

  /**
   * Constructs a new instance of the OI class. 
   * Initializes driver and operator controllers.
   */
  public OperatorInput() {
    runTest(() -> {
      driverXBXCtrlr = new XboxController(RobotMap.DriverConstants.D_XBOX_PORT);
      driverPS4Ctrlr = new PS4Controller(RobotMap.DriverConstants.D_PS4_PORT);
      driverLGTCtrlr = new Joystick(RobotMap.DriverConstants.D_LOGITECH_PORT);

      operatorXBXCtrlr = new XboxController(RobotMap.OperatorConstants.O_XBOX_PORT);
      operatorPS4Ctrlr = new PS4Controller(RobotMap.OperatorConstants.O_PS4_PORT);
      operatorLGTCtrlr = new Joystick(RobotMap.OperatorConstants.O_LOGITECH_PORT);
    });
  }

  /**
   * Executes controller access simulation for testing purposes.
   * Calls controller retrieval methods within a testing environment.
   */
  public void debugClass() {
    // ...
  }

  /**
   * Runs the provided code as a runnable task. If the code throws an exception, it is caught, and an uncaught exception is passed to the default uncaught exception handler for the current thread.
   *
   * @param code The runnable task to be executed.
   */
  public static void runTest(final Runnable code) {
    try {
      code.run();
    } catch (Exception e) {
      adam.uncaughtException(Thread.currentThread(), e);
    }
  }
}
