package com.sj.pixelfarm.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public final class WorldUtils {

    private static final Matrix4 isoTransform;
    private static final Matrix4 invIsoTransform;
    private static final Vector3 screenPos = new Vector3();
    private static final GridPoint2 gridPos = new GridPoint2();
    private static final Vector2 mousePos = new Vector2();
    private static final Vector2 tmpGridToVec = new Vector2();
    private static final float TILE_OFFSET = 0.06f;

    static {
        isoTransform = new Matrix4();
        isoTransform.idt();

        isoTransform.scale((float)(Math.sqrt(2.0) / 2.0), (float)(Math.sqrt(2.0) / 4.0), 1.0f);
        isoTransform.rotate(-TILE_OFFSET, -TILE_OFFSET, 1.0f, -45);

        invIsoTransform = new Matrix4(isoTransform);
        invIsoTransform.inv();
    }

    public static Vector2 gridToVec(GridPoint2 point) {
        tmpGridToVec.set(
                (point.x + point.y) * 0.5f,
                (point.y - point.x) * 0.25f + TILE_OFFSET
        );
        return new Vector2(tmpGridToVec);
    }

    public static GridPoint2 getGridPos(Vector2 vec) {
        screenPos.set(vec.x, vec.y, 0);
        screenPos.mul(invIsoTransform);
        gridPos.set((int) screenPos.x, (int) screenPos.y);
        return new GridPoint2(gridPos);
    }

    public static GridPoint2 getGridPosFromMouse(ExtendViewport viewport) {
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(mousePos);
        mousePos.y -= 0.25f;
        return getGridPos(mousePos);
    }
}
