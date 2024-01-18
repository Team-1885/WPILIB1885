package frc.robot.hardware.vendors.firstparties;

import com.flybotix.hfr.codex.Codex;
import com.flybotix.hfr.codex.CodexOf;

import com.flybotix.hfr.codex.RobotCodex;
import edu.wpi.first.wpilibj.PowerDistribution;

public enum EPDP implements CodexOf<Double> {
  CURRENT0(40),
  CURRENT1(40),
  CURRENT2(40),
  CURRENT3(40),
  CURRENT4(30),
  CURRENT5(30),
  CURRENT6(30),
  CURRENT7(30),
  CURRENT8(30),
  CURRENT9(30),
  CURRENT10(30),
  CURRENT11(30),
  CURRENT12(40),
  CURRENT13(40),
  CURRENT14(40),
  CURRENT15(40),
  VOLTAGE(12),
  TEMPERATURE(70);

  public final double BREAKER_VALUE;

  EPDP(double pBreakerValue) {
    BREAKER_VALUE = pBreakerValue;
  }

  /**
   * WARNING -
   * This code takes about 5ms to execute. If I try to split it to concurrently read the PDP, it takes about 7.5ms.
   * 
   * Longer-term, we should cut out the PDP current readings we don't need.
   * 
   * @param pCodex PDP codex
   * @param pPDP   PDP hardware
   */
  public static void map(Codex<Double, EPDP> pCodex, PowerDistribution pPDP) {
    for (int i = 0; i < 16; i++) {
      pCodex.set(i, pPDP.getCurrent(i));
    }
    pCodex.set(VOLTAGE, pPDP.getVoltage());
    pCodex.set(TEMPERATURE, pPDP.getTemperature());
  }

  public static boolean isAboveCurrentThreshold(double pCurrentThreshold, RobotCodex<EPDP> pPdpCodex,
      EPDP... pPdpSlots) {
    boolean isCurrentLimiting = false;
    for (EPDP slot : pPdpSlots) {
      if (pPdpCodex.get(slot) >= pCurrentThreshold)
        isCurrentLimiting = true;
    }

    return isCurrentLimiting;
  }

}