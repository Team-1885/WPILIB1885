// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.common.types.MatchMetadata;
import frc.logging.CSVLogger;
import frc.robot.auto.AutonSelection;
import frc.robot.hardware.vendors.firstparties.Clock;
import frc.robot.hardware.vendors.firstparties.Data;
import frc.robot.hardware.vendors.firstparties.Settings;
import lombok.Getter;
import java.util.logging.Logger;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation.
 * If you change the name of this class or the package after creating this
 * project, you must also update the build.gradle file in the project.
 */

@SuppressWarnings("PMD.CommentSize")
public class Robot extends TimedRobot {
  // private ILog mLogger = Logger.createLog(this.getClass());
  public static final Data DATA = new Data();
  public static final Clock CLOCK = (RobotBase.isReal() ? new Clock() : new Clock().simulated());
  public static final Field2d FIELD = new Field2d();
  public static final boolean IS_SIMULATED = RobotBase.isSimulation();
  // private static EMatchMode MODE = DISABLED;
  public static String CLIMB_MODE = "";
  private final Settings mSettings = new Settings();
  private CSVLogger mCSVLogger;
  private Timer initTimer = new Timer();

  private AutonSelection mAutonSelection;
  private ClimbModeSelection mClimberSelector;
  // private BallTracking mPixy;

  private InputMap mOI;
  private MatchMetadata mMatchMeta = null;
  /**
   * Lorem Ipsum.
   */
  private @Getter Command autonomousCommand;
  /**
   * Lorem Ipsum.
   */
  private static final @Getter Logger LOGGER = Logger.getLogger(Robot.class.getName());
  /**
   * Lorem Ipsum.
   */
  private @Getter RobotContainer robotContainer;

  /**
   * Default constructor for the Robot class. This constructor is automatically
   * invoked when an instance of the Robot class is created.
   * Initializes the Robot instance by calling the no-argument constructor of the
   * superclass (TimedRobot).
   */
  public Robot() {
    super();
  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
   RobotContainer robotContainer = new RobotContainer();

  }
  // Instantiate our RobotContainer.
  // This will perform all our button bindings, and put our autonomous chooser on
  // the dashboard.
  

  

  /**
   * This function is called every 20 ms, no matter the mode.
   * Use this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.
    // This is responsible for polling buttons, adding newly-scheduled commands,
    // running already-scheduled commands, removing finished or interrupted
    // commands, and running subsystem periodic() methods.
    // This must be called from the robot's periodic block in order for anything in
    // the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    // ...
  }

  @Override
  public void disabledPeriodic() {
    // ...
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    autonomousCommand = robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // ...
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when teleop starts running.
    // If you want the autonomous to continue until interrupted by another command,
    // remove this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // ...
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    // ...
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
    // ...
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
    // ...
  }
}
