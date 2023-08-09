// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;

/**
 * 🔘 Operator Interface (OI) class that handles controller input and button bindings. 
 * 🤔 This class provides access to the driver and operator controllers and allows configuring button, axis, and POV bindings. 
 */
public class OI {

    private final static ADAM adam = new ADAM(null);

    private XboxController mDriverXboxController;
    private PS4Controller mDriverPS4Controller;
    private Joystick mDriverLogitechController;

    private XboxController mOperatorXboxController;
    private PS4Controller mOperatorPS4Controller;
    private Joystick mOperatorLogitechController;

    /**
     * 🏗️ Constructs a new instance of the OI class. 
     * 🕹️ Initializes driver and operator controllers.
     */
    public OI() {
        runTest(() -> {
            mDriverXboxController = new XboxController(Constants.kDriverXboxControllerPort);
            mDriverPS4Controller = new PS4Controller(Constants.kDriverPS4ControllerPort);
            mDriverLogitechController = new Joystick(Constants.kDriverLogitechControllerPort);

            mOperatorXboxController = new XboxController(Constants.kOperatorXboxControllerPort);
            mOperatorPS4Controller = new PS4Controller(Constants.kOperatorPS4ControllerPort);
            mOperatorLogitechController = new Joystick(Constants.kOperatorLogitechControllerPort);
        });
    }

    /**
     * 📎 Get the driver Xbox controller. 
     *
     * 📋 @return The driver Xbox controller. 
     */
    public XboxController getDriverXboxController() {
        return mDriverXboxController;
    }

    /**
     * 📎 Get the driver PS4 controller.
     *
     * 📋 @return The driver PS4 controller. 
     */
    public PS4Controller getDriverPS4Controller() {
        return mDriverPS4Controller;
    }

    /**
     * 📎 Get the driver Logitech controller. 
     *
     * 📋 @return The driver Logitech controller. 
     */
    public Joystick getDriverLogitechController() {
        return mDriverLogitechController;
    }

    /**
     * 📎 Get the operator Xbox controller. 
     *
     * 📋 @return The operator Xbox controller. 
     */
    public XboxController getOperatorXboxController() {
        return mOperatorXboxController;
    }

    /**
     * 📎 Get the operator PS4 controller. 
     *
     * 📋 @return The operator PS4 controller. 
     */
    public PS4Controller getOperatorPS4Controller() {
        return mOperatorPS4Controller;
    }

    /**
     * 📎 Get the operator Logitech controller. 
     *
     * 📋 @return The operator Logitech controller. 
     */
    public Joystick getOperatorLogitechController() {
        return mOperatorLogitechController;
    }

    public void debugClass() {
        runTest(() -> getDriverXboxController());
        runTest(() -> getOperatorXboxController());

        runTest(() -> getDriverPS4Controller());
        runTest(() -> getOperatorPS4Controller());

        runTest(() -> getDriverLogitechController());
        runTest(() -> getOperatorLogitechController());
    }

    public static void runTest(Runnable code) {
        try {
            code.run();
        } catch (Exception e) {
            adam.uncaughtException(Thread.currentThread(), e);
        }
    }
}
