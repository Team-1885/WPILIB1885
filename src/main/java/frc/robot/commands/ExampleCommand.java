// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ADAM;
import frc.robot.subsystems.ExampleSubsystem;

/**
 * An example command for use as a template.
 */
@SuppressWarnings("PMD")
public class ExampleCommand extends CommandBase {

  private final ADAM adam = new ADAM(null);
  private final ExampleSubsystem exampleSubsystem;

  /** Creates a new ExampleCommand. */
  public ExampleCommand(ExampleSubsystem exampleSubsystem) {
    debugCommand();
    // Use addRequirements() here to declare subsystem dependencies.
    this.exampleSubsystem = exampleSubsystem;
    addRequirements(exampleSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    runTest(() -> {

    });
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    runTest(() -> {

    });
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    runTest(() -> {

    });
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  /**
   * Executes debugging actions for testing purposes.
   * Calls initialize(), execute(), and end() within a testing environment.
   *
   * @see #initialize()
   * @see #execute()
   * @see #end(boolean)
   */
  public void debugCommand() {
    runTest(() -> initialize());
    runTest(() -> execute());
    runTest(() -> end(false));
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
