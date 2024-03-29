package frc.common.lib.control;

public class PIDGains {
  public final double kP;
  public final double kI;
  public final double kD;
  public final double kF;

  public PIDGains(double pP, double pI, double pD) {
    this(pP, pI, pD, 0d);
  }

  public PIDGains(double pP, double pI, double pD, double pF) {
    kP = pP;
    kI = pI;
    kD = pD;
    kF = pF;
  }

  public String toString() {
    return String.format("kP: %s\nkI: %s\nkD: %s\nkF: %s", kP, kI, kD, kF);
  }

}