package com.sj.pixelfarm.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.item.Items;
import com.sj.pixelfarm.core.itemgrid.ItemStack;
import com.sj.pixelfarm.core.mem.Assets;
import com.sj.pixelfarm.core.mem.PoolManager;


public class Car {

    private static final TextureRegion car = Assets.getAtlasTexture("world/car");
    private float time = 0f;
    private final ItemStack order = PoolManager.obtain(Items.cucumber, 4, Item.Quality.GOOD);
    private final Path path;
    private boolean drive = true;

    public Car(Path path) {
        this.path = path;
    }

    public void start() {
        drive = true;
        time = 0f;
    }

    public void drive(float delta) {
        if (drive) {
            if (!path.update(delta)) {
                drive = false;
            }
        }
    }

    public GridPoint2 getPosition() {
        return WorldUtils.getGridPos(path.start).cpy().add(0, 1);
    }

    public boolean acceptOrder(ItemStack order) {
        if (this.order.equalsWithAmount(order)) {
            path.continuePath();
            return true;
        }
        return false;
    }

    public void draw(Batch batch, float delta) {
        if (!drive) return;

        time += delta;
        float bounceOffset = MathUtils.sin(time * 2f) * 0.1f;

        batch.begin();
        batch.draw(car, path.start.x, path.start.y, 0.9f, 0.9f);

        if (path.reachedStop) {
            batch.draw(order.item.image, path.start.x + 0.1f, path.start.y + 0.75f + bounceOffset, 0.5f, 0.5f);
        }
        batch.end();
    }

    public static class Path {
        private final Vector2 start;
        private final Vector2 end;
        private final Vector2 stop;
        public boolean reachedStop = false;

        private final Vector2 stopBackup;
        private final Vector2 startBackup;

        public Path(GridPoint2 start, GridPoint2 end, GridPoint2 stop) {
            this.start = WorldUtils.gridToVec(start);
            this.end = WorldUtils.gridToVec(end);
            this.stop = WorldUtils.gridToVec(stop);

            stopBackup = this.stop.cpy();
            startBackup = this.start.cpy();
        }

        public void reset() {
            stop.set(stopBackup);
            start.set(startBackup);
        }

        public void continuePath() {
            reachedStop = false;
            stop.set(end);
        }

        public boolean update(float alpha) {
            if (end.equals(stop) && start.x >= stop.x) {
                reset();
                return false;
            }

            if (reachedStop || start.x >= stop.x) {
                reachedStop = true;
                return true;
            }

            start.lerp(end, alpha);
            return true;
        }
    }
}
