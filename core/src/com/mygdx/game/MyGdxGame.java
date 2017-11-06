package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.MainMenuScreen;
import com.mygdx.game.Tools.ControlHandler;

public class MyGdxGame extends Game {
	public static final String APP_NAME = "MyTestGame";

	@Override
	public void create() {
		Gdx.app.log(APP_NAME, "Application started");

		setScreen(new MainMenuScreen(this));
		Controllers.addListener(new ControlHandler());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		ControlHandler.update();
	}

	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.log(APP_NAME, "Application closed");
	}
}
