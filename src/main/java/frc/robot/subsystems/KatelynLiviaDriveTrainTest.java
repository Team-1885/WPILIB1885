package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import frc.robot.RobotMap;

public class KatelynLiviaDriveTrainTest {
     // Left side motor controllers

    private CANSparkMax leftDrivePrimary = new CANSparkMax(RobotMap.DriveConstants.kLDP_ID,
    MotorType.kBrushless);
    private CANSparkMax leftDriveFollower1 = new CANSparkMax(RobotMap.DriveConstants.kLDF1_ID,
    MotorType.kBrushless);
    private CANSparkMax leftDriveFollower2 = new CANSparkMax(RobotMap.DriveConstants.kLDF2_ID,
    MotorType.kBrushless);

// Right side motor controllers

    private CANSparkMax rightDrivePrimary = new CANSparkMax(RobotMap.DriveConstants.kRDP_ID,
    MotorType.kBrushless);
    private CANSparkMax rightDriveFollower1 = new CANSparkMax(RobotMap.DriveConstants.kRDF1_ID,
    MotorType.kBrushless);
    private CANSparkMax rightDriveFollower2 = new CANSparkMax(RobotMap.DriveConstants.kRDF2_ID,
    MotorType.kBrushless);

    public KatelynLiviaDriveTrainTest() {
        initializeMotors();
        initializeEncoders();
        initializePIDControllers();
      }
      void initializeMotors() {
        // The above code restores the factory defaults for the CANSparkMax objects associated with the left and right sides of the drivetrain.
        // By invoking the restoreFactoryDefaults() method on each motor controller, the drivetrain is reset to its default settings.
    
        leftDrivePrimary.restoreFactoryDefaults();
        leftDriveFollower1.restoreFactoryDefaults();
        leftDriveFollower2.restoreFactoryDefaults();
    
        rightDrivePrimary.restoreFactoryDefaults();
        rightDriveFollower1.restoreFactoryDefaults();
        rightDriveFollower2.restoreFactoryDefaults();
    
        // By calling the follow() method on the follower motors and passing the primary motor as the argument, the follower motors are set to synchronize their movement with the primary motor.
        // This ensures that the left and right sides of the drivetrain move together in a coordinated manner.
    
        leftDriveFollower1.follow(leftDrivePrimary);
        leftDriveFollower2.follow(leftDrivePrimary);
    
        rightDriveFollower1.follow(rightDrivePrimary);
        rightDriveFollower2.follow(rightDrivePrimary);
    
        // By calling the setInverted() method on each motor, the direction of motor
        // rotation is adjusted.
        // For the left side, all motors (primary and followers) are set to be
        // inverted (reversed) by passing 'true' as the argument, meaning they rotate in
        // the desired direction for the robot's movement.
        // For the right side, all motors are set to 'false' (not inverted), meaning
        // they rotate in the default direction.
    
        leftDrivePrimary.setInverted(true);
        leftDriveFollower1.setInverted(true);
        leftDriveFollower2.setInverted(true);
    
        rightDrivePrimary.setInverted(false);
        rightDriveFollower1.setInverted(false);
        rightDriveFollower2.setInverted(false);
      }
}
