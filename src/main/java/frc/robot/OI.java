// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

/** Add your docs here. */
public class OI {
    
    private XboxController driverController;

    public OI() {
        driverController = new XboxController(0);
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        // Map buttons to commands or subsystem actions
        // Example:
        // driverController.getXButton().whenPressed(new ExampleCommand());
    }

    public XboxController getDriverController() {
        return driverController;
    }
}
