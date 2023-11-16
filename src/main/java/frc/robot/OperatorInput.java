// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;

/** 
 * Manages controller input and bindings for the Operator Interface (OI). 
 */

public class OperatorInput {

  private static final ADAM adam = new ADAM(null);

  private XboxController driverXBXCtrlr;
  private PS4Controller driverPS4Ctrlr;
  private Joystick driverLGTCtrlr;

  private XboxController operatorXBXCtrlr;
  private PS4Controller operatorPS4Ctrlr;
  private Joystick operatorLGTCtrlr;

  /**
   * Constructs a new instance of the OI class. 
   * Initializes driver and operator controllers.
   */
  public OperatorInput() {
    runTest(() -> {
      driverXBXCtrlr = new XboxController(RobotMap.DriverConstants.DriverXBXCtrlrPort);
      driverPS4Ctrlr = new PS4Controller(RobotMap.DriverConstants.DriverPS4CtrlrPort);
      driverLGTCtrlr = new Joystick(RobotMap.DriverConstants.DriverLGTCtrlrPort);

      operatorXBXCtrlr = new XboxController(RobotMap.OperatorConstants.kOperatorXBXCtrlrPort);
      operatorPS4Ctrlr = new PS4Controller(RobotMap.OperatorConstants.kOperatorPS4CtrlrPort);
      operatorLGTCtrlr = new Joystick(RobotMap.OperatorConstants.kOperatorLGTCtrlrPort);
    });
  }

  /**
   * Get the driver Xbox controller. 
   *
   * @return The driver Xbox controller. 
   */
  public XboxController getDriverXboxController() {
    return driverXBXCtrlr;
  }

  /**
   * Get the driver PS4 controller.
   *
   * @return The driver PS4 controller. 
   */
  public PS4Controller getDriverPS4Controller() {
    return driverPS4Ctrlr;
  }

  /**
   * Get the driver Logitech controller. 
   *
   * @return The driver Logitech controller. 
   */
  public Joystick getDriverLogitechController() {
    return driverLGTCtrlr;
  }

  /**
   * Get the operator Xbox controller. 
   *
   * @return The operator Xbox controller. 
   */
  public XboxController getOperatorXboxController() {
    return operatorXBXCtrlr;
  }

  /**
   * Get the operator PS4 controller. 
   *
   * @return The operator PS4 controller. 
   */
  public PS4Controller getOperatorPS4Controller() {
    return operatorPS4Ctrlr;
  }

  /**
   * Get the operator Logitech controller. 
   *
   * @return The operator Logitech controller. 
   */
  public Joystick getOperatorLogitechController() {
    return operatorLGTCtrlr;
  }

  /**
   * Executes controller access simulation for testing purposes.
   * Calls controller retrieval methods within a testing environment.
   *
   * @see #getDriverXboxController()
   * @see #getOperatorXboxController()
   * @see #getDriverPS4Controller()
   * @see #getOperatorPS4Controller()
   * @see #getDriverLogitechController()
   * @see #getOperatorLogitechController()
   */
  public void debugClass() {
    runTest(() -> getDriverXboxController());
    runTest(() -> getOperatorXboxController());

    runTest(() -> getDriverPS4Controller());
    runTest(() -> getOperatorPS4Controller());

    runTest(() -> getDriverLogitechController());
    runTest(() -> getOperatorLogitechController());
  }

  /**
   * Runs the provided code as a runnable task. If the code throws an exception, it is caught, and an uncaught exception is passed to the default uncaught exception handler for the current thread.
   *
   * @param code The runnable task to be executed.
   */
  public static void runTest(Runnable code) {
    try {
      code.run();
    } catch (Exception e) {
      adam.uncaughtException(Thread.currentThread(), e);
    }
  }
}
