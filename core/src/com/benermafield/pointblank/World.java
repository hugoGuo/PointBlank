package com.benermafield.pointblank;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.benermafield.pointblank.entity.Target;

public class World {
    private Engine engine;

    public int spawnedTargetCount;
    public int targetMax;

    public float width, height;

    public World(Engine engine) {
        this.engine = engine;
        this.spawnedTargetCount = 0;
        this.targetMax = 5;
        this.width = 1000;
        this.height = 1000f * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
    }

    public void spawnTarget(int type) {
        spawnedTargetCount ++;
        float x = (float) Math.random() * width;
        float y = (float) Math.random() * height;
        float vx = ((float) Math.random() * 1000) - 500;
        float vy = ((float) Math.random() * 1000) - 500;
        engine.addEntity(new Target(type, x, y, vx, vy, this));
    }
}
