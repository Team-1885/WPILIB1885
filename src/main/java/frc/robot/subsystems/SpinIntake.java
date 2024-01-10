package frc.robot.subsystems;



import com.revrobotics.CANSparkMax;

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

        private static CANSparkMax intakeMotor = new CANSparkMax(9,
                        REVLibCAN.MOTOR_TYPE);
                       
                    
       
                        

        
        

        public void setMotorSpeed(final double leftSpeed) {
                intakeMotor.set(leftSpeed);
                

        }

        public double getMotorSpeed() {
                return intakeMotor.get();
        }
        

}
