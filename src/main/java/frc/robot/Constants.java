// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  /**
   * Constants related to the operator.
   */
  public static class OperatorConstants {

    // * Driver Controller Port
    public static final int kDriverControllerPort = 0;

    // * Left drive motor IDs
    public static final int LEFT_DRIVE_PRIMARY_ID = 0;
    public static final int LEFT_DRIVE_FOLLOWER1_ID = 1;
    public static final int LEFT_DRIVE_FOLLOWER2_ID = 2;

    // * Right drive motor IDs
    public static final int RIGHT_DRIVE_PRIMARY_ID = 3;
    public static final int RIGHT_DRIVE_FOLLOWER1_ID = 4;
    public static final int RIGHT_DRIVE_FOLLOWER2_ID = 5;

    // * PID constants
    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    // * Operator Controller Ports
    public static final int kOperatorXboxControllerPort = 0;
    public static final int kOperatorPS4ControllerPort = 0;
    public static final int kOperatorLogitechControllerPort = 0;

    // * Constants for Xbox operator controller buttons

    public static final XboxController.Button OPERATOR_XBOX_BUTTON_A = XboxController.Button.kA;
    public static final XboxController.Button OPERATOR_XBOX_BUTTON_B = XboxController.Button.kB;
    public static final XboxController.Button OPERATOR_XBOX_BUTTON_BACK = XboxController.Button.kBack;
    public static final XboxController.Button OPERATOR_XBOX_BUTTON_LEFT_BUMPER = XboxController.Button.kLeftBumper;
    public static final XboxController.Button OPERATOR_XBOX_BUTTON_LEFT_STICK = XboxController.Button.kLeftStick;
    public static final XboxController.Button OPERATOR_XBOX_BUTTON_RIGHT_BUMPER = XboxController.Button.kRightBumper;
    public static final XboxController.Button OPERATOR_XBOX_BUTTON_RIGHT_STICK = XboxController.Button.kRightStick;
    public static final XboxController.Button OPERATOR_XBOX_BUTTON_START = XboxController.Button.kStart;
    public static final XboxController.Button OPERATOR_XBOX_BUTTON_X = XboxController.Button.kX;
    public static final XboxController.Button OPERATOR_XBOX_BUTTON_Y = XboxController.Button.kY;

    // * Constants for PS4 operator controller buttons

    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_CIRCLE = PS4Controller.Button.kCircle;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_CROSS = PS4Controller.Button.kCross;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_L1 = PS4Controller.Button.kL1;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_L2 = PS4Controller.Button.kL2;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_L3 = PS4Controller.Button.kL3;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_OPTIONS = PS4Controller.Button.kOptions;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_PS = PS4Controller.Button.kPS;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_R1 = PS4Controller.Button.kR1;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_R2 = PS4Controller.Button.kR2;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_R3 = PS4Controller.Button.kR3;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_SHARE = PS4Controller.Button.kShare;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_SQUARE = PS4Controller.Button.kSquare;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_TOUCHPAD = PS4Controller.Button.kTouchpad;
    public static final PS4Controller.Button OPERATOR_PS4_BUTTON_TRIANGLE = PS4Controller.Button.kTriangle;

    // * Constants for Logitech operator controller buttons and axes.
    // * Please refer to the controller's documentation to verify the mappings.

    public static final int OPERATOR_LOGITECH_BUTTON_A = 1;
    public static final int OPERATOR_LOGITECH_BUTTON_B = 2;
    public static final int OPERATOR_LOGITECH_BUTTON_X = 3;
    public static final int OPERATOR_LOGITECH_BUTTON_Y = 4;
    public static final int OPERATOR_LOGITECH_BUTTON_LEFT_BUMPER = 5;
    public static final int OPERATOR_LOGITECH_BUTTON_RIGHT_BUMPER = 6;
    public static final int OPERATOR_LOGITECH_BUTTON_BACK = 7;
    public static final int OPERATOR_LOGITECH_BUTTON_START = 8;
    public static final int OPERATOR_LOGITECH_BUTTON_LEFT_STICK = 9;
    public static final int OPERATOR_LOGITECH_BUTTON_RIGHT_STICK = 10;
    public static final int OPERATOR_LOGITECH_BUTTON_LEFT_ANALOG_PUSH = 11;
    public static final int OPERATOR_LOGITECH_BUTTON_RIGHT_ANALOG_PUSH = 12;
    
    public static final int OPERATOR_LOGITECH_AXIS_LEFT_X = 0;
    public static final int OPERATOR_LOGITECH_AXIS_LEFT_Y = 1;
    public static final int OPERATOR_LOGITECH_AXIS_LEFT_TRIGGER = 2;
    public static final int OPERATOR_LOGITECH_AXIS_RIGHT_TRIGGER = 3;
    public static final int OPERATOR_LOGITECH_AXIS_RIGHT_X = 4;
    public static final int OPERATOR_LOGITECH_AXIS_RIGHT_Y = 5;

  }

  /**
   * Constants related to the driver.
   */
  public static class DriverConstants {

    // * Driver Controller Ports
    public static final int kDriverXboxControllerPort = 0;
    public static final int kDriverPS4ControllerPort = 0;
    public static final int kDriverLogitechControllerPort = 0;

    // * Constants for Xbox driver controller buttons

    public static final XboxController.Button DRIVER_XBOX_BUTTON_A = XboxController.Button.kA;
    public static final XboxController.Button DRIVER_XBOX_BUTTON_B = XboxController.Button.kB;
    public static final XboxController.Button DRIVER_XBOX_BUTTON_BACK = XboxController.Button.kBack;
    public static final XboxController.Button DRIVER_XBOX_BUTTON_LEFT_BUMPER = XboxController.Button.kLeftBumper;
    public static final XboxController.Button DRIVER_XBOX_BUTTON_LEFT_STICK = XboxController.Button.kLeftStick;
    public static final XboxController.Button DRIVER_XBOX_BUTTON_RIGHT_BUMPER = XboxController.Button.kRightBumper;
    public static final XboxController.Button DRIVER_XBOX_BUTTON_RIGHT_STICK = XboxController.Button.kRightStick;
    public static final XboxController.Button DRIVER_XBOX_BUTTON_START = XboxController.Button.kStart;
    public static final XboxController.Button DRIVER_XBOX_BUTTON_X = XboxController.Button.kX;
    public static final XboxController.Button DRIVER_XBOX_BUTTON_Y = XboxController.Button.kY;

    // * Constants for PS4 driver controller buttons

    public static final PS4Controller.Button DRIVER_PS4_BUTTON_CIRCLE = PS4Controller.Button.kCircle;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_CROSS = PS4Controller.Button.kCross;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_L1 = PS4Controller.Button.kL1;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_L2 = PS4Controller.Button.kL2;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_L3 = PS4Controller.Button.kL3;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_OPTIONS = PS4Controller.Button.kOptions;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_PS = PS4Controller.Button.kPS;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_R1 = PS4Controller.Button.kR1;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_R2 = PS4Controller.Button.kR2;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_R3 = PS4Controller.Button.kR3;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_SHARE = PS4Controller.Button.kShare;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_SQUARE = PS4Controller.Button.kSquare;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_TOUCHPAD = PS4Controller.Button.kTouchpad;
    public static final PS4Controller.Button DRIVER_PS4_BUTTON_TRIANGLE = PS4Controller.Button.kTriangle;


    // * Constants for Logitech driver controller buttons and axes.
    // * Please refer to the controller's documentation to verify the mappings.

    public static final int DRIVER_LOGITECH_BUTTON_A = 1;
    public static final int DRIVER_LOGITECH_BUTTON_B = 2;
    public static final int DRIVER_LOGITECH_BUTTON_X = 3;
    public static final int DRIVER_LOGITECH_BUTTON_Y = 4;
    public static final int DRIVER_LOGITECH_BUTTON_LEFT_BUMPER = 5;
    public static final int DRIVER_LOGITECH_BUTTON_RIGHT_BUMPER = 6;
    public static final int DRIVER_LOGITECH_BUTTON_BACK = 7;
    public static final int DRIVER_LOGITECH_BUTTON_START = 8;
    public static final int DRIVER_LOGITECH_BUTTON_LEFT_STICK = 9;
    public static final int DRIVER_LOGITECH_BUTTON_RIGHT_STICK = 10;
    public static final int DRIVER_LOGITECH_BUTTON_LEFT_ANALOG_PUSH = 11;
    public static final int DRIVER_LOGITECH_BUTTON_RIGHT_ANALOG_PUSH = 12;

    public static final int DRIVER_LOGITECH_AXIS_LEFT_X = 0;
    public static final int DRIVER_LOGITECH_AXIS_LEFT_Y = 1;
    public static final int DRIVER_LOGITECH_AXIS_LEFT_TRIGGER = 2;
    public static final int DRIVER_LOGITEC_AXIS_RIGHT_TRIGGER = 3;
    public static final int DRIVER_LOGITECH_AXIS_RIGHT_X = 4;
     public static final int DRIVER_LOGITECH_AXIS_RIGHT_Y = 5;
  } 
}

  