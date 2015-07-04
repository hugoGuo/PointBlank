package com.benermafield.pointblank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.benermafield.pointblank.PointBlank;
import com.benermafield.pointblank.util.Assets;
import com.benermafield.pointblank.util.Settings;

public class MainMenuScreen extends ScreenAdapter {
    private PointBlank game;
    private OrthographicCamera camera;
    private Rectangle playBounds;
    private Vector3 touchPoint;

    public MainMenuScreen(PointBlank game) {
        this.game = game;

        camera = new OrthographicCamera(480, 320);
        camera.position.set(480 / 2, 320 / 2, 0);
        playBounds = new Rectangle(50, 50, 190, 49);
        touchPoint = new Vector3();
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new com.benermafield.pointblank.screen.GameScreen(game));
                return;
            }
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);

        game.batcher.enableBlending();
        game.batcher.begin();
        game.batcher.draw(Assets.blueButtonRectangle, 50, 50, 190, 49);
        game.batcher.end();
    }

    @Override
    public void pause() {
        Settings.save();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }
}
