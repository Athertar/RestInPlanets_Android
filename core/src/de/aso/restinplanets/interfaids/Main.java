package de.aso.restinplanets.interfaids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.net.InetAddress;

import de.aso.restinplanets.net.Client;

public class Main extends Game {

	public Client client;

	@Override
	public void create() {
		setScreen(new IntroScreen());
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
	}

	@Override
	public void render() {
		super.render();
	}

}
