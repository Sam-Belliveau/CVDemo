package CVDemo.commands;

import com.stuypulse.stuylib.math.Angle;

import CVDemo.Limelight;
import CVDemo.Robot;

/**
 * This is the part we are going to edit
 */
public class AlignmentCommand {
    
    public Robot robot;
    public Limelight limelight;

    public AlignmentCommand(Robot r, Limelight l) {
        robot = r;
        limelight = l;
    }

    public void execute() {

        Angle a = limelight.getTargetXAngle();

        // NO ANGLE IS FOUND MEANS FAIL OUT
        if (a == null) {
            return;
        }

        double error = a.toDegrees();

        System.out.println(error);

        if(error < 0) {
            robot.arcadeDrive(0, -1);
        } else {
            robot.arcadeDrive(0, 1);
        }
    }


}