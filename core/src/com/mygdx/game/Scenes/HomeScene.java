package com.mygdx.game.Scenes;

import com.mygdx.game.Screens.GameScreen;

public class HomeScene extends TopDownScene {
    public HomeScene(GameScreen screen) {
        super(screen);
        name = "Home";
        loadMap();
    }

    @Override
    public void render() {
        super.render();
        mapRenderer.render();
    }
}
