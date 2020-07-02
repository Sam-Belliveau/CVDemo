package CVDemo.entity;

public class Forces {

    public static Forces kDefaults = new Forces();

    public static final double TURNING_DRAG = 100;
    public static final double FORWARDS_DRAG = 5;
    public static final double SIDEWAYS_DRAG = 100;
    
    // These values are high because its added incrementally
    public static final double MOVE_SPEED = 50;
    public static final double TURN_SPEED = 150;

    private final double turning;
    private final double forwards;
    private final double sideways;

    private final double turnSpeed;
    private final double moveSpeed;

    public Forces(double turning, double forwards, double sideways, double turnSpeed, double moveSpeed) {
        this.turning = turning;
        this.forwards = forwards; 
        this.sideways = sideways;

        this.turnSpeed = turnSpeed;
        this.moveSpeed = moveSpeed;
    }

    public Forces() {
        this(TURNING_DRAG, FORWARDS_DRAG, SIDEWAYS_DRAG, TURN_SPEED, MOVE_SPEED);
    }

    public double getTurningDrag() {
        return turning;
    }

    public double getForwardsDrag() {
        return forwards;
    }

    public double getSidewaysDrag() {
        return sideways;
    }

    public double getTurnSpeed() {
        return turnSpeed;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }

}
