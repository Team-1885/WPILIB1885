// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import frc.robot.RobotMap;
import lombok.Getter;

/**
 * Runs the WestCoastDrive --> robot's motor and wheels
 */
@SuppressWarnings("PMD.CommentSize")
public class WestCoastDrive extends SubsystemBase {

        /**
         * instance of the exception handler
         */
        private @Getter final ADAM adam = new ADAM(null);
        /**
         * sets the id for every instance of CANSparkMax for the leftMaster
         */
        private static CANSparkMax leftMaster = new CANSparkMax(RobotMap.WestCoastDriveConstants.L_D_PRIMARY_ID, MotorType.kBrushless),
        leftFollower = new CANSparkMax(RobotMap.WestCoastDriveConstants.L_D_FOLLOWER_ID, MotorType.kBrushless);
        /**
         * sets the id for every instance of CANSparkMax for the rightMaster
         */

        private static CANSparkMax rightMaster = new CANSparkMax(RobotMap.WestCoastDriveConstants.R_D_PRIMARY_ID, MotorType.kBrushless),
        rightFollower = new CANSparkMax(RobotMap.WestCoastDriveConstants.R_D_FOLLOWER_ID, MotorType.kBrushless);                           /**
         
        /**
        * instantiates variables for the positions of the motor, intended to return the number of rotations for each encoder
        */
        private @Getter RelativeEncoder leftEncoder,
                        rightEncoder;
        /**
         * Lorem Ipsum.
         */
        private static SparkMaxPIDController leftCtrl,
                        rightCtrl;
        /**
         * feedback loop for error control based on proportional, derivative and integral terms
         * sets variables for the left and right pid data
         */
        private static PIDController leftPositionPID,
                        rightPositionPID;

        private ShuffleboardTab tab = Shuffleboard.getTab("===== WEST COAST DRIVE =====");
        private GenericEntry testEntry = 
                tab.add("===== SET MOTOR SPEED =====", 0)
                        .getEntry();
                
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

        }

        @Override
        public void periodic() {
                runTest(() -> {
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

        public void setMotorSpeed(final double param) {

                leftMaster.set(param);
                leftFollower.set(param);
                rightMaster.set(param);
                rightFollower.set(param);
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
