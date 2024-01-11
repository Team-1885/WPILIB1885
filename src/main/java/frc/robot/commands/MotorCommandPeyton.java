// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.MotorSpinPeyton;
import frc.robot.ADAM;
import lombok.Getter;

public class MotorCommandPeyton extends CommandBase {

  private @Getter ADAM adam = new ADAM(null);

  private final @Getter MotorSpinPeyton motorSpinPeyton;

  public MotorCommandPeyton(final MotorSpinPeyton motorSpinPeyton) {
    
    this.motorSpinPeyton = motorSpinPeyton;
    addRequirements(motorSpinPeyton);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("STARTING DRIVECOMMAND");
    runTest(() -> {

    });
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    runTest(() -> {
      double forwardSpeed = -RobotContainer.logitech.getRawAxis(1) * 0.30;
      double turnSpeed = -RobotContainer.logitech.getRawAxis(0) * 0.20; 

      double leftSpeed = forwardSpeed + turnSpeed;
      double rightSpeed = forwardSpeed - turnSpeed;
      
      motorSpinPeyton.setMotorSpeed(leftSpeed, rightSpeed);
    });
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
    runTest(() -> {

    });
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void debugCommand() {
    runTest(() -> initialize());
    runTest(() -> execute());
    runTest(() -> end(false));
  }

  public void runTest(final Runnable code) {
    try {
      code.run();
    } catch (Exception e) {
      adam.uncaughtException(Thread.currentThread(), e);
    }
  }
}
