package CVDemo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

public class Limelight implements Drawable{
 
    public static final double VIEW_DISTANCE = 8;
    public static final double VIEW_ANGLE = 45;
    
    private Robot mRobot;
    private Target mTarget;
    private boolean mTurnedOn;

    public Limelight(Robot robot, Target target) {
        mRobot = robot;
        mTarget = target;
        mTurnedOn = false;
    }

    public void turnOn() {
        mTurnedOn = true;
    }
    
    public void turnOff() {
        mTurnedOn = false;
    }
    

    public Angle getTargetXAngle() {
        Vector2D target = mTarget.getPosition().sub(mRobot.getPosition());
        Angle angle = target.getAngle().sub(mRobot.getAngle());
        return angle;
    }

    public boolean isVisible() {
        Vector2D target = mTarget.getPosition().sub(mRobot.getPosition());
        return (Math.abs(target.getAngle().sub(mRobot.getAngle()).toDegrees()) < VIEW_ANGLE) && (target.distance() < VIEW_DISTANCE);
    }

    public void draw(Graphics g) {
        if(mTurnedOn) {
            g.setColor(Color.GREEN);
            
            Angle ang = mRobot.getAngle();
            Vector2D robotCenter = mRobot.getPosition();
            
            final int POINTS = 64;
    
            Vector2D[] points = new Vector2D[POINTS];
            points[0] = robotCenter;
    
            for(int i = 1; i < POINTS; ++i) {
                points[i] = robotCenter.add(ang.add(Angle.fromDegrees((2 * VIEW_ANGLE * i) / POINTS - VIEW_ANGLE)).getVector().mul(VIEW_DISTANCE));
            }
    
            int[] x = new int[POINTS];
            int[] y = new int[POINTS];
            
            for(int i = 0; i < POINTS; ++i) {
                x[i] = WorldDisplay.WorldToScreenX(points[i].x);
                y[i] = WorldDisplay.WorldToScreenY(points[i].y);
            }
    
            g.fillPolygon(x, y, POINTS);
            
            if(isVisible()) {
                g.setColor(Color.RED);
                Vector2D targetCenter = mTarget.getPosition();
                g.drawLine(
                    WorldDisplay.WorldToScreenX(robotCenter.x), 
                    WorldDisplay.WorldToScreenY(robotCenter.y), 
                    WorldDisplay.WorldToScreenX(targetCenter.x), 
                    WorldDisplay.WorldToScreenY(targetCenter.y)
                );
            }

        }
    }

}