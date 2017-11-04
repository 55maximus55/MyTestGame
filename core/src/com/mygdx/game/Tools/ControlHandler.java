package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ControlHandler implements ControllerListener {
    private static Vector2 lStick = new Vector2();
    private static Vector2 rStick = new Vector2();

    public static Vector2 ctrl() {
        Vector2 ctrl = new Vector2();
        ctrl.add(keyControl());
        if (lStick.len() > 0.25f)
            ctrl.add(lStick);
        if (ctrl.len2() > 1)
            ctrl.clamp(0f, 1f);
        return ctrl;
    }

    public static Vector2 keyControl() {
        Vector2 ctrl = new Vector2();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            ctrl.add(-1f, 0f);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            ctrl.add(1f, 0f);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            ctrl.add(0f, 1f);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            ctrl.add(0f, -1f);
        return ctrl;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        if (axisCode == 1) {
            lStick.x = value;
        }
        if (axisCode == 0) {
            lStick.y = -value;
        }
        if (axisCode == 3) {
            rStick.x = value;
        }
        if (axisCode == 2) {
            rStick.y = -value;
        }
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
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

    public static float dir() {
        return rStick.angleRad();
    }
}
