package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Screens.GameScreen;
import org.jetbrains.annotations.NotNull;

public abstract class Scene implements Disposable {
    public GameScreen screen;

    public String name;
    public TiledMap map;
    public int save;

    public OrthographicCamera camera;

    public Scene(int save, GameScreen screen) {
        this.save = save;
        this.screen = screen;

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
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
