package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class ClimbModeSelection {
  private SendableChooser<String> mClimbSelection = new SendableChooser<String>();
  private ShuffleboardTab mClimbOptions = Shuffleboard.getTab("Pre-Match Configuration");

  public ClimbModeSelection() {
    mClimbOptions.add("Climber Mode", mClimbSelection)
        .withPosition(1, 3)
        .withSize(2, 2);
    mClimbSelection.setDefaultOption("Default - Districts", "DCMP");
    mClimbSelection.addOption("Districts Automation", "DCMP");
    mClimbSelection.addOption("Worlds Automation", "WCMP");
  }

  public String getSelectedMode() {
    return mClimbSelection.getSelected();
  }
}