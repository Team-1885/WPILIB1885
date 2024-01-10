package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ADAM;
import frc.robot.RobotContainer;
import frc.robot.subsystems.SpinIntake;
import lombok.Getter;

public class SpinIntakeCommand extends CommandBase{
    private @Getter ADAM adam = new ADAM(null);
    

    private @Getter SpinIntake intake;


    public SpinIntakeCommand(SpinIntake intake)
    {
        this.intake = intake;
        addRequirements(intake);

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
            double leftSpeed = 0.3;
            intake.setMotorSpeed(leftSpeed);
          });
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
