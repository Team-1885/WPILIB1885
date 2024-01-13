// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

  // DoubleSolenoid corresponds to a double solenoid.
  // In this case, it's connected to channels 1 and 2 of a PH with the default CAN ID.0
  

public class Pneumatics extends SubsystemBase{
    /**
     * Creates a new Pneumatics
     */

     //private final DoubleSolenoid m_doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);

     private DoubleSolenoid arm, claw;


     public Pneumatics()
     {
        arm = new DoubleSolenoid(null, 1,2);
        claw = new DoubleSolenoid(null, 5,6);
    }

    public void intake()
    {
        claw.set(Value.kReverse);
        arm.set(Value.kForward);
        claw.set(Value.kForward);
        arm.set(Value.kReverse);
    }

    public void outtake()
    {
        claw.set(Value.kReverse);
        arm.set(Value.kForward);
        claw.set(Value.kForward);
        arm.set(Value.kReverse);
    }
}

