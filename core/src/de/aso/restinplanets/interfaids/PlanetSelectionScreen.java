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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.io.IOException;
import java.util.ArrayList;

import de.aso.restinplanets.net.Client;
import de.aso.restinplanets.net.Planet;
import de.aso.restinplanets.util.FontCreator;

public class PlanetSelectionScreen implements Screen {


	private Client client;
	private TextButton planetButton;
	private TextButton.TextButtonStyle textButtonStyle;
	private Skin textFieldSkin;
	private Skin buttonSkin;
	private float borderOffset = 20f;
	private Stage stage;
	private Main main;
	private PlanetGroup planetStage;
	private Pixmap pixmap;
	private float buttonHeight = 200;
	private ArrayList<Planet> planets;
	private Table layoutTable;


	public PlanetSelectionScreen(Client client, Main main, String user) throws IOException {
		this.client = client;
		this.main = main;
		this.planets = client.requestPlayersPlanets(user);
		create();
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

	public void create() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		layoutTable = new Table();
		//layoutTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(layoutTable);
		layoutTable.left().top();
		layoutTable.setFillParent(true);

		buttonSkin = new Skin();
		textFieldSkin = new Skin();

		FontCreator fontCreator = new FontCreator("LemonMilk.otf", 4, 2, 2, 5);
		BitmapFont bitmapFont = fontCreator.generateFont((int) buttonHeight / 3);

		buttonSkin.add("default", bitmapFont);

		pixmap = new Pixmap(Gdx.graphics.getWidth() / 4 ,Gdx.graphics.getHeight() / 4, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		buttonSkin.add("background", new Texture(pixmap));
		textFieldSkin.add("background", new Texture(pixmap));

		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = buttonSkin.newDrawable("background", Color.GRAY);
		textButtonStyle.down = buttonSkin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.checked = buttonSkin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.over = buttonSkin.newDrawable("background", Color.LIGHT_GRAY);
		textButtonStyle.font = buttonSkin.getFont("default");
		buttonSkin.add("default", textButtonStyle);


		for (int i = 0; i < planets.size(); i++) {
			planetButton = new TextButton("Planet " + planets.get(i).getPlanetID(), buttonSkin);
			planetButton.addListener(new PlanetSelectionListener(planets.get(i)));
			layoutTable.add(planetButton).height(buttonHeight).expandX();
			if(i%2 == 1) {
				layoutTable.row();
			}
		}
	}

	private class PlanetSelectionListener extends ChangeListener {

		private Planet planet;

		public PlanetSelectionListener(Planet planet) {
			this.planet = planet;
		}

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			main.setScreen(new PlanetScreen(planet));
		}
	}
}
