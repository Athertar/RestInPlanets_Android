package de.aso.restinplanets.interfaids;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import de.aso.restinplanets.net.Planet;
import de.aso.restinplanets.util.RIPStyle;

public class PlanetSelectionScreen implements Screen {

	private Stage stage;
	private Main main;
	private long[] planetIDs;

	public PlanetSelectionScreen(long[] planetIDs, Main main) throws IOException {
		this.main = main;
		this.planetIDs = planetIDs;
		Arrays.sort(planetIDs);
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		Table layoutTable = new Table();
		stage.addActor(layoutTable);
		layoutTable.left().top();
		layoutTable.setFillParent(true);

		float buttonHeight = 200;

		for (int i = 0; i < planetIDs.length; i++) {
			TextButton planetButton = new TextButton("Planet " + planetIDs[i], RIPStyle.getButtonSkin(200));
			planetButton.addListener(new PlanetSelectionListener(planetIDs[i]));
			layoutTable.add(planetButton).height(buttonHeight).expandX();
			if(i%2 == 1) {
				layoutTable.row();
			}
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

	private class PlanetSelectionListener extends ChangeListener {

		private long planetID;

		public PlanetSelectionListener(long planetID) {
			this.planetID = planetID;
		}

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			main.showPlanetScreen(planetID);
		}
	}
}
