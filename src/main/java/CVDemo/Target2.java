package CVDemo;

import java.awt.Color;
import java.awt.Graphics;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

import CVDemo.entity.Entity;


public class Target2 extends Entity {
   
    public Target2(Vector2D pos, Angle angle) {
        super(pos, angle, new Vector2D[] {
            new Vector2D(8,4),
            new Vector2D(8,6),
            new Vector2D(7,6),
            new Vector2D(7,4)
        });
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        Entity.fillEntity(this, g);
    }

    @Override
    public void step() {
    }

}