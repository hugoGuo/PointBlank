package com.benermafield.pointblank.component;

import com.badlogic.ashley.core.Component;

public class TargetComponent extends Component {
    public static final int STATE_SPAWNING = 101;
    public static final int STATE_ALIVE = 102;
    public static final int STATE_CLICKED = 103;
    public static final int STATE_DEAD = 104;

    public static final int TYPE_STATIC = 201;
    public static final int TYPE_DROPPING = 202;

    public int state;
    public int type;
    public float stateDuration;
    public float x, y, vx, vy;

    public TargetComponent(int type, float x, float y, float vx, float vy) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.stateDuration = 0;

        switch (this.type) {
            case TYPE_STATIC:
                this.state = STATE_ALIVE;
                break;

            case TYPE_DROPPING:
                this.state = STATE_SPAWNING;
                break;
        }
    }

    public void spawned() {
        this.state = STATE_ALIVE;
        this.stateDuration = 0;
    }

    public void clicked() {
        this.state = STATE_CLICKED;
        this.stateDuration = 0;
    }

    public void dead() {
        this.state = STATE_DEAD;
        this.stateDuration = 0;
    }

    public void update(float deltaTime) {
        this.stateDuration += deltaTime;
    }
}
