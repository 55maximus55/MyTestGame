package com.mygdx.game.Scenes;

import com.mygdx.game.Screens.GameScreen;

public class IScene extends IsometricScene {
    public IScene(int save, GameScreen screen) {
        super(save, screen);
        name = "isometric";
        loadMap();
    }

    @Override
    public void render() {
        mapRenderer.render();
    }
}
