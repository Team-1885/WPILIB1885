package frc.robot.hardware.vendors.firstparties;

import com.flybotix.hfr.codex.RobotCodex;
import com.flybotix.hfr.util.lang.EnumUtils;
import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.util.Color;
import frc.common.types.EClimberData;
import frc.common.types.EFeederData;
import frc.common.types.EIntakeData;
import frc.common.types.input.ELogitech310;
import frc.robot.Enums;
import frc.robot.InputMap.EDriveData;
import frc.robot.hardware.ECommonNeutralMode;
import frc.robot.hardware.vendors.thirdparties.EGyro;
import frc.robot.hardware.vendors.thirdparties.LL3.ELimelightData;
import frc.robot.hardware.vendors.thirdparties.LL3.ERawLimelightData;
import frc.robot.hardware.vendors.thirdparties.WS2812.ELEDControlData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Think of this class link an in-memory database optimized for 1 row. If you
 * need multiple rows, simply manage multiple
 * instances of this class. Just like all databases, access to data should be
 * universal but without assumptions of
 * null-ness, order of operations, etc.
 */
public class Data {

  private final ILog mLogger = Logger.createLog(Data.class);
  public Color DJ_COLOR;
  // Add new codexes here as we need more

  public static final double NULL_CODEX_VALUE = 0.0;
  private boolean mHasRegisteredWithShuffleboard = false;
  
  public final RobotCodex<EGyro> imu = new RobotCodex(Double.NaN, EGyro.class);
  public final RobotCodex<ELogitech310> driverinput = new RobotCodex(NULL_CODEX_VALUE, ELogitech310.class);
  public final RobotCodex<ELogitech310> operatorinput = new RobotCodex(NULL_CODEX_VALUE, ELogitech310.class);
  public final RobotCodex<EPDP> pdp = new RobotCodex(NULL_CODEX_VALUE, EPDP.class);
  public final RobotCodex<ERawLimelightData> rawLimelight = new RobotCodex(NULL_CODEX_VALUE, ERawLimelightData.class);
  public final RobotCodex<EClimberData> climber = new RobotCodex(NULL_CODEX_VALUE, EClimberData.class);
  public final RobotCodex<EDriveData> drivetrain = new RobotCodex(NULL_CODEX_VALUE, EDriveData.class);
  public final RobotCodex<EIntakeData> intake = new RobotCodex(NULL_CODEX_VALUE, EIntakeData.class);
  public final RobotCodex<EFeederData> feeder = new RobotCodex(NULL_CODEX_VALUE, EFeederData.class);
  public final RobotCodex<ELimelightData> limelight = new RobotCodex(NULL_CODEX_VALUE, ELimelightData.class);
  public final RobotCodex<ELEDControlData> ledcontrol = new RobotCodex(NULL_CODEX_VALUE, ELEDControlData.class);
  public final RobotCodex[] mAllCodexes = new RobotCodex[] {
      driverinput,
      operatorinput,
      imu,
      drivetrain,
      pdp,
      rawLimelight,
      feeder,
      intake,
      climber,
  };

  public final Map<String, RobotCodex> mMappedCodex = new HashMap<>();

  public final RobotCodex[] mLoggedCodexes = new RobotCodex[] {
      // Order Matters for these 2
      driverinput,
      operatorinput,
      // Order does not matter for the rest
      imu,
      drivetrain,
      feeder,
      pdp,
      intake,
      climber,
  };

  // Stores writers per codex needed for CSV logging

  /**
   * Create a Data object based on whether or not it is being used for logging
   *
   * @param pLogging
   */
  public Data(boolean pLogging) {
    for (int i = 0; i < mLoggedCodexes.length; i++) {
      RobotCodex rc = mLoggedCodexes[i];
      switch (i) {
        case 0:
          mMappedCodex.put("Driver Input", rc);
          break;
        case 1:
          mMappedCodex.put("Operator Input", rc);
          break;
        default:
          mMappedCodex.put(rc.meta().getEnum().getSimpleName(), rc);
      }
      rc.meta().setGlobalId(i);
    }

    drivetrain.createSimpleBooleanConverter(EDriveData.IS_CURRENT_LIMITING);
    drivetrain.createSimpleEnumConverter(EDriveData.STATE, Enums.EDriveState.class);
    drivetrain.createSimpleEnumConverter(EDriveData.NEUTRAL_MODE, ECommonNeutralMode.class);

    intake.createSimpleEnumConverter(EIntakeData.FEEDER_STATE, Enums.EFeederState.class);
    intake.createSimpleEnumConverter(EIntakeData.ROTATER_STATE, Enums.ERotaterState.class);
    

    climber.createSimpleEnumConverter(EClimberData.HANGER_STATE, Enums.EClimberMode.class);
    climber.createSimpleEnumConverter(EClimberData.RUNG_STATE, Enums.ERungState.class);
    climber.createSimpleBooleanConverter(EClimberData.SET_COAST);
    climber.createSimpleEnumConverter(EClimberData.IS_DOUBLE_CLAMPED, Enums.EClampMode.class);
    climber.createSimpleEnumConverter(EClimberData.IS_SINGLE_CLAMPED, Enums.EClampMode.class);

    ledcontrol.createSimpleEnumConverter(ELEDControlData.DESIRED_COLOR, Enums.LEDColorMode.class);
    ledcontrol.createSimpleBooleanConverter(ELEDControlData.LED_STATE);

    // This should be temporary - will investigate adding something to RobotCodex
    // directly
    mConvertedFields.add(EDriveData.IS_CURRENT_LIMITING.name());
    mConvertedFields.add(EDriveData.STATE.name());
    mConvertedFields.add(EDriveData.NEUTRAL_MODE.name());
    mConvertedFields.add(EIntakeData.ROTATER_STATE.name());
    mConvertedFields.add(EIntakeData.FEEDER_STATE.name());
    mConvertedFields.add(EFeederData.ENTRY_BEAM.name());
    mConvertedFields.add(EFeederData.STATE.name());
    mConvertedFields.add(EClimberData.HANGER_STATE.name());
    mConvertedFields.add(EClimberData.SET_COAST.name());
    mConvertedFields.add(EClimberData.IS_DOUBLE_CLAMPED.name());
    mConvertedFields.add(EClimberData.RUNG_STATE.name());
    mConvertedFields.add(EClimberData.IS_SINGLE_CLAMPED.name());
    mConvertedFields.add(ELEDControlData.DESIRED_COLOR.name());
    mConvertedFields.add(ELEDControlData.LED_STATE.name());
  }

  private List<String> mConvertedFields = new ArrayList<>();

  public Data() {
    this(true);
  }

  public void registerAllWithShuffleboard() {
    if (mHasRegisteredWithShuffleboard) {
      return;
    }
    for (String key : mMappedCodex.keySet()) {
      ShuffleboardTab tab = Shuffleboard.getTab(key);
      List<Enum<?>> enums = EnumUtils.getEnums(mMappedCodex.get(key).meta().getEnum(), true);
      enums.stream().forEach(
          e -> {
            if (mConvertedFields.contains(e.name())) {
              tab.addString(
                  e.name(), () -> {
                    if (mMappedCodex.get(key).isSet(e)) {
                      return mMappedCodex.get(key).toString(e);
                    } else {
                      return "";
                    }
                  });
            } else {
              tab.addNumber(e.name(), () -> {
                if (mMappedCodex.get(key).isSet(e)) {
                  return mMappedCodex.get(key).get(e);
                } else {
                  return 0d;
                }
              });
            }
          });
    }
    mHasRegisteredWithShuffleboard = true;
  }

  /**
   * Makes the log file if it doesn't already exist
   */
  public static void handleCreation(File pFile) {
    // Makes every folder before the file if the CSV's parent folder doesn't exist
    if (Files.notExists(pFile.toPath())) {
      pFile.getAbsoluteFile().getParentFile().mkdirs();
    }

    // Creates the .CSV if it doesn't exist
    if (!pFile.exists()) {
      try {
        pFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static char recieveColorFmsRelay() {
    // For testing make sure to comment out the method and
    // return a single char for the symbol of the color.
    String gameData;
    gameData = DriverStation.getGameSpecificMessage();
    if (gameData.length() > 0) {
      return gameData.charAt(0);
    } else {
      return '\u1000';
    }
  }

  // USE TO TEST LOGGING
  public void randomizeCodex(RobotCodex c) {
    for (RobotCodex rb : mLoggedCodexes) {
      List<Enum<?>> enums = EnumUtils.getEnums(c.meta().getEnum(), true);
      if (rb.equals(c)) {
        for (Enum e : enums) {
          rb.set(e, Math.random() * 10);
        }
      }
    }
  }
}