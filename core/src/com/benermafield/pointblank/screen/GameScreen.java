package com.benermafield.pointblank.screen;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.benermafield.pointblank.PointBlank;
import com.benermafield.pointblank.World;
import com.benermafield.pointblank.component.PositionComponent;
import com.benermafield.pointblank.component.TargetComponent;
import com.benermafield.pointblank.component.UIComponent;
import com.benermafield.pointblank.component.VisualComponent;
import com.benermafield.pointblank.entity.Target;
import com.benermafield.pointblank.system.MovementSystem;
import com.benermafield.pointblank.system.RenderSystem;
import com.benermafield.pointblank.system.TargetSystem;
import com.benermafield.pointblank.util.Assets;

public class GameScreen extends ScreenAdapter {
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;

    private PointBlank game;
    private Engine engine;
    private World world;
    private OrthographicCamera camera;
    private Vector3 touchPoint;

    private int state;

    private float runningTotalTime = 0;
    private int score = 0;

    ImmutableArray<Entity> entities;
    ComponentMapper<PositionComponent> pm;
    ComponentMapper<VisualComponent> vm;
    PositionComponent pc;
    VisualComponent vc;
    Rectangle rect;
    Entity pauseButton;

    public GameScreen (PointBlank game) {
        this.game = game;
        state = GAME_READY;
        engine = new Engine();
        world = new World(engine);
        camera = new OrthographicCamera(world.width, world.height);
        camera.position.set(world.width / 2, world.height / 2, 0);
        touchPoint = new Vector3();
        rect = new Rectangle();

        // UI Elements
        pauseButton = new Entity();
        pauseButton.add(new VisualComponent(Assets.blueButtonSquare));
        pauseButton.add(new UIComponent());
        pauseButton.add(new PositionComponent(10, 10));

        // Component Mappers
        pm = ComponentMapper.getFor(PositionComponent.class);
        vm = ComponentMapper.getFor(VisualComponent.class);

        engine.addSystem(new TargetSystem());
        engine.addSystem(new MovementSystem(world));
        engine.addSystem(new RenderSystem(camera, game.batcher));

        pauseSystems();
    }

    public void update (float deltaTime) {
        Gdx.gl.glClearColor((float)201/255, (float)222/255, (float)158/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (deltaTime > 0.1f) deltaTime = 0.1f;

        engine.update(deltaTime);

        switch (state) {
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
        }
    }

    private void updateReady () {
        if (Gdx.input.justTouched()) {
            state = GAME_RUNNING;
            resumeSystems();
        }
    }

    private void updateRunning (float deltaTime) {
        runningTotalTime += deltaTime;
        if (world.spawnedTargetCount < runningTotalTime && world.spawnedTargetCount < world.targetMax) {
            world.spawnTarget(TargetComponent.TYPE_DROPPING);
        }

        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            entities = engine.getEntitiesFor(Family.all(UIComponent.class, VisualComponent.class).get());
            for (Entity entity : entities) {
                pc = pm.get(entity);
                vc = vm.get(entity);
                rect.set(pc.x, pc.y, vc.region.getRegionWidth(), vc.region.getRegionHeight());
                if (rect.contains(touchPoint.x, touchPoint.y)) {
                    pause();
                    return;
                }
            }

            entities = engine.getEntitiesFor(Family.all(TargetComponent.class).get());
            for (Entity entity : entities) {
                pc = pm.get(entity);

                // TODO clicking the targets is too difficult atm (too small or too quick?)
                // If too small: either increase target size, or make "hitbox" larger
                // If too quick: slow it down, or make "hitbox" larger
                rect.set(pc.x, pc.y, Target.SIZE, Target.SIZE);
                if (rect.contains(touchPoint.x, touchPoint.y)) {
                    Assets.playSound(Assets.randomPop());
                    engine.removeEntity(entity);
                    score++;
                    return;
                }
            }
        }
    }

    public void updatePaused() {
        if (Gdx.input.justTouched()) {
            state = GAME_RUNNING;
            resumeSystems();
        }
    }

    public void drawUI () {
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);
        game.batcher.begin();
        switch (state) {
            case GAME_READY:
                presentReady();
                break;
            case GAME_RUNNING:
                presentRunning();
                break;
            case GAME_PAUSED:
                presentPaused();
                break;
            /*case GAME_LEVEL_END:
                presentLevelEnd();
                break;
            case GAME_OVER:
                presentGameOver();
                break;*/
        }
        game.batcher.end();
    }

    private void presentReady () {
        // Show button saying "go" or something similar
    }

    private void presentRunning () {
        // Show the score, timer, pause button, etc
        Assets.uiFont.draw(game.batcher, "Score: " + String.valueOf(score), 50, world.height - 50);
    }

    private void presentPaused () {

    }

    private void pauseSystems() {
        engine.getSystem(TargetSystem.class).setProcessing(false);
        engine.getSystem(MovementSystem.class).setProcessing(false);
        engine.removeEntity(pauseButton);
    }

    private void resumeSystems() {
        engine.getSystem(TargetSystem.class).setProcessing(true);
        engine.getSystem(MovementSystem.class).setProcessing(true);
        engine.addEntity(pauseButton);
    }

    @Override
    public void render(float delta) {
        update(delta);
        drawUI();
    }

    @Override
    public void pause() {
        if (state == GAME_RUNNING) {
            Assets.playSound(Assets.clickSound);
            state = GAME_PAUSED;
            pauseSystems();
        }
    }
}
