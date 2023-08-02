// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.OperatorConstants.*;
import static frc.robot.Constants.DriverConstants.*;
import frc.robot.subsystems.DriveTrainSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/** 
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  
  // ! The robot's subsystems and commands are defined here...
  private final DriveTrainSubsystem DRIVETRAIN_SUBSYSTEM = new DriveTrainSubsystem();
  private final OI OPERATOR_INTERFACE = new OI();
  private final ADAM ADAM = new ADAM(null);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    XboxController mDriverXboxController = OPERATOR_INTERFACE.getDriverXboxController();
    XboxController mOperatorXboxController = OPERATOR_INTERFACE.getOperatorXboxController();
    
    PS4Controller mDriverPS4Controller = OPERATOR_INTERFACE.getDriverPS4Controller();
    PS4Controller mOperatorPS4Controller = OPERATOR_INTERFACE.getOperatorPS4Controller();

    Joystick mDriverLogitechController = OPERATOR_INTERFACE.getDriverLogitechController();
    Joystick mOperatorLogitechController = OPERATOR_INTERFACE.getOperatorLogitechController();

    // TODO: Configure driver Xbox controller button bindings
    // Example:
    //mDriverXboxController.getAButton().whenPressed(new ExampleCommand());

    // TODO: Configure operator Xbox controller button bindings
    // Example:
    // mOperatorXboxController.getTriggerAxis(Hand.kRight).whileActiveContinuous(new ExampleCommand());

    // TODO Configure driver PS4 controller button bindings
    // Example:
    // mDriverPS4Controller.getDPadUp().whenPressed(new ExampleCommand());

    // TODO: Configure operator PS4 controller button bindings
    // Example:
    // mOperatorPS4Controller.getSquareButton().whileHeld(new ExampleCommand());

    // TODO: Configure driver Logitech controller button bindings
    // Example:
    //mDriverLogitechController.getRawButton().whenReleased(new ExampleCommand());

    // TODO: Configure operator Logitech controller button bindings
    // Example:
    // mOperatorLogitechController.getPOV(0).whenPressed(new ExampleCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new SequentialCommandGroup(); // basically a placeholder
  }
}
