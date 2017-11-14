package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Tools.ContactHandler;
import com.mygdx.game.Tools.ControlHandler;

public class IsometricScene extends Scene {
    World world;
    Body body;

    IsometricTiledMapRenderer mapRenderer;
    Box2DDebugRenderer debugRenderer;

    public IsometricScene(GameScreen screen) {
        super(screen);
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
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        for (Object object : map.getLayers().get("walls").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rect.getY() + rect.getHeight() / 2, - rect.getX() - rect.getWidth() / 2);

            body = world.createBody(bDef);

            shape.setAsBox(rect.getHeight() / 2, rect.getWidth() / 2);
            fDef.shape = shape;
            body.createFixture(fDef);
            body.setUserData("wall");
        }
    }

    private void createLocations() {
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        for (Object object : map.getLayers().get("location").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rect.getY() + rect.getHeight() / 2, - rect.getX() - rect.getWidth() / 2);

            body = world.createBody(bDef);

            shape.setAsBox(rect.getHeight() / 2, rect.getWidth() / 2);
            fDef.shape = shape;
            body.createFixture(fDef);
            body.setUserData(((RectangleMapObject)object).getName());
        }
    }

    @Override
    public void update() {
        float cx = body.getPosition().x;
        float cy = body.getPosition().y / 2;

        float bx = cx - 2 * cy;
        float by = cy + cx / 2;

        camera.position.x = bx;
        camera.position.y = by + 16;
        camera.update();

        mapRenderer.setView(camera);
//        body.linearVelocity = ControlHandler.ctrl(ControlHandler()).scl(3000f)
        body.setTransform(body.getPosition().add(ControlHandler.ctrl().rotate(-45).scl(2)), ControlHandler.dir());
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
