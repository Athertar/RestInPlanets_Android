package de.aso.restinplanets.interfaids;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.io.IOException;

import de.aso.restinplanets.net.Client;
import de.aso.restinplanets.net.Planet;

import static com.badlogic.gdx.Input.Keys.CENTER;

public class PlanetScreen implements Screen {

	private SpriteBatch spriteBatch;

	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

	private BitmapFont textFont;
	private BitmapFont smallTextFont;

	private Stage stage;
	private Skin textFieldSkin;
	private Skin buttonSkin;
	private Pixmap pixmap;

	private float buttonLetterHeight;
	private float textFieldLetterHeight;
	private float borderOffset = 20f;

	private TextField.TextFieldStyle textFieldStyle;
	private TextField inputField;
	private TextField titaniumAmount;
	private TextField siliconAmount;
	private TextField asoiumAmount;
	private TextField aluminiumAmount;

	private TextButton.TextButtonStyle textButtonStyle;
	private TextButton databaseButton;

	private Client client;
	private Planet planet;


	public PlanetScreen(Client client) {
		this.client = client;
		create();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(planet != null) {
			planet.update(delta);
			reDrawResources();
		}
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
		textFieldSkin = new Skin();
		buttonSkin = new Skin();

		generator = new FreeTypeFontGenerator(Gdx.files.internal("LemonMilk.otf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 46;
		parameter.borderWidth = 4;
		parameter.shadowOffsetX = 2;
		parameter.shadowOffsetY = 2;
		parameter.spaceX = 5;
		smallTextFont = generator.generateFont(parameter);
		parameter.size = 96;
		textFont = generator.generateFont(parameter);
		generator.dispose();

		textFieldSkin.add("default", smallTextFont);
		buttonSkin.add("default", textFont);

		textFieldLetterHeight = 3*smallTextFont.getXHeight();
		buttonLetterHeight = 3*textFont.getXHeight();

		pixmap = new Pixmap(Gdx.graphics.getWidth()/4,Gdx.graphics.getWidth(), Pixmap.Format.RGB888);
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

		textFieldStyle = new TextField.TextFieldStyle();
		textFieldStyle.font = textFieldSkin.getFont("default");
		textFieldStyle.fontColor = Color.WHITE;
		textFieldStyle.background = textFieldSkin.newDrawable("background", Color.LIGHT_GRAY);
		textFieldSkin.add("default", textFieldStyle);

		databaseButton = new TextButton("Fetch Planet", buttonSkin);
		databaseButton.setHeight(buttonLetterHeight);
		databaseButton.setPosition(borderOffset, Gdx.graphics.getHeight()-7*textFieldLetterHeight-borderOffset);
		databaseButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				String userInput = inputField.getText();
				if(userInput != null) {
					try {
						planet = client.receivePlanet(Long.parseLong(userInput));
						reDrawResources();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		stage.addActor(databaseButton);

		inputField = new TextField("", textFieldSkin);
		inputField.setMessageText("Enter Planet ID: ");
		inputField.setAlignment(CENTER);
		inputField.setWidth(Gdx.graphics.getWidth() / 4 - borderOffset/4);
		inputField.setHeight(textFieldLetterHeight);
		inputField.setPosition(borderOffset, Gdx.graphics.getHeight()-4*textFieldLetterHeight-borderOffset);
		stage.addActor(inputField);

		float fieldWidth = Gdx.graphics.getWidth() / 4 - borderOffset/2;

		aluminiumAmount = new TextField("", textFieldSkin);
		aluminiumAmount.setPosition(borderOffset, Gdx.graphics.getHeight()-2*textFieldLetterHeight-borderOffset);
		aluminiumAmount.setAlignment(CENTER);
		aluminiumAmount.setWidth(fieldWidth);
		aluminiumAmount.setHeight(textFieldLetterHeight);
		stage.addActor(aluminiumAmount);

		siliconAmount = new TextField("", textFieldSkin);
		siliconAmount.setPosition(borderOffset + fieldWidth, Gdx.graphics.getHeight()-2*textFieldLetterHeight-borderOffset);
		siliconAmount.setAlignment(CENTER);
		siliconAmount.setWidth(fieldWidth);
		siliconAmount.setHeight(textFieldLetterHeight);
		stage.addActor(siliconAmount);

		titaniumAmount = new TextField("", textFieldSkin);
		titaniumAmount.setPosition(borderOffset + 2*fieldWidth, Gdx.graphics.getHeight()-2*textFieldLetterHeight-borderOffset);
		titaniumAmount.setAlignment(CENTER);
		titaniumAmount.setWidth(fieldWidth);
		titaniumAmount.setHeight(textFieldLetterHeight);
		stage.addActor(titaniumAmount);

		asoiumAmount = new TextField("", textFieldSkin);
		asoiumAmount.setPosition(borderOffset + 3*fieldWidth, Gdx.graphics.getHeight()-2*textFieldLetterHeight-borderOffset);
		asoiumAmount.setAlignment(CENTER);
		asoiumAmount.setWidth(fieldWidth);
		asoiumAmount.setHeight(textFieldLetterHeight);
		stage.addActor(asoiumAmount);

	}

	public void reDrawResources() {
		if(planet != null) {
			aluminiumAmount.setText("" + planet.getAluminiumTons());
			siliconAmount.setText("" + planet.getSiliconTons());
			titaniumAmount.setText("" + planet.getTitaniumTons());
			asoiumAmount.setText("" + planet.getAsoiumTons());
		}
	}
}

