package frc.common.types;

import com.flybotix.hfr.codex.CodexOf;

public enum EIntakeData implements CodexOf<Double> {
  ROTATER_STATE,

  FEEDER_VEL_ft_s,
  SET_FEEDER_VEL_ft_s,
  CURRENT_FEEDER_RPM,
  SET_FEEDER_RPM,
  FEEDER_PCT,
  DESIRED_FEEDER_pct,
  FEEDER_STATE,
}
