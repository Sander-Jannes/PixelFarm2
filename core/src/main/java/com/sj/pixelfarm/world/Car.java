package com.sj.pixelfarm.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.sj.pixelfarm.core.item.Item;
import com.sj.pixelfarm.core.item.Items;
import com.sj.pixelfarm.core.itemgrid.ItemStack;
import com.sj.pixelfarm.core.mem.Assets;
import com.sj.pixelfarm.core.mem.PoolManager;


public class Car {

    private static final TextureRegion car = Assets.getAtlasTexture("world/car");

    private final CarPoint[] carGridPath = new CarPoint[] {
        new CarPoint(new GridPoint2(40, 66), false),
        new CarPoint(new GridPoint2(65, 66), false),
        new CarPoint(new GridPoint2(75, 66), false)
    };
    private CarPoint currentCarPoint = carGridPath[0];
    private CarPoint destination = carGridPath[1];

    private int currentSegment = 0;
    private float progress = 0f;
    private float speed = 0.5f;

    private final ItemStack order = PoolManager.obtain(Items.cucumber, 4, Item.Quality.GOOD);

    public void drive(float delta) {
        if (currentSegment < carGridPath.length - 1 && !currentCarPoint.lock) {
            progress += delta;

            if (currentCarPoint.reachedDestination(destination)) {
                progress = 0f;
                currentSegment++;
                currentCarPoint = carGridPath[currentSegment];

                if (currentSegment >= carGridPath.length - 1) {
                    currentCarPoint = carGridPath[carGridPath.length - 1];
                } else {
                    destination = carGridPath[currentSegment + 1];
                }
            }

            if (currentSegment < carGridPath.length - 1) {
                currentCarPoint.update(destination, delta, Interpolation.linear);
            }
        }
    }

    public boolean acceptOrder(ItemStack order) {
        if (this.order.equalsWithAmount(order)) {
            currentCarPoint.unlock();
            return true;
        }
        return false;
    }

    public GridPoint2 getPosition() {
        return currentCarPoint.getPos();
    }

    public void draw(Batch batch) {
        batch.begin();
        batch.draw(car, currentCarPoint.getX(), currentCarPoint.getY(), 0.9f, 0.9f);
        batch.end();
    }

    public static class CarPoint {
        private final GridPoint2 pos;
        private final Vector2 endVec;
        private boolean lock;

        public CarPoint(GridPoint2 endGrid, boolean lock) {
            this.pos = endGrid;
            this.endVec = WorldUtils.gridToVec(endGrid);
            this.lock = lock;
        }

        public float getX() {
            return endVec.x;
        }

        public float getY() {
            return endVec.y;
        }

        public void unlock() {
            lock = false;
        }

        public GridPoint2 getPos() {
            return pos;
        }

        public boolean reachedDestination(CarPoint destination) {
            return endVec.equals(destination.endVec);
        }

        public void update(CarPoint destination, float alpha, Interpolation interpolation) {
            endVec.interpolate(destination.endVec, alpha, interpolation);
        }
    }
}
