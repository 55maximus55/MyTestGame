package com.mygdx.game.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Scenes.Scene
import com.mygdx.game.Scenes.TopDownScene

class GameScreen(private val game: MyGdxGame, private val save: Int) : Screen {
    lateinit var scene : Scene

    override fun show() {
        scene = TopDownScene(save, this)
    }

    override fun render(delta: Float) {
        update()
        draw()

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.screen = MainMenuScreen(game)
        }
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        scene.dispose()
    }

    private fun update() {
        scene.update()
    }

    private fun draw() {
        scene.render()
    }
}