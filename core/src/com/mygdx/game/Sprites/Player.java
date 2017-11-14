package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

public class Player extends Sprite implements Disposable {
    Texture texture;

    public Player() {
        texture = new Texture("player.png");
    }

    public void update() {

    }

    @Override
    public void dispose() {

    }
}
