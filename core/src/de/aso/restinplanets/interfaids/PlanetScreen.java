package de.aso.restinplanets.interfaids;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

import de.aso.restinplanets.net.Building;
import de.aso.restinplanets.net.Planet;
import de.aso.restinplanets.util.RIPStyle;

import static com.badlogic.gdx.Input.Keys.C;
import static com.badlogic.gdx.Input.Keys.CENTER;

public class PlanetScreen implements Screen {

	private Stage stage;
	private final Main main;
	private Planet planet;
	private ArrayList<Building> buildings;
	private TextField aluminiumAmount;
	private TextField siliconAmount;
	private TextField titaniumAmount;
	private TextField asoiumAmount;

	public PlanetScreen(Planet planet, ArrayList<Building> buildings, Main main) {
		this.main = main;
		this.planet = planet;
		this.buildings = buildings;
	}

	@Override
	public void show() {
		this.stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		Table table = new Table();
		table.setFillParent(true);
		table.left().top();

		TextButton returnButton = new TextButton("Back", RIPStyle.getButtonSkin(200));
		returnButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PlanetScreen.this.main.returnToPlanetSelection();
			}
		});

		float height = 100;
		float width = Gdx.graphics.getWidth() / 4;

		TextField planetName = new TextField("Planet " + planet.getPlanetID(), RIPStyle.getTextFieldSkin(height));
		TextField aluminium = new TextField("Aluminium", RIPStyle.getTextFieldSkin(height));
		TextField silicon = new TextField("Silicon", RIPStyle.getTextFieldSkin(height));
		TextField titanium = new TextField("Titanium", RIPStyle.getTextFieldSkin(height));
		TextField asoium = new TextField("Asoium", RIPStyle.getTextFieldSkin(height));
		aluminiumAmount = new TextField("", RIPStyle.getTextFieldSkin(height));
		siliconAmount = new TextField("", RIPStyle.getTextFieldSkin(height));
		titaniumAmount = new TextField("", RIPStyle.getTextFieldSkin(height));
		asoiumAmount = new TextField("", RIPStyle.getTextFieldSkin(height));

		planetName.setAlignment(CENTER);
		aluminium.setAlignment(CENTER);
		silicon.setAlignment(CENTER);
		titanium.setAlignment(CENTER);
		asoium.setAlignment(CENTER);
		aluminiumAmount.setAlignment(CENTER);
		siliconAmount.setAlignment(CENTER);
		titaniumAmount.setAlignment(CENTER);
		asoiumAmount.setAlignment(CENTER);

		table.add(planetName).width(width).height(height);
		table.add(titanium).width(width).height(height);
		table.add(aluminium).width(width).height(height);
		table.add(silicon).width(width).height(height);
		table.add(asoium).width(width).height(height);
		table.row();

		table.add(titaniumAmount).width(width).height(height);
		table.add(aluminiumAmount).width(width).height(height);
		table.add(siliconAmount).width(width).height(height);
		table.add(asoiumAmount).width(width).height(height);
		table.row();

		for (int i = 0; i < buildings.size(); i++) {
			TextField textField = new TextField(buildings.get(i).getName(), RIPStyle.getTextFieldSkin(height));
			table.add(textField).colspan(4).height(height).width(Gdx.graphics.getWidth());
			table.row();
		}

		stage.addActor(returnButton);
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		planet.update(delta);
		aluminiumAmount.setText("" + planet.getAluminiumTons() + " K");
		siliconAmount.setText("" + planet.getSiliconTons()+ " K");
		titaniumAmount.setText("" + planet.getTitaniumTons()+ " K");
		asoiumAmount.setText("" + planet.getAsoiumTons()+ " K");

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

