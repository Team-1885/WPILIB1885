// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ADAM;
import frc.robot.RobotContainer;
import frc.robot.subsystems.PeytonDriveTrain;
import lombok.Getter;


@SuppressWarnings("PMD.CommentSize")
public class PeytonCommand extends CommandBase {

  private @Getter ADAM adam = new ADAM(null);

  private final @Getter PeytonDriveTrain peytonDriveTrain;

  public PeytonCommand(final PeytonDriveTrain peytonDriveTrain) {
    super();
    this.peytonDriveTrain = peytonDriveTrain;
    addRequirements(peytonDriveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("========== STARTING PEYTONCOMMAND ==========");
    runTest(() -> {

    });
  }

  @Override
  public void execute() {
    runTest(() -> {
      
      double forwardSpeed = RobotContainer.logitech.getRawAxis(1) * 1;
      double turnSpeed = RobotContainer.logitech.getZ() * 1; 

      forwardSpeed = applyDeadzone(forwardSpeed, 0.05);
      turnSpeed = applyDeadzone(turnSpeed, 0.05);

      double leftSpeed = forwardSpeed + turnSpeed;
      double rightSpeed = forwardSpeed - turnSpeed;

      peytonDriveTrain.setMotorSpeed(leftSpeed, rightSpeed);
    });
  }

  private double applyDeadzone(double value, double deadzone) {
    if (Math.abs(value) < deadzone) {
      return 0.0;
    } else {
      return value;
    }
  }

  @Override
  public void end(boolean interrupted) {
    runTest(() -> {

    });
  }

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
