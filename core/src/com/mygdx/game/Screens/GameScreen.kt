package com.mygdx.game.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Scenes.Scene
import com.mygdx.game.Scenes.TDScene
import com.mygdx.game.Scenes.TDScene1
import com.mygdx.game.Tools.ContactHandler

class GameScreen(private val game: MyGdxGame, private val save: Int) : Screen {
    lateinit var scene : Scene

    override fun show() {
        scene = TDScene(save, this)
    }

    override fun render(delta: Float) {
        update()
        draw()

        if (ContactHandler.location != "" && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            val newScene = scene.getNewScene()
            scene.dispose()
            scene = newScene
            ContactHandler.location = ""
        }

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