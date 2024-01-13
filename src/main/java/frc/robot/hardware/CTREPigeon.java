package frc.robot.hardware;

import com.flybotix.hfr.codex.Codex;
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

  public static void map(Codex<Double, EPigeon> pCodex) {
    
  }
}
