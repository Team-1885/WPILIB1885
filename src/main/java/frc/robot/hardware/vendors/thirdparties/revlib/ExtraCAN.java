package frc.robot.hardware.vendors.thirdparties.revlib;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;


public class ExtraCAN extends CANSparkMax {
    
    protected double mLastSet = Double.NaN;
    protected ControlMode mLastControlMode = null;

    public ExtraCAN(int deviceNumber) {
        super(deviceNumber, REVLibCAN.MOTOR_TYPE);
    }

    public double getLastSet() {
        return mLastSet;
    }

    @Override
    public void set(double value) {
        if (value != mLastSet || mode != mLastControlMode) {
            mLastSet = value;
            mLastControlMode = mode;
            super.set(mode, value);
        }
    }
}
