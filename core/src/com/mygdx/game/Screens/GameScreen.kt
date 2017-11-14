package com.mygdx.game.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Scenes.*
import com.mygdx.game.Tools.ContactHandler
import com.mygdx.game.Tools.ControlHandler

class GameScreen(private val game: MyGdxGame, public val save: Int) : Screen {
    lateinit var scene : Scene

    override fun show() {
        val preferences = Gdx.app.getPreferences("Save" + save)
        val s = preferences.getString("scene", SceneList.START_SCENE)
        scene = SceneList.getNewScene(s, this)
//        when (s) {
//            "topdown" -> scene = TDScene(save, this)
//            "topdown1" -> scene = TDScene1(save, this)
//            "isometric" -> scene = IScene(1, this)
//        }
    }

    override fun render(delta: Float) {
        update()
        draw()

        if (ControlHandler.useKeyJustPressed() && ContactHandler.location != "") {
            val newScene = scene.newScene
            scene.dispose()
            scene = newScene
            ContactHandler.location = ""
        }

        if (ControlHandler.pauseKeyJustPressed()) {
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