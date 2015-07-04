package com.benermafield.pointblank.entity;

import com.badlogic.ashley.core.Entity;
import com.benermafield.pointblank.World;
import com.benermafield.pointblank.component.MovementComponent;
import com.benermafield.pointblank.component.PositionComponent;
import com.benermafield.pointblank.component.TargetComponent;
import com.benermafield.pointblank.component.VisualComponent;
import com.benermafield.pointblank.util.Assets;

public class Target extends Entity {
    public static final float DROPPING_VELOCITY = -500;
    public static final float SIZE = 100;

    public Target(int type, float x, float y, float vx, float vy, World world) {
        super();
        this.add(new TargetComponent(type, x, y, vx, vy));
        switch (type) {
            case TargetComponent.TYPE_STATIC:
                this.add(new PositionComponent(x, y));
                this.add(new MovementComponent(vx, vy));
                break;

            case TargetComponent.TYPE_DROPPING:
                this.add(new PositionComponent(x, world.height));
                this.add(new MovementComponent(0, DROPPING_VELOCITY));
                break;
        }

        this.add(new VisualComponent(Assets.target));
    }
}
