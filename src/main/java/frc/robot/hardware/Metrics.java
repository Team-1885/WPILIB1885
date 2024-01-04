package frc.robot.hardware;

public class Metrics {
  public static double rpm_to_rads_per_sec(double rpm) {
    return rpm * 2.0 * Math.PI / 60.0;
  }
  
}
