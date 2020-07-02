package CVDemo.entity;

import java.awt.Graphics;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.stuylib.util.StopWatch;

public abstract class PhysicalEntity extends Entity {

    public static Vector2D abs(Vector2D vec) {
        return new Vector2D(Math.abs(vec.x), Math.abs(vec.y));
    }

    private final Forces forces;

    private double mAngleVel;

    private Vector2D mVelocity;

    private final StopWatch mTimer;

    private double mTargetSpeed;
    private double mTargetAngle;

    private IStream mInputSpeed;
    private IStream mInputAngle;

    protected PhysicalEntity setTargetSpeed(double speed) {
        mTargetSpeed = speed;
        return this;
    }

    protected PhysicalEntity setTargetAngle(double angle) {
        mTargetAngle = angle;
        return this;
    }

    protected PhysicalEntity setSpeedStream(IStream stream) {
        mInputSpeed = stream;
        return this;
    }

    protected PhysicalEntity setAngleStream(IStream stream) {
        mInputSpeed = stream;
        return this;
    }

    public PhysicalEntity(Forces forces, Vector2D pos, Angle angle, Vector2D[] mesh) {
        super(pos, angle, mesh);

        this.forces = forces;

        mAngleVel = 0;

        mVelocity = new Vector2D(0, 0);

        mTimer = new StopWatch();
    
        mTargetSpeed = 0;
        mTargetAngle = 0;
        
        mInputSpeed = () -> mTargetSpeed;
        mInputAngle = () -> mTargetAngle;
        
        mInputSpeed = mInputSpeed.filtered(new LowPassFilter(0.1));
        mInputAngle = mInputAngle.filtered(new LowPassFilter(0.2));
    }

    @Override
    public void step() {
        final double dt = mTimer.reset();

        // Turn and Move
        mAngle = mAngle.add(Angle.fromRadians(mAngleVel * dt));
        mPosition = mPosition.add(mVelocity.mul(dt));

        // Calculate Drags
        double angleDrag = Math.abs(mAngleVel) * mAngleVel * forces.getTurningDrag() * dt;

        Vector2D rVel = mVelocity.rotate(Angle.kZero.sub(mAngle));
        double x = rVel.x - Math.abs(rVel.x) * rVel.x * forces.getForwardsDrag() * dt;
        double y = rVel.y - Math.abs(rVel.y) * rVel.y * forces.getSidewaysDrag() * dt;
        rVel = new Vector2D(x, y);

        // Apply Drag
        mAngleVel -= angleDrag;
        mVelocity = rVel.rotate(mAngle);

        // Add input speeds to robot
        mAngleVel += forces.getTurnSpeed() * mInputAngle.get() * dt;
        mVelocity = mVelocity.add(mAngle.getVector().mul(mInputSpeed.get() * forces.getMoveSpeed() * dt));
    }

    @Override
    public abstract void draw(Graphics g);
    
}