package com.mygdx.game.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.MyGdxGame
import com.mygdx.game.Tools.ControlHandler

class GameSelectionScreen(private val game: MyGdxGame) : Screen {
    private lateinit var stage: Stage
    private lateinit var camera: OrthographicCamera

    private var buttons1 = ArrayList<Button>()
    private var buttons2 = ArrayList<Button>()
    private var buttons3 = ArrayList<Button>()

    private var cursorX = -1
    private var cursorY = -1
    private var x = 0
    private var y = 0

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
            pressedOffsetX = 7f
            checkedOffsetX = 5f
        }

        table.apply {
            for (i in 1..4) {
                val b = TextButton("Save " + i, textButtonStyle).apply {
                    addListener(object : ClickListener() {
                        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                            super.touchUp(event, x, y, pointer, button)
                            game.screen = GameScreen(game, i)
                        }
                    })
                }
                add(b)
                buttons1.add(b)
            }
            row()
            for (i in 5..8) {
                val b = TextButton("Save " + i, textButtonStyle).apply {
                    addListener(object : ClickListener() {
                        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                            super.touchUp(event, x, y, pointer, button)
                            game.screen = GameScreen(game, i)
                        }
                    })
                }
                add(b)
                buttons2.add(b)
            }
            row()
            val back = TextButton("Back", textButtonStyle).apply {
                addListener(object : ClickListener() {
                    override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                        super.touchUp(event, x, y, pointer, button)
                        game.screen = MainMenuScreen(game)
                    }
                })
            }
            add(back)
            buttons3.add(back)
            val del = TextButton("Delete", textButtonStyle).apply {
                addListener(object : ClickListener() {
                    override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                        super.touchUp(event, x, y, pointer, button)
                    }
                })
            }
            add(del)
            buttons3.add(del)
            row()
        }

        stage.addActor(table)
    }

    override fun render(delta: Float) {
        stage.isDebugAll = true
        stage.act()
        stage.draw()

        if (ControlHandler.upKeyJustPressed()) {
            if (cursorX != -1) {
                when {
                    cursorY == 0 -> buttons1[cursorX].isChecked = false
                    cursorY == 1 -> buttons2[cursorX].isChecked = false
                    cursorX in 0..1 -> buttons3[cursorX].isChecked = false
                }
            }
            if (cursorX == -1)
                cursorX = 0
            cursorY--
            if (cursorY < 0) {
                cursorY = 2
                if (cursorX !in 0..1)
                    cursorX = 1
            }
        }
        if (ControlHandler.downKeyJustPressed()) {
            if (cursorX != -1) {
                when {
                    cursorY == 0 -> buttons1[cursorX].isChecked = false
                    cursorY == 1 -> buttons2[cursorX].isChecked = false
                    cursorX in 0..1 -> buttons3[cursorX].isChecked = false
                }
            }
            cursorY++
            if (cursorY > 2) {
                cursorY = 0
            }
            if (cursorX == -1)
                cursorX = 0
            if (cursorY == 2 && cursorX !in 0..1)
                cursorX = 1
        }
        if (ControlHandler.leftKeyJustPressed()) {
            if (cursorX != -1) {
                when {
                    cursorY == 0 -> buttons1[cursorX].isChecked = false
                    cursorY == 1 -> buttons2[cursorX].isChecked = false
                    cursorX in 0..1 -> buttons3[cursorX].isChecked = false
                }
            }
            cursorX--
            if (cursorY == -1)
                cursorY = 0
            if (cursorX < 0) {
                cursorX = if (cursorY in 0..1)
                    3
                else
                    1
            }
        }
        if (ControlHandler.rightKeyJustPressed()) {
            if (cursorX != -1) {
                when {
                    cursorY == 0 -> buttons1[cursorX].isChecked = false
                    cursorY == 1 -> buttons2[cursorX].isChecked = false
                    cursorX in 0..1 -> buttons3[cursorX].isChecked = false
                }
            }
            cursorX++
            if (cursorY == -1)
                cursorY = 0
            if (cursorY in 0..1 && cursorX > 3)
                cursorX = 0
            if (cursorY == 2 && cursorX > 1)
                cursorX = 0
        }

        if (x != Gdx.input.x || y != Gdx.input.y) {
            if (cursorX != -1) {
                when (cursorY) {
                    0 -> buttons1[cursorX].isChecked = false
                    1 -> buttons2[cursorX].isChecked = false
                    2 -> buttons3[cursorX].isChecked = false
                }
                cursorX = -1
                cursorY = -1
            }
        }
        x = Gdx.input.x
        y = Gdx.input.y

        if (cursorX != -1)
            when (cursorY) {
                0 -> buttons1[cursorX].isChecked = true
                1 -> buttons2[cursorX].isChecked = true
                2 -> buttons3[cursorX].isChecked = true
            }

        if (cursorX != -1 && ControlHandler.useKeyJustPressed()) {
            when (cursorY) {
                0 -> (buttons1[cursorX].listeners[1] as ClickListener).touchUp(null, 0f, 0f, 0, 0)
                1 -> (buttons2[cursorX].listeners[1] as ClickListener).touchUp(null, 0f, 0f, 0, 0)
                2 -> (buttons3[cursorX].listeners[1] as ClickListener).touchUp(null, 0f, 0f, 0, 0)
            }
        }
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
