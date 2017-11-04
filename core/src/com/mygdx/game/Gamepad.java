package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Tools.XBox360Pad;

public class Gamepad extends ApplicationAdapter implements ControllerListener {
    SpriteBatch batch;
    Sprite sprite;
    BitmapFont font;
    boolean hasControllers = true;
    String message = "Please install a controller";

    XBox360Pad1 x;
    ShapeRenderer shapeRenderer;

    @Override
    public void create () {
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture("gamepad.jpg"));

        // Listen to all controllers, not just one
        Controllers.addListener(this);

        font = new BitmapFont();
        font.setColor(Color.WHITE);


        if(Controllers.getControllers().size == 0)
        {
            hasControllers = false;
        }

        x = new XBox360Pad1();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (x.BUTTON_X) {
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.circle(567, 400, 27);
        }
        if (x.BUTTON_Y) {
            shapeRenderer.setColor(Color.ORANGE);
            shapeRenderer.circle(627, 459, 27);
        }
        if (x.BUTTON_A) {
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.circle(627, 341, 27);
        }
        if (x.BUTTON_B) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(687, 400, 27);
        }

        if (x.BUTTON_BACK) {
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.circle(627, 341, 27);
        }
        if (x.BUTTON_START) {
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.circle(687, 400, 27);
        }
        shapeRenderer.end();
    }

    // connected and disconnect dont actually appear to work for XBox 360 controllers.
    @Override
    public void connected(Controller controller) {
        hasControllers = true;
    }

    @Override
    public void disconnected(Controller controller) {
        hasControllers = false;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        if(buttonCode == com.mygdx.game.Tools.XBox360Pad.BUTTON_Y)
            x.BUTTON_Y = true;
        if(buttonCode == com.mygdx.game.Tools.XBox360Pad.BUTTON_A)
            x.BUTTON_A = true;
        if(buttonCode == com.mygdx.game.Tools.XBox360Pad.BUTTON_X)
            x.BUTTON_X = true;
        if(buttonCode == com.mygdx.game.Tools.XBox360Pad.BUTTON_B)
            x.BUTTON_B = true;

        if(buttonCode == com.mygdx.game.Tools.XBox360Pad.BUTTON_BACK)
            x.BUTTON_BACK = true;
        if(buttonCode == com.mygdx.game.Tools.XBox360Pad.BUTTON_START)
            x.BUTTON_START = true;
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if(buttonCode == com.mygdx.game.Tools.XBox360Pad.BUTTON_Y)
            x.BUTTON_Y = false;
        if(buttonCode == com.mygdx.game.Tools.XBox360Pad.BUTTON_A)
            x.BUTTON_A = false;
        if(buttonCode == com.mygdx.game.Tools.XBox360Pad.BUTTON_X)
            x.BUTTON_X = false;
        if(buttonCode == com.mygdx.game.Tools.XBox360Pad.BUTTON_B)
            x.BUTTON_B = false;

        if(buttonCode == com.mygdx.game.Tools.XBox360Pad.BUTTON_BACK)
            x.BUTTON_BACK = false;
        if(buttonCode == XBox360Pad.BUTTON_START)
            x.BUTTON_START = false;
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        // This is your analog stick
        // Value will be from -1 to 1 depending how far left/right, up/down the stick is
        // For the Y translation, I use a negative because I like inverted analog stick
        // Like all normal people do! ;)

        // Left Stick
//        if(axisCode == XBox360Pad.AXIS_LEFT_X)
//            sprite.translateX(10f * value);
//        if(axisCode == XBox360Pad.AXIS_LEFT_Y)
//            sprite.translateY(-10f * value);
//
//        // Right stick
//        if(axisCode == XBox360Pad.AXIS_RIGHT_X)
//            sprite.rotate(10f * value);
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        // This is the dpad
//        if(value == XBox360Pad.BUTTON_DPAD_LEFT)
//            sprite.translateX(-10f);
//        if(value == XBox360Pad.BUTTON_DPAD_RIGHT)
//            sprite.translateX(10f);
//        if(value == XBox360Pad.BUTTON_DPAD_UP)
//            sprite.translateY(10f);
//        if(value == XBox360Pad.BUTTON_DPAD_DOWN)
//            sprite.translateY(-10f);
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
