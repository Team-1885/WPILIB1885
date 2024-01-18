package frc.robot.subsystems;



import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ADAM;
import lombok.Getter;

/**
 * Lorem Ipsum.
 */
@SuppressWarnings("PMD.CommentSize")
public class SpinIntake extends SubsystemBase {
        private ShuffleboardTab tab = Shuffleboard.getTab("SPIN INTAKE");
        private GenericEntry testEntry = tab.add("SET MOTOR SPEED", 0)
                        .getEntry();
        
        private @Getter final ADAM adam = new ADAM(null);

        private TalonFX intakeMotor = new TalonFX(9);

        public void setMotorSpeed(final double leftSpeed) {
                intakeMotor.set(ControlMode.PercentOutput, leftSpeed);
                

        }
        @Override
        public void periodic(){
                // testEntry.setDouble(intakeMotor.getMotorOutputPercent());
        }
        
        public double getMotorSpeed() {
                return intakeMotor.getMotorOutputPercent();
        }
        

}
