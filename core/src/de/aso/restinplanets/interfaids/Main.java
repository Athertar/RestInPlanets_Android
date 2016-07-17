package de.aso.restinplanets.interfaids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.io.IOException;
import java.net.InetAddress;

import de.aso.restinplanets.net.Client;
import de.aso.restinplanets.net.Planet;
import de.aso.restinplanets.util.FontCreator;

public class Main extends Game {

	public Client client;
	private String player;

	@Override
	public void create() {
		final Thread clientConnector = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					client = new Client(InetAddress.getByName("restinplanets.ddns.net"), 6666);
					Thread.sleep(4000);
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							final LoginScreen loginScreen = new LoginScreen(Main.this);
							Main.this.setScreen(loginScreen);
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		clientConnector.start();
		setScreen(new IntroScreen());
	}

	@Override
	public void render() {
		super.render();
	}

	public void showPlanetScreen(long planetID) {
		try {
			setScreen(new PlanetScreen(client.requestPlanet(planetID), this));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showPlanetSelectionScreen(String playerName) {
		try {
			this.player = playerName;
			setScreen(new PlanetSelectionScreen(client.requestPlayersPlanets(playerName), this));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void returnToPlanetSelection() {
		try {
			setScreen(new PlanetSelectionScreen(client.requestPlayersPlanets(player), this));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
