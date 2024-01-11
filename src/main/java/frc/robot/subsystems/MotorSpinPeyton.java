// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;
import java.util.stream.Stream;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.hardware.REVLibCAN;
import lombok.Getter;
import frc.robot.ADAM;


public class MotorSpinPeyton extends SubsystemBase {

  private @Getter final ADAM adam = new ADAM(null);

  private static CANSparkMax leftMaster = new CANSparkMax(REVLibCAN.L_MASTER_ID, REVLibCAN.MOTOR_TYPE),
                             leftFollower = new CANSparkMax(REVLibCAN.L_FOLLOWER_ID, REVLibCAN.MOTOR_TYPE);

  private static CANSparkMax rightMaster = new CANSparkMax(REVLibCAN.L_MASTER_ID, REVLibCAN.MOTOR_TYPE),
                             rightFollower = new CANSparkMax(REVLibCAN.L_FOLLOWER_ID, REVLibCAN.MOTOR_TYPE);                                   

  private @Getter RelativeEncoder leftEncoder, rightEncoder;
                       
  private static SparkMaxPIDController leftCtrl, rightCtrl;
                        
  private static PIDController leftPositionPID, rightPositionPID;

  private ShuffleboardTab tab = Shuffleboard.getTab("===== WEST COAST DRIVE =====");
  private GenericEntry testEntry = tab.add("===== SET MOTOR SPEED =====", 0).getEntry();
   
  /** Creates a new MotorSpinPeyton. */
  public MotorSpinPeyton() {
    super();
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower) .forEach(CANSparkMax::restoreFactoryDefaults);

    final Map<CANSparkMax, CANSparkMax> masterFollowerMap = Map.of(leftMaster, leftFollower,rightMaster, rightFollower);

    masterFollowerMap.forEach((master, follower) -> follower.follow(master));

    Stream.of(leftMaster, leftFollower).forEach(motor -> motor.setInverted(false));
    Stream.of(rightMaster, rightFollower).forEach(motor -> motor.setInverted(true));
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower).forEach(motor -> motor.setIdleMode(CANSparkMax.IdleMode.kCoast));
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower).forEach(motor -> motor.setSmartCurrentLimit(30, 35, 100));

    leftEncoder = leftMaster.getEncoder();
    rightEncoder = rightMaster.getEncoder();

    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower).forEach(motor -> motor.setClosedLoopRampRate(0.5));
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower).forEach(motor -> motor.setOpenLoopRampRate(0.5));
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower).forEach(motor -> motor.setControlFramePeriodMs(1));
    Stream.of(leftMaster, leftFollower, rightMaster, rightFollower).forEach(CANSparkMax::burnFlash);                
}

@Override
public void periodic() {
        runTest(() -> {
                 testEntry.setDouble(leftMaster.get());
                REVLibCAN.logFaults(Stream.of(leftMaster, leftFollower, rightMaster, rightFollower));
        });
}
public void reset() {
  runTest(() -> {
          Stream.of(leftEncoder, rightEncoder).forEach(encoder -> encoder.setPosition(0));
          Stream.of(leftMaster, rightMaster).forEach(motor -> motor.stopMotor());
  });
}
public void setMotorSpeed(final double leftSpeed, final double rightSpeed) {
  leftMaster.set(leftSpeed);
  leftFollower.set(leftSpeed);

  rightMaster.set(rightSpeed);
  rightFollower.set(rightSpeed);
}

public double getMotorSpeed() {
  return leftMaster.get();
}
public void debugSubsystem() {
  runTest(() -> periodic());
  runTest(() -> reset());
}
public void runTest(final Runnable code) {
  try {
          code.run();
  } catch (Exception e) {
          adam.uncaughtException(Thread.currentThread(), e);
  }
  //my own drivetrain!
}
}