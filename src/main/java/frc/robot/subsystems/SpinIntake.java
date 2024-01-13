package frc.robot.subsystems;



import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import frc.robot.hardware.REVLibCAN;
import lombok.Getter;

/**
 * Lorem Ipsum.
 */
@SuppressWarnings("PMD.CommentSize")
public class SpinIntake extends SubsystemBase {
        
        private @Getter final ADAM adam = new ADAM(null);

        private TalonFX intakeMotor = new TalonFX(9);

        public void setMotorSpeed(final double leftSpeed) {
                intakeMotor.set(ControlMode.PercentOutput, leftSpeed);
        }

        public double getMotorSpeed() {
                return intakeMotor.getMotorOutputPercent();
        }
        

}
