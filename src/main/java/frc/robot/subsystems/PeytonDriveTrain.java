// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;
import java.util.stream.Stream;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import frc.robot.hardware.vendors.thirdparties.revlib.REVLibCAN;
import lombok.Getter;

//added some PID stuff, don't know yet what is relevent and what isn't

@SuppressWarnings("PMD.CommentSize")
public class PeytonDriveTrain extends SubsystemBase {

  private @Getter final ADAM adam = new ADAM(null);

  private static CANSparkMax REV_0xM1 = new CANSparkMax(REVLibCAN.L_MASTER_ID, REVLibCAN.MOTOR_TYPE),
                        REV_0xF1 = new CANSparkMax(REVLibCAN.L_FOLLOWER_ID, REVLibCAN.MOTOR_TYPE);

  private static CANSparkMax REV_0xM2 = new CANSparkMax(REVLibCAN.R_MASTER_ID, REVLibCAN.MOTOR_TYPE),
                        REV_0xF2 = new CANSparkMax(REVLibCAN.R_FOLLOWER_ID, REVLibCAN.MOTOR_TYPE);   
                        
  private @Getter RelativeEncoder leftEncoder, rightEncoder;

  private ShuffleboardTab tab = Shuffleboard.getTab("===== Peyton Drive Train =====");
  private GenericEntry testEntry = tab.add("===== SET MOTOR SPEED =====", 0).getEntry();   
  
  
  /** Creates a new PeytonDriveTrain. */
  public PeytonDriveTrain() {

    super();
    Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2).forEach(CANSparkMax::restoreFactoryDefaults);
    final Map<CANSparkMax, CANSparkMax> masterFollowerMap = Map.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2);
    masterFollowerMap.forEach((master, follower) -> follower.follow(master));
    Stream.of(REV_0xM1, REV_0xF1).forEach(motor -> motor.setInverted(false));
    Stream.of(REV_0xM2, REV_0xF2).forEach(motor -> motor.setInverted(true));
    Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2).forEach(motor -> motor.setIdleMode(CANSparkMax.IdleMode.kCoast));
    Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2)
                .forEach(motor -> motor.setSmartCurrentLimit(30, 35, 100));
                leftEncoder = REV_0xM1.getEncoder();
                rightEncoder = REV_0xM2.getEncoder();
    Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2).forEach(motor -> motor.setClosedLoopRampRate(1));
    Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2).forEach(motor -> motor.setControlFramePeriodMs(1));
    Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2).forEach(CANSparkMax::burnFlash);

  }

  @Override
  public void periodic() {
    runTest(() -> {
      testEntry.setDouble(REV_0xM1.get());
      REVLibCAN.logFaults(Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2));
      });
}
  public void reset() {
    runTest(() -> {
      Stream.of(leftEncoder, rightEncoder).forEach(encoder -> encoder.setPosition(0));
      Stream.of(REV_0xM1, REV_0xM2).forEach(motor -> motor.stopMotor());
      });
}

public void setMotorSpeed(final double leftSpeed, final double rightSpeed) {
  REV_0xM1.set(leftSpeed * 0.5);
  REV_0xM2.set(rightSpeed * 0.5);
}

public double getMotorSpeed() {
  return REV_0xM1.get();
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
}
}
