// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.hardware.REVLibCAN;


//using a drivetrain motor because we don't have any others yet

public class PeytonCANLauncher extends SubsystemBase {
  /** Creates a new PeytonCANLauncher. */
  CANSparkMax REV_0xM1;

  public PeytonCANLauncher() {
    REV_0xM1 = new CANSparkMax(REVLibCAN.L_MASTER_ID, REVLibCAN.MOTOR_TYPE);

    REV_0xM1.setSmartCurrentLimit(80); //value found in kitbot constants
  }

  public Command getIntakeCommand() {
    return this.startEnd(
      () -> {
        setLaunchWheel(-1); //value found in kitbot constants
      },
      // When the command stops, stop the wheels
      () -> {
        stop();
      });
  }

  public void setLaunchWheel(double speed) {
    REV_0xM1.set(speed);
  }

  public void stop() {
    REV_0xM1.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  }


