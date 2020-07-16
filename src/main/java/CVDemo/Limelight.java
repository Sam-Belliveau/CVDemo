package CVDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

import CVDemo.entity.Entity;

public class Limelight extends Entity {
 
    public static final double VIEW_DISTANCE = 8;
    public static final double VIEW_ANGLE = 45;
    
    private Robot mRobot;
    private Entity[] mTargets;
    private boolean mTurnedOn;

    public Limelight(Robot robot, Entity... target) {
        super(robot.getCentroid(), Angle.kZero, new Vector2D[] {new Vector2D(0,0)});

        mRobot = robot;
        mTargets = target;
        mTurnedOn = false;
    }

    public void turnOn() {
        mTurnedOn = true;
    }
    
    public void turnOff() {
        mTurnedOn = false;
    }
    

    public double getTargetXAngle() {
        ArrayList<Integer> visible = getVisible();

        if (visible.size() == 0)
            return 0;

        double minDistance = Double.MAX_VALUE;
        int index = 1;

        for (int i : visible) {
            double mag = mTargets[i].getPosition().sub(mRobot.getPosition()).distance();
            if (mag < minDistance) {
                minDistance = mag;
                index = i;
            }
        }
        Vector2D target = mTargets[index].getPosition().sub(mRobot.getPosition());
        Angle angle = target.getAngle().sub(mRobot.getAngle());
        return angle.toDegrees();
    }

    public double getTargetDistance() {
        ArrayList<Integer> visible = getVisible();

        if (visible.size() == 0)
            return 0;

        double minDistance = Double.MAX_VALUE;

        for (int i : visible) {
            double mag = mTargets[i].getPosition().sub(mRobot.getPosition()).distance();
            if (mag < minDistance) {
                minDistance = mag;
            }
        }
        return minDistance;
    }

    public ArrayList<Integer> getVisible() {
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < mTargets.length;++i) {

            Vector2D target = mTargets[i].getPosition().sub(mRobot.getPosition());
            if ( (Math.abs(target.getAngle().sub(mRobot.getAngle()).toDegrees()) < VIEW_ANGLE) && (target.distance() < VIEW_DISTANCE)) {
                indices.add(i);
            }
    
        }

        return indices;
    }

    public boolean isVisible() {
        return getVisible().size() != 0;
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
            
            ArrayList<Integer> visible = getVisible();

            if(visible.size() != 0) {
                for (int index : visible) {
                    g.setColor(Color.RED);
                    Vector2D targetCenter = mTargets[index].getPosition();
                    g.drawLine(
                        WorldDisplay.WorldToScreenX(robotCenter.x), 
                        WorldDisplay.WorldToScreenY(robotCenter.y), 
                        WorldDisplay.WorldToScreenX(targetCenter.x), 
                        WorldDisplay.WorldToScreenY(targetCenter.y)
                    );


                    g.setColor(Color.BLUE);
                    Vector2D visionCenter = robotCenter.add(mRobot.getAngle().getVector().mul(VIEW_DISTANCE));
                    g.drawLine(
                        WorldDisplay.WorldToScreenX(robotCenter.x), 
                        WorldDisplay.WorldToScreenY(robotCenter.y), 
                        WorldDisplay.WorldToScreenX(visionCenter.x), 
                        WorldDisplay.WorldToScreenY(visionCenter.y)
                    );
                }
            }

        }
    }

    @Override
    public void step() {
    }

}