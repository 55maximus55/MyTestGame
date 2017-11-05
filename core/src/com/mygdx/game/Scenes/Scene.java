package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Screens.GameScreen;
import org.jetbrains.annotations.NotNull;

public abstract class Scene implements Disposable {
    public String name;
    public TiledMap map;
    public OrthographicCamera camera;
    public int save;

    public Scene(int save, GameScreen screen) {
        this.save = save;
    }

    public void loadMap() {
        map = new TmxMapLoader().load("scenes/" + name + ".tmx");
    }

    public abstract void update();

    public abstract void render();

    @Override
    public void dispose() {
        map.dispose();
    }

    public abstract Scene getNewScene();
}
