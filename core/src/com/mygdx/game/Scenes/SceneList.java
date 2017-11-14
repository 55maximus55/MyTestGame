package com.mygdx.game.Scenes;

import com.mygdx.game.Screens.GameScreen;

public class SceneList {
    public static final String START_SCENE = "Home";

    public static final String[] SCENES = {"Home"};

    public static Scene getNewScene(String name, GameScreen screen) {
        if (name.equals("Home"))
            return new HomeScene(screen);
        return null;
    }
}
