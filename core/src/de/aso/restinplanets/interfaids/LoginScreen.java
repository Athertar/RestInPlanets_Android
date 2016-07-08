package de.aso.restinplanets.interfaids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;

import de.aso.restinplanets.net.Client;
import de.aso.restinplanets.util.FontCreator;

import static com.badlogic.gdx.Input.Keys.CENTER;

public class LoginScreen implements Screen {

	private SpriteBatch spriteBatch;

	private FontCreator fontCreator;

	private BitmapFont textFont;
	private BitmapFont smallTextFont;

	private Skin buttonSkin;
	private Skin textFieldSkin;

	private Pixmap pixmap;

	private Stage stage;

	private float buttonLetterHeight;
	private float textFieldLetterHeight;
	private float borderOffset = 20f;

	private TextButton.TextButtonStyle textButtonStyle;
	private TextButton loginButton;
	private TextButton signUpButton;

	private TextField.TextFieldStyle textFieldStyle;
	private TextField accountInput;
	private TextField passwordInput;


	private Main main;
	private Client client;

	public LoginScreen(Main main) {
		this.main = main;
		spriteBatch = new SpriteBatch();
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
		buttonSkin = new Skin();
		textFieldSkin = new Skin();

		try {
			client = new Client(InetAddress.getByName("192.168.1.8"), 6666);
		} catch (IOException e) {
			//TODO
			e.printStackTrace();
		}

		fontCreator = new FontCreator("LemonMilk.otf", 4, 2, 2, 5);
		textFont = fontCreator.generateFont(96);
		smallTextFont = fontCreator.generateFont(46);
		fontCreator.dispose();

		buttonSkin.add("default", textFont);
		textFieldSkin.add("default", smallTextFont);

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



		loginButton = new TextButton("log in", buttonSkin);
		loginButton.setPosition(borderOffset, Gdx.graphics.getHeight() - 7* textFieldLetterHeight);
		loginButton.setHeight(buttonLetterHeight);
		loginButton.setWidth(Gdx.graphics.getWidth() / 2 - borderOffset);
		loginButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				String account = accountInput.getText();
				String password = passwordInput.getText();
				if (account != null && password != null) {
					try {
						if (client.verify(account, password)) {
							Gdx.app.log("Login Screen", "Verification successful");
							main.setScreen(new PlanetSelectionScreen(client, main, account));
						} else {
							Gdx.app.log("Login Screen", "Verification failed");
							//TODO
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
				}
			}
		});
		stage.addActor(loginButton);

		signUpButton = new TextButton("sign up", buttonSkin);
		signUpButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 7* textFieldLetterHeight);
		signUpButton.setHeight(buttonLetterHeight);
		signUpButton.setWidth(Gdx.graphics.getWidth() / 2 - borderOffset);
		stage.addActor(signUpButton);

		accountInput = new TextField("", textFieldSkin);
		accountInput.setMessageText("account:");
		accountInput.setAlignment(CENTER);
		accountInput.setWidth(Gdx.graphics.getWidth() - 2*borderOffset);
		accountInput.setHeight(3*smallTextFont.getXHeight());
		accountInput.setPosition(borderOffset, Gdx.graphics.getHeight() - 2* textFieldLetterHeight);
		stage.addActor(accountInput);

		passwordInput = new TextField("", textFieldSkin);
		passwordInput.setMessageText("password:");
		passwordInput.setAlignment(CENTER);
		passwordInput.setWidth(Gdx.graphics.getWidth() - 2*borderOffset);
		passwordInput.setHeight(3*smallTextFont.getXHeight());
		passwordInput.setPosition(borderOffset, Gdx.graphics.getHeight() - 4* textFieldLetterHeight);
		passwordInput.setPasswordMode(true);
		stage.addActor(passwordInput);

	}
}
