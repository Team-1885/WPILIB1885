package frc.common.types;

import com.flybotix.hfr.codex.CodexOf;

public enum EFeederState implements CodexOf<Double> {
  ACTUAL_VEL_pct,//compared to max
  
  FEEDER_STATE, //on or off

  DESIRED_VEL_pct, //compared to max

}
