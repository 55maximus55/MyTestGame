package com.mygdx.game.Scenes;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class TopDownScene extends Scene {
    @Override
    public void loadScene() {
        type = "topDown";
        map = new TmxMapLoader().load("scenes/topdown.tmx");
    }
}
