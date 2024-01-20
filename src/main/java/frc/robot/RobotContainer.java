// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.PeytonCommand;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
// import frc.robot.commands.SpinIntakeCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.PeytonDriveTrain;
// import frc.robot.subsystems.Intake;
import frc.robot.subsystems.WestCoastDrive;
import lombok.Getter;

/** 
 * This class is where the bulk of the robot should be declared. 
 * Since Command-based is a "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls). 
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
@SuppressWarnings("PMD.CommentSize") public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  private @Getter final WestCoastDrive westCoastDrive = new WestCoastDrive();
  private @Getter final DriveCommand driveCommand = new DriveCommand(westCoastDrive);
  private @Getter final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
  private @Getter final ExampleCommand exampleCommand = new ExampleCommand(exampleSubsystem);
  private @Getter final PeytonDriveTrain peytonDriveTrain = new PeytonDriveTrain();
  private @Getter final PeytonCommand peytonCommand = new PeytonCommand(peytonDriveTrain);
  // private @Getter final Intake intake = new Intake();
  // private @Getter final SpinIntakeCommand intakeCommand = new SpinIntakeCommand(intake);
  private @Getter final XboxController xboxController = new XboxController(RobotMap.DriverConstants.D_XBOX_PORT);
  public @Getter final static Joystick logitech = new Joystick(RobotMap.DriverConstants.D_LOGITECH_PORT);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    westCoastDrive.setDefaultCommand(driveCommand);
  }

  /**
   * Use this method to define your trigger->command mappings. 
   * Triggers can be created via the {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings() {
    // Add code here

    // Trigger driveTriggerX = new Trigger(() -> logitech.getRawAxis(0) > 0.01); // Replace 0 with the axis number for the X axis
    // driveTriggerX.whileTrue(driveCommand);

    // Trigger driveTriggerY = new Trigger(() -> logitech.getRawAxis(1) > 0.01); // Replace 1 with the axis number for the Y axis
    // driveTriggerY.whileTrue(driveCommand);

    logitech.getRawAxis(0); // X
    logitech.getRawAxis(1); // Y
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Load the path you want to follow using its name in the GUI
    PathPlannerPath path = PathPlannerPath.fromPathFile("New Path");

    // Create a path following command using AutoBuilder. This will also trigger event markers.
    return AutoBuilder.followPathWithEvents(path);
  }
}
