package com.benermafield.pointblank.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.benermafield.pointblank.World;
import com.benermafield.pointblank.component.MovementComponent;
import com.benermafield.pointblank.component.PositionComponent;
import com.benermafield.pointblank.entity.Target;

public class MovementSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<MovementComponent> cm = ComponentMapper.getFor(MovementComponent.class);
    private PositionComponent pc;
    private MovementComponent mc;

    private World world;

    public MovementSystem(World world) {
        super(Family.all(MovementComponent.class, PositionComponent.class).get());
        this.world = world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        pc = pm.get(entity);
        mc = cm.get(entity);

        //TODO  Using Target.SIZE here is wrong, it works on a presumption that everything that moves has a size
        //      equivalent to that of Targets
        //      Also, there may be a possibility that eventually Targets have varying sizes, and as such this
        //      system would not work, due to presuming a constant size
        // Edge collision
        if ((pc.x < 0 && (mc.x < 0)) || (pc.x > world.width - Target.SIZE && (mc.x > 0)))
            mc.x = -1 * mc.x;
        if ((pc.y < 0 && (mc.y < 0)) || (pc.y > world.height - Target.SIZE && (mc.y > 0)))
            mc.y = -1 * mc.y;

        pc.x += mc.x * deltaTime;
        pc.y += mc.y * deltaTime;
    }
}
