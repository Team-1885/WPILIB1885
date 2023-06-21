// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import static frc.robot.Constants.DriverConstants.*;
import static frc.robot.Constants.OperatorConstants.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: More specific try catch logging (will come when I do GlobalExceptionHandler)

/**
 * Operator Interface (OI) class that handles controller input and button bindings.
 * This class provides access to the driver and operator controllers and allows configuring button, axis, and POV bindings.
 */

public class OI {

    // * Logger instance for logging events and errors

    private static final Logger logger = Logger.getLogger(OI.class.getName());

    // * Driver Controllers
    private XboxController mDriverXboxController;
    private PS4Controller mDriverPS4Controller;
    private Joystick mDriverLogitechController;

    // * Operator Controllers
    private XboxController mOperatorXboxController;
    private PS4Controller mOperatorPS4Controller;
    private Joystick mOperatorLogitechController;

    /**
     * Constructs a new instance of the OI class.
     */
    public OI() {
        try {
            mDriverXboxController = new XboxController(kDriverXboxControllerPort);
            mDriverPS4Controller = new PS4Controller(kDriverPS4ControllerPort);
            mDriverLogitechController = new Joystick(kDriverLogitechControllerPort);

            mOperatorXboxController = new XboxController(kOperatorXboxControllerPort);
            mOperatorPS4Controller = new PS4Controller(kOperatorPS4ControllerPort);
            mOperatorLogitechController = new Joystick(kOperatorLogitechControllerPort);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize controllers", e);
            DriverStation.reportError("Controller initialization failed: " + e.getMessage(), true);
        }
    }

    /**
     * Get the driver Xbox controller.
     * 
     * @return The driver Xbox controller.
     */
    public XboxController getDriverXboxController() {
        return mDriverXboxController;
    }

    /**
     * Get the driver PS4 controller.
     * 
     * @return The driver PS4 controller.
     */
    public PS4Controller getDriverPS4Controller() {
        return mDriverPS4Controller;
    }

    /**
     * Get the driver Logitech controller.
     * 
     * @return The driver Logitech controller.
     */
    public Joystick getDriverLogitechController() {
        return mDriverLogitechController;
    }

    /**
     * Get the operator Xbox controller.
     * 
     * @return The operator Xbox controller.
     */
    public XboxController getOperatorXboxController() {
        return mOperatorXboxController;
    }

    /**
     * Get the operator PS4 controller.
     * 
     * @return The operator PS4 controller.
     */
    public PS4Controller getOperatorPS4Controller() {
        return mOperatorPS4Controller;
    }

    /**
     * Get the operator Logitech controller.
     * 
     * @return The operator Logitech controller.
     */
    public Joystick getOperatorLogitechController() {
        return mOperatorLogitechController;
    }
}
