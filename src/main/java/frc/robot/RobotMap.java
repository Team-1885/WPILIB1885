// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The RobotMap class provides a convenient place for teams to hold robot-wide numerical or boolean constants. 
 * This class should not be used for any other purpose. 
 * All constants should be declared globally (i.e. public static). Do not put anything functional in this class.
 * It is advised to statically import this class (or one of its inner classes) wherever the constants are needed, to reduce verbosity.
 */
public final class RobotMap {
  /**
   *  (k) [constants] for drive || drive-related robot systems.
   */
  public static final class DriveConstants {
    public static final int kLDP_ID = 0;   
    public static final int kLDF1_ID = 1;  
    public static final int kLDF2_ID = 2;  
    
    public static final int kRDP_ID = 3;   
    public static final int kRDF1_ID = 4;  
    public static final int kRDF2_ID = 5;  
    
  }

  /**
   * (k) [constants] for drivers & associated hardware/other usages.
   */
  public static final class DriverConstants {
    public static final int kDriverXBXCtrlrPort = 0;
    public static final int kDriverPS4CtrlrPort = 0;
    public static final int kDriverLGTCtrlrPort = 0;
  }

  /**
   * (k) [constants] for operators & associated hardware/other usages.
   */
  public static final class OperatorConstants {
    public static final int kOperatorXBXCtrlrPort = 0;
    public static final int kOperatorPS4CtrlrPort = 0;
    public static final int kOperatorLGTCtrlrPort = 0;
  }

  /**
   * (k) [constants] for PID controller's (control loop mechanism employing feedback that is widely used in applications requiring continuously modulated control).
   */
  public static final class PIDConstants {
    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
  }

  /**
   *  (k) [constants] for button mappings to different FIRST approved ctrlr's.
   */
  public static final class ButtonMappings {
    public static final XboxController.Button kXBX_A = XboxController.Button.kA;
    public static final XboxController.Button kXBX_B = XboxController.Button.kB;
    public static final XboxController.Button kXBX_Back = XboxController.Button.kBack;
    public static final XboxController.Button kXBX_LB = XboxController.Button.kLeftBumper;
    public static final XboxController.Button kXBX_LS = XboxController.Button.kLeftStick;
    public static final XboxController.Button kXBX_RB = XboxController.Button.kRightBumper;
    public static final XboxController.Button kXBX_RS = XboxController.Button.kRightStick;
    public static final XboxController.Button kXBX_Start = XboxController.Button.kStart;
    public static final XboxController.Button kXBX_X = XboxController.Button.kX;
    public static final XboxController.Button kXBX_Y = XboxController.Button.kY;

    public static final PS4Controller.Button kPS4_Circle = PS4Controller.Button.kCircle;
    public static final PS4Controller.Button kPS4_Cross = PS4Controller.Button.kCross;
    public static final PS4Controller.Button kPS4_L1 = PS4Controller.Button.kL1;
    public static final PS4Controller.Button kPS4_L2 = PS4Controller.Button.kL2;
    public static final PS4Controller.Button kPS4_L3 = PS4Controller.Button.kL3;
    public static final PS4Controller.Button kPS4_Options = PS4Controller.Button.kOptions;
    public static final PS4Controller.Button kPS4_PS = PS4Controller.Button.kPS;
    public static final PS4Controller.Button kPS4_R1 = PS4Controller.Button.kR1;
    public static final PS4Controller.Button kPS4_R2 = PS4Controller.Button.kR2;
    public static final PS4Controller.Button kPS4_R3 = PS4Controller.Button.kR3;
    public static final PS4Controller.Button kPS4_Share = PS4Controller.Button.kShare;
    public static final PS4Controller.Button kPS4_Square = PS4Controller.Button.kSquare;
    public static final PS4Controller.Button kPS4_Touchpad = PS4Controller.Button.kTouchpad;
    public static final PS4Controller.Button kPS4_Triangle = PS4Controller.Button.kTriangle;

    public static final int kLGT_A = 1;
    public static final int kLGT_B = 2;
    public static final int kLGT_X = 3;
    public static final int kLGT_Y = 4;
    public static final int kLGT_LB = 5;
    public static final int kLGT_RB = 6;
    public static final int kLGT_BACK = 7;
    public static final int kLGT_START = 8;
    public static final int kLGT_LS = 9;
    public static final int kLGT_RS = 10;
    public static final int kLGT_L_AP = 11;
    public static final int kLGT_R_AP = 12;
  
    public static final int kLGT_AX_L_X = 0;
    public static final int kLGT_AX_L_Y = 1;
    public static final int kLGT_AX_LT = 2;
    public static final int kLGT_AX_RT = 3;
    public static final int kLGT_AX_R_X = 4;
    public static final int kLGT_AX_R_Y = 5;
  }
}
