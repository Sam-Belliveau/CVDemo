package CVDemo.commands;

import CVDemo.Limelight;
import CVDemo.Robot;

import com.stuypulse.stuylib.control.PIDController;

public class AlignmentCommand {

    public static final double TARGET_DISTANCE = 5;
    public static final double TARGET_ANGLE = 0;
    
    private PIDController angleController;
    private PIDController distanceController;

    public Robot robot;
    public Limelight limelight;

    public AlignmentCommand(Robot r, Limelight l) {
        robot = r;
        limelight = l;
        
        angleController = new PIDController(0.02, 0, 0.002);
        distanceController = new PIDController(0.2, 0 , 0.04);
     }


     public void execute() { 
        double angle = 0;
        double speed = 0;
        
        if(limelight.isVisible()) {
            double angleError = limelight.getTargetXAngle() - TARGET_ANGLE;
    
            angle = angleController.update(angleError);

            double distanceError = limelight.getTargetDistance() - TARGET_DISTANCE;
         
            speed = distanceController.update(distanceError);
        
        } 

        robot.arcadeDrive(speed, angle);
    }
}
