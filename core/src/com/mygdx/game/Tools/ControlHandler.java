package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.SharedLibraryLoader;

public class ControlHandler implements ControllerListener {
    public static final float STICK_DEATH_ZONE = 0.25f;

    private static Vector2 lStick = new Vector2();

    private static float angle = 0f;
    private static Vector2 rStick = new Vector2();
    private static int x = Gdx.input.getX();
    private static int y = Gdx.input.getY();

    public static boolean gamepad = false;

    private static boolean gamepadUseKeyJustPressed = false;
    private static boolean gamepadPauseKeyJustPressed = false;

    private static boolean gamepadUpKeyJustPressed = false;
    private static boolean gamepadDownKeyJustPressed = false;
    private static boolean gamepadLeftKeyJustPressed = false;
    private static boolean gamepadRightKeyJustPressed = false;

    public static void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || x != Gdx.input.getX() || y != Gdx.input.getY())
            gamepad = false;
        x = Gdx.input.getX();
        y = Gdx.input.getY();

        gamepadUseKeyJustPressed = false;
        gamepadPauseKeyJustPressed = false;

        gamepadUpKeyJustPressed = false;
        gamepadDownKeyJustPressed = false;
        gamepadLeftKeyJustPressed = false;
        gamepadRightKeyJustPressed = false;
    }

    public static Vector2 ctrl() {
        Vector2 ctrl = new Vector2();
        ctrl.add(keyControl());
        if (lStick.len() >= STICK_DEATH_ZONE)
            ctrl.add(lStick);
        if (ctrl.len2() > 1)
            ctrl.clamp(0f, 1f);
        return ctrl;
    }

    public static float dir() {
        if (gamepad) {
            if (rStick.len() >= STICK_DEATH_ZONE) {
                angle = rStick.angleRad();
            }
            return angle;
        }
        else {
            angle = new Vector2(Gdx.input.getX() - Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - Gdx.input.getY()).angleRad();
        }
        return angle;
    }

    public static Vector2 keyControl() {
        Vector2 ctrl = new Vector2();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            ctrl.add(-1f, 0f);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            ctrl.add(1f, 0f);
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            ctrl.add(0f, 1f);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
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
        gamepad = true;
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if (buttonCode == 0) {
            gamepadUseKeyJustPressed = true;
        }
        if (buttonCode == 7) {
            gamepadPauseKeyJustPressed = true;
        }

        System.out.println(buttonCode);
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        if (SharedLibraryLoader.isWindows) {
            if (axisCode == 1)
                lStick.x = value;
            if (axisCode == 0)
                lStick.y = -value;

            if (axisCode == 3)
                rStick.x = value;
            if (axisCode == 2)
                rStick.y = -value;
        }
        else if (SharedLibraryLoader.isLinux) {
            if (axisCode == 0)
                lStick.x = value;
            if (axisCode == 1)
                lStick.y = -value;

            if (axisCode == 3)
                rStick.x = value;
            if (axisCode == 4)
                rStick.y = -value;

        }

        if (lStick.len() >= STICK_DEATH_ZONE || rStick.len() >= STICK_DEATH_ZONE)
            gamepad = true;

        if (value >= 0.8) {
            System.out.println(axisCode + " " + "+1");
        }
        if (value <= -0.8) {
            System.out.println(axisCode + " " + "-1");
        }
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        gamepad = true;
        if (value == PovDirection.north)
            gamepadUpKeyJustPressed = true;
        if (value == PovDirection.south)
            gamepadDownKeyJustPressed = true;
        if (value == PovDirection.west)
            gamepadLeftKeyJustPressed = true;
        if (value == PovDirection.east)
            gamepadRightKeyJustPressed = true;
        System.out.println(value);
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

    public static boolean useKeyJustPressed() {
        if (gamepad) {
            if (gamepadUseKeyJustPressed) {
                gamepadUseKeyJustPressed = false;
                return true;
            }
            else
                return false;
        }
        else {
            return Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
        }
    }

    public static boolean pauseKeyJustPressed() {
        if (gamepad) {
            if (gamepadPauseKeyJustPressed) {
                gamepadPauseKeyJustPressed = false;
                return true;
            }
            else
                return false;
        }
        else {
            return Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE);
        }
    }

    public static boolean upKeyJustPressed() {
        if (gamepad) {
            if (gamepadUpKeyJustPressed) {
                gamepadUpKeyJustPressed = false;
                return true;
            }
            else
                return false;
        }
        else {
            return Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W);
        }
    }

    public static boolean downKeyJustPressed() {
        if (gamepad) {
            if (gamepadDownKeyJustPressed) {
                gamepadDownKeyJustPressed = false;
                return true;
            }
            else
                return false;
        }
        else {
            return Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S);
        }
    }

    public static boolean leftKeyJustPressed() {
        if (gamepad) {
            if (gamepadLeftKeyJustPressed) {
                gamepadLeftKeyJustPressed = false;
                return true;
            }
            else
                return false;
        }
        else {
            return Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A);
        }
    }

    public static boolean rightKeyJustPressed() {
        if (gamepad) {
            if (gamepadRightKeyJustPressed) {
                gamepadRightKeyJustPressed = false;
                return true;
            }
            else
                return false;
        }
        else {
            return Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D);
        }
    }
}
