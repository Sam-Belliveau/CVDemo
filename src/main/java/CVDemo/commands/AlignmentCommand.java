package CVDemo.commands;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.control.*;

import CVDemo.Limelight;
import CVDemo.Robot;

/**
 * This is the part we are going to edit
 */
public class AlignmentCommand {
    
    public Robot robot;
    public Limelight limelight;
    public PIDController controller = new PIDController(1.0/90.0, 0.0, 0.01);

    //integral deritive
    public AlignmentCommand(Robot r, Limelight l) {
        robot = r;
        limelight = l;
    }

    public void execute() {

        double error = limelight.getTargetXAngle();

        System.out.println(error);

        robot.arcadeDrive(0, controller.update(error)); 
        //wait so in real world do i need to take into consideration friction and stuff
        //what if you have many limelights 360 degree limelights
        // it yeoa also you have to calculate the opposing force on the thing so when you hold it back or something the error/ tings wont go to infinity and never slow down and take years and stuff so you have to use the "I" in pid pretty sure...

// @ sam @ @ 
/*
    8===D
*/
        // Bang Bang + Deadband
        // if (Math.abs(error) >= 5) { //speed too much to match the abs?
        //     if(error < 0) {
        //         robot.arcadeDrive(0, -0.2);//change speped proportional to the error so that eventually is gets to zero 
        //     } else {
        //         robot.arcadeDrive(0, 0.2);      
        //     }
        // } else {
        //     robot.arcadeDrive(0, 0);
        // }


        // Bang Bang
        // if(error < 0) {
        //     robot.arcadeDrive(0, -1);
        // } else {
        //     robot.arcadeDrive(0, 1);      
        // }
        // shouldnt we do deadband first? 
    } //lower speed when closer to target (pid)
//is abs under Math 
}
//okay so 90 deg totall
// 

// you guys cant just jump from deadband to pid
 
// i think since error is 45 max for abs it woul dbe like 45:1 rat: 
//yea whatver the right proportion is
//proportions use them  
// sam isnt the real limelight -27.5 - 27.5 so 45 deg
//scam
// anthony use Math.abs(error) 

// sam rerun the program for 1 target







