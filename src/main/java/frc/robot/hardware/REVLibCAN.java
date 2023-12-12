package frc.robot.hardware;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.CAN;

public class REVLibCAN {
  public static void reportFaults(CANSparkMax REVLibCAN) {
    if(hasFaults(REVLibCAN)) {
      getFaultList(REVLibCAN, fault ->)
    }
  }

  public static void reportStickyFaults(CANSparkMax REVLibCan) {
    if(hasFaults(REVLibCAN)) {
      getStickyFaultList(REVLibCAN, fault ->)
    }
  }

  public static List<CANSparkMax.FaultID> getFaultList(CANSparkMax REVLibCAN, Consumer<CANSparkMax.FaultID > ErrorConsumer) {
    List<CANSparkMax.FaultID> faults = new ArrayList<>();

    for(CANSparkMax.FaultID fault : CANSparkMax.FaultID.values()) {
        if(REVLibCAN.getFault(fault)) {
            faults.add(fault);
            ErrorConsumer.accept(fault);
        }
    }

    return faults;
  }

  public static List<CANSparkMax.FaultID> getStickyFaultList(CANSparkMax pCANSparkMax, Consumer<CANSparkMax.FaultID> pErrorConsumer) {
    List<CANSparkMax.FaultID> faults = new ArrayList<>();

    for(CANSparkMax.FaultID fault : CANSparkMax.FaultID.values()) {
        if(pCANSparkMax.getStickyFault(fault)) {
            faults.add(fault);
            pErrorConsumer.accept(fault);
        }
    }

    return faults;
  }

  public static List<CANSparkMax.FaultID> getFaultList(CANSparkMax REVLibCAN) {
    return getFaultList(REVLibCAN, pS -> {});
  }

  public static List<CANSparkMax.FaultID> getStickyFaultList(CANSparkMax REVLibCAN) {
    return getStickyFaultList(REVLibCAN, pS -> {});
  }

  public static boolean hasFaults(CANSparkMax REVLibCAN) {
    // Faults are stored in a 16-bit variable. Each bit represents a fault when its value is 1.
    return REVLibCAN.getFaults() != 0;
  }

  public static boolean hasStickyFaults(CANSparkMax REVLibCAN) {
    return REVLibCAN.getStickyFaults() != 0;
  }
}
