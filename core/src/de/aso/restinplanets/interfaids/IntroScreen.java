package de.aso.restinplanets.interfaids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.collision.BoundingBox;

public class IntroScreen implements Screen {

	private PerspectiveCamera cam;
	private ModelBatch modelBatch;
	private AssetManager assets;
	private Environment environment;
	private boolean loading;
	private boolean instanceLoaded = false;
	private float distance = 20f;
	private float angle;
	private float angleSpeed = 0.02f;
	private ModelInstance shipInstance;

	IntroScreen() {
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 0f, distance);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		assets = new AssetManager();
		assets.load("models/asoproductions.g3db", Model.class);
		loading = true;
	}

	private void doneLoading() {
		Model ship = assets.get("models/asoproductions.g3db", Model.class);
		this.shipInstance = new ModelInstance(ship);
		BoundingBox boundingBox = new BoundingBox();
		ship.calculateBoundingBox(boundingBox);
		boundingBox.getWidth();

		shipInstance.transform.translate(-boundingBox.getCenterX(), -boundingBox.getCenterY(), -boundingBox.getCenterZ());

		loading = false;
		instanceLoaded = true;
	}

	@Override
	public void render(float delta) {
		if (loading && assets.update())
			doneLoading();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		if (instanceLoaded) {
			angle += angleSpeed;
			cam.position.set(distance * (float) Math.sin(angle), 0.0f, distance * (float) Math.cos(angle));
			cam.lookAt(0,0,0);
			cam.update();
			modelBatch.begin(cam);
			modelBatch.render(shipInstance, environment);
			modelBatch.end();
		}
	}

	public void resize (int width, int height) {
	}

	public void pause () {
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		modelBatch.dispose();
		assets.dispose();
	}

	@Override
	public void hide() {

	}

	@Override
	public void show() {

	}
}
