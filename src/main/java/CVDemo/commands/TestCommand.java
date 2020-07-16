package CVDemo.commands;

import CVDemo.Limelight;
import CVDemo.Robot;

import com.stuypulse.stuylib.control.PIDCalculator;

public class TestCommand {

    private PIDCalculator controller;

    public Robot robot;
    public Limelight limelight;

    public TestCommand(Robot r, Limelight l) {
        robot = r;
        limelight = l;
        
        controller = new PIDCalculator(1.0);
    }

    public void execute() { 
        robot.arcadeDrive(0, 
            controller.update(limelight.getTargetXAngle())
        );

        System.out.println(controller.getPDController());
    }
}
