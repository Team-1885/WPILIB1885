package frc.robot.hardware;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DriverStation;

public class REVLibCAN {

  // ====================================
  // DO NOT EDIT THESE PHYSICAL CONSTANTS - WESTCOASTDRIVE
  // ====================================
  
  public static final double GEARBOX_RATIO = (12.0 / 40.0) * (16.0 / 38.0);
  public static final double WHEEL_DIAMETER_FT = 0.5;
  public static final double WHEEL_DIAMETER_IN = 6;
  public static final double TRACK_WIDTH_FEET = 1.8;
  public static final double WHEEL_CIRCUM_FT = WHEEL_DIAMETER_FT * Math.PI;
  public static final double NEO_POS_FACTOR = GEARBOX_RATIO * WHEEL_CIRCUM_FT;
  public static final double NEO_VEL_FACTOR = NEO_POS_FACTOR / 60.0;
  public static final double MAX_VELOCITY_RPM = 5676;
  public static final double PULSES_PER_ROTATION = 256.0;
  public static final double CURRENT_LIMIT_AMPS = 60.0;
  public static final int L_MASTER_ID = 2;
  public static final int L_FOLLOWER_ID = 4;
  

  // =======================================
  // DO NOT EDIT THESE CONFIGURATION OPTIONS - WESTCOASTDRIVE
  // =======================================

  public static final MotorType MOTOR_TYPE = MotorType.kBrushless;

  private static final Logger logger = Logger.getLogger(REVLibCAN.class.getName());

  public static void reportFaults(CANSparkMax REVLibCAN) {
    if(hasFaults(REVLibCAN)) {
      getFaultList(REVLibCAN, fault -> logger.log(Level.SEVERE, "Fault " + fault.name() + " detected on Spark MAX ID " + REVLibCAN.getDeviceId()));
    }
  }

  public static void reportStickyFaults(CANSparkMax REVLibCAN) {
    if(hasFaults(REVLibCAN)) {
      getStickyFaultList(REVLibCAN, fault -> logger.log(Level.SEVERE, "Sticky Fault " + fault.name() + " detected on Spark MAX ID " + REVLibCAN.getDeviceId()));
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
