package com.sj.pixelfarm.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.sj.pixelfarm.core.input.events.EventType;
import com.sj.pixelfarm.core.input.events.Events;
import com.sj.pixelfarm.core.mem.Assets;
import com.sj.pixelfarm.items.box.Box;
import com.sj.pixelfarm.items.box.Order;
import com.sj.pixelfarm.items.box.OrderGenerator;


public class Car {

    private static final TextureRegion car = Assets.getAtlasTexture("world/car");
    private final Path path;
    private boolean drive = false;
    public Order order = OrderGenerator.generateRandomOrder();

    public Car(GridPoint2[] points) {
        this.path = new Path(points[0], points[1], points[2]);
    }

    public void start() {
        drive = true;
    }

    public void drive(float delta) {
        if (drive) {
            if (path.reachedEnd()) {
                path.reset();
                drive = false;
            }

            if (!path.update(delta / 8f)) {
                Events.fire(new EventType.ShowOrderEvent(order, path.start.cpy()));
            }
        }
    }

    public GridPoint2 getPosition() {
        return WorldUtils.getGridPos(path.start).cpy().add(0, 1);
    }

    public boolean acceptOrder(Box order) {
        if (this.order.doesBoxFulfilOrder(order)) {
            path.continuePath();
            Events.fire(new EventType.RemoveOrderEvent());
            this.order = OrderGenerator.generateRandomOrder();
            return true;
        }
        return false;
    }

    public void draw(Batch batch) {
        if (!drive) return;

        batch.begin();
        batch.draw(car, path.start.x, path.start.y, 0.9f, 0.9f);
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

        public boolean reachedEnd() {
            return end.equals(stop) && start.x >= stop.x - 1f;
        }

        public boolean update(float alpha) {
            if (!reachedStop && start.x >= stop.x) {
                reachedStop = true;
                return false;

            } else if (!reachedStop) {
                start.lerp(end, alpha);
            }

            return true;
        }
    }
}
