package com.benermafield.pointblank.component;

import com.badlogic.ashley.core.Component;

/**
 * Created by Andrew on 01/05/2015.
 */
public class PositionComponent extends Component {
    public float x, y;

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
