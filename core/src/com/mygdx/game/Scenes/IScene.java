package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Tools.ContactHandler;

public class IScene extends IsometricScene {
    Texture texture;
    TextureRegion textureRegion;

    SpriteBatch batch = new SpriteBatch();

    public IScene(int save, GameScreen screen) {
        super(save, screen);
        name = "isometric";
        loadMap();
        loadSave();
        texture = new Texture("player.png");
        textureRegion = new TextureRegion(texture);
    }

    private void save(int save) {
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

        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.x = preferences.getFloat("playerX");
        bDef.position.y = preferences.getFloat("playerY");

        body = world.createBody(bDef);

        shape.setRadius(1f);

        fDef.shape = shape;

        body.createFixture(fDef);
        body.setUserData("player");
    }

    @Override
    public void render() {
        mapRenderer.render();

        float a = body.getAngle() * MathUtils.radiansToDegrees + 22.5f;
        if (a > 0f) {
            textureRegion.setRegion((int)(a / 45f) * 16, 0, 16, 48);
        }
        else {
            textureRegion.setRegion((7 + (int)(a / 45f)) * 16, 0, 16, 48);
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(textureRegion, camera.position.x, camera.position.y);
        batch.end();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public Scene getNewScene() {
        if (ContactHandler.location.equals("location topdown 128 550")) {
            Preferences preferences = Gdx.app.getPreferences("Save" + save + "_" + "topdown");
            preferences.putFloat("playerX", 128f);
            preferences.putFloat("playerY", 550f);
            preferences.flush();

            preferences = Gdx.app.getPreferences("Save" + save);
            preferences.putString("scene", "topdown");
            preferences.flush();

            return new TDScene(save, screen);
        }
        if (ContactHandler.location.equals("location topdown1 128 128")) {
            Preferences preferences = Gdx.app.getPreferences("Save" + save + "_" + "topdown1");
            preferences.putFloat("playerX", 128f);
            preferences.putFloat("playerY", 128f);
            preferences.flush();

            preferences = Gdx.app.getPreferences("Save" + save);
            preferences.putString("scene", "topdown1");
            preferences.flush();

            return new TDScene1(save, screen);
        }
        return null;
    }

    @Override
    public void dispose() {
        super.dispose();
        mapRenderer.dispose();
        save(save);
    }
}
