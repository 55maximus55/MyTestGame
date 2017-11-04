package com.mygdx.game.Scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.mygdx.game.Screens.GameScreen
import com.mygdx.game.Tools.ContactHandler
import com.mygdx.game.Tools.ControlHandler

class TopDownScene(private val save: Int, val screen: GameScreen) : Scene() {
    var mapRenderer: OrthogonalTiledMapRenderer
    var world: World
    var debugRenderer: Box2DDebugRenderer

    lateinit var body: Body

    init {
        type = "topdown"
        name = "topdown"
        loadMap()
        mapRenderer = OrthogonalTiledMapRenderer(map)

        camera = OrthographicCamera()
        camera.setToOrtho(false)

        world = World(Vector2(0f, 0f), false)
        world.setContactListener(ContactHandler())
        debugRenderer = Box2DDebugRenderer()

        createWalls()
        createLocations()

        loadSave(save)
    }

    private fun loadSave(save: Int) {
        val preferences = Gdx.app.getPreferences("Save" + save + "_" + name)

        val bdef = BodyDef()
        val shape = CircleShape()
        val fdef = FixtureDef()

        for (`object` in map.layers.get("player").objects.getByType(RectangleMapObject::class.java)) {
            val rect = (`object` as RectangleMapObject).rectangle

            bdef.type = BodyDef.BodyType.DynamicBody

            bdef.position.x = preferences.getFloat("playerX", rect.getX() + rect.getWidth() / 2)
            bdef.position.y = preferences.getFloat("playerY", rect.getY() + rect.getHeight() / 2)

            body = world.createBody(bdef)

            shape.radius = 16f

            fdef.shape = shape

            body.createFixture(fdef)
            body.userData = "player"
        }
    }

    override fun update() {
        camera.position.x = body.position.x
        camera.position.y = body.position.y
        camera.update()

        mapRenderer.setView(camera)
//        body.linearVelocity = ControlHandler.ctrl(ControlHandler()).scl(3000f)
        body.setTransform(body.getPosition().add(ControlHandler.ctrl().scl(3.8f)), ControlHandler.dir())
        world.step(Gdx.graphics.deltaTime, 10, 10)
    }

    override fun render() {
        mapRenderer.render()
        debugRenderer.render(world, camera.combined)
    }

    override fun dispose() {
        super.dispose()
        mapRenderer.dispose()

        save()
    }

    private fun save() {
        val preferences = Gdx.app.getPreferences("Save" + save + "_" + name)
        preferences.putFloat("playerX", body.position.x)
        preferences.putFloat("playerY", body.position.y)
        preferences.flush()
    }

    private fun createWalls() {
        val bdef = BodyDef()
        val shape = PolygonShape()
        val fdef = FixtureDef()
        var body: Body

        for (`object` in map.layers.get("walls").objects.getByType(RectangleMapObject::class.java)) {
            val rect = (`object` as RectangleMapObject).rectangle

            bdef.type = BodyDef.BodyType.StaticBody
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2)

            body = world.createBody(bdef)

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2)
            fdef.shape = shape
            body.createFixture(fdef)
            body.userData = "wall"
        }
    }

    private fun createLocations() {
        val bdef = BodyDef()
        val shape = PolygonShape()
        val fdef = FixtureDef()
        var body: Body

        for (`object` in map.layers.get("location").objects.getByType(RectangleMapObject::class.java)) {
            val rect = (`object` as RectangleMapObject).rectangle

            bdef.type = BodyDef.BodyType.StaticBody
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2)

            body = world.createBody(bdef)

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2)
            fdef.shape = shape
            body.createFixture(fdef)
            body.userData = `object`.name
        }
    }
}
