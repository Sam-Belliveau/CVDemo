package CVDemo.entity;

import java.awt.Graphics;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

import CVDemo.WorldDisplay;

public abstract class Entity {
    
    protected final Vector2D[] mMesh;
    private Vector2D mCentroid;

    protected Vector2D mPosition;
    protected Angle mAngle;

    public Entity(Vector2D pos, Angle angle, Vector2D[] mesh) {
        mPosition = pos;
        mAngle = angle;

        if (mesh.length < 1) {
            throw new IllegalArgumentException("Mesh must have at least 1 points");
        }
        mMesh = mesh;

        mCentroid = null;
    }

    public Angle getAngle() {
        return mAngle;
    }

    public Vector2D getCentroid() {
        if (mCentroid == null) {
            int n = mMesh.length;
            int xSum = 0;
            int ySum = 0;
            for (int i = 0; i < mMesh.length;++i) {
                xSum += mMesh[i].x;
                ySum += mMesh[i].y;
            }
            mCentroid = new Vector2D(xSum / n, ySum / n);
        }
        return mCentroid;
    }

    public Vector2D getPosition() {
        return mPosition;
    }

    public static final void drawEntity(Entity e, Graphics g) {
        int n = e.mMesh.length;
        int x[] = new int[n];
        int y[] = new int[n];

        Vector2D centroid = e.getCentroid();
        Vector2D pos = e.getPosition();
        Angle ang = e.getAngle();

        Vector2D translation = pos.sub(centroid);

        for (int i = 0; i < n;++i) {
            Vector2D out = e.mMesh[i].rotate(ang, centroid);
            out = out.add(translation);

            x[i] = (int) WorldDisplay.WorldToScreenX(out.x);
            y[i] = (int) WorldDisplay.WorldToScreenX(out.y);
        }
        
        g.drawPolygon(x,y,n);
    }

    public static final void fillEntity(Entity e, Graphics g) {
        int n = e.mMesh.length;
        int x[] = new int[n];
        int y[] = new int[n];

        Vector2D centroid = e.getCentroid();
        Vector2D pos = e.getPosition();
        Angle ang = e.getAngle();

        Vector2D translation = pos.sub(centroid);

        for (int i = 0; i < n;++i) {
            Vector2D out = e.mMesh[i].rotate(ang, centroid);
            out = out.add(translation);

            x[i] = (int) WorldDisplay.WorldToScreenX(out.x);
            y[i] = (int) WorldDisplay.WorldToScreenX(out.y);
        }
        
        g.fillPolygon(x,y,n);
    }

    public abstract void step();

    public abstract void draw(Graphics g);

}