package com.benermafield.pointblank;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.benermafield.pointblank.screen.MainMenuScreen;
import com.benermafield.pointblank.util.Assets;
import com.benermafield.pointblank.util.Settings;

public class PointBlank extends Game {
    public SpriteBatch batcher;

	@Override
	public void create () {
        batcher = new SpriteBatch();
        Settings.load();
        Assets.load();
        setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        super.render();
	}
}
