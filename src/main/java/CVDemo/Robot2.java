package CVDemo;

import java.awt.Color;
import java.awt.Graphics;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.stuylib.util.StopWatch;

import CVDemo.entity.Entity;
import CVDemo.entity.Forces;
import CVDemo.entity.PhysicalEntity;

/**
 * This class just simulates the position and angle of the robot
 * 
 * This class is super complicated just to simulate the physics of the robot.
 */
public class Robot2 extends PhysicalEntity {
    
    // These values are high because its added incrementally
    public static final double DRIVE_SPEED = 50;
    public static final double TURN_SPEED = Math.toRadians(12000);


    private final static Vector2D[] ROBOT_MESH = {
        new Vector2D(-1,1),
        new Vector2D(1,0.5),
        new Vector2D(1,-0.5),
        new Vector2D(-1,-1)
    };

    private final static Vector2D[] SCALE_ROBOT_MESH(double scale) {
        Vector2D[] out = ROBOT_MESH;

        for (int i = 0; i < out.length;++i) {
            out[i] = out[i].mul(scale);
        }

        return out;
    }


    public Robot2() {
        super(Forces.kDefaults, new Vector2D(0,0), Angle.kZero, ROBOT_MESH);
    }

    public Robot2(double scale) {
        super(Forces.kDefaults, new Vector2D(0,0), Angle.kZero, SCALE_ROBOT_MESH(scale));
    }
    
    public void arcadeDrive(double speed, double angle) {
        setTargetSpeed(speed);
        setTargetAngle(angle);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        Entity.fillEntity(this, g);
    }
};
