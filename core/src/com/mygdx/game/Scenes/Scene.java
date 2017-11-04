package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

public abstract class Scene implements Disposable {
    public String type;
    public String name;
    public TiledMap map;
    public OrthographicCamera camera;

    public void loadMap() {
        map = new TmxMapLoader().load("scenes/" + name + ".tmx");
    }

    public abstract void update();

    public abstract void render();

    @Override
    public void dispose() {
        map.dispose();
    }
}
