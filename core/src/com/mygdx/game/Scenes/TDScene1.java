package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Tools.ContactHandler;

public class TDScene1 extends TopDownScene {
    public TDScene1(int save, GameScreen screen) {
        super(screen);
        name = "topdown1";
        loadMap();

        loadSave(save);
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

        bDef.type = BodyDef.BodyType.DynamicBody;

        bDef.position.x = preferences.getFloat("playerX");
        bDef.position.y = preferences.getFloat("playerY");

        body = world.createBody(bDef);

        shape.setRadius(16f);

        fDef.shape = shape;

        body.createFixture(fDef);
        body.setUserData("player");
    }

    @Override
    public void dispose() {
        super.dispose();
        mapRenderer.dispose();
        save(save);
    }

    @Override
    public Scene getNewScene() {
        if (ContactHandler.location.equals("location isometric 240 -570")) {
            Preferences preferences = Gdx.app.getPreferences("Save" + save + "_" + "isometric");
            preferences.putFloat("playerX", 240f);
            preferences.putFloat("playerY", -570f);
            preferences.flush();

            preferences = Gdx.app.getPreferences("Save" + save);
            preferences.putString("scene", "isometric");
            preferences.flush();

            return new IScene(screen);
        }
        return null;
    }
}
