package frc.robot.hardware;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DriverStation;

public class REVLibCAN {

  // ==========
  // DO NOT EDIT THESE PHYSICAL CONSTANTS
  // ==========
  private static final Logger logger = Logger.getLogger(REVLibCAN.class.getName());

  public static void reportFaults(CANSparkMax REVLibCAN) {
    if(hasFaults(REVLibCAN)) {
      getFaultList(REVLibCAN, fault -> logger.log(Level.SEVERE, "Fault " + fault.name() + " detected on Spark MAX ID " + REVLibCAN.getDeviceId()));
    }
  }

  public static void reportStickyFaults(CANSparkMax REVLibCAN) {
    if(hasFaults(REVLibCAN)) {
      getStickyFaultList(REVLibCan, fault -> Level.SEVERE, "Sticky Fault " + fault.name() + " detected on Spark MAX ID " + REVLibCAN.getDeviceId()));
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
