// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;
import java.util.stream.Stream;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import frc.robot.hardware.REVLibCAN;
import lombok.Getter;

/**
 * Limits comment size
 */
@SuppressWarnings("PMD.CommentSize")
public class MakeMotorSpin extends SubsystemBase {
        private @Getter final ADAM adam = new ADAM(null);
        
        private static CANSparkMax leftMaster = new CANSparkMax(REVLibCAN.L_FOLLOWER_ID,
                        REVLibCAN.MOTOR_TYPE),
                        leftFollower = new CANSparkMax(REVLibCAN.L_FOLLOWER_ID,
                        REVLibCAN.MOTOR_TYPE);
        /**
         * Set up drive system- makes motor ID, power amt, motor output, and stop motor
         */
        private static CANSparkMax rightMaster = new CANSparkMax(REVLibCAN.R_MASTER_ID,
        REVLibCAN.MOTOR_TYPE),

        rightFollower = new CANSparkMax(REVLibCAN.R_FOLLOWER_ID,
        REVLibCAN.MOTOR_TYPE);

        
                              
                                        /**
         * ensures that the 'follower' follows the 'master'
         */
        private @Getter RelativeEncoder leftEncoder,
                        rightEncoder;
        //display robot info
        private ShuffleboardTab tab = Shuffleboard.getTab("===== MAKE MOTOR SPIN =====");
        private GenericEntry testEntry = 
                tab.add("===== SET MOTOR SPEED =====", 0)
                        .getEntry();
                
        /** Creates a new Make motor spin. */
        public MakeMotorSpin() {
                super();
                //sets controller motor configuration
                Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
                                .forEach(CANSparkMax::restoreFactoryDefaults);
                //Map.of maps out leftMaster & leftFollower and rightMaster & rightFollower                
                final Map<CANSparkMax, CANSparkMax> masterFollowerMap = Map.of(
                                leftMaster, leftFollower,
                                rightMaster, rightFollower);
                //master is paired with follower
                masterFollowerMap.forEach((master, follower) -> follower.follow(master));
               //sets the property of inversion to false- LEFT 
                Stream.of(leftMaster, leftFollower)
                                .forEach(motor -> motor.setInverted(false));
                //sets the property of inversion to true- RIGHT
                Stream.of(rightMaster, rightFollower)
                                .forEach(motor -> motor.setInverted(true));
                //sets idle mode (Brake or Coast) for the motor
                Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
                                .forEach(motor -> motor.setIdleMode(CANSparkMax.IdleMode.kCoast));
                //sets limits on motor movement (ex: stall, free movement, and RPM)
                Stream.of(leftMaster, leftFollower, rightMaster, rightFollower)
                                .forEach(motor -> motor.setSmartCurrentLimit(30, 35, 100));
                //gets objects for left and right motors
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

        }

        @Override
        public void periodic() {
                runTest(() -> {
                        //sets motors to entry
                        testEntry.setDouble(leftMaster.get());
                        testEntry.setDouble(leftFollower.get());
                        testEntry.setDouble(rightMaster.get());
                        testEntry.setDouble(rightFollower.get());
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

        public void setMotorSpeed(final double left, final double right) {

                leftMaster.set(left);
                leftFollower.set(left);
                rightMaster.set(right);
                rightFollower.set(right);
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
