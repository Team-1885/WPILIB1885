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
 * Lorem Ipsum.
 */
@SuppressWarnings("PMD.CommentSize")
public class AviSamSpinMotor extends SubsystemBase {
        
        private @Getter final ADAM adam = new ADAM(null);

        private static CANSparkMax leftMaster = new CANSparkMax(2,
                        REVLibCAN.MOTOR_TYPE),
                        leftFollower = new CANSparkMax(REVLibCAN.L_FOLLOWER_ID,
                                        REVLibCAN.MOTOR_TYPE);

        private static CANSparkMax rightMaster = new CANSparkMax(1,
                        REVLibCAN.MOTOR_TYPE);
        
        private @Getter RelativeEncoder leftEncoder,
                                        rightEncoder;
                    
       
                        

        private ShuffleboardTab tab = Shuffleboard.getTab("===== SPIN ROBOT =====");
        private GenericEntry testEntry = tab.add("===== SET MOTOR SPEED =====", 0)
                        .getEntry();

        public AviSamSpinMotor() {
                super();
                Stream.of(leftMaster, leftFollower)
                                .forEach(CANSparkMax::restoreFactoryDefaults);
                final Map<CANSparkMax, CANSparkMax> masterFollowerMap = Map.of(
                                leftMaster, leftFollower);
                masterFollowerMap.forEach((master, follower) -> follower.follow(master));
                Stream.of(leftMaster, leftFollower)
                                .forEach(motor -> motor.setInverted(false));
                Stream.of(leftMaster, leftFollower)
                                .forEach(motor -> motor.setIdleMode(CANSparkMax.IdleMode.kCoast));
                Stream.of(leftMaster, leftFollower)
                                .forEach(motor -> motor.setSmartCurrentLimit(30, 35, 100));
                leftEncoder = leftMaster.getEncoder();
                
                Stream.of(leftMaster, leftFollower)
                                .forEach(motor -> motor.setClosedLoopRampRate(0.5));
                Stream.of(leftMaster, leftFollower)
                                .forEach(motor -> motor.setOpenLoopRampRate(0.5));
                Stream.of(leftMaster, leftFollower)
                                .forEach(motor -> motor.setControlFramePeriodMs(1));
                Stream.of(leftMaster, leftFollower)
                                .forEach(CANSparkMax::burnFlash);
        }

        @Override
        public void periodic() {
                runTest(() -> {
                        testEntry.setDouble(leftMaster.get());
                        REVLibCAN.logFaults(Stream.of(leftMaster, leftFollower));
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

        public void setMotorSpeed(final double leftSpeed) {
                leftMaster.set(leftSpeed);
                leftFollower.set(leftSpeed);

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
