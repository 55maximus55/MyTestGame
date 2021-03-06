package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Tools.ContactHandler;

public class TDScene extends TopDownScene {
    public TDScene(GameScreen screen) {
        super(screen);
        name = "topdown";
        loadMap();
        loadSave();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render() {
        mapRenderer.render();
        debugRenderer.render(world, camera.combined);
    }

    private void save() {
        Preferences preferences = Gdx.app.getPreferences("Save" + save + "_" + name);
        preferences.putFloat("playerX", body.getPosition().x);
        preferences.putFloat("playerY", body.getPosition().y);
        preferences.flush();
    }

    private void loadSave() {
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
        save();
    }

    @Override
    public Scene getNewScene() {
        if (ContactHandler.location.equals("location isometric 240 -440")) {
            Preferences preferences = Gdx.app.getPreferences("Save" + save + "_" + "isometric");
            preferences.putFloat("playerX", 240f);
            preferences.putFloat("playerY", -440f);
            preferences.flush();

            preferences = Gdx.app.getPreferences("Save" + save);
            preferences.putString("scene", "isometric");
            preferences.flush();

            return new IScene(screen);
        }
        return null;
    }
}
