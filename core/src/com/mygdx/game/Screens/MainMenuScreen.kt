package com.mygdx.game.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.MyGdxGame
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class MainMenuScreen(private val game: MyGdxGame) : Screen {

    private lateinit var stage: Stage
    private lateinit var camera: OrthographicCamera

    override fun show() {
        camera = OrthographicCamera()
        stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera))
        Gdx.input.inputProcessor = stage

        val table = Table().apply {
            center()
            setFillParent(true)
        }

        val textButtonStyle = TextButton.TextButtonStyle().apply {
            font = BitmapFont()
        }

        val startGameButton = TextButton("Start game", textButtonStyle).apply {
            addListener(object : ClickListener() {
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    super.touchUp(event, x, y, pointer, button)
                    game.screen = GameSelectionScreen(game)
                }
            })
        }

        val settingsButton = TextButton("Settins", textButtonStyle).apply {
            addListener(object : ClickListener() {
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    super.touchUp(event, x, y, pointer, button)
                }
            })
        }

        val exitButton = TextButton("Exit", textButtonStyle).apply {
            addListener(object : ClickListener() {
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    super.touchUp(event, x, y, pointer, button)
                    Gdx.app.exit()
                }
            })
        }

        table.apply {
            add(startGameButton).row()
            add(settingsButton).row()
            add(exitButton).row()
        }

        stage.addActor(table)
    }

    override fun render(delta: Float) {
        stage.isDebugAll = true
        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        stage.dispose()
    }
}
