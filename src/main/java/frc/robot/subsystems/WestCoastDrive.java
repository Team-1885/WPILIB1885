// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;
import java.util.stream.Stream;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import frc.robot.hardware.REVLibCAN;
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
         * Creates two CANSparkMax motors, inheriting physical constants from the {@link #REVLibCAN} helper class.
         */
        private static CANSparkMax REV_0xM1 = new CANSparkMax(REVLibCAN.L_MASTER_ID,
                        REVLibCAN.MOTOR_TYPE),
                        REV_0xF1 = new CANSparkMax(REVLibCAN.L_FOLLOWER_ID,
                                        REVLibCAN.MOTOR_TYPE);
        /**
         * Creates two CANSparkMax motors, inheriting physical constants from the {@link #REVLibCAN} helper class.
         */
        private static CANSparkMax REV_0xM2 = new CANSparkMax(REVLibCAN.R_MASTER_ID,
                        REVLibCAN.MOTOR_TYPE),
                        REV_0xF2 = new CANSparkMax(REVLibCAN.R_FOLLOWER_ID,
                                        REVLibCAN.MOTOR_TYPE);
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

        private ShuffleboardTab tab = Shuffleboard.getTab("===== WEST COAST DRIVE =====");
        private GenericEntry testEntry = tab.add("===== SET MOTOR SPEED =====", 0)
                        .getEntry();

        /** Creates a new WestCoastDrive. */
        public WestCoastDrive() {
                /**
                 * 
                 */
                super();
                Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2)
                                .forEach(CANSparkMax::restoreFactoryDefaults);
                final Map<CANSparkMax, CANSparkMax> masterFollowerMap = Map.of(
                                REV_0xM1, REV_0xF1,
                                REV_0xM2, REV_0xF2);
                masterFollowerMap.forEach((master, follower) -> follower.follow(master));
                Stream.of(REV_0xM1, REV_0xF1)
                                .forEach(motor -> motor.setInverted(false));
                Stream.of(REV_0xM2, REV_0xF2)
                                .forEach(motor -> motor.setInverted(true));
                Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2)
                                .forEach(motor -> motor.setIdleMode(CANSparkMax.IdleMode.kCoast));
                Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2)
                                .forEach(motor -> motor.setSmartCurrentLimit(30, 35, 100));
                leftEncoder = REV_0xM1.getEncoder();
                rightEncoder = REV_0xM2.getEncoder();
                Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2)
                                .forEach(motor -> motor.setClosedLoopRampRate(0.5));
                Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2)
                                .forEach(motor -> motor.setOpenLoopRampRate(0.5));
                Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2)
                                .forEach(motor -> motor.setControlFramePeriodMs(1));
                Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2)
                                .forEach(CANSparkMax::burnFlash);
        }

        @Override
        public void periodic() {
                runTest(() -> {
                        testEntry.setDouble(REV_0xM1.get());
                        REVLibCAN.logFaults(Stream.of(REV_0xM1, REV_0xF1, REV_0xM2, REV_0xF2));
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
                        Stream.of(REV_0xM1, REV_0xM2)
                                        .forEach(motor -> motor.stopMotor());
                });
        }

        public void setMotorSpeed(final double leftSpeed, final double rightSpeed) {
                REV_0xM1.set(leftSpeed);
                REV_0xF1.set(leftSpeed);

                REV_0xM2.set(rightSpeed);
                REV_0xF2.set(rightSpeed);
        }

        public double getMotorSpeed() {
                return REV_0xM1.get();
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
