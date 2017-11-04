package com.mygdx.game.Scenes;

import com.badlogic.gdx.maps.tiled.TiledMap;

public abstract class Scene {
    public String type;
    public TiledMap map;
    public abstract void loadScene();
}
