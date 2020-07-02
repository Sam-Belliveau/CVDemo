package CVDemo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

public class Target implements Drawable {
    
    private Vector2D mPosition;
    private Angle mAngle;

    public Target(Vector2D pos, Angle angle) {
        mPosition = pos;
        mAngle = angle;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        
        Vector2D target1 = mPosition.add(mAngle.add(Angle.fromDegrees(80)).getVector().mul(1));
        Vector2D target2 = mPosition.add(mAngle.add(Angle.fromDegrees(110)).getVector().mul(1));

        Vector2D target3 = mPosition.add(mAngle.sub(Angle.fromDegrees(110)).getVector().mul(1));
        Vector2D target4 = mPosition.add(mAngle.sub(Angle.fromDegrees(80)).getVector().mul(1));


        g.fillPolygon(
            new int[] {
                WorldDisplay.WorldToScreenX(target1.x),
                WorldDisplay.WorldToScreenX(target2.x),
                WorldDisplay.WorldToScreenX(target3.x),
                WorldDisplay.WorldToScreenX(target4.x),
            }, 
            new int[] {
                WorldDisplay.WorldToScreenY(target1.y),
                WorldDisplay.WorldToScreenY(target2.y),
                WorldDisplay.WorldToScreenY(target3.y),
                WorldDisplay.WorldToScreenY(target4.y),
            }, 4);
    }

    public Vector2D getPosition() {
        return mPosition;
    }

}