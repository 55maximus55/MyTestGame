package com.mygdx.game.Scenes;

import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Tools.ContactHandler;

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
        camera.update();

        mapRenderer.setView(camera);
    }

    @Override
    public void render() {

    }

    @Override
    public Scene getNewScene() {
        return null;
    }
}
