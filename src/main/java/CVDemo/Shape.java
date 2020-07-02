package CVDemo;

import java.awt.Color;
import java.awt.Graphics;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

import CVDemo.entity.Entity;


public class Shape extends Entity {
   
    public Shape(Vector2D pos, Angle angle, Vector2D[] mesh) {
        super(pos, angle, mesh);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        Entity.fillEntity(this, g);
    }

    @Override
    public void step() {
    }

}