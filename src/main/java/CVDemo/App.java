/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package CVDemo;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

public class App {

    public static void main(String[] args) {
        WorldDisplay world = new WorldDisplay();
        Robot robot = new Robot(); 
        Target target = new Target(new Vector2D(5, 0), Angle.kZero);
        Limelight limelight = new Limelight(robot, target);
        AlignmentCommand aligner = new AlignmentCommand(robot, limelight);

        world.addDrawable(limelight);
        world.addDrawable(target);
        world.addDrawable(robot);
        

        while(world.isVisible()) {

            if(world.getKey("space")) {
                limelight.turnOn();
                if(limelight.isVisible()) {
                    aligner.execute();
                } else {
                    world.control(robot);
                }
            } else {
                limelight.turnOff();
                world.control(robot);
            }

            robot.step();
            world.draw(robot);
            world.update();

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("Thread Sleeping was interrupted!");
                System.out.println("This literally never happens in the main function!");
                break;
            }
        }
    }
}
