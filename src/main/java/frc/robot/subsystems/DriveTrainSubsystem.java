package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import java.util.logging.Level;
import java.util.logging.Logger;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Constants.OperatorConstants.*;

/**
 * The DriveTrainSubsystem class represents the subsystem responsible for controlling the robot's drivetrain.
 * It handles the motor controllers, encoders, and PID controllers for precise control and movement.
 */

// TODO: Gyro, Odometry, SmartDashboard/Format of Code??

public class DriveTrainSubsystem extends SubsystemBase {

  // * Each motor controller is assigned a unique device ID and is set to use the kBrushless motor type, enabling control via the CAN interface.
  // ! The drivetrain employs primary and follower motors on both sides, ensuring synchronized movement.
  // * You can adjust the device IDs in the code to match your robot's physical configuration.

  // * Left side motor controllers

  private CANSparkMax mLeftDrivePrimaryMotor = new CANSparkMax(Constants.OperatorConstants.LEFT_DRIVE_PRIMARY_ID,
      MotorType.kBrushless);
  private CANSparkMax mLeftDriveFollowerMotor1 = new CANSparkMax(Constants.OperatorConstants.LEFT_DRIVE_FOLLOWER1_ID,
      MotorType.kBrushless);
  private CANSparkMax mLeftDriveFollowerMotor2 = new CANSparkMax(Constants.OperatorConstants.LEFT_DRIVE_FOLLOWER2_ID,
      MotorType.kBrushless);

  // * Right side motor controllers

  private CANSparkMax mRightDrivePrimaryMotor = new CANSparkMax(Constants.OperatorConstants.RIGHT_DRIVE_PRIMARY_ID,
      MotorType.kBrushless);
  private CANSparkMax mRightDriveFollowerMotor1 = new CANSparkMax(Constants.OperatorConstants.RIGHT_DRIVE_FOLLOWER1_ID,
      MotorType.kBrushless);
  private CANSparkMax mRightDriveFollowerMotor2 = new CANSparkMax(Constants.OperatorConstants.RIGHT_DRIVE_FOLLOWER2_ID,
      MotorType.kBrushless);

  // * Encoders for left and right sides of the drivetrain

  private RelativeEncoder mLeftEncoder;
  private RelativeEncoder mRightEncoder;

  // * PID controllers for left and right motors

  private PIDController mLeftPIDController;
  private PIDController mRightPIDController;

  // * Logger instance for logging events and errors

  private static final Logger logger = Logger.getLogger(DriveTrainSubsystem.class.getName());

  /**
 * Constructs a new DriveTrainSubsystem object.
 * Initializes the motors, encoders, and PID controllers.
 */

  public DriveTrainSubsystem() {
    try {
      // * Initializes the motor controllers
      initializeMotors();
      // * Initializes the encoders
      initializeEncoders();
      // * Initializes the PID controllers
      initializePIDControllers();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "DriveTrainSubsystem initialization failed", e);
      DriverStation.reportError("DriveTrainSubsystem initialization failed: " + e.getMessage(), true);
    }
  }

  private void initializeMotors() {
    try {
      // * The above code restores the factory defaults for the CANSparkMax objects associated with the left and right sides of the drivetrain.
      // ! By invoking the restoreFactoryDefaults() method on each motor controller, the drivetrain is reset to its default settings.

      mLeftDrivePrimaryMotor.restoreFactoryDefaults();
      mLeftDriveFollowerMotor1.restoreFactoryDefaults();
      mLeftDriveFollowerMotor2.restoreFactoryDefaults();

      mRightDrivePrimaryMotor.restoreFactoryDefaults();
      mRightDriveFollowerMotor1.restoreFactoryDefaults();
      mRightDriveFollowerMotor2.restoreFactoryDefaults();

      // ! By calling the follow() method on the follower motors and passing the primary motor as the argument, the follower motors are set to synchronize their movement with the primary motor.
      // * This ensures that the left and right sides of the drivetrain move together in a coordinated manner.

      mLeftDriveFollowerMotor1.follow(mLeftDrivePrimaryMotor);
      mLeftDriveFollowerMotor2.follow(mLeftDrivePrimaryMotor);

      mRightDriveFollowerMotor1.follow(mRightDrivePrimaryMotor);
      mRightDriveFollowerMotor2.follow(mRightDrivePrimaryMotor);

      // ! By calling the setInverted() method on each motor, the direction of motor rotation is adjusted.
      // * For the left side, all motors (primary and followers) are set to be inverted (reversed) by passing 'true' as the argument, meaning they rotate in the desired direction for the robot's movement.
      // * For the right side, all motors are set to 'false' (not inverted), meaning they rotate in the default direction.

      mLeftDrivePrimaryMotor.setInverted(true);
      mLeftDriveFollowerMotor1.setInverted(true);
      mLeftDriveFollowerMotor2.setInverted(true);

      mRightDrivePrimaryMotor.setInverted(false);
      mRightDriveFollowerMotor1.setInverted(false);
      mRightDriveFollowerMotor2.setInverted(false);

    } catch (Exception e) {
      logger.log(Level.SEVERE, "DriveTrainSubsystem motor initialization failed", e);
      DriverStation.reportError("DriveTrainSubsystem motor initialization failed: " + e.getMessage(), true);
    }
  }

  private void initializeEncoders() {
    try {
      // * Assigning encoders to track the position and velocity of the right and left drivetrain sides
      // ! By calling the 'getEncoder()' method on the right and left primary motor controllers, we obtain the corresponding encoders and store them in 'mRightEncoder' and 'mLeftEncoder' variables respectively.

      mRightEncoder = mRightDrivePrimaryMotor.getEncoder();
      mLeftEncoder = mLeftDrivePrimaryMotor.getEncoder();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "DriveTrainSubsystem encoder initialization failed", e);
      DriverStation.reportError("DriveTrainSubsystem encoder initialization failed: " + e.getMessage(), true);
    }
  }

  private void initializePIDControllers() {
    try {
      // * Create PID controllers for left and right sides of the drive train with the specified proportional (kP), integral (kI), and derivative (kD) gains.

      mLeftPIDController = new PIDController(kP, kI, kD);
      mRightPIDController = new PIDController(kP, kI, kD);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "DriveTrainSubsystem PIDController initialization failed", e);
      DriverStation.reportError("DriveTrainSubsystem PIDController initialization failed: " + e.getMessage(), true);
    }
  }

  /**
 * Executes periodically as part of the subsystem's update loop.
 * Currently does nothing.
 */

  @Override
  public void periodic() {
    try {

    } catch (Exception e) {
      logger.log(Level.SEVERE, "DriveTrainSubsystem periodic failed", e);
      DriverStation.reportError("DriveTrainSubsystem periodic failed: " + e.getMessage(), true);
    }
  }

  /**
 * Sets the desired drive speeds for the left and right sides of the drivetrain.
 * The speeds are limited to the range [-1, 1] and PID controllers are used to adjust the motor outputs accordingly.
 * @param leftSpeed The desired speed for the left side of the drivetrain.
 * @param rightSpeed The desired speed for the right side of the drivetrain.
 */

  public void setDriveSpeeds(double leftSpeed, double rightSpeed) {
    try {
      // * Check if CANSparkMax objects are initialized

      if (mLeftDrivePrimaryMotor != null && mRightDrivePrimaryMotor != null) {

        // * Limit the speed values within a valid range (-1.0 to 1.0)

        leftSpeed = Math.max(-1.0, Math.min(leftSpeed, 1.0));
        rightSpeed = Math.max(-1.0, Math.min(rightSpeed, 1.0));

        // * Calculate the error by subtracting the current position from the desired setpoint

        double leftError = leftSpeed - getLeftVelocity();
        double rightError = rightSpeed - getRightVelocity();

        // * Use the PID controllers to calculate the control outputs

        double leftOutput = mLeftPIDController.calculate(leftError);
        double rightOutput = mRightPIDController.calculate(rightError);

        // ! The set method adjusts the speed of the left and right drive motors based on the provided parameters (by using the .set() method).

        mLeftDrivePrimaryMotor.set(leftOutput);
        mRightDrivePrimaryMotor.set(rightOutput);

        updateSmartDashboard();
      }

      else {
        
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "DriveTrainSubsystem set failed", e);
      DriverStation.reportError("DriveTrainSubsystem set failed: " + e.getMessage(), true);
    }
  }

  /**
 * Updates the SmartDashboard with relevant values such as encoder positions, motor outputs, and PID controller outputs.
 * This method is called periodically.
 */

  private void updateSmartDashboard() {
    try {

    // * Update SmartDashboard with encoder values

    SmartDashboard.putNumber("Left Encoder Position", getLeftPosition());
    SmartDashboard.putNumber("Right Encoder Position", getRightPosition());
    SmartDashboard.putNumber("Left Encoder Velocity", getLeftVelocity());
    SmartDashboard.putNumber("Right Encoder Velocity", getRightVelocity());

    // * Displaying motor output values on the SmartDashboard

    SmartDashboard.putNumber("Left Motor Output", mLeftDrivePrimaryMotor.getAppliedOutput());
    SmartDashboard.putNumber("Right Motor Output", mRightDrivePrimaryMotor.getAppliedOutput());

    // * Calculating and displaying PID controller output values on the SmartDashboard

    SmartDashboard.putNumber("Left PID Output", mLeftPIDController.calculate(getLeftVelocity()));
    SmartDashboard.putNumber("Right PID Output", mRightPIDController.calculate(getRightVelocity()));
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Error updating SmartDashboard", e);
      DriverStation.reportError("Error updating SmartDashboard: " + e.getMessage(), true);
    }
  }

  /**
 * Resets the drivetrain by setting the encoder positions to zero and stopping the motors.
 */

  public void reset() {
    try {

      // ! The encoder velocity is calculated based on the change in position over time, so when you reset the position, the velocity will naturally become zero.

      mLeftEncoder.setPosition(0);
      mRightEncoder.setPosition(0);

      mLeftDrivePrimaryMotor.stopMotor();
      mRightDrivePrimaryMotor.stopMotor();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "DriveTrainSubsystem reset failed", e);
      DriverStation.reportError("DriveTrainSubsystem reset failed: " + e.getMessage(), true);
    }
  }

  // * Utilizing encoders allows for autonomous navigation, closed-loop control, odometry, and collision detection, enhancing robot performance and reliability.

  /**
 * Returns the position of the left encoder.
 * @return The position of the left encoder in units determined by the encoder type.
 */

  public double getLeftPosition() {
    try {
      return mLeftEncoder.getPosition();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to get LeftEncoderPosition", e);
      DriverStation.reportError("Failed to get LeftEncoderPosition: " + e.getMessage(), true);
      return 0.0;
    }
  }

  /**
 * Returns the position of the right encoder.
 * @return The position of the right encoder in units determined by the encoder type.
 */

  public double getRightPosition() {
    try {
      return mRightEncoder.getPosition();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to get RightEncoderPosition", e);
      DriverStation.reportError("Failed to get RightEncoderPosition: " + e.getMessage(), true);
      return 0.0;
    }
  }

  /**
 * Returns the velocity of the left encoder.
 * @return The velocity of the left encoder in units per second determined by the encoder type.
 */

  public double getLeftVelocity() {
    try {
      return mLeftEncoder.getVelocity();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to get LeftEncoderVelocity", e);
      DriverStation.reportError("Failed to get LeftEncoderVelocity: " + e.getMessage(), true);
      return 0.0;
    }
  }

  /**
 * Returns the velocity of the right encoder.
 * @return The velocity of the right encoder in units per second determined by the encoder type.
 */

  public double getRightVelocity() {
    try {
      return mRightEncoder.getVelocity();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to get RightEncoderVelocity", e);
      DriverStation.reportError("Failed to get RightEncoderVelocity: " + e.getMessage(), true);
      return 0.0;
    }
  }

  /**
 * Returns the output of the left primary motor.
 * @return The output of the left primary motor in the range [-1, 1].
 */

  public double getLeftMotorOutput() {
    try {
      return mLeftDrivePrimaryMotor.getAppliedOutput();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to get LeftMotorOutput", e);
      DriverStation.reportError("Failed to get LeftMotorOuput: " + e.getMessage(), true);
      return 0.0;
    }
  }

  /**
 * Returns the output of the right primary motor.
 * @return The output of the right primary motor in the range [-1, 1].
 */

  public double getRightMotorOutput() {
    try {
      return mRightDrivePrimaryMotor.getAppliedOutput();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to get RightMotorOutput", e);
      DriverStation.reportError("Failed to get RightMotorOutput: " + e.getMessage(), true);
      return 0.0;
    }
  }
}
