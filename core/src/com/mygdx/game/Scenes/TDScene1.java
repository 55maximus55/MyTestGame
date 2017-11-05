package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Tools.ContactHandler;
import com.mygdx.game.Tools.ControlHandler;

public class TDScene1 extends Scene {
    OrthogonalTiledMapRenderer mapRenderer;
    World world;
    Box2DDebugRenderer debugRenderer;
    Body body;

    public TDScene1(int save, GameScreen screen) {
        super(save, screen);

        name = "topdown1";
        loadMap();
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        world = new World(new Vector2(0f, 0f), false);
        world.setContactListener(new ContactHandler());
        debugRenderer = new Box2DDebugRenderer();

        createWalls();
        createLocations();

        loadSave(save);
    }

    @Override
    public void update() {
        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;
        camera.update();

        mapRenderer.setView(camera);
//        body.linearVelocity = ControlHandler.ctrl(ControlHandler()).scl(3000f)
        body.setTransform(body.getPosition().add(ControlHandler.ctrl().scl(3.8f)), ControlHandler.dir());
        world.step(Gdx.graphics.getDeltaTime(), 10, 10);
    }

    @Override
    public void render() {
        mapRenderer.render();
        debugRenderer.render(world, camera.combined);
    }

    private void createWalls() {
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        for (Object object : map.getLayers().get("walls").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bDef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
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
            bDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bDef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fDef.shape = shape;
            body.createFixture(fDef);
            body.setUserData(((RectangleMapObject)object).getName());
        }
    }

    private void save(int save) {
        Preferences preferences = Gdx.app.getPreferences("Save" + save + "_" + name);
        preferences.putFloat("playerX", body.getPosition().x);
        preferences.putFloat("playerY", body.getPosition().y);
        preferences.flush();
    }

    private void loadSave(int save) {
        Preferences preferences = Gdx.app.getPreferences("Save" + save + "_" + name);

        BodyDef bDef = new BodyDef();
        CircleShape shape = new CircleShape();
        FixtureDef fDef = new FixtureDef();

        for (Object object : map.getLayers().get("player").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.DynamicBody;

            bDef.position.x = preferences.getFloat("playerX", rect.getX() + rect.getWidth() / 2);
            bDef.position.y = preferences.getFloat("playerY", rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bDef);

            shape.setRadius(16f);

            fDef.shape = shape;

            body.createFixture(fDef);
            body.setUserData("player");
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        mapRenderer.dispose();

        save(save);
    }

    @Override
    public Scene getNewScene() {
        return null;
    }
}
