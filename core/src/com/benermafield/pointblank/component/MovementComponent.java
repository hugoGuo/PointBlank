package com.benermafield.pointblank.component;

import com.badlogic.ashley.core.Component;

/**
 * Created by Andrew on 01/05/2015.
 */
public class MovementComponent extends Component {
    public float x, y;

    public MovementComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
