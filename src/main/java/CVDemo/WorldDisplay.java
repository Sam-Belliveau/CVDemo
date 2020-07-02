package CVDemo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.stuypulse.stuylib.util.chart.KeyTracker;

import CVDemo.entity.Entity;

public class WorldDisplay extends JFrame {
    
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 1024;

    public static final double WORLD_MIN_X = -10.0;
    public static final double WORLD_MIN_Y = -10.0;
    
    public static final double WORLD_MAX_X = 10.0;
    public static final double WORLD_MAX_Y = 10.0;
    
    public static int WorldToScreenX(double x) {
        x -= WORLD_MIN_X;
        x /= WORLD_MAX_X - WORLD_MIN_X;
        x *= WIDTH;

        int out = (int)(x + 0.5);

        out = Math.min(out, WIDTH - 1);
        out = Math.max(out, 0);

        return out;
    }

    public static int WorldToScreenY(double y) {
        y -= WORLD_MIN_Y;
        y /= WORLD_MAX_Y - WORLD_MIN_Y;
        y *= HEIGHT;

        int out = (int)(y + 0.5);

        out = Math.min(out, HEIGHT - 1);
        out = Math.max(out, 0);

        return out;
    }

    private final Canvas canvas;
    private final KeyTracker keys;
    private final LinkedList<Entity> objects;

    public WorldDisplay() {
        keys = new KeyTracker();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        canvas = new Canvas();
        canvas.setSize(WIDTH, HEIGHT); 
        canvas.addKeyListener(keys);
        
        add(canvas);

        pack();

        setLocationRelativeTo(null);
        setVisible(true);

        objects = new LinkedList<>();
    }

    public void addEntity(Entity... d) {
        for (int i = 0; i < d.length;++i) {
            objects.add(d[i]);
        }
    }


    public void draw() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for(Entity d : objects) {
            d.draw(g);
        }

        bs.show();
        g.dispose();
    }

    public void step() {
        for (Entity d : objects) {
            d.step();
        }
    }

    public void update() {
        Toolkit.getDefaultToolkit().sync();
    }

    public boolean getKey(String key) {
        return keys.getKey(key);
    }

}