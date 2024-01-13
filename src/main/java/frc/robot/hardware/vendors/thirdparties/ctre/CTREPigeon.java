package frc.robot.hardware.vendors.thirdparties.ctre;

import com.flybotix.hfr.codex.CodexOf;

public class CTREPigeon {

  public enum EPigeon implements CodexOf<Double> {
    YAW,
    FUSED_HEADING,
    ROLL,
    PITCH,
    ACCEL_X,
    ACCEL_Y,
    JERK_X,
    JERK_Y,
    COLLISION
  }
}
