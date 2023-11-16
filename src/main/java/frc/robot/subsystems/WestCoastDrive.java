// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;
import java.util.stream.Stream;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import frc.robot.RobotMap;

/**
 * Lorem Ipsum.
 */
public class WestCoastDrive extends SubsystemBase {

  /**
   * Lorem Ipsum.
   */
  private final transient ADAM adam = new ADAM(null);
  /**
   * Lorem Ipsum.
   */
  private static CANSparkMax leftMaster = new CANSparkMax(RobotMap.DriveConstants.LDP_ID, MotorType.kBrushless),
      leftFollower = new CANSparkMax(RobotMap.DriveConstants.LDF_ID, MotorType.kBrushless);
  /**
   * Lorem Ipsum.
   */
  private static CANSparkMax rightMaster = new CANSparkMax(RobotMap.DriveConstants.RDP_ID, MotorType.kBrushless),
      rightFollower = new CANSparkMax(RobotMap.DriveConstants.RDF_ID, MotorType.kBrushless);
  /**
   * Lorem Ipsum.
   */
  private final RelativeEncoder leftEncoder,
      rightEncoder;
  /**
   * Lorem Ipsum.
   */
  private static SparkMaxPIDController leftCtrl,
      rightCtrl;
  /**
   * Lorem Ipsum.
   */
  private static PIDController leftPositionPID,
      rightPositionPID;

  /** Creates a new WestCoastDrive. */
  public WestCoastDrive() {
    super();
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
        .forEach(CANSparkMax::restoreFactoryDefaults);
    final Map<CANSparkMax, CANSparkMax> masterFollowerMap = Map.of(
        leftMaster, leftFollower,
        rightMaster, rightFollower);
    masterFollowerMap.forEach((master, follower) -> follower.follow(master));
    Stream.of(leftMaster, leftFollower)
        .forEach(motor -> motor.setInverted(false));
    Stream.of(rightMaster, rightFollower)
        .forEach(motor -> motor.setInverted(true));
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
        .forEach(motor -> motor.setIdleMode(CANSparkMax.IdleMode.kCoast));
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
        .forEach(motor -> motor.setSmartCurrentLimit(30, 35, 100));
    leftEncoder = leftMaster.getEncoder();
    rightEncoder = rightMaster.getEncoder();
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
        .forEach(motor -> motor.setClosedLoopRampRate(0.5));
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
        .forEach(motor -> motor.setOpenLoopRampRate(0.5));
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
        .forEach(motor -> motor.setControlFramePeriodMs(1));
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
        .forEach(CANSparkMax::burnFlash);
  }

  @Override public void periodic() {
    runTest(() -> {

    });
  }

  /**
   * Executes a custom method, running it within a testing environment.
   *
   * @see #runTest(Runnable)
   */
  public void reset() {
    runTest(() -> {
      Stream.of(leftEncoder, rightEncoder)
          .forEach(encoder -> encoder.setPosition(0));
      Stream.of(leftMaster, rightMaster)
          .forEach(motor -> motor.stopMotor());
    });
  }

  /**
   * Executes custom testing and validation methods in a controlled environment.
   * Any exceptions thrown during execution are caught and logged.
   *
   * @see #runTest(Runnable)
   */
  public void debugSubsystem() {
    runTest(() -> periodic());
    runTest(() -> reset());
  }

  /**
   * Runs the code as a task; if an exception occurs, it is caught, and an
   * uncaught exception is passed to the default handler for the current thread.
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
