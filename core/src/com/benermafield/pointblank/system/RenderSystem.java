package com.benermafield.pointblank.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.benermafield.pointblank.component.PositionComponent;
import com.benermafield.pointblank.component.VisualComponent;

public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VisualComponent> vm = ComponentMapper.getFor(VisualComponent.class);
    private PositionComponent pc;
    private VisualComponent vc;

    public RenderSystem(OrthographicCamera camera, SpriteBatch batch) {
        this.batch = batch;
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VisualComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {
        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (Entity e: entities) {
            pc = pm.get(e);
            vc = vm.get(e);

            batch.draw(vc.region, pc.x, pc.y);
        }

        batch.end();
    }
}
