package CVDemo.commands;

import com.stuypulse.stuylib.math.Angle;

import CVDemo.Limelight;
import CVDemo.Robot2;

/**
 * This is the part we are going to edit
 */
public class AlignmentCommand {
    
    static int i = 0;

    public Robot2 robot;
    public Limelight limelight;

    public AlignmentCommand(Robot2 r, Limelight l) {
        robot = r;
        limelight = l;
    }

    public void execute() {

        Angle a = limelight.getTargetXAngle();

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