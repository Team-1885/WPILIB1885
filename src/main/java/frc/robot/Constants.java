// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;

/**
 * 🤔 The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean constants. 
 * ❌ This class should not be used for any other purpose. 
 * 👍 All constants should be declared globally (i.e. public static). Do not put anything functional in this class.
 * 
 * 👍 It is advised to statically import this class (or one of its inner classes) wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    
    // 🚟 Controller ports for driver and operator
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 0;

    // 🛞 Motor controller IDs for left and right drive
    public static final int LEFT_DRIVE_PRIMARY_ID = 0;
    public static final int LEFT_DRIVE_FOLLOWER1_ID = 1;
    public static final int LEFT_DRIVE_FOLLOWER2_ID = 2;

    public static final int RIGHT_DRIVE_PRIMARY_ID = 3;
    public static final int RIGHT_DRIVE_FOLLOWER1_ID = 4;
    public static final int RIGHT_DRIVE_FOLLOWER2_ID = 5;

    // 👨‍🔬 PID constants for motor control
    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    // 🏓 Controller ports for different types of driver controllers
    public static final int kDriverXboxControllerPort = 0;
    public static final int kDriverPS4ControllerPort = 0;
    public static final int kDriverLogitechControllerPort = 0;

    // 🏓 Controller ports for different types of operator controllers
    public static final int kOperatorXboxControllerPort = 0;
    public static final int kOperatorPS4ControllerPort = 0;
    public static final int kOperatorLogitechControllerPort = 0;

    // 🔘 Button mappings for Xbox controller
    public static final XboxController.Button XBOX_BUTTON_A = XboxController.Button.kA;
    public static final XboxController.Button XBOX_BUTTON_B = XboxController.Button.kB;
    public static final XboxController.Button XBOX_BUTTON_BACK = XboxController.Button.kBack;
    public static final XboxController.Button XBOX_BUTTON_LEFT_BUMPER = XboxController.Button.kLeftBumper;
    public static final XboxController.Button XBOX_BUTTON_LEFT_STICK = XboxController.Button.kLeftStick;
    public static final XboxController.Button XBOX_BUTTON_RIGHT_BUMPER = XboxController.Button.kRightBumper;
    public static final XboxController.Button XBOX_BUTTON_RIGHT_STICK = XboxController.Button.kRightStick;
    public static final XboxController.Button XBOX_BUTTON_START = XboxController.Button.kStart;
    public static final XboxController.Button XBOX_BUTTON_X = XboxController.Button.kX;
    public static final XboxController.Button XBOX_BUTTON_Y = XboxController.Button.kY;

    // 🔘 Button mappings for PS4 controller
    public static final PS4Controller.Button PS4_BUTTON_CIRCLE = PS4Controller.Button.kCircle;
    public static final PS4Controller.Button PS4_BUTTON_CROSS = PS4Controller.Button.kCross;
    public static final PS4Controller.Button PS4_BUTTON_L1 = PS4Controller.Button.kL1;
    public static final PS4Controller.Button PS4_BUTTON_L2 = PS4Controller.Button.kL2;
    public static final PS4Controller.Button PS4_BUTTON_L3 = PS4Controller.Button.kL3;
    public static final PS4Controller.Button PS4_BUTTON_OPTIONS = PS4Controller.Button.kOptions;
    public static final PS4Controller.Button PS4_BUTTON_PS = PS4Controller.Button.kPS;
    public static final PS4Controller.Button PS4_BUTTON_R1 = PS4Controller.Button.kR1;
    public static final PS4Controller.Button PS4_BUTTON_R2 = PS4Controller.Button.kR2;
    public static final PS4Controller.Button PS4_BUTTON_R3 = PS4Controller.Button.kR3;
    public static final PS4Controller.Button PS4_BUTTON_SHARE = PS4Controller.Button.kShare;
    public static final PS4Controller.Button PS4_BUTTON_SQUARE = PS4Controller.Button.kSquare;
    public static final PS4Controller.Button PS4_BUTTON_TOUCHPAD = PS4Controller.Button.kTouchpad;
    public static final PS4Controller.Button PS4_BUTTON_TRIANGLE = PS4Controller.Button.kTriangle;

    // 🔘 Button and axis mappings for Logitech controller
    public static final int LOGITECH_BUTTON_A = 1;
    public static final int LOGITECH_BUTTON_B = 2;
    public static final int LOGITECH_BUTTON_X = 3;
    public static final int LOGITECH_BUTTON_Y = 4;
    public static final int LOGITECH_BUTTON_LEFT_BUMPER = 5;
    public static final int LOGITECH_BUTTON_RIGHT_BUMPER = 6;
    public static final int LOGITECH_BUTTON_BACK = 7;
    public static final int LOGITECH_BUTTON_START = 8;
    public static final int LOGITECH_BUTTON_LEFT_STICK = 9;
    public static final int LOGITECH_BUTTON_RIGHT_STICK = 10;
    public static final int LOGITECH_BUTTON_LEFT_ANALOG_PUSH = 11;
    public static final int LOGITECH_BUTTON_RIGHT_ANALOG_PUSH = 12;

    public static final int LOGITECH_AXIS_LEFT_X = 0;
    public static final int LOGITECH_AXIS_LEFT_Y = 1;
    public static final int LOGITECH_AXIS_LEFT_TRIGGER = 2;
    public static final int LOGITECH_AXIS_RIGHT_TRIGGER = 3;
    public static final int LOGITECH_AXIS_RIGHT_X = 4;
    public static final int LOGITECH_AXIS_RIGHT_Y = 5;
}
