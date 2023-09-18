// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import frc.robot.RobotMap;

/**
 * The DriveTrainSubsystem class represents the subsystem responsible for
 * controlling the robot's drivetrain.
 * It handles the motor controllers, encoders, and PID controllers for precise
 * control and movement.
 */

public class DriveTrainSubsystem extends SubsystemBase {

  // Each motor controller is assigned a unique device ID and is set to use the kBrushless motor type, enabling control via the CAN interface.
  // The drivetrain employs primary and follower motors on both sides, ensuring synchronized movement.
  // You can adjust the device IDs in the code to match the robot's physical configuration.

  // Left side motor controllers

  private CANSparkMax leftDrivePrimary = new CANSparkMax(RobotMap.DriveConstants.kLDP_ID,
      MotorType.kBrushless);
  private CANSparkMax leftDriveFollower1 = new CANSparkMax(RobotMap.DriveConstants.kLDF1_ID,
      MotorType.kBrushless);
  private CANSparkMax leftDriveFollower2 = new CANSparkMax(RobotMap.DriveConstants.kLDF2_ID,
      MotorType.kBrushless);

  // Right side motor controllers

  private CANSparkMax rightDrivePrimary = new CANSparkMax(RobotMap.DriveConstants.kRDP_ID,
      MotorType.kBrushless);
  private CANSparkMax rightDriveFollower1 = new CANSparkMax(RobotMap.DriveConstants.kRDF1_ID,
      MotorType.kBrushless);
  private CANSparkMax rightDriveFollower2 = new CANSparkMax(RobotMap.DriveConstants.kRDF2_ID,
      MotorType.kBrushless);

  // Encoders for left and right sides of the drivetrain

  private RelativeEncoder leftEncoder;
  private RelativeEncoder rightEncoder;

  // PID controllers for left and right motors

  private PIDController leftPIDController;
  private PIDController rightPIDController;

  // Create an instance of ADAM to handle exception logging
  private final ADAM adam = new ADAM(null);

  /**
   * Constructs a new DriveTrainSubsystem object.
   * Initializes the motors, encoders, and PID controllers.
   */

  public DriveTrainSubsystem() {
    initializeMotors();
    initializeEncoders();
    initializePIDControllers();
  }

  private void initializeMotors() {
    // The above code restores the factory defaults for the CANSparkMax objects associated with the left and right sides of the drivetrain.
    // By invoking the restoreFactoryDefaults() method on each motor controller, the drivetrain is reset to its default settings.

    leftDrivePrimary.restoreFactoryDefaults();
    leftDriveFollower1.restoreFactoryDefaults();
    leftDriveFollower2.restoreFactoryDefaults();

    rightDrivePrimary.restoreFactoryDefaults();
    rightDriveFollower1.restoreFactoryDefaults();
    rightDriveFollower2.restoreFactoryDefaults();

    // By calling the follow() method on the follower motors and passing the primary motor as the argument, the follower motors are set to synchronize their movement with the primary motor.
    // This ensures that the left and right sides of the drivetrain move together in a coordinated manner.

    leftDriveFollower1.follow(leftDrivePrimary);
    leftDriveFollower2.follow(leftDrivePrimary);

    rightDriveFollower1.follow(rightDrivePrimary);
    rightDriveFollower2.follow(rightDrivePrimary);

    // By calling the setInverted() method on each motor, the direction of motor
    // rotation is adjusted.
    // For the left side, all motors (primary and followers) are set to be
    // inverted (reversed) by passing 'true' as the argument, meaning they rotate in
    // the desired direction for the robot's movement.
    // For the right side, all motors are set to 'false' (not inverted), meaning
    // they rotate in the default direction.

    leftDrivePrimary.setInverted(true);
    leftDriveFollower1.setInverted(true);
    leftDriveFollower2.setInverted(true);

    rightDrivePrimary.setInverted(false);
    rightDriveFollower1.setInverted(false);
    rightDriveFollower2.setInverted(false);
  }

  private void initializeEncoders() {
    // Assigning encoders to track the position and velocity of the right and left drivetrain sides
    // By calling the 'getEncoder()' method on the right and left primary motor controllers, we obtain the corresponding encoders and store them in 'mRightEncoder' and 'mLeftEncoder' variables respectively.

    leftEncoder = leftDrivePrimary.getEncoder();
    rightEncoder = rightDrivePrimary.getEncoder();
  }

  private void initializePIDControllers() {
    // Create PID controllers for left and right sides of the drive train with the
    // specified proportional (kP), integral (kI), and derivative (kD) gains.

    leftPIDController = new PIDController(RobotMap.PIDConstants.kP, RobotMap.PIDConstants.kI, RobotMap.PIDConstants.kD);
    rightPIDController = new PIDController(RobotMap.PIDConstants.kP, RobotMap.PIDConstants.kI, RobotMap.PIDConstants.kD);
  }

  /**
   * Executes periodically as part of the subsystem's update loop.
   * Currently does nothing.
   */

  @Override
  public void periodic() {
    runTest(() -> {
      // This method will be called once per scheduler run
    });
  }

  /**
   * Sets the desired drive speeds for the left and right sides of the drivetrain.
   * The speeds are limited to the range [-1, 1] and PID controllers are used to adjust the motor outputs accordingly.
   *
   * @param leftSpeed  The desired speed for the left side of the drivetrain.
   *
   * @param rightSpeed The desired speed for the right side of the drivetrain.
   */

  public void setDriveSpeeds(double leftSpeed, double rightSpeed) {  
    runTest(() -> {
      // Calculate errors compared to speed setpoints
      double leftSetpoint = Math.max(-1.0, Math.min(leftSpeed, 1.0));
      double rightSetpoint = Math.max(-1.0, Math.min(rightSpeed, 1.0));
        
      double leftError = leftSetpoint - getLeftVelocity();
      double rightError = rightSetpoint - getRightVelocity();
      
      // Use PID controllers to calculate outputs
      double leftOutput = leftPIDController.calculate(leftError);
      double rightOutput = rightPIDController.calculate(rightError);
      
      // Set motor speeds  
      leftDrivePrimary.set(leftOutput);
      rightDrivePrimary.set(rightOutput);
      
      // Update dashboard
      updateSmartDashboard();
    });
  }
  

  /**
   * Updates the SmartDashboard with relevant values such as encoder positions, motor outputs, and PID controller outputs.
   * This method is called periodically.
   */

  private void updateSmartDashboard() {
    runTest(() -> {
      // Update SmartDashboard with encoder values

      SmartDashboard.putNumber("Left Encoder Position", getLeftPosition());
      SmartDashboard.putNumber("Right Encoder Position", getRightPosition());
      SmartDashboard.putNumber("Left Encoder Velocity", getLeftVelocity());
      SmartDashboard.putNumber("Right Encoder Velocity", getRightVelocity());

      // Displaying motor output values on the SmartDashboard

      SmartDashboard.putNumber("Left Motor Output", leftDrivePrimary.getAppliedOutput());
      SmartDashboard.putNumber("Right Motor Output", rightDrivePrimary.getAppliedOutput());

      // Calculating and displaying PID controller output values on the SmartDashboard

      SmartDashboard.putNumber("Left PID Output", leftPIDController.calculate(getLeftVelocity()));
      SmartDashboard.putNumber("Right PID Output", rightPIDController.calculate(getRightVelocity()));
    });
  }

  /**
   * Resets the drivetrain by setting the encoder positions to zero and stopping
   * the motors.
   */

  public void reset() {
    runTest(() -> {
      // The encoder velocity is calculated based on the change in position over time, so when you reset the position, the velocity will naturally become zero.

      leftEncoder.setPosition(0);
      rightEncoder.setPosition(0);

      leftDrivePrimary.stopMotor();
      rightDrivePrimary.stopMotor();
    });
  }

  // Utilizing encoders allows for autonomous navigation, closed-loop control, odometry, and collision detection, enhancing robot performance and reliability.

  /**
   * Returns the position of the left encoder.
   *
   * @return The position of the left encoder in units determined by the encoder
   *         type.
   */

  public double getLeftPosition() {
    return leftEncoder.getPosition();
  }

  /**
   * Returns the position of the right encoder.
   *
   * @return The position of the right encoder in units determined by the encoder
   *         type.
   */

  public double getRightPosition() {
    return rightEncoder.getPosition();
  }

  /**
   * Returns the velocity of the left encoder.
   *
   * @return The velocity of the left encoder in units per second determined by
   *         the encoder type.
   */

  public double getLeftVelocity() {
    return leftEncoder.getVelocity();
  }

  /**
   * Returns the velocity of the right encoder.
   *
   * @return The velocity of the right encoder in units per second determined by
   *         the encoder type.
   */

  public double getRightVelocity() {
    return rightEncoder.getVelocity();
  }

  /**
   * Returns the output of the left primary motor.
   *
   * @return The output of the left primary motor in the range [-1, 1].
   */

  public double getLeftMotorOutput() {
    return leftDrivePrimary.getAppliedOutput();
  }

  /**
   * Returns the output of the right primary motor.
   *
   * @return The output of the right primary motor in the range [-1, 1].
   */

  public double getRightMotorOutput() {
    return rightDrivePrimary.getAppliedOutput();
  }

  /**
   * Executes custom testing and validation methods within a controlled testing environment.
   * Any exceptions thrown during execution are caught and logged.
   *
   * @see #runTest(Runnable)
   */
  public void debugSubsystem() {
    runTest(() -> periodic());
    runTest(() -> setDriveSpeeds(0, 0));
    runTest(() -> updateSmartDashboard());
    runTest(() -> reset());
  }

  /**
   * Runs the provided code as a runnable task. If the code throws an exception, it is caught, and an uncaught exception is passed to the default uncaught exception handler for the current thread.
   *
   * @param code The runnable task to be executed.
   */
  public void runTest(Runnable code) {
    try {
      code.run();
    } catch (Exception e) {
      adam.uncaughtException(Thread.currentThread(), e);
    }
  }
}
