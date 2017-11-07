package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Tools.ContactHandler;
import com.mygdx.game.Tools.ControlHandler;

public class IsometricScene extends Scene {
    World world;
    Body body;

    IsometricTiledMapRenderer mapRenderer;
    Box2DDebugRenderer debugRenderer;

    public IsometricScene(int save, GameScreen screen) {
        super(save, screen);
        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new ContactHandler());
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void loadMap() {
        super.loadMap();
        createWalls();
        createLocations();
        mapRenderer = new IsometricTiledMapRenderer(map);
    }

    private void createWalls() {

    }

    private void createLocations() {

    }

    @Override
    public void update() {
        float cx = body.getPosition().x;
        float cy = body.getPosition().y;

        float bx = cx - 2 * cy + 32;
        float by = cy + cx / 2 - 16;

        camera.position.x = bx;
        camera.position.y = by;
        camera.update();

        mapRenderer.setView(camera);
//        body.linearVelocity = ControlHandler.ctrl(ControlHandler()).scl(3000f)
        body.setTransform(body.getPosition().add(ControlHandler.ctrl().scl(2, 1)), ControlHandler.dir());
        world.step(Gdx.graphics.getDeltaTime(), 10, 10);
    }

    @Override
    public void render() {

    }

    @Override
    public Scene getNewScene() {
        return null;
    }
}
