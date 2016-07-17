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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import de.aso.restinplanets.util.FontCreator;
import de.aso.restinplanets.util.RIPStyle;

import static com.badlogic.gdx.Input.Keys.CENTER;

public class LoginScreen implements Screen {

	private Stage stage;

	private TextButton loginButton;
	private TextButton signUpButton;
	private TextField accountInput;
	private TextField passwordInput;

	private float buttonHeight = 200;
	private float buttonWidth = 400;
	private float textFieldWidth = 400;
	private float textFieldHeight = 200;

	private Main main;

	public LoginScreen(Main main) {
		this.main = main;
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		loginButton = new TextButton("log in", RIPStyle.getButtonSkin(buttonHeight));
		float borderOffset = 20f;
		loginButton.setPosition(borderOffset, Gdx.graphics.getHeight() / 2 - buttonHeight);
		loginButton.setHeight(buttonHeight);
		loginButton.setWidth(buttonWidth - borderOffset);
		loginButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				String account = accountInput.getText();
				String password = passwordInput.getText();
				if (account != null && password != null) {
					try {
						if (main.client.verify(account, password)) {
							Gdx.app.log("Login Screen", "Verification successful");
							Gdx.input.setOnscreenKeyboardVisible(false);
							main.showPlanetSelectionScreen(account);
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

		signUpButton = new TextButton("sign up", RIPStyle.getButtonSkin(buttonHeight));
		signUpButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - buttonHeight);
		signUpButton.setHeight(buttonHeight);
		signUpButton.setWidth(buttonWidth - borderOffset);
		stage.addActor(signUpButton);

		accountInput = new TextField("", RIPStyle.getTextFieldSkin(textFieldHeight));
		accountInput.setMessageText("account:");
		accountInput.setAlignment(CENTER);
		accountInput.setWidth(Gdx.graphics.getWidth() - 2* borderOffset);
		accountInput.setHeight(textFieldHeight);
		accountInput.setPosition(borderOffset, Gdx.graphics.getHeight() - 2 * textFieldHeight);
		stage.addActor(accountInput);

		passwordInput = new TextField("", RIPStyle.getTextFieldSkin(textFieldHeight));
		passwordInput.setMessageText("password:");
		passwordInput.setAlignment(CENTER);
		passwordInput.setWidth(Gdx.graphics.getWidth() - 2* borderOffset);
		passwordInput.setHeight(textFieldHeight);
		passwordInput.setPosition(borderOffset, Gdx.graphics.getHeight() - 4 * textFieldHeight);
		passwordInput.setPasswordMode(true);
		stage.addActor(passwordInput);
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
