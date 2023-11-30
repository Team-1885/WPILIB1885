// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import frc.robot.RobotMap;
import lombok.Getter;

/**
 * Lorem Ipsum.
 */
@SuppressWarnings("PMD.CommentSize")
public class WestCoastDrive extends SubsystemBase {

    /**
     * Lorem Ipsum.
     */
    private @Getter final ADAM adam = new ADAM(null);
    /**
     * Lorem Ipsum.
     */
    private static CANSparkMax leftMaster = new CANSparkMax(RobotMap.WestCoastDriveConstants.L_D_PRIMARY_ID,
            MotorType.kBrushless),
            leftFollower = new CANSparkMax(RobotMap.WestCoastDriveConstants.L_D_FOLLOWER_ID, MotorType.kBrushless);
    /**
     * Lorem Ipsum.
     */
    private static CANSparkMax rightMaster = new CANSparkMax(RobotMap.WestCoastDriveConstants.R_D_PRIMARY_ID,
            MotorType.kBrushless),
            rightFollower = new CANSparkMax(RobotMap.WestCoastDriveConstants.R_D_FOLLOWER_ID, MotorType.kBrushless);
    /**
     * Lorem Ipsum.
     */
    private @Getter RelativeEncoder leftEncoder,
            rightEncoder;
    /**
     * Lorem Ipsum.
     */
    private static SparkMaxPIDController leftCtrl,
            rightCtrl;
    /**
     * Lorem Ipsum.
     */
    private static PIDController leftPositionPID,
            rightPositionPID;

    private @Getter final Map<String, List<SimpleWidget>> widgetMap = new ConcurrentHashMap<>();

    /** Creates a new WestCoastDrive. */
    public WestCoastDrive() {
        super();
        Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
                .forEach(CANSparkMax::restoreFactoryDefaults);
        final Map<CANSparkMax, CANSparkMax> masterFollowerMap = Map.of(
                leftMaster, leftFollower,
                rightMaster, rightFollower);
        masterFollowerMap.forEach((master, follower) -> follower.follow(master));
        Stream.of(leftMaster, leftFollower)
                .forEach(motor -> motor.setInverted(false));
        Stream.of(rightMaster, rightFollower)
                .forEach(motor -> motor.setInverted(true));
        Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
                .forEach(motor -> motor.setIdleMode(CANSparkMax.IdleMode.kCoast));
        Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
                .forEach(motor -> motor.setSmartCurrentLimit(30, 35, 100));
        leftEncoder = leftMaster.getEncoder();
        rightEncoder = rightMaster.getEncoder();
        Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
                .forEach(motor -> motor.setClosedLoopRampRate(0.5));
        Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
                .forEach(motor -> motor.setOpenLoopRampRate(0.5));
        Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
                .forEach(motor -> motor.setControlFramePeriodMs(1));
        Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
                .forEach(CANSparkMax::burnFlash);

        // Create a Shuffleboard tab for the WestCoastDrive subsystem
        final ShuffleboardTab westCoastDriveTab = Shuffleboard.getTab("WestCoastDrive");

        final BiConsumer<String, CANSparkMax> widFactory = (label, motor) -> {
            final List<SimpleWidget> widgets = List.of(
                    westCoastDriveTab.add(label + " - Class", motor.getClass().getName())
                            .withWidget(BuiltInWidgets.kTextView),
                    westCoastDriveTab.add(label + " - Firmware String", motor.getFirmwareString())
                            .withWidget(BuiltInWidgets.kTextView),
                    westCoastDriveTab.add(label + " - Set Speed", motor.get())
                            .withWidget(BuiltInWidgets.kNumberBar),
                    westCoastDriveTab.add(label + " - Applied Output", motor.getAppliedOutput())
                            .withWidget(BuiltInWidgets.kDial),
                    westCoastDriveTab.add(label + " - Bus Voltage", motor.getBusVoltage())
                            .withWidget(BuiltInWidgets.kGraph),
                    westCoastDriveTab.add(label + " - Closed Loop Ramp Rate", motor.getClosedLoopRampRate())
                            .withWidget(BuiltInWidgets.kTextView),
                    westCoastDriveTab.add(label + " - Device ID", motor.getDeviceId())
                            .withWidget(BuiltInWidgets.kTextView),
                    westCoastDriveTab.add(label + " - Encoder Position", motor.getEncoder().getPosition())
                            .withWidget(BuiltInWidgets.kGraph),
                    westCoastDriveTab.add(label + " - Firmware Version", motor.getFirmwareVersion())
                            .withWidget(BuiltInWidgets.kTextView),
                    westCoastDriveTab.add(label + " - Motor Temperature", motor.getMotorTemperature())
                            .withWidget(BuiltInWidgets.kNumberBar),
                    westCoastDriveTab.add(label + " - Motor Type", motor.getMotorType().toString())
                            .withWidget(BuiltInWidgets.kTextView),
                    westCoastDriveTab.add(label + " - Open Loop Ramp Rate", motor.getOpenLoopRampRate())
                            .withWidget(BuiltInWidgets.kTextView),
                    westCoastDriveTab.add(label + " - Output Current", motor.getOutputCurrent())
                            .withWidget(BuiltInWidgets.kTextView)
            // ... add other widgets
            );

            widgetMap.put(label, widgets);
        };

        // Add widgets for left master and follower
        widFactory.accept("Left Master", leftMaster);
        widFactory.accept("Left Follower", leftFollower);

        // Add widgets for right master and follower
        widFactory.accept("Right Master", rightMaster);
        widFactory.accept("Right Follower", rightFollower);
    }

    @Override
    public void periodic() {
        runTest(() -> {
            // Helper method to create or update widgets
            final BiConsumer<String, CANSparkMax> widConsumer = (label, motor) -> {
                // Get widgets
                final List<SimpleWidget> widgets = widgetMap.get(label);

                // Update widget values
                widgets.get(0).withWidget(BuiltInWidgets.kTextView)
                        .withProperties(Map.of("Text", motor.getClass().getName()));
                widgets.get(1).withWidget(BuiltInWidgets.kTextView)
                        .withProperties(Map.of("Text", motor.getFirmwareString()));
                widgets.get(2).withWidget(BuiltInWidgets.kNumberBar)
                        .withProperties(Map.of("Value", motor.get()));
                widgets.get(3).withWidget(BuiltInWidgets.kDial)
                        .withProperties(Map.of("Value", motor.getAppliedOutput()));
                widgets.get(4).withWidget(BuiltInWidgets.kGraph)
                        .withProperties(Map.of("Value", motor.getBusVoltage()));
                widgets.get(5).withWidget(BuiltInWidgets.kTextView)
                        .withProperties(Map.of("Text", String.valueOf(motor.getClosedLoopRampRate())));
                widgets.get(6).withWidget(BuiltInWidgets.kTextView)
                        .withProperties(Map.of("Text", String.valueOf(motor.getDeviceId())));
                widgets.get(7).withWidget(BuiltInWidgets.kGraph)
                        .withProperties(Map.of("Value", motor.getEncoder().getPosition()));
                widgets.get(8).withWidget(BuiltInWidgets.kTextView)
                        .withProperties(Map.of("Text", motor.getFirmwareVersion()));
                widgets.get(9).withWidget(BuiltInWidgets.kNumberBar)
                        .withProperties(Map.of("Value", motor.getMotorTemperature()));
                widgets.get(10).withWidget(BuiltInWidgets.kTextView)
                        .withProperties(Map.of("Text", motor.getMotorType().toString()));
                widgets.get(11).withWidget(BuiltInWidgets.kTextView)
                        .withProperties(Map.of("Text", String.valueOf(motor.getOpenLoopRampRate())));
                widgets.get(12).withWidget(BuiltInWidgets.kTextView)
                        .withProperties(Map.of("Text", String.valueOf(motor.getOutputCurrent())));
                // ... Update other widgets
            };

            // Create or update widgets for left master and follower
            widConsumer.accept("Left Master", leftMaster);
            widConsumer.accept("Left Follower", leftFollower);

            // Create or update widgets for right master and follower
            widConsumer.accept("Right Master", rightMaster);
            widConsumer.accept("Right Follower", rightFollower);

            // ... Other periodic tasks
        });
    }

    /**
     * Executes a custom method, running it within a testing environment.
     *
     * @see #runTest(Runnable)
     */
    public void reset() {
        runTest(() -> {
            Stream.of(leftEncoder, rightEncoder)
                    .forEach(encoder -> encoder.setPosition(0));
            Stream.of(leftMaster, rightMaster)
                    .forEach(motor -> motor.stopMotor());
        });
    }

    public void setMotorSpeed(final double param) {
        leftMaster.set(param);
    }

    public double getMotorSpeed() {
        return leftMaster.get();
    }

    /**
     * Executes custom testing and validation methods in a controlled environment.
     * Any exceptions thrown during execution are caught and logged.
     *
     * @see #runTest(Runnable)
     */
    public void debugSubsystem() {
        runTest(() -> periodic());
        runTest(() -> reset());
    }

    /**
     * Runs the code as a task; if an exception occurs, it is caught, and an
     * uncaught exception is passed to the default handler for the current thread.
     *
     * @param code The runnable task to be executed.
     */
    public void runTest(final Runnable code) {
        try {
            code.run();
        } catch (Exception e) {
            adam.uncaughtException(Thread.currentThread(), e);
        }
    }
}
