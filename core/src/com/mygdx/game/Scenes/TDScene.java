package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Tools.ContactHandler;

public class TDScene extends TopDownScene {

    Texture texture;
    TextureRegion textureRegion;

    SpriteBatch batch = new SpriteBatch();

    public TDScene(int save, GameScreen screen) {
        super(save, screen);
        name = "topdown";
        loadMap();

        loadSave(save);

        texture = new Texture("player.png");
        textureRegion = new TextureRegion(texture);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render() {
        mapRenderer.render();

        float a = body.getAngle() * MathUtils.radiansToDegrees + 22.5f;
        System.out.println(a);
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
}
