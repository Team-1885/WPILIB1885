package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// TODO: Gyro, Odometry

public class DriveTrainSubsystem extends SubsystemBase {

  // * The code below initializes the CANSparkMax objects for the left and right sides of the drivetrain.
  // * Each motor controller is assigned a unique device ID and is set to use the kBrushless motor type, enabling control via the CAN interface.
  // ! The drivetrain employs primary and follower motors on both sides, ensuring synchronized movement.
  // * You can adjust the device IDs in the code to match your robot's physical configuration.

  private CANSparkMax mLeftDrivePrimary = new CANSparkMax(Constants.OperatorConstants.LEFT_DRIVE_PRIMARY_ID, MotorType.kBrushless);
  private CANSparkMax mLeftDriveFollower1 = new CANSparkMax(Constants.OperatorConstants.LEFT_DRIVE_FOLLOWER1_ID, MotorType.kBrushless);
  private CANSparkMax mLeftDriveFollower2 = new CANSparkMax(Constants.OperatorConstants.LEFT_DRIVE_FOLLOWER2_ID, MotorType.kBrushless);

  private CANSparkMax mRightDrivePrimary = new CANSparkMax(Constants.OperatorConstants.RIGHT_DRIVE_PRIMARY_ID, MotorType.kBrushless);
  private CANSparkMax mRightDriveFollower1 = new CANSparkMax(Constants.OperatorConstants.RIGHT_DRIVE_FOLLOWER1_ID, MotorType.kBrushless);
  private CANSparkMax mRightDriveFollower2 = new CANSparkMax(Constants.OperatorConstants.RIGHT_DRIVE_FOLLOWER2_ID, MotorType.kBrushless);

  // * Declaration of RelativeEncoder objects for left and right sides of the drivetrain.

  private RelativeEncoder mLeftEncoder;
  private RelativeEncoder mRightEncoder;

  // * PIDController objects for left and right motors.

  private PIDController mLeftPIDController;
  private PIDController mRightPIDController;

  // * Placeholder values for the PID controller gains.

  private static final double kP = 0.0;
  private static final double kI = 0.0;
  private static final double kD = 0.0;

  public DriveTrainSubsystem() {
    try {
      // * The above code restores the factory defaults for the CANSparkMax objects associated with the left and right sides of the drivetrain.
      // ! By invoking the restoreFactoryDefaults() method on each motor controller, the drivetrain is reset to its default settings.

      mLeftDrivePrimary.restoreFactoryDefaults();
      mLeftDriveFollower1.restoreFactoryDefaults();
      mLeftDriveFollower2.restoreFactoryDefaults();

      mRightDrivePrimary.restoreFactoryDefaults();
      mRightDriveFollower1.restoreFactoryDefaults();
      mRightDriveFollower2.restoreFactoryDefaults();

      // ! By calling the follow() method on the follower motors and passing the primary motor as the argument, the follower motors are set to synchronize their movement with the primary motor.
      // * This ensures that the left and right sides of the drivetrain move together in a coordinated manner.

      mLeftDriveFollower1.follow(mLeftDrivePrimary);
      mLeftDriveFollower2.follow(mLeftDrivePrimary);

      mRightDriveFollower1.follow(mRightDrivePrimary);
      mRightDriveFollower2.follow(mRightDrivePrimary);

      // ! By calling the setInverted() method on each motor, the direction of motor rotation is adjusted.
      // * For the left side, all motors (primary and followers) are set to be inverted (reversed) by passing 'true' as the argument, meaning they rotate in the desired direction for the robot's movement.
      // * For the right side, all motors are set to 'false' (not inverted), meaning they rotate in the default direction.
      // ! Adjusting the inversion of the motors allows for proper synchronization and control of the drivetrain.

      mLeftDrivePrimary.setInverted(true);
      mLeftDriveFollower1.setInverted(true);
      mLeftDriveFollower2.setInverted(true);

      mRightDrivePrimary.setInverted(false);
      mRightDriveFollower1.setInverted(false);
      mRightDriveFollower2.setInverted(false);

      // * Assigning encoders to track the position and velocity of the right and left drivetrain sides
      // ! By calling the 'getEncoder()' method on the right and left primary motor controllers, we obtain the corresponding encoders and store them in 'mRightEncoder' and 'mLeftEncoder' variables respectively.

      mRightEncoder = mRightDrivePrimary.getEncoder();
      mLeftEncoder = mLeftDrivePrimary.getEncoder();

      // * Create PID controllers for left and right sides of the drive train with the specified proportional (kP), integral (kI), and derivative (kD) gains.

      mLeftPIDController = new PIDController(kP, kI, kD);
      mRightPIDController = new PIDController(kP, kI, kD);

    } catch (Exception e) {
      DriverStation.reportError("DriveTrainSubsystem initialization failed: " + e.getMessage(), true);
    }
  }

  @Override
  public void periodic() {
    try {
      updateSmartDashboard();
    } catch (Exception e) {
      DriverStation.reportError("DriveTrainSubsystem periodic failed: " + e.getMessage(), true);
    }
  }

  private void updateSmartDashboard() {

    // ! This method will be called once per scheduler run (every 20ms)

    // * Update SmartDashboard with encoder values

    SmartDashboard.putNumber("Left Encoder Position", getLeftPosition());
    SmartDashboard.putNumber("Right Encoder Position", getRightPosition());
    SmartDashboard.putNumber("Left Encoder Velocity", getLeftVelocity());
    SmartDashboard.putNumber("Right Encoder Velocity", getRightVelocity());

    // * Displaying motor output values on the SmartDashboard

    SmartDashboard.putNumber("Left Motor Output", mLeftDrivePrimary.getAppliedOutput());
    SmartDashboard.putNumber("Right Motor Output", mRightDrivePrimary.getAppliedOutput());

    // * Calculating and displaying PID controller output values on the SmartDashboard

    SmartDashboard.putNumber("Left PID Output", mLeftPIDController.calculate(getLeftVelocity()));
    SmartDashboard.putNumber("Right PID Output", mRightPIDController.calculate(getRightVelocity()));
  }

  public void set(double leftSpeed, double rightSpeed) {
    try {
      // * Check if CANSparkMax objects are initialized

      if (mLeftDrivePrimary != null && mRightDrivePrimary != null) {

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

        mLeftDrivePrimary.set(leftOutput);
        mRightDrivePrimary.set(rightOutput);
      }

      else {
        System.out.println("CANSparkMax objects are not properly initialized!");
      }
    } catch (Exception e) {
      DriverStation.reportError("DriveTrainSubsystem set failed: " + e.getMessage(), true);
    }
  }

  public void reset() {
    try {
      
    // ! The .setPosition() method is called on both the left and right encoders to set their positions to 0.
    // ! The encoder velocity is calculated based on the change in position over time, so when you reset the position, the velocity will naturally become zero.

    mLeftEncoder.setPosition(0);
    mRightEncoder.setPosition(0);

    // ! The .stopMotor() method is called on both the left and right drive motors to stop their movement.

    mLeftDrivePrimary.stopMotor();
    mRightDrivePrimary.stopMotor();
    }
    catch (Exception e) {
      DriverStation.reportError("DriveTrainSubsystem reset failed: " + e.getMessage(), true);
    }
  }

  // * Encoders in robotics drivetrains track position, velocity, and enable precise distance tracking, motion control, and profiling.
  // * Utilizing encoders allows for autonomous navigation, closed-loop control, odometry, and collision detection, enhancing robot performance and reliability.
  // ! The methods below return the left and right encoder positions using the .getPosition() method

  public double getLeftPosition() {
    try {
      return mLeftEncoder.getPosition();
    } 
    catch (Exception e) {
      DriverStation.reportError("Failed to get left encoder position: " + e.getMessage(), true);
      return 0.0;
    }
  }

  public double getRightPosition() {
    try {
      return mRightEncoder.getPosition();
    } 
    catch (Exception e) {
      DriverStation.reportError("Failed to get right encoder position: " + e.getMessage(), true);
      return 0.0;
    }
  }

  // ! The methods below return the left and right encoder velocities using the .getVelocity() method

  public double getLeftVelocity() {
    try {
      return mLeftEncoder.getVelocity();
    } 
    catch (Exception e) {
      DriverStation.reportError("Failed to get left encoder velocity: " + e.getMessage(), true);
      return 0.0;
    }
  }

  public double getRightVelocity() {
    try {
      return mRightEncoder.getVelocity();
    } 
    catch (Exception e) {
      DriverStation.reportError("Failed to get right encoder velocity: " + e.getMessage(), true);
      return 0.0;
    }
  }

  // ! The methods below return the left and right motor outputs by calling the .getAppliedOutput() method

  public double getLeftMotorOutput() {
    try {
      return mLeftDrivePrimary.getAppliedOutput();
    } catch (Exception e) {
      DriverStation.reportError("Failed to get left motor output: " + e.getMessage(), true);
      return 0.0;
    }
  }

  public double getRightMotorOutput() {
    try {
      return mRightDrivePrimary.getAppliedOutput();
    } catch (Exception e) {
      DriverStation.reportError("Failed to get right motor output: " + e.getMessage(), true);
      return 0.0;
    }
  }
}
