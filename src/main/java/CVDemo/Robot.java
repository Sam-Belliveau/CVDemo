package CVDemo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.util.StopWatch;
import com.stuypulse.stuylib.util.chart.KeyTracker;

/**
 * This class just simulates the position and angle of the robot
 * 
 * This class is super complicated just to simulate the physics of the robot.
 */
public class Robot implements Drawable {
    
    public static final double TURNING_DRAG = 25;
    public static final double FORWARDS_DRIVING_DRAG = 5;
    public static final double SIDEWAYS_DRIVING_DRAG = 100;
    
    // These values are high because its added incrementally
    public static final double DRIVE_SPEED = 50;
    public static final double TURN_SPEED = Math.toRadians(12000);

    private Angle mAngle;
    private double mAngleVel;

    private Vector2D mPosition;
    private Vector2D mVelocity;

    private StopWatch mTimer;

    private double mTargetSpeed;
    private double mTargetAngle;

    private IStream mInputSpeed;
    private IStream mInputAngle;
    

    public Robot() {
        mAngle = Angle.kZero;
        mAngleVel = 0;

        mPosition = new Vector2D(0, 0);
        mVelocity = new Vector2D(0, 0);

        mTimer = new StopWatch();
    
        mTargetSpeed = 0;
        mTargetAngle = 0;
        
        mInputSpeed = () -> mTargetSpeed;
        mInputAngle = () -> mTargetAngle;
        
        mInputSpeed = mInputSpeed.filtered(new LowPassFilter(0.1));
        mInputAngle = mInputAngle.filtered(new LowPassFilter(0.2));
    }
    
    public Vector2D abs(Vector2D vec) {
        return new Vector2D(Math.abs(vec.x), Math.abs(vec.y));
    }

    public void step() {
        final double dt = mTimer.reset();
        System.out.println(1.0 / dt);

        // Turn and Move
        mAngle = mAngle.add(Angle.fromRadians(mAngleVel * dt));
        mPosition = mPosition.add(mVelocity.mul(dt));

        // Calculate Drags
        double angleDrag = Math.abs(mAngleVel) * mAngleVel * TURNING_DRAG * dt;

        Vector2D rVel = mVelocity.rotate(Angle.kZero.sub(mAngle));
        double x = rVel.x - Math.abs(rVel.x) * rVel.x * FORWARDS_DRIVING_DRAG * dt;
        double y = rVel.y - Math.abs(rVel.y) * rVel.y * SIDEWAYS_DRIVING_DRAG * dt;
        rVel = new Vector2D(x, y);

        // Apply Drag
        mAngleVel -= angleDrag;
        mVelocity = rVel.rotate(mAngle);

        // Add input speeds to robot
        mAngleVel += TURN_SPEED * mInputAngle.get() * dt;
        mVelocity = mVelocity.add(mAngle.getVector().mul(mInputSpeed.get() * DRIVE_SPEED * dt));
    }
    
    public void arcadeDrive(double speed, double angle) {
        mTargetSpeed = speed;
        mTargetAngle = angle;
    }

    public Angle getAngle() {
        return mAngle;
    }

    public Vector2D getPosition() {
        return mPosition;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        
        Angle ang = getAngle();
        Vector2D robotCenter = getPosition();
        Vector2D robot1 = robotCenter.add(ang.add(Angle.fromDegrees(25)).getVector().mul(0.5));
        Vector2D robot2 = robotCenter.add(ang.add(Angle.fromDegrees(135)).getVector().mul(0.5));

        Vector2D robot3 = robotCenter.add(ang.sub(Angle.fromDegrees(135)).getVector().mul(0.5));
        Vector2D robot4 = robotCenter.add(ang.sub(Angle.fromDegrees(25)).getVector().mul(0.5));

        // System.out.println("Robot 1: " + robot1);
        // System.out.println("Robot 2: " + robot2);
        // System.out.println("Robot 3: " + robot3);
        // System.out.println("Robot 4: " + robot4);
        

        g.fillPolygon(
            new int[] {
                WorldDisplay.WorldToScreenX(robot1.x),
                WorldDisplay.WorldToScreenX(robot2.x),
                WorldDisplay.WorldToScreenX(robot3.x),
                WorldDisplay.WorldToScreenX(robot4.x),
            }, 
            new int[] {
                WorldDisplay.WorldToScreenY(robot1.y),
                WorldDisplay.WorldToScreenY(robot2.y),
                WorldDisplay.WorldToScreenY(robot3.y),
                WorldDisplay.WorldToScreenY(robot4.y),
            }, 4);
    }
};
