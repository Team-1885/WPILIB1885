// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;
import java.util.stream.Stream;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import frc.robot.hardware.REVLibCAN;
import lombok.Getter;

/**
 * SpinMotor Subsystem.
 */
@SuppressWarnings("PMD.CommentSize")
public class SpinMotor extends SubsystemBase {
        private @Getter final ADAM adam = new ADAM(null);

        // Creates two CANSparkMax motors, inheriting physical constants from the {@link#REVLibCAN} helper class.
        private static CANSparkMax REV_0xM1 = new CANSparkMax(REVLibCAN.L_MASTER_ID, REVLibCAN.MOTOR_TYPE),
                        REV_0xF1 = new CANSparkMax(REVLibCAN.L_FOLLOWER_ID, REVLibCAN.MOTOR_TYPE);

        /**
         * Lorem Ipsum.
         */

        private ShuffleboardTab tab = Shuffleboard.getTab("===== WEST COAST DRIVE =====");
        private GenericEntry testEntry = tab.add("===== SET MOTOR SPEED =====", 0)
                        .getEntry();

        /** Constructor for the Subsystem */
        public SpinMotor() {
                /**
                 * Low-level configurations for the hardware objects
                 */
                super();
                Stream.of(REV_0xM1, REV_0xF1)
                                .forEach(CANSparkMax::restoreFactoryDefaults);
                final Map<CANSparkMax, CANSparkMax> masterFollowerMap = Map.of(
                                REV_0xM1, REV_0xF1);
                masterFollowerMap.forEach((master, follower) -> follower.follow(master));
                Stream.of(REV_0xM1, REV_0xF1)
                                .forEach(motor -> motor.setInverted(true));
                Stream.of(REV_0xM1, REV_0xF1)
                                .forEach(motor -> motor.setIdleMode(CANSparkMax.IdleMode.kCoast));
                Stream.of(REV_0xM1, REV_0xF1)
                                .forEach(motor -> motor.setSmartCurrentLimit(30, 35, 100));
                Stream.of(REV_0xM1, REV_0xF1)
                                .forEach(motor -> motor.setClosedLoopRampRate(0.5));
                Stream.of(REV_0xM1, REV_0xF1)
                                .forEach(motor -> motor.setOpenLoopRampRate(0.5));
                Stream.of(REV_0xM1, REV_0xF1)
                                .forEach(motor -> motor.setControlFramePeriodMs(1));
                Stream.of(REV_0xM1, REV_0xF1)
                                .forEach(CANSparkMax::burnFlash);
        }

        @Override
        public void periodic() { // This method will be called once per scheduler run (usually, once every 20 ms),
                runTest(() -> {
                        testEntry.setDouble(REV_0xM1.get());
                        REVLibCAN.logFaults(Stream.of(REV_0xM1, REV_0xF1));
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
                        // Resets
                        Stream.of(REV_0xM1)
                                        .forEach(motor -> motor.stopMotor());
                });
        }

        public void setMotorSpeed(final double leftSpeed, final double rightSpeed) {
                // Setting motor speed using the ".set()" method from the CANSparkMax class
                REV_0xM1.set(leftSpeed);
                REV_0xF1.set(leftSpeed);

        }

        public double getMotorSpeed() {
                // Getting motor speed using the ".get()" method from the CANSparkMax class
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
