package CVDemo.commands;

import CVDemo.Robot2;
import CVDemo.WorldDisplay;

/**
 * This is the part we are going to edit
 */
public class ControlCommand {
    
    private final Robot2 robot;
    private final WorldDisplay world;

    public ControlCommand(Robot2 r, WorldDisplay world) {
        robot = r;
        this.world = world;
    }

    public void execute() {
        double speed = (world.getKey("w") ? 1 : 0) - (world.getKey("s") ? 1 : 0);
        double angle = (world.getKey("d") ? 1 : 0) - (world.getKey("a") ? 1 : 0);
        robot.arcadeDrive(speed, angle);
    }


}