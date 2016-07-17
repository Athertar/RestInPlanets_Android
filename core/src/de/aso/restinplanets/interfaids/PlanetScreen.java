package de.aso.restinplanets.interfaids;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sun.javafx.scene.control.skin.ButtonSkin;

import de.aso.restinplanets.net.Planet;
import de.aso.restinplanets.util.FontCreator;
import de.aso.restinplanets.util.RIPStyle;

public class PlanetScreen implements Screen {

	private Stage stage;
	private PlanetGroup planetGroup;
	private final Main main;

	public PlanetScreen(Planet planet, Main main) {
		this.main = main;
		this.stage = new Stage();
		this.planetGroup = new PlanetGroup(planet);
		stage.addActor(planetGroup);
		Gdx.input.setInputProcessor(stage);

		TextButton returnButton = new TextButton("Back", RIPStyle.getButtonSkin(200));
		returnButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PlanetScreen.this.main.returnToPlanetSelection();
			}
		});

		stage.addActor(returnButton);

	}

	@Override
	public void show() {

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
}

