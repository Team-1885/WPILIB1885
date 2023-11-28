// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.FaultID;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import frc.robot.RobotMap;
import lombok.Getter;

/**
 * Lorem Ipsum.
 */
public class WestCoastDrive extends SubsystemBase {

  /**
   * Lorem Ipsum.
   */
  private @Getter final ADAM adam = new ADAM(null);
  /**
   * Lorem Ipsum.
   */
  private static CANSparkMax leftMaster = new CANSparkMax(RobotMap.WestCoastDriveConstants.L_D_PRIMARY_ID,
      MotorType.kBrushless),
      leftFollower = new CANSparkMax(RobotMap.WestCoastDriveConstants.L_D_FOLLOWER_ID, MotorType.kBrushless);
  /**
   * Lorem Ipsum.
   */
  private static CANSparkMax rightMaster = new CANSparkMax(RobotMap.WestCoastDriveConstants.R_D_PRIMARY_ID,
      MotorType.kBrushless),
      rightFollower = new CANSparkMax(RobotMap.WestCoastDriveConstants.R_D_FOLLOWER_ID, MotorType.kBrushless);
  /**
   * Lorem Ipsum.
   */
  private @Getter RelativeEncoder leftEncoder,
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

  @Override
  public void periodic() {
    runTest(() -> {
      // Create a Shuffleboard tab for the WestCoastDrive subsystem
      final ShuffleboardTab driveTab = Shuffleboard.getTab("WestCoastDrive");

      // Define a function to add a widget for a motor
      BiConsumer<String, CANSparkMax> addMotorWidget = (label, motor) -> {
        driveTab.add(label + " - Class", motor.getClass().getName())
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(0, 1)
            .withSize(4, 1);
        driveTab.add(label + " - Firmware String", motor.getFirmwareString())
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(0, 2)
            .withSize(4, 1);
        driveTab.add(label + " - Set Speed", motor.get())
            .withWidget(BuiltInWidgets.kNumberBar)
            .withPosition(0, 0)
            .withSize(4, 1);
        driveTab.add(label + " - Applied Output", motor.getAppliedOutput())
            .withWidget(BuiltInWidgets.kDial)
            .withPosition(0, 3)
            .withSize(4, 1);
        driveTab.add(label + " - Bus Voltage", motor.getBusVoltage())
            .withWidget(BuiltInWidgets.kGraph)
            .withPosition(0, 4)
            .withSize(4, 1);
        driveTab.add(label + " - Closed Loop Ramp Rate", motor.getClosedLoopRampRate())
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(0, 5)
            .withSize(4, 1);
        driveTab.add(label + " - Device ID", motor.getDeviceId())
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(0, 6)
            .withSize(4, 1);
        driveTab.add(label + " - Encoder Position", motor.getEncoder().getPosition())
            .withWidget(BuiltInWidgets.kGraph)
            .withPosition(0, 7)
            .withSize(4, 1);
        driveTab.add(label + " - Firmware Version", motor.getFirmwareVersion())
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(0, 7)
            .withSize(4, 1);
        driveTab.add(label + " - Motor Temperature", motor.getMotorTemperature())
            .withWidget(BuiltInWidgets.kNumberBar)
            .withPosition(0, 8)
            .withSize(4, 1);
        driveTab.add(label + " - Motor Temperature", motor.getMotorType())
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(0, 8)
            .withSize(4, 1);
        driveTab.add(label + " - Motor Temperature", motor.getOpenLoopRampRate())
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(0, 8)
            .withSize(4, 1);
        driveTab.add(label + " - Output Current", motor.getOutputCurrent())
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(0, 9)
            .withSize(4, 1);
        // Add more widgets for other methods as needed
      };

      // Add widgets for left master and follower
      addMotorWidget.accept("Left Master", leftMaster);
      addMotorWidget.accept("Left Follower", leftFollower);

      // Add widgets for right master and follower
      addMotorWidget.accept("Right Master", rightMaster);
      addMotorWidget.accept("Right Follower", rightFollower);
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

  public void setMotorSpeed(double param) {
    leftMaster.set(param);
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
