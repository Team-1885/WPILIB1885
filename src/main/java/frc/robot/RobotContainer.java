// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.MakeMotorSpinCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.MakeMotorSpin;
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
  private @Getter final XboxController xboxController = new XboxController(RobotMap.DriverConstants.D_XBOX_PORT);
  public @Getter final static Joystick logitech = new Joystick(RobotMap.DriverConstants.D_LOGITECH_PORT);
  private @Getter final MakeMotorSpin makeMotorSpin = new MakeMotorSpin();
  private @Getter final MakeMotorSpinCommand makeMotorSpinCommand = new MakeMotorSpinCommand(makeMotorSpin);
  

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

    if(logitech.getRawButton(1)) {
      driveCommand.schedule();
    }

    logitech.getRawAxis(0); // X
    logitech.getRawAxis(1); // Y
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return exampleCommand; // basically a placeholder
  }
}
