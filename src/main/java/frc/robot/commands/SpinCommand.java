package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ADAM;
import frc.robot.RobotContainer;
import frc.robot.subsystems.AviSamSpinMotor;
import lombok.Getter;

public class SpinCommand extends CommandBase{
    private @Getter ADAM adam = new ADAM(null);
    

    private @Getter AviSamSpinMotor aviSamSpinMotor;


    public SpinCommand(AviSamSpinMotor aviSamSpinMotor)
    {
        this.aviSamSpinMotor = aviSamSpinMotor;
        addRequirements(aviSamSpinMotor);

    }
    @Override
    public void initialize() {
        System.out.println("========== STARTING SPINCOMMAND ==========");
        runTest(() -> {

    });
  }

    @Override
    public void execute() {
        runTest(() -> {
            // double forwardSpeed = -RobotContainer.logitech.getRawAxis(1) * 0.5; // Get Y-axis value of left stick
            double forwardSpeed = -RobotContainer.logitech.getRawAxis(1) * 0.30;
            double turnSpeed = -RobotContainer.logitech.getRawAxis(0) * 0.20; // Get X-axis value of left stick
      
            // You may want to add deadzones to prevent small joystick values from causing
            // unintended movement
            forwardSpeed = applyDeadzone(forwardSpeed, 0);
            turnSpeed = applyDeadzone(turnSpeed, 0);
      
            // Calculate left and right motor speeds for tank drive
            double leftSpeed = forwardSpeed + turnSpeed;
            /* 
            if (leftSpeed > .5) {
              leftSpeed = .4;
            }
            if (leftSpeed < -.3) {
              leftSpeed = -.1;
            }
            */
            
            
      
            // Set motor speeds in the AviSamSpinMotor subsystem
            aviSamSpinMotor.setMotorSpeed(leftSpeed);
          });
      }


      private double applyDeadzone(double value, double deadzone) {
        if (Math.abs(value) < deadzone) {
          return 0.0;
        } else {
          return value;
        }
      }
    
      // Called once the command ends or is interrupted.
      @Override
      public void end(final boolean interrupted) {
        runTest(() -> {
    
        });
      }
    
      // Returns true when the command should end.
      @Override
      public boolean isFinished() {
        return false;
      }
    
      /**
       * Executes debugging actions for testing purposes.
       * Calls initialize(), execute(), and end() within a testing environment.
       *
       * @see #initialize()
       * @see #execute()
       * @see #end(boolean)
       */
      public void debugCommand() {
        runTest(() -> initialize());
        runTest(() -> execute());
        runTest(() -> end(false));
      }



      public void runTest(final Runnable code) {
        try {
          code.run();
        } catch (Exception e) {
          adam.uncaughtException(Thread.currentThread(), e);
        }
      }
}
