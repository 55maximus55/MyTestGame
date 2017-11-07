package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Screens.GameScreen;

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

    private void loadSave() {
        BodyDef bDef = new BodyDef();
        CircleShape shape = new CircleShape();
        FixtureDef fDef = new FixtureDef();

        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.x = 0;
        bDef.position.y = 0;

        body = world.createBody(bDef);

        shape.setRadius(16f);

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
}
