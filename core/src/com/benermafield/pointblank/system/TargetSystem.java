package com.benermafield.pointblank.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.benermafield.pointblank.component.MovementComponent;
import com.benermafield.pointblank.component.PositionComponent;
import com.benermafield.pointblank.component.TargetComponent;

public class TargetSystem extends IteratingSystem {
    private ComponentMapper<TargetComponent> tm = ComponentMapper.getFor(TargetComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
    TargetComponent tc;
    PositionComponent pc;
    MovementComponent mc;

    public TargetSystem() {
        super(Family.all(TargetComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        tc = tm.get(entity);
        pc = pm.get(entity);
        mc = mm.get(entity);

        tc.update(deltaTime);

        switch (tc.state) {
            case TargetComponent.STATE_SPAWNING:
                if (pc.y <= tc.y) {
                    pc.y = tc.y;
                    mc.x = tc.vx;
                    mc.y = tc.vy;
                    tc.spawned();
                }
                break;

            case TargetComponent.STATE_ALIVE:
                break;

            case TargetComponent.STATE_CLICKED:
                // Animate explosion or something?
                break;

            case TargetComponent.STATE_DEAD:
                break;
        }
    }
}
