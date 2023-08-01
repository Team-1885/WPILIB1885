// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.DriveTrainSubsystem;
import java.util.HashMap;

/** Add your docs here. */
public class NOTEPAD {
    public enum DashboardType {
        GLASS,
        SMART_DASHBOARD,
        SHUFFLEBOARD,
        LABVIEW_DASHBOARD;
    }

    HashMap<DashboardType, > dashboardType = new HashMap<>();
    

    public NOTEPAD() {
        chooseDashboards(dashboard);
    }

    private void chooseDashboards(DashboardType dashboard) {
        switch(dashboard) {
            case GLASS:

                break;
            default:

                break;
        }
    }

    private void updateNetworkTables() {}

    private void updateSmartDashboard() {}

    private void updateShuffleboard() {}

    private void updateLabViewDashboard() {}
}
