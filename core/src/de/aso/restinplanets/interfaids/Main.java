package de.aso.restinplanets.interfaids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import de.aso.restinplanets.net.Building;
import de.aso.restinplanets.net.Client;
import de.aso.restinplanets.net.Planet;

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
					Thread.sleep(1000);
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

	public void showPlanetScreen(final long planetID) {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				try {
					Planet planet = client.requestPlanet(planetID);
					ArrayList<Building> buildings = client.requestBuildingsOnPlanet(planetID);
					setScreen(new PlanetScreen(planet, buildings, Main.this));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void showPlanetSelectionScreen(final String playerName) {
		this.player = playerName;
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				try {
					setScreen(new PlanetSelectionScreen(client.requestPlayersPlanets(playerName), Main.this));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void returnToPlanetSelection() {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				try {
					setScreen(new PlanetSelectionScreen(client.requestPlayersPlanets(player), Main.this));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
