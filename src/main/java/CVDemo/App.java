/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package CVDemo;

import java.util.Random;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

import CVDemo.commands.AlignmentCommand;
import CVDemo.commands.ControlCommand;
import CVDemo.entity.Entity;

public class App {
    
    private static final Vector2D[] GOAL_MESH = {
        new Vector2D(0.5,0.5),
        new Vector2D(0.5, -0.5),
        new Vector2D(0, -1.5),
        new Vector2D(0,1.5)
    };
    
    private static final Random random = new Random();

    public static void main(String[] args) {
        WorldDisplay world = new WorldDisplay();
        Robot robot = new Robot(0.5);

        Entity target = new Shape(new Vector2D(7, 0), Angle.kZero, GOAL_MESH);

        Limelight limelight = new Limelight(robot, target);

        AlignmentCommand aligner = new AlignmentCommand(robot, limelight);
        ControlCommand drive = new ControlCommand(robot, world);

        world.addEntity(limelight);
        world.addEntity(robot);
        world.addEntity(target);

        limelight.turnOn();

        while(world.isVisible()) {

            if(world.getKey("space")) {
                limelight.turnOn();
                if (world.getKey("q") && limelight.isVisible()) {
                    aligner.execute();
                }
                else {
                    drive.execute();
                }
            } else {
                limelight.turnOff();
                drive.execute();
            }

            world.step();
            world.draw();
            world.update();

            try {
                Thread.sleep(2);
            } catch (InterruptedException interruptedException) {
                System.out.println("Thread Sleeping was interrupted!");
                System.out.println("This literally never happens in the main function!");
                break;
            }
        }
    }
}
